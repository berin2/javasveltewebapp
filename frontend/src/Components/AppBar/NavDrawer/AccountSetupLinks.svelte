<script lang="ts">
    import ApplicationUser from "../../../Dto/auth/ApplicationUser";
    import {onMount} from "svelte";
    import NavDrawerItem from "./NavDrawerItem.svelte";
    import authenticationStore from "../../../Stores/AuthenticationStore";


    $: isEmailValidated = $authenticationStore.isEmailValidated;
    $: isIdentityValidated = $authenticationStore.isIdentityVerified;
    $: renderNavBlock =  !isEmailValidated || !isIdentityValidated;
    $: linkArray = []

    $:{
      if (!isEmailValidated) {
            linkArray.push({
                to: "/verifyemail",
                text: "Email Verification",
                iconClass: "bi bi-envelope"
            });
        }
      console.log("isIdentityValidated value"  + isIdentityValidated +"!!!!!")
      console.log(JSON.stringify($authenticationStore))
        if (!isIdentityValidated)
        {
                linkArray.push({
                to: "/profilesetup",
                text: "Profile Setup",
                iconClass: "bi bi-app"
            });
        }
    }

    onMount(() => {
        isEmailValidated = $authenticationStore.isEmailValidated;
        isIdentityValidated = $authenticationStore.isIdentityVerified;
    });

</script>


{#if renderNavBlock}
    <section class="d-flex flex-column justify-content-center mx-4">
        <div class="text-black border-bottom d-flex justify-content-between align-items-center">
            <h3 class=" text-black">Account Setup</h3>
            <span class="bi bi-segmented-nav text-black"></span>
        </div>
        {#each linkArray as link}
            <NavDrawerItem {...link}/>
        {/each}
    </section>
{/if}