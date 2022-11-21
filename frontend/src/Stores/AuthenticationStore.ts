import {Readable, Writable, writable} from "svelte/store";
import ApplicationUser from "../Dto/auth/ApplicationUser";
let authenticationStore:Writable<ApplicationUser> = writable(new ApplicationUser());

export default authenticationStore;