<script lang="ts">
    import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";
    import RadioInput from "./RadioInput.svelte";
    import RadioInputProp from "../../../PropObjects/RadioInputProp";

    const CONTROL_ID: string = (Math.random() * Math.random() * Math.random()).toString();

    export let radioIndex = 0;
    export let radios: RadioInputProp [] = [];
    export let radioGroup:boolean = false;

</script>

<style></style>

<section class="container">
    <section class="row">
        {#each radios as radio}
            {#if !isFalsy(radio)}
                <div class="col-12 d-flex flex-row p-1 justify-content-between">
                    <RadioInput bind:radioGroup={radioGroup} labelValue="test" {...radio} bind:radioValue={radio.radioValue}  />
                </div>
            {/if}
        {/each}
    </section>
</section>
