package com.svelteup.app.backend.aws.s3.services;






import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.svelteup.app.backend.aws.s3.services.interfaces.AwsService;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.utils.events.OwningUserEvent;
import com.svelteup.app.backend.utils.services.interfaces.IUserLifeCycle;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.NotSupportedException;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

@Service
@EqualsAndHashCode()
@Profile("dev")
public class SImageS3  extends SHttpExceptionThrower implements IUserLifeCycle<Object,OwningUserEvent> {
    public static String PNG_SPECIFIER = "data:image/png;base64,";
    public static Integer PNG_SPECIFIER_LENGTH = PNG_SPECIFIER.length();
    protected AmazonS3 s3Client;

    protected String bucketName;
    protected final String OBJECT_USER_FOLDER_PATH = "users/%s";
    protected final String OBJECT_USER_ENTITY_FOLDER_PATH = "users/%s/%s";
    protected final String OBJECT_USER_ENTITY_FOLDER_IMAGE_PATH = "users/%s/%s/%s";
    protected final String OBJECT_USER_ENTITY_FOLDER_IMAGE_PATH_WITH_MULITPLE_ENTITIES = "users/%s/%s/%s/%s";
    protected ApplicationEventPublisher applicationEventPublisher;
    protected TransferManager s3ClientTransferManager;

    public static Integer MAXIMUM_IMAGE_SIZE = 1500000;
    protected OutputStream fileOutPutStream;

    public SImageS3(Environment springEnv, AmazonS3 s3Client, TransferManager transferManager)
    {
        this.bucketName = springEnv.getProperty("cloud.aws.bucket.bucket-name");
        this.s3Client = s3Client;
        this.s3ClientTransferManager = transferManager;
        this.fileOutPutStream = fileOutPutStream;
    }

    @PostConstruct
    public void onInit()
    {
       if(!s3Client.doesBucketExistV2(bucketName))
           s3Client.createBucket(bucketName);
    }

    @PreDestroy
    public void onDestroy()
    {
       // if(s3Client.doesBucketExistV2(bucketName))
           // s3Client.deleteBucket(bucketName);
    }


    protected boolean validateImageFile(String uploadedImage){
        boolean isValid = false;
        int pngSpecifierLength = PNG_SPECIFIER.length();
        if(uploadedImage != null)
        {
            isValid = uploadedImage.length() > SImageS3.PNG_SPECIFIER_LENGTH &&
                      uploadedImage.substring(0, PNG_SPECIFIER_LENGTH).contains(PNG_SPECIFIER);
        }

        return isValid;
    }
    protected List<String> processImageFiles(List<String> fileList)
    {
        List<String> returnList = new ArrayList<>();

        if(fileList != null && fileList.size() > 0)
            for(String listString : fileList)
                if(listString != null)
                    returnList.add(listString);

        return returnList;
    }

    @Override
    /**
     * initializeUser creates a bucket for a username
     * @param username The username to create the object for.
     * @throws Http500Exception when the bucket object already exists.
     */
    public Object initializeUser(OwningUserEvent userEvent) throws Http500Exception
    {
        File emptyFile = null;
        String userFolder = this.getUserFolderString(userEvent.getOwningUsername());

        if(!s3Client.doesObjectExist(this.bucketName,userFolder))
            s3Client.putObject(this.bucketName,userFolder,emptyFile);
        else
            this.throwHttp500("initializeUser",this.getClass().getName(),userEvent.getOwningUsername(),"The username bucket object folder has already been created.");

        return null;
    }

    @Override
    public void destroyVerifiedUser(String username) {
        String userFolder = String.format(OBJECT_USER_FOLDER_PATH,username);

        if(s3Client.doesObjectExist(this.bucketName,userFolder))
            s3Client.deleteObject(this.bucketName,userFolder);
        else
            this.throwHttp500("destroyUser",this.getClass().getName(),username,"The username bucket object folder has not been been created.");
    }

    protected String getUserFolderString(String username)
    {
        return String.format(OBJECT_USER_FOLDER_PATH,username);
    }


    public String [] getMultipleImages(Class imageClass, String username, Integer maxIndexSize, Object identifier) throws IOException {
        String [] multipleImages = new String[maxIndexSize];
        String returnImageString = null;
        Map<Integer,String>   imageMap = new LinkedHashMap<>();

        for(int i = 0; i < maxIndexSize; i++)
        {

            returnImageString = this.getSingleImage(imageClass, username, i, identifier);
            if(returnImageString != null)
                imageMap.put(i,returnImageString);
        }

        multipleImages = this.processImageObjectToStringArray(imageMap.values().toArray());

        return multipleImages;
    }

    protected String [] processImageObjectToStringArray(Object [] stringArray)
    {
        String [] returnArray = null;

        if(stringArray != null &&  stringArray.length > 0)
            returnArray = new String[stringArray.length];
        else
            returnArray = new String[0];

        for(int  i = 0; i < stringArray.length; i++)
            returnArray[i] = stringArray[i].toString();

        return returnArray;
    }


