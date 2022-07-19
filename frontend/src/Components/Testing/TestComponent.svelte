<script lang="ts">
    import {ScrollTopAction} from "../SvelteActions/ScrollBottomAction";
    import Graph from "../../Datastructures/Graph";
    import {onMount} from "svelte";

    enum Test {
        ONE,
        TWO,
        THREE,
        UNREACHABLE
    }

    let graph:Graph<Test> = new Graph(Test.UNREACHABLE);

    let testArr = ["test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test"];

    onMount(
        () =>{


        }
    );
</script>

<style>
    .test-div
    {
        min-height: 500px;
        min-width: 500px;
        overflow: scroll;
    }
    .test-p {
        height: 300px;
    }
</style>



<h1>Testing</h1>
<div class="test-div" use:ScrollTopAction>
    {#each testArr as tes}
        <p class="test-p" use:ScrollTopAction>{tes}</p>
    {/each}
</div>