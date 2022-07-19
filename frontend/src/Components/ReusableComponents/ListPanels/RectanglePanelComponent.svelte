<script lang="ts">
    import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";

    export let img:string|null = null;
    export let msg:string|null = null;

    $:showImg =!isFalsy(img) ? true : false;
    $:showMsg = !isFalsy(msg) ? true : false;
</script>
<style>
    .color-styling
    {
        background-color: #f0f0f0;
        height:65px;
        width:100%;
    }

    .inner-content-styling
    {
        background-color: #d6d6d6;
        height: 40px;
        width: 40px;
    }

    .inner-content-styling-row
    {
        background-color: #d6d6d6;
        border-radius: 4px;
        height: 40px;
        width: 100%;
    }
    .icon-image
    {
        font-size: 45px;
    }

    .msg-panel
    {
        color: black;
        font-size: 10px;
        height: 30px;
        overflow: hidden;
    }

</style>
    <div class="color-styling container-fluid w-100">
        <div class="row h-100 p-2 ">
            <div class="col-4 d-flex flex-row justify-content-center align-items-center">
              <!--  <div class="inner-content-styling img-thumbnail"> </div>-->
                {#if showImg}
                {:else}
                    <i class="bi bi-person-circle text-secondary icon-image "></i>
                {/if}
            </div>
            <div class="col-6 text-center">
                <div class="d-flex flex-row justify-content-center align-items-center">
                    <p class="form-text overflow-hidden msg-panel">{msg}</p>
                </div>
            </div>
            <div class="col-2 ">
                <slot name="slot-one"></slot>
            </div>
        </div>
    </div>
