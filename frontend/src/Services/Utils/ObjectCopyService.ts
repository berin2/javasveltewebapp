class ObjectCopyService
{
    static readonly ARRAY:string = typeof [];
    static readonly OBJ:string = typeof {};

    /**
     * Copies props of arg into a brand new empty object. Helpful in triggering sv eltye
     * @param arg
     */
    public static shallowCopy(arg:object):object
    {
        let returnObj: object  = {};
        for(let prop in arg)
            returnObj[prop] =  arg[prop];

        return returnObj;
    }

    public static deepCopy(arg:object):object
    {
        let returnObj: object  = {};
        let propIterator: string;

        for(let prop in arg)
        {
            propIterator = typeof returnObj[prop];

            switch (propIterator)
            {
                case this.ARRAY:
                case this.OBJ:
                    returnObj[prop] = this.deepCopy(arg[prop]);
                    break;
                default:
                    returnObj[prop] =  arg[prop];
                    break;
            }
            returnObj[prop] =  arg[prop]
        }

        return returnObj;
    }
}

export default ObjectCopyService;