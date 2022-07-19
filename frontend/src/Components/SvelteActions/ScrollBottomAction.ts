import {isFalsy} from "../../Validators/IsFalsyObjectValidator";
import StoreService from "../../Stores/StoreService";

const SCROLL_BOTTOM_REACHED: string = "SCROLL_BOTTOM_REACHED";
const SCROLL_TOP_REACHED: string = "SCROLL_TOP_REACHED";
const LOGOUT_ACTION:string = "LOGOUT_ACTION";

function ScrollTopAction (event:Event, dispatchFunction: () => void)
{

    let target:EventTarget = event.target;

    if(target instanceof Element)
    {
        let elementTarget:Element=  target;
        if(elementTarget.scrollTop === 0)
            if(!isFalsy(dispatchFunction))
                dispatchFunction();
    }
}


function ScrollBottomAction(scrollNode, offsetBoundary:number  = 0){

    function scrollHandler(event)
    {
            if(scrollNode.scrollHeight - Math.abs(scrollNode.scrollTop) === scrollNode.clientHeight)
                scrollNode.dispatchEvent(
                    new CustomEvent(SCROLL_BOTTOM_REACHED, scrollNode)
                )
    }

    scrollNode.addEventListener("scroll", scrollHandler,true);
    return {destroy() {document.removeEventListener(SCROLL_BOTTOM_REACHED, scrollHandler, true);}}
}

function ResetStoreAction(node:Node)
{
    alert("Reset action")
    document.addEventListener(LOGOUT_ACTION, () => {StoreService.resetStore();alert("logout!!")});
    return {destroy(){document.removeEventListener(LOGOUT_ACTION,StoreService.resetStore)}}
}

export {ScrollBottomAction,ScrollTopAction, ResetStoreAction,SCROLL_TOP_REACHED,SCROLL_BOTTOM_REACHED,LOGOUT_ACTION};