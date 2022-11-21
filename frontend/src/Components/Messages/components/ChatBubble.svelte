<script lang="ts">
    import authenticationStore from "../../../Stores/AuthenticationStore";
    import ApplicationUser from "../../../Dto/auth/ApplicationUser";
    import {onMount} from "svelte";
    import {httpClient} from "../../../Stores/HttpClient";
    import {HttpRequest, POST} from "../../../Services/HttpClients/HttpClient";


    let authUser:ApplicationUser = $authenticationStore;

    export let msg:string = "";
    export let username = "";

    $: isLoggedInUser = username == authUser.username;
    $: chatBubbleClass = isLoggedInUser ? " chat-bubble-logged-in-user bg-white " : " chat-bubble-logged-sending-user bg-success text-white ";


</script>

<style>
    .chat-bubble
    {
        border: 1px solid gray !important;
        border-radius: 15px;
        height: auto;
        padding: 10px;
        margin: 15px;
    }

    .chat-bubble-logged-in-user
    {
    }

    .chat-bubble-logged-sending-user
    {
        margin-left: 40px;
    }
</style>

<section class="w-100">
    <section class={" chat-bubble border shadow rounded w-75 " + chatBubbleClass}>
        {#if !isLoggedInUser}
            <p class="text-white">{username}</p>
        {:else}
            <p class="text-muted">me</p>
        {/if}
        <p>{msg}</p>
    </section>
</section>
