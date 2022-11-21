// @ts-ignore
import SvelteUpUserProfileDto from "./SvelteUpUserProfileDto";

class UserProfileDto
{
    public  svelteUpProfileIdentity:SvelteUpUserProfileDto;

    constructor(svelteUpProfile: SvelteUpUserProfileDto) {
        this.svelteUpProfileIdentity = svelteUpProfile;
    }
}

export default UserProfileDto;