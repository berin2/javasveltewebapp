import type ViewType from "../../Dto/ViewType";
import {throwError} from "svelte-preprocess/dist/modules/errors";

function defualtErrorFunctions(functionName:string) {
    throwError("ViewProps object function was not implemented " + functionName);
}

class ViewProps {

    constructor(
        public viewType: ViewType,
        public dispatchFunction: () =>void = () => defualtErrorFunctions("dispatchfunction"),
        public onMountFunction: () => void = () => defualtErrorFunctions("onMountFunction"),
        public onDestroyFunction:() => void = () => defualtErrorFunctions("onDestroyFunction"),
        public onUpdateFunction:() => void =() => defualtErrorFunctions("onUpdateFunction"),
        public errorFlag: boolean = false,
        public errorMsg: string =  ""
    ) {}
}

export default ViewProps;