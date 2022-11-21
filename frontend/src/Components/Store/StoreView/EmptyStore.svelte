<script>
    import PlaceholderSquare from "../../LoadingPlaceHolders/PlaceholderSquare.svelte";
    import authenticationStore from "../../../Stores/AuthenticationStore";
    import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";

    let user = $authenticationStore
    let username = user.username;
    let image = !isFalsy(user.profile) ? user.profile.image : null;

</script>

<style>
    .display-circle {
        width: 80px;
        height: 80px;
        background-color: #8f8f8f;
    }

    .display-image
    {
        height: 100px;
        width: auto;
    }
</style>

<section class=" col-12 text-secondary text-center my-3 border-bottom py-2">
    <h1 class="text-secondary ">Wows, such empty.</h1>
    <p>But, don't worry creating new products is fast and free.</p>
    <p>(It only takes a second or two :))</p>
    <button class="btn btn-success"on:click={()=>{}}>Add<i class="m-2 bi bi-plus-circle"></i></button>
</section>
{#each Array(4) as _,i}
    <section class="col-6 p-4">
        <PlaceholderSquare containingElementHeight={250}/>
    </section>
{/each}