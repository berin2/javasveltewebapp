
class ReusableFormProps
{
    public constructor(public labelValue: string, public spanValue:string, iconValue:string, validator: (arg:any) => void = (arg:any) => {}) {}
}

export default ReusableFormProps;