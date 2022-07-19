<script>
    import BaseButton from "../../ReusableComponents/Buttons/BaseButton.svelte";
    import ThemeClass from "../../ReusableComponents/ThemeClasses/ThemeClass";
    import {useNavigate} from 'svelte-navigator';
    const nav = useNavigate()
</script>

<style>
    .brand-icon
    {
        height: 55px;
        width: 55px;
    }
</style>

<section  class="col-2">
    <img class="img-fluid brand-icon" src="/images/SvelteUpBrand.png" />
</section>

<section class="col-3 d-flex flex-row justify-content-end px-2 " id="p1234">
    <BaseButton  onClick={() => nav('login')} color={ThemeClass.app_control_gray } value="Login" marginPadding=" mx-1"/>
    <BaseButton  onClick={() => nav('signup')} color={ThemeClass.app_control_green} value="SignUp" marginPadding=" mx-1"/>
</section>