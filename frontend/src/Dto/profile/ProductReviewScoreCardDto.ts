/**
 * Dto describing users ProductReview star ratings.
 */
import {isFalsy} from "../../Validators/IsFalsyObjectValidator";

export class ProductReviewScoreCardDto {
    public  fiveStarReviews: number ;
    public  fourStarReviews: number;
    public  threeStarReviews: number;
    public  twoStarReviews: number;
    public  oneStarReviews:number;
    public  totalOrders: number;

    constructor(apiArg:object) {
        if(!isFalsy(apiArg))
        {
            this.fiveStarReviews = !isFalsy(apiArg["fiveStarReviews"]) ? apiArg["fiveStarReviews"] : 0;
            this.fourStarReviews = !isFalsy(apiArg["fourStarReviews"]) ? apiArg["fourStarReviews"] : 0;
            this.threeStarReviews = !isFalsy(apiArg["threeStarReviews"]) ? apiArg["threeStarReviews"] : 0;
            this.twoStarReviews = !isFalsy(apiArg["twoStarReviews"]) ? apiArg["twoStarReviews"] : 0;
            this.oneStarReviews = !isFalsy(apiArg["oneStarReviews"]) ? apiArg["oneStarReviews"] : 0;
            this.totalOrders = !isFalsy(apiArg["totalOrders"]) ? apiArg["totalOrders"] : 0;
        }
    }
}