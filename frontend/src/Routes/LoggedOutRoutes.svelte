<script>
    import {Route} from 'svelte-navigator';
    import LoginComponent from "../Components/Login/LoginComponent.svelte";
    import RegisterComponent from "../Components/Register/RegisterComponent.svelte";
</script>

<Route path="login"><LoginComponent /></Route>
<Route path="signup"><RegisterComponent/></Route>