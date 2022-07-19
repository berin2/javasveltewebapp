import {Writable, writable} from "svelte/store";
import type SvelteUpUserAccountDto from "../../../Dto/profile/SvelteUpUserAccountDto";

let accountStore:Writable<SvelteUpUserAccountDto> = writable(null);

export default accountStore;