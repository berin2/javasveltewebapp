import {Writable, writable} from "svelte/store";
import UserProfileDto from "../Dto/profile/UserProfileDto";
import SvelteUpUserAccountDto from "../Dto/profile/SvelteUpUserAccountDto";

let userProfileStore: Writable<SvelteUpUserAccountDto> = writable(new SvelteUpUserAccountDto());

export default userProfileStore;