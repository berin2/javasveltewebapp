import {isFalsy} from "../../Validators/IsFalsyObjectValidator";

export default class StringUtils {
    /**
     * truncates a string to specified length and appends .. to the end of the string.
     * @param str the str to truncate
     * @param length
     */
    public static truncate(str: string, length:number,falsyStrReplacement: string = "..."){
        let retVal:string = falsyStrReplacement;
        if(!isFalsy(str)&& str.length > 0 )
        {
            retVal = str.substring(0,length) + "..."
        }
        return retVal;
    }
}