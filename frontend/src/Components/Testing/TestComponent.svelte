<script lang="ts">
    import {ScrollTopAction} from "../SvelteActions/ScrollBottomAction";
    import Graph from "../../Datastructures/Graph";
    import {onMount} from "svelte";
    import PlaceholderSquare from "../LoadingPlaceHolders/PlaceholderSquare.svelte";

    enum Test {
        ONE,
        TWO,
        THREE,
        UNREACHABLE
    }

    let graph: Graph<Test> = new Graph(Test.UNREACHABLE);

    let testArr = ["test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", "test"];

    onMount(
        () => {


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


<PlaceholderSquare />
<PlaceholderSquare />
<PlaceholderSquare />
<PlaceholderSquare />
<PlaceholderSquare />