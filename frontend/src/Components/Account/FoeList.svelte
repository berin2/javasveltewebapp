<script lang="ts">
    import ContactDto from "../Contacts/Dto/ContactDto";
    import {isFalsy} from "../../Validators/IsFalsyObjectValidator";
    import CheckInput from "../ReusableComponents/FormControls/CheckInput.svelte";

    export let blockedFoes: ContactDto [] = [
        new ContactDto("svelte", "nelly", null),
        new ContactDto("svelte", "nelly", null),
        new ContactDto("svelte", "nelly", null),
        new ContactDto("svelte", "nelly", null),
        new ContactDto("svelte", "nelly", null)
    ];

    let unblockMap:Map<String,String> = new Map<String,String>();

    function onChecked(username: string):void
    {
        let retrievedValue: string | undefined = unblockMap.get(username);

        if( retrievedValue == undefined) unblockMap.set(username,username);
        else unblockMap.delete(username);
    }

    function dispatchFunction():void {}

</script>
<style>
    .foe-list
    {
        max-height: 300px;
        min-height: 200px;
        overflow-y: scroll;
    }
    .list-row
    {
        box-sizing: border-box;
        height: 60px;
    }
</style>

<section class="w-100 p-3 foe-list">
    {#if !isFalsy(blockedFoes) && !isFalsy(blockedFoes.length)}
        {#each blockedFoes as foe}
            <div class="list-row d-flex flex-row  align-items-center justify-content-between border-bottom">
                <div>
                    <p class="m-1">{foe.username}</p>
                    <p class="text-muted ps-2 m-1">{foe.firstName}</p>
                </div>
                <CheckInput labelValue="" onCheck={()=>onChecked(foe.firstName)}/>
            </div>
        {/each}
    {:else}
        <h5>No foes found.</h5>
        <p>Consider yourself lucky.</p>
    {/if}
</section>

