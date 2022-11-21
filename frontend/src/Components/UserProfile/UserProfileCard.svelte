<script lang="ts">
    import SvelteUpUserProfileDto from "../../Dto/profile/SvelteUpUserProfileDto";
    import {isFalsy} from "../../Validators/IsFalsyObjectValidator";
    import authenticationStore from "../../Stores/AuthenticationStore";
    import {ProductReviewScoreCardDto} from "../../Dto/profile/ProductReviewScoreCardDto";
    import ApplicationUser from "../../Dto/auth/ApplicationUser";

    //@ts-ignore
    let profile:ApplicationUser | null  = $authenticationStore;

    let svelteProfile: SvelteUpUserProfileDto | null = null;
    let reviewDto: ProductReviewScoreCardDto | null = null;

    let ratingAverage: number | null = null;
    let image: string | null  = "";

    $: if( profile ) {
        svelteProfile = profile.profile;
        if(!isFalsy(svelteProfile))
        {
            if( !isFalsy(svelteProfile.image))
            {
                image = svelteProfile.image;
            }
            if(!isFalsy(svelteProfile.productReviewScoreCard))
            {
                reviewDto = profile.profile.productReviewScoreCard;
                if(reviewDto.totalOrders >= 1) {
                    ratingAverage = reviewDto.oneStarReviews + reviewDto.twoStarReviews * 2
                    reviewDto.threeStarReviews * 3 + reviewDto.fourStarReviews * 4
                    reviewDto.fiveStarReviews * 5;
                    ratingAverage /= (reviewDto.totalOrders );
                    ratingAverage = Math.round(ratingAverage);
                }
                else
                    ratingAverage = null;
            }

        }
    }

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


<section class="container-fluid">
    <div class="row">
        <div class="col-6 d-flex flex-row justify-content-start">
            {#if image !== null}
                <div class="rounded-circle display-circle d-flex flex-row justify-content-center align-items-center overflow-hidden">
                    <img class=" display-image rounded-circle border shadow" src={image} alt="profile image"/>
                </div>
            {:else}
                <h2 class="rounded-circle  display-circle m-2  d-flex justify-content-center align-items-center text-white">
                    {user.username.charAt(0).toUpperCase()}
                </h2>
            {/if}
            <section class="d-flex flex-column">
                <section class="d-flex flex-row justify-content-between">
                    <h5 class="text-secondary me-1">Scorecard </h5>
                    <i class="bi bi-calendar2-week"></i>
                </section>
                <section class="">
                    <section>
                        {#if ratingAverage != null}
                            {#each Array(ratingAverage) as i,_}
                                <i class="bi bi-star text-warning"></i>
                            {/each}
                            <p>{reviewDto.totalOrders} sold</p>
                        {:else}
                            <span> No products sold (yet!)</span>
                        {/if}
                    </section>
                </section>
            </section>

        </div>
        <div class="col-6  text-center">
            <h5 class="text-secondary me-1 text-start">
                Area
                <i class="bi bi-geo-alt"></i>
            </h5>
            <div class="text-secondary text-start">{svelteProfile.firstName}</div>
            <div class="text-secondary text-start">{svelteProfile.userProfileCity + "," + svelteProfile.userProfileState} </div>
            <div class="text-secondary text-start">{svelteProfile.zipCode}</div>
        </div>
    </div>
</section>

