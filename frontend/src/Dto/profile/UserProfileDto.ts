// @ts-ignore
import SvelteUpUserAccountDto from "./SvelteUpUserAccountDto";

class UserProfileDto
{
    public  svelteUpProfileIdentity:SvelteUpUserAccountDto;

    constructor(svelteUpProfile: SvelteUpUserAccountDto) {
        this.svelteUpProfileIdentity = svelteUpProfile;
    }
}

export default UserProfileDto;