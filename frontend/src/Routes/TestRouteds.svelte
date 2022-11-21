<script>
   import {Route} from "svelte-navigator";

   import ProfileLetterComponent
      from "../Components/ReusableComponents/LayoutComponents/ImageComponentReusables/ProfileLetterComponent.svelte";
   import CircularImage
      from "../Components/ReusableComponents/LayoutComponents/ImageComponentReusables/CircularImage.svelte";
   import EditProfile from "../Components/Profile/EditProfile.svelte";
</script>

<Route path="test">
   <EditProfile/>
</Route>
