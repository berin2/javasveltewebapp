import ReusableFormProps from "./ReusableFormProps";

class RadioInputProp extends ReusableFormProps
{
    constructor(
            public radioIndex: number,
            public col:string = " col-12 ",
            public radioValue:any = true,
            labelValue: string = "",
            spanValue: string = "",
            iconValue: string = "",
            validator: (arg:any) => boolean =  (arg:any) =>{ return true}
        )
    { super(labelValue,spanValue,iconValue,validator)}
}
export default RadioInputProp;