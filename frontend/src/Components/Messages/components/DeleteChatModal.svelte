<script lang="ts">
    export let modalActive: boolean = false;
    export let dispatchFunction:(arg:any) =>any =  ()  => {};

    function onClick(){

    }
</script>
<style>
    .app-modal-z-index-override
    {
        z-index: 9991 !important;
    }
</style>

<section class="app-modal app-modal-z-index-override"></section>