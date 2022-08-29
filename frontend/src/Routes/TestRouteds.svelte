<script>
    import {Route} from "svelte-navigator";
    import RectanglePanelComponent from "../Components/ReusableComponents/ListPanels/RectanglePanelComponent.svelte";
    import TestComponent from "../Components/Testing/TestComponent.svelte";
</script>

<Route path="test">
    <RectanglePanelComponent msg="this is a sample msg"/>
    <TestComponent/>
</Route>
