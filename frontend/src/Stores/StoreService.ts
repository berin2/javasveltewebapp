import heartBeatStore from "../Components/HeartBeat/store/HeartBeatStore";
import HeartBeatDto from "../Components/HeartBeat/dto/HeartBeatDto";
import authenticationStore from "./AuthenticationStore";
import ApplicationUser from "../Dto/auth/ApplicationUser";

class StoreService {
    public static resetStore() : void
    {
       heartBeatStore.set(new HeartBeatDto());
       authenticationStore.set(new ApplicationUser())
    }
}

export default StoreService;