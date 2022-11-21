import {Writable, writable} from "svelte/store";
import UserProfileDto from "../Dto/profile/UserProfileDto";
import SvelteUpUserProfileDto from "../Dto/profile/SvelteUpUserProfileDto";

let userProfileStore: Writable<SvelteUpUserProfileDto> = writable(new SvelteUpUserProfileDto());

export default userProfileStore;