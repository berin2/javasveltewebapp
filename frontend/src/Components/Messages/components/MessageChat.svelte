<script lang="ts">
    import RectanglePanelComponent from "../../ReusableComponents/ListPanels/RectanglePanelComponent.svelte";
    import {MessageChatDto} from "../dto/MessageChatDto";
    import {DELETE, HttpClient, HttpRequest} from "../../../Services/HttpClients/HttpClient";
    import ApiService from "../../../Services/ApiService/ApiService";
    import UUIDdto from "../../../Dto/UUIDdto";
    import {httpClient} from "../../../Stores/HttpClient";
    import heartBeatStore from "../../HeartBeat/store/HeartBeatStore";
    import HeartBeatDto from "../../HeartBeat/dto/HeartBeatDto";
    import {createEventDispatcher, onMount} from "svelte";

    export let messageChat:MessageChatDto | null = null;
    export let deleteTransitionFunction: () => void = () => {};
    let client: HttpClient = $httpClient;
    let hearBeatDto:HeartBeatDto = $heartBeatStore;
    let publishEvent: any = createEventDispatcher();

    function successDeleteMessageChat(messageChat:MessageChatDto): void {
        hearBeatDto.deleteMessageChat(messageChat.messageChatId);
        $heartBeatStore = $heartBeatStore;
    }

    function errorDeleteMessageChat(): void {
        alert("error  deleting apparently.")
    }

    function dispatchDeleteMessageChat(messageChatToDelete: MessageChatDto): void {
        let messageChatId: string = messageChatToDelete.messageChatId;
        let request: HttpRequest = new HttpRequest(DELETE, ApiService.getMessagePutPostDelete(), true, ApiService.getDefaultHeaders(), new UUIDdto(messageChatToDelete.messageChatId));
        client.dispatchRequest(request, () => successDeleteMessageChat(messageChatToDelete), errorDeleteMessageChat);
    }
</script>

<style>
    .row-option-border
    {
        border-bottom:1px solid green !important;
    }

    .chat-option-row
    {
        background: rgb(113,157,127);
        background: linear-gradient(90deg, rgba(113,157,127,1) 0%, rgba(240,240,240,1) 100%);
    }

    .chat-option-icon
    {
        height: 22px;
        width: 22px;
        font-size: 13px;
    }

    .chat-option-icon-border
    {
        border-left: 1px solid black !important;
    }

    .chat-option-icon:hover
    {
        background-color: green;
        color:white;
    }
</style>
<RectanglePanelComponent msg={messageChat.latestMessage}>
    <div slot="slot-one">
        {#if !messageChat.readByOwner}
            <i class="bi bi-check2-all text-success"></i>
        {:else}
            <i class="bi bi-check2 text-secondary"></i>
        {/if}
    </div>
</RectanglePanelComponent>
<div class="row-option-border d-flex flex-row justify-content-end" on:RENDER_CHAT>
    <div class="ps-2 me-auto">{messageChat.senderUsername}</div>
    <span class="chat-option-icon-border"></span>
    <div class="chat-option-icon  mx-2 d-flex flex-row justify-content-center align-items-center rounded-circle">
        <i class=" bi bi-trash " on:click|preventDefault={() => dispatchDeleteMessageChat(messageChat)}></i>
    </div>
    <div  class="chat-option-icon mx-2 d-flex flex-row justify-content-center align-items-center rounded-circle">
        <i class="bi bi-eye " on:click|preventDefault={()=> publishEvent("RENDER_CHAT",messageChat)}></i>
        <div>
        </div>
    </div>
</div>