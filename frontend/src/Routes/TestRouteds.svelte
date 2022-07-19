<script>
    import {Route} from "svelte-navigator";
    import ImageGrid from "../Components/ReusableComponents/LayoutComponents/ImageGrid/ImageGrid.svelte";
    import StoreDisplay from "../Components/Store/StoreDisplay.svelte";
    import ProductCreateView from "../Components/Store/ProductViews/ProductCreateView.svelte";
    import ProductUpdateView from "../Components/Store/ProductViews/ProductUpdateView.svelte";
    import PaymentView from "../Components/Payment/PaymentView.svelte";
    import PlaceholderRectangle from "../Components/LoadingPlaceHolders/PlaceholderRectangle.svelte";
    import RectanglePanelComponent from "../Components/ReusableComponents/ListPanels/RectanglePanelComponent.svelte";
    import TestComponent from "../Components/Testing/TestComponent.svelte";
</script>

<Route path="test">
    <RectanglePanelComponent msg="this is a sample msg"/>
    <TestComponent/>
</Route>
