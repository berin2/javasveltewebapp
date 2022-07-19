import heartBeatStore from "../Components/HeartBeat/store/HeartBeatStore";
import HeartBeatDto from "../Components/HeartBeat/dto/HeartBeatDto";
import authenticationStore from "./AuthenticationStore";
import AppInitDto from "../Dto/auth/AppInitDto";

class StoreService {
    public static resetStore() : void
    {
       heartBeatStore.set(new HeartBeatDto());
       authenticationStore.set(new AppInitDto())
    }
}

export default StoreService;