    public String buildFilePathForMultipleOrSingularEntites(Class imageClass,String username,Integer imageindex, Object identifier)
    {
        String fileString = null;

        if(identifier == null)
            fileString = this.buildObjectFilePath(imageClass.getCanonicalName(),username,imageindex);
        else
            fileString = this.buildObjectFilePathForEntitiesWithMultipleInstances(imageClass.getCanonicalName(),username,imageindex,identifier);

        return fileString;
    }
    public String getSingleImage(Class imageClass,String username,Integer imageindex, Object identifier) throws IOException {


        String fileName = this.buildFilePathForMultipleOrSingularEntites(imageClass,username,imageindex,identifier);
        byte [] imageFile = null;
        String returnByteString = null;

      boolean exists = s3Client.doesObjectExist(bucketName,fileName);

      if(exists)
      {
            S3ObjectInputStream inputStream =
                    this.s3Client.getObject(this.bucketName, fileName)
                    .getObjectContent();

            imageFile = StreamUtils.copyToByteArray(inputStream);
            returnByteString = new String(imageFile);
        }


        return returnByteString;
    }

    public void put(String authenticatedUser, List<String> imageStrings, Object identifier, Class entityClass) throws Http400Exception, Http403Exception, Http500Exception, NotSupportedException, InterruptedException, IOException {
        PutObjectRequest putObjectRequestIterator = null;
        List<File> fileToDeleteList = new ArrayList<>();
        List<Upload> uploadList = new ArrayList<>();
        File fileIterator = null;
        FileOutputStream fileWriter =  null;
        byte [] imageStringBytes  = null;
        int imageIndex  = 0;
        List<String> processedImageStrings = this.processImageFiles(imageStrings);

        for(String file : processedImageStrings)
        {
            imageStringBytes = file.getBytes(StandardCharsets.UTF_8);
            if(imageStringBytes != null && imageStringBytes.length > 0 && imageStringBytes.length <= MAXIMUM_IMAGE_SIZE)
            {
                String fileName = this.buildFilePathForMultipleOrSingularEntites(entityClass, authenticatedUser, imageIndex, identifier);
                fileIterator = Files.createTempFile("img", ".txt").toFile();
                fileWriter = new FileOutputStream(fileIterator);
                fileWriter.write(imageStringBytes);

                fileToDeleteList.add(fileIterator);
                putObjectRequestIterator = this.buildPutObjectRequest(entityClass, authenticatedUser, imageIndex, fileName, fileIterator, identifier);
                Upload newUpload = this.s3ClientTransferManager.upload(putObjectRequestIterator);
                uploadList.add(newUpload);
            }
            imageIndex++;
        }

        //ensures uploads finish
        for(Upload upload: uploadList)
            upload.waitForUploadResult();

        //deletes files from JVM instance after uploads are complete.
        for(File fileToDelete: fileToDeleteList)
            if(fileToDelete != null)
                fileToDelete.delete();

        List<Integer> deleteList = this.buildDeleteList(imageStrings);

        if(deleteList != null && deleteList.size() > 0)
            this.delete(authenticatedUser,deleteList,identifier,entityClass);

    }


    public void delete(String authenticatedUser, List<Integer> deleteIndexes, Object identifier, Class entityClass) throws Http400Exception, Http403Exception, Http500Exception, NotSupportedException {
        for(Integer imageIndex: deleteIndexes) {
            String objectPath = this.buildFilePathForMultipleOrSingularEntites(entityClass, authenticatedUser, imageIndex,identifier);
            if(s3Client.doesObjectExist(this.bucketName,objectPath))
                s3Client.deleteObject(this.bucketName,objectPath);
        }

    }

    protected PutObjectRequest buildPutObjectRequest(Class entity,String username,Integer index,String key, File fileToUpload, Object identifier)
    {
        String objectKey =  buildFilePathForMultipleOrSingularEntites(entity,username,index,identifier);
        return  new PutObjectRequest(bucketName,objectKey,fileToUpload);
    }
    protected String buildObjectFilePath(String entity,String username,Integer index)
    {
        entity = entity.toUpperCase(Locale.ROOT);
        return  String.format(OBJECT_USER_ENTITY_FOLDER_IMAGE_PATH,username,entity,index.toString());
    }

    protected String  buildObjectFilePathForEntitiesWithMultipleInstances(String entity,String username,Integer index,Object identifier){
        entity = entity.toUpperCase(Locale.ROOT);
        return  String.format(OBJECT_USER_ENTITY_FOLDER_IMAGE_PATH_WITH_MULITPLE_ENTITIES,username,entity,identifier.toString(),index.toString());
    }

    public File getFileFromImage(byte [] presentedByteArray) throws IOException {
        File tempBuffer = null;
       try
       {
           tempBuffer =  File.createTempFile("temp",".jpeg");
           FileOutputStream stream = new FileOutputStream(tempBuffer);
           stream.write(presentedByteArray);
      }
       catch(IOException ioException)
       { }
       catch(Exception e)
       { }
       finally { }

       return tempBuffer;
    }

    public List<Integer>  buildDeleteList(List<String> list)
    {
        List<Integer> returnList = new ArrayList<>();

        if(list != null)
            for(int i = list.size(); i < Product.MAXIMUM_IMAGES; i++)
                returnList.add(i);

        return returnList;
    }

    public String convertResourceToString(Resource springResource) throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream(springResource.getFile());
        String returnString = StreamUtils.copyToString(fileInputStream, Charset.defaultCharset());
        return returnString;
    }
    public String getTestProfileImage()
    {return null;}

    public String getTestProductImage() throws IOException {
        Resource springFile = new ClassPathResource("/static/images/profileImg.txt");
        String byteString = this.convertResourceToString(springFile);
        return byteString;
    }

    public String [] getTestProductImageArray() throws IOException {
        Resource springFile = new ClassPathResource("/static/images/productImage.txt");
        String byteString = this.convertResourceToString(springFile);
        String [] stringArray = new String[]{byteString,byteString,byteString};
        return stringArray;
    }


}
