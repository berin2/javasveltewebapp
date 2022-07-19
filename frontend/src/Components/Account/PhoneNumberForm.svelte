<script lang="ts">
    import {PhoneViewGraphEnum, PhoneViewTypeEnum} from "../../Datastructures/EnumClasses";
    import Graph from "../../Datastructures/Graph";
    import PhoneNumberInput from "../ReusableComponents/FormControls/PhoneNumberInput.svelte";
    import NumberInput from "../ReusableComponents/FormControls/NumberInput.svelte";
    import {fade} from "svelte/transition";

    export let viewType: PhoneViewTypeEnum = PhoneViewTypeEnum.RENDER_BOTH;

    export let phoneNumberButtonText:string = "";
    export let togglVerificationView:boolean = false;


    $: if(togglVerificationView)
    {
        graph = graph.transition(PhoneViewGraphEnum.RENDER_VERIFICATION_INPUT);
    }

    $: if(!togglVerificationView)
    {
        graph = graph.transition(PhoneViewGraphEnum.RENDER_PHONE_VIEW);
    }


    let graph: Graph<PhoneViewGraphEnum> | null = null;


    setUp();

    function setUp(): void {
        graph = new Graph(PhoneViewGraphEnum.RENDER_VERIFICATION_INPUT);

        switch (viewType) {
            case PhoneViewTypeEnum.RENDER_PHONE_INPUT_ONLY:
                graph.currentVertex = PhoneViewGraphEnum.RENDER_PHONE_VIEW;
                break;
            case PhoneViewTypeEnum.RENDER_VERIFICATION_INPUT_ONLY:
                graph.currentVertex = PhoneViewGraphEnum.RENDER_VERIFICATION_INPUT;
                break;
            case PhoneViewTypeEnum.RENDER_BOTH:
                graph.currentVertex = PhoneViewGraphEnum.RENDER_PHONE_VIEW;
                graph.createBiDirectionalEdge(PhoneViewTypeEnum.RENDER_PHONE_INPUT_ONLY, PhoneViewTypeEnum.RENDER_VERIFICATION_INPUT_ONLY);
                break;
        }
    }

    function transition(phoneViewEnum: PhoneViewTypeEnum):void
    {
        if(graph.canTransition(phoneViewEnum))
        {
            graph.transition(phoneViewEnum)
        }
    }

</script>
<style></style>

<div class="d-flex flex-row justify-content-center align-items-center">
    {#if viewType == PhoneViewTypeEnum.RENDER_BOTH && graph.currentVertex == PhoneViewTypeEnum.RENDER_PHONE_INPUT_ONLY }
         <p in:fade={{duration:400}}> Verify an existing phone number <a href="#" class="text-success" on:click|preventDefault={()=>togglVerificationView = true}>here</a></p>
    {:else if viewType == PhoneViewTypeEnum.RENDER_BOTH && graph.currentVertex == PhoneViewTypeEnum.RENDER_VERIFICATION_INPUT_ONLY }
         <p in:fade={{duration:500}}> Enter a new phone number  <a href="#" class="text-success" on:click|preventDefault={()=>togglVerificationView = false}>here</a></p>
    {/if}

</div>

<div class="d-flex flex-row align-items-center justify-content-center h-100 w-100">
    {#if graph.currentVertex == PhoneViewTypeEnum.RENDER_PHONE_INPUT_ONLY}
        <div in:fade={{duration:500}}>
            <PhoneNumberInput />
        </div>
    {:else if graph.currentVertex == PhoneViewTypeEnum.RENDER_VERIFICATION_INPUT_ONLY}
        <div in:fade={{duration:500}}>
            <NumberInput />
        </div>
    {/if}
</div>


