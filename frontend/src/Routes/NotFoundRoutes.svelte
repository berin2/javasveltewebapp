<script>
    import {Route} from 'svelte-navigator';
    import SvelteBannerContainer
        from "../Components/ReusableComponents/LayoutComponents/SvelteBannerContainer/EmailSetup.svelte";
</script>
<Route path="*">
    <SvelteBannerContainer bannerHeader="Email"/>
</Route>