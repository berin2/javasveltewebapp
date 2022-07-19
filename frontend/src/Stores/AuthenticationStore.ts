import {Readable, Writable, writable} from "svelte/store";
import AppInitDto from "../Dto/auth/AppInitDto";
let authenticationStore:Writable<AppInitDto> = writable(new AppInitDto());

export default authenticationStore;