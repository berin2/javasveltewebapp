import ImageService from "../../Services/Utils/ImageService";
import type ToApiDto from "../ToApiDto";
import {isFalsy} from "../../Validators/IsFalsyObjectValidator";
import BaseDto from "../BaseDto";

class ProductDto  extends BaseDto implements ToApiDto
{
    surrogateId:string;
    productName:string;
    productImageStrings: string [];
    productDescription: string;
    productCalories: number;
    productAcceptsReturns: boolean;
    productCost: number;

    constructor(productDto: object)
    {
        super();
        this.productName = productDto["productName"];
        this.surrogateId = productDto["surrogateId"];
        this.productImageStrings = productDto["productImageStrings"];
        this.productDescription = productDto["productDescription"];
        this.productCalories = productDto["productCalories"];
        this.productAcceptsReturns =  productDto["productAcceptsReturns"];
        this.productCost =  productDto["productCost"];
    }

    toApiDto(): object {
        return this;
    }

    public static emptyDto():object
    {
        let returnDto: ProductDto = new ProductDto({
            "productName":"",
            "surrogateId": null,
            "productImageStrings" : [],
            "productDescription": "",
            "productCalories":0,
            "productAcceptsReturns": false,
            "productCost": 1.00
        });

        return returnDto;
    }

}

function processProductDto(jsonArg: object):object
{
    let imageArray: string []  = jsonArg["productImageStrings"];
    let processedArray:string [] = [];

    if(!isFalsy(imageArray))
        for(let img of imageArray)
            if(!isFalsy(img))
                processedArray.push(img);

    jsonArg["productImageStrings"] = processedArray;

    return jsonArg;
}

export  {ProductDto,processProductDto};