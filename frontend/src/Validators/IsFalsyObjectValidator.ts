/**returns true or false if arg is detected to be falsy
 * @param arg the argument to test
 * @returns true if arg is false, false if not
 */
import {stop_propagation} from "svelte/internal";
import type {Writable} from "svelte/store";

function isFalsy(arg){
    return (arg === null || arg === undefined || arg === '' || arg === 0)
}

function isFalsyStore(store:Writable<any>, storeValue:any) : boolean
{
    return isFalsy(store) && isFalsy(storeValue)
}

function isFalsyArray(array: any []):boolean
{
    return !isFalsy(array) && array.length===0;
}

/**Tests if arg is an instance of a Javascript functionl
 * @param arg the argument to test
 * @returns true if arg is of a Function, false if not
 */
function isFunction(arg) {
    return arg instanceof Function;
}



export { isFalsy,isFalsyStore, isFalsyArray,isFunction }