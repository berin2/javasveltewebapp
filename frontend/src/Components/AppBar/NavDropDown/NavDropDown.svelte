<script lang="ts">
    import {afterUpdate, onMount} from "svelte";
    import NotificationDto from "../../../Dto/NotificationDto";
    import {SCROLL_BOTTOM_REACHED,ScrollBottomAction} from "../../SvelteActions/ScrollBottomAction";

    export let showDropDown:boolean = true;
    export let parentNavBarId:string = "";
    export let parentIconId:string = "";
    export let headerText: string = "";
    export let id:string = "";
    export let hideFunction: () => void = () => {};
    export let fetchFunction: () => void = () => {};


    let parentNavElement:HTMLElement|null = null;
    let selfElement: HTMLElement|null = null;
    let parentIconElement:HTMLElement|null  = null;
    let scrollFinished:boolean = false;
    let showSpinner:boolean = true;
    let selfId:string = parentNavBarId + "-" + id;
    let scrollBoundary: number = 30;

    export let dropDownElements: any [] = [];
    $: reactiveDropDownElements = dropDownElements;

    export function clickOutside(node) {

        const handleClick = event => {
            if (node && !node.contains(event.target) && !event.defaultPrevented) {
                hideFunction();
            }
        }

        document.addEventListener('click', handleClick, true);

        return {
            destroy() {
                document.removeEventListener('click', handleClick, true);
            }
        }
    }

    afterUpdate(()=>{
        if(showDropDown) {
            parentNavElement = document.getElementById(parentNavBarId);
            parentIconElement = document.getElementById(parentIconId);
            selfElement = document.getElementById(selfId);
            selfElement.style.top = parentNavElement.offsetHeight + "px";
            selfElement.style.right = parentIconElement.style.right;
        }

    })

    function success(data:any[]){
        dropDownElements = [...dropDownElements, ...data];
        alert("succ")
        showSpinner  =  false;
    }

    function fail(){
        showSpinner = false;
    }

</script>

<style>

    .bg-white {
        background-color: white !important;
    }
    .drop-down-header
    {
        height: 50px;
    }

    .drop-down-background{
        background: rgb(232,232,232);
        background: linear-gradient(90deg, rgba(232,232,232,1) 0%, rgba(255,255,255,1) 100%);
    }

    .load-spinner {
        height: 50px;
    }

    .nav-drop-down
    {
        background-color: white;
        border-bottom: 1px solid gray;
        height: 50vh;

        position: fixed;
        min-width: 25vw;
        width: auto;
        max-width: 50vw;
        overflow-y:scroll;
    }
</style>



{#if showDropDown}
    <section  on:SCROLL_BOTTOM_REACHED|preventDefault = { () => fetchFunction()}  use:clickOutside  use:ScrollBottomAction class="nav-drop-down border shadow-lg overflow-auto drop-down-background " id={selfId} >
        <h5 class=" border-bottom drop-down-header d-flex flex-row align-items-center text-secondary bg-white px-1 my-0">
            {headerText}
        </h5>
        <slot name="content">
        </slot>
        {#if showSpinner}
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12 d-flex flex-row justify-content-between py-2">
                        <strong class="text-secondary">Loading...</strong>
                        <div class=" spinner-grow text-success " role="status" aria-hidden="true"></div>
                    </div>
                </div>
            </div>
        {/if}
    </section>
{/if}