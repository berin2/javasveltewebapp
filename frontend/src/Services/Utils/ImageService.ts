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

}

export default ImageService;