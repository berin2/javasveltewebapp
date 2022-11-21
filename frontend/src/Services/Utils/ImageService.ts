import {isFalsy} from "../../Validators/IsFalsyObjectValidator";

class ImageService
{
    public static readonly PNG_DATA:string = "data:image/png;base64,";
    public static readonly JPEG_DATA: string = "data:image/jpeg;base64,"
    public static getJpegFrom64ByteArray(byteArrayString:string):string
    {
        return  ImageService.JPEG_DATA + byteArrayString
    }

    public static getStringFromJpegPng(arg:any):string
    {
        return ""+arg;
    }

    public static validatePngOrJpeg(byteArrayString:string):boolean
    {
        return this.validatePng(byteArrayString) || this.validateJpeg(byteArrayString);
    }
    public static validatePng(byteArrayString: string):boolean{
        return !isFalsy(byteArrayString) && byteArrayString.substring(0,this.PNG_DATA.length+1).includes(this.PNG_DATA,0);
    }
    public static validateJpeg(byteArrayString: string): boolean{
        return !isFalsy(!byteArrayString) && byteArrayString.substring(0,this.JPEG_DATA.length).includes(this.JPEG_DATA,0);
    }

}

export default ImageService;