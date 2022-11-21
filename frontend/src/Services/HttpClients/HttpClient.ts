import {isFalsy,isFunction} from "../../Validators/IsFalsyObjectValidator";
import {throwError} from "svelte-preprocess/dist/modules/errors";

const GET:string = "GET";
const PUT:string = "PUT";
const POST:string = "POST";
const DELETE:string = "DELETE";
const PATCH: string = "PATCH"

const HTTP_METHOD: string[] = [
    GET,
    PUT,
    POST,
    DELETE,
    PATCH
]

class HttpRequest
{
    method:string;
    uri:string;
    credentials:string;
    headers:object|null;
    body?: object|string|null;

    constructor(method:string, uri:string, withCredentials:boolean, headers:object = {},body:object = null) {
        this.validatedMethod(method);
        this.method = method;
        this.uri = uri;
        this.credentials = withCredentials ? 'include' : 'omit';
        this.headers = headers;
        this.body = body;
    }

    protected validatedMethod(method:string): void {
        if(!HTTP_METHOD.includes(method))
            throwError("HttpMethod passed to client is not supported.")
    }

    toJson():object
    {
        let returnRequest: object = this;
        if(this.method === GET) delete this.body;
        else this.body = JSON.stringify(this.body);
        return this;
    }
}

class HttpClient {
    public static readonly GET:string = "get" //hardcoded http verbs
    public static readonly PUT:string = "put"
    public static readonly POST:string = "post"
    public static readonly DELETE:string = "delete"
    public static readonly PATCH:string = "patch"

    private baseUrl:string;

    constructor(baseUrl:string) {
        this.baseUrl = baseUrl;
    }

    public validateStatus: (resp:Response) => Response = (response:Response) => {
        if (response.status >= 200 && response.status >= 300)
            throwError(`Non 200 status returned from server: ${response.status}`)

        return response;
    }
    public processJson(resp:Response) : Promise<any>
    {
        let retObj = null;

        try {retObj = resp.text();retObj = JSON.parse(retObj);}
        catch(e) {retObj  = new Promise<any>(()=> null); }
        return retObj;
    }

    httpRequest(request:HttpRequest, success:SuccessFunction, fail:FailFunction, dtoProcessFunction: (arg:any) => object = (arg:object) =>  arg):void {

        if (isFunction(success) && isFunction(fail)) {
            fetch(this.baseUrl + request.uri, request.toJson())
                .then( resp => this.validateStatus(resp))
                .then( resp => { return resp.text()})
                .then( resp => { if (resp.length) return JSON.parse(resp); return {};})
                .then(jsonResp => dtoProcessFunction(jsonResp))
                .then(jsonResp => success(jsonResp))
                .catch(err =>{ fail(err);})
        } else {
            if (isFunction(fail))
                fail("Success and fail are both not functions but are required to befunctions.");
            else
                throw Error("HttpService error. success or fail or both arguments passed as callbacks are not functions.")
        }
    }

    public dispatchRequest(request:HttpRequest, success:(...args:any[])=>any, fail:(...args:any[])=>any, processDtoFunction:(arg:object) =>  object = (arg:object) => arg )
    {
        this.httpRequest(request,success,fail,processDtoFunction);
    }
}

type SuccessFunction = (jsonObject:any) => any;
type FailFunction = (reasonObject:any) => any;

export type { SuccessFunction, FailFunction };
export {HttpClient, HttpRequest, GET, PUT, POST, PATCH, DELETE};
