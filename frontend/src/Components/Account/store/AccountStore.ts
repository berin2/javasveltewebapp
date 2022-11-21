import {Writable, writable} from "svelte/store";
import type SvelteUpUserProfileDto from "../../../Dto/profile/SvelteUpUserProfileDto";

let accountStore:Writable<SvelteUpUserProfileDto> = writable(null);

export default accountStore;