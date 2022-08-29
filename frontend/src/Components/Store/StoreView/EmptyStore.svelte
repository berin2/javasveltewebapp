<script>
    import PlaceholderSquare from "../../LoadingPlaceHolders/PlaceholderSquare.svelte";
    import AppInitDto from "../../../Dto/auth/AppInitDto";
    import authenticationStore from "../../../Stores/AuthenticationStore";
    import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";

    let user = $authenticationStore
    let username = user.username;
    let image = !isFalsy(user.profile) ? user.profile.image:null;

    alert(JSON.stringify(user))

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
<section class="col-md-6 col-12 border-right d-flex flex-column justify-content-center align-items-center ">
    <section class="container-fluid">
        <div class="row">
            <div class="col-12 d-flex  justify-content-center">
                {#if image !== null}
                    <div class="rounded-circle display-circle d-flex flex-row justify-content-center align-items-center overflow-hidden">
                        <img class=" display-image rounded-circle border shadow" src={image} alt="profile image"/>
                    </div>
                {:else}
                    <h2 class="rounded-circle  display-circle m-2  d-flex justify-content-center align-items-center text-white"> {user.username.charAt(0).toUpperCase()}</h2>
                {/if}
            </div>
            <div class="col-12 text-center">
                <div class="text-secondary">{user.profile.firstName}</div>
                <div>
                    Average Star Rating:
                    <i class="bi bi-star text-warning"></i>
                    <i class="bi bi-star text-warning"></i>
                    <i class="bi bi-star text-warning"></i>
                    <i class="bi bi-star text-warning"></i>
                    <i class="bi bi-star text-warning"></i>
                </div>
                <div class="text-secondary">{user.profile.userProfileCity + ","+user.profile.userProfileState} </div>
            </div>

        </div>
    </section>

</section>
<section class=" col-md-6 col-12 text-secondary text-center my-3">
    <h1 class="text-secondary ">Wows, such empty.</h1>
    <p>But, don't worry creating new products is fast and free.</p>
    <p>(It only takes a second or two :))</p>
</section>
{#each Array(4) as _,i}
    <section class="col-6 p-4">
        <PlaceholderSquare containingElementHeight={250}/>
    </section>
{/each}