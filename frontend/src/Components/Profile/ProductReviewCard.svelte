<script lang="ts">
    import {ProductReviewScoreCardDto} from "../../Dto/profile/ProductReviewScoreCardDto";
    import authenticationStore from "../../Stores/AuthenticationStore";
    import ApplicationUser from "../../Dto/auth/ApplicationUser";
    import {isFalsy} from "../../Validators/IsFalsyObjectValidator";
    import {afterUpdate, onMount} from "svelte";

    let userDto: ApplicationUser = $authenticationStore

    let productReviewCard: ProductReviewScoreCardDto | null = null;
    let avgStar: number | null = null;

    onMount(()=>{
        if(userDto.authenticated && !isFalsy(userDto.profile) && !isFalsy(userDto.profile.productReviewScoreCard))
            productReviewCard = userDto.profile.productReviewScoreCard;
    });

    afterUpdate(() => {
        let avg: number = productReviewCard.oneStarReviews + productReviewCard.twoStarReviews * 2 +
                          productReviewCard.threeStarReviews * 3 + productReviewCard.fourStarReviews * 4
                          productReviewCard.fourStarReviews * 5;
        avg /= 5;
        avgStar = Math.round(avg);
    });

</script>

{#if productReviewCard != null}
    <div class="col-12 text-center">
        <div>
            Average Star Rating:
            {#each Array(avgStar) as _, i}
                <i className="bi bi-star text-warning"></i>
            {/each}
        </div>
        <div> Orders Served: {productReviewCard.totalOrders}</div>
    </div>
{/if}