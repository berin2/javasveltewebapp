import {Readable, readable} from "svelte/store";
import {HttpClient} from "../Services/HttpClients/HttpClient";
import ApplicationConfig from "../Components/Config/ApplicationConfig";
import ApiService from "../Services/ApiService/ApiService";

const httpClient: Readable<HttpClient> = readable( new HttpClient(ApplicationConfig.BACKEND_URL));
const apiServiceStore: Readable<ApiService> = readable(new ApiService());

export  {httpClient,apiServiceStore};
