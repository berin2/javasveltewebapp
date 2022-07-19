import {Writable, writable} from "svelte/store";
import PaymentMethodStoreDto from "../dto/PaymentMethodStoreDto";

let paymentStore:Writable<PaymentMethodStoreDto> = writable(new PaymentMethodStoreDto({}));

export default paymentStore;