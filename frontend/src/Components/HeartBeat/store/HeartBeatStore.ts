import {writable, Writable} from "svelte/store";
import HeartBeatDto from "../dto/HeartBeatDto";


let heartBeatStore: Writable<HeartBeatDto> = writable(new HeartBeatDto({}));

export default heartBeatStore;