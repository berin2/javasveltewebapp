<script lang="ts">
    import {afterUpdate, onDestroy, onMount} from "svelte";
    import {fly,fade} from "svelte/transition";
    import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";
    import TextInput from "../../ReusableComponents/FormControls/TextInput.svelte";
    import TextArea from "../../ReusableComponents/FormControls/TextArea.svelte";
    import BaseButton from "../../ReusableComponents/Buttons/BaseButton.svelte";
    import ThemeClass from "../../ReusableComponents/ThemeClasses/ThemeClass";
    import ChatBubble from "./ChatBubble.svelte";
    import {httpClient} from "../../../Stores/HttpClient";
    import {DELETE, GET, HttpClient, HttpRequest, POST, PUT} from "../../../Services/HttpClients/HttpClient";
    import ApiService from "../../../Services/ApiService/ApiService";
    import {MessageChatDto} from "../dto/MessageChatDto";
    import {ScrollTopAction} from "../../SvelteActions/ScrollBottomAction";
    import PlaceholderRectangle from "../../LoadingPlaceHolders/PlaceholderRectangle.svelte";
    import MessageDto from "../dto/MessageDto";
    import authenticationStore from "../../../Stores/AuthenticationStore";

    export let offsetHeight: number = 30;
    export let offsetMarginTop: number = 38;
    export let username: string | null = "";
    export let img: string | null = ""
    export let messageChat:MessageChatDto | null = null;
    export let onDestroyFunction: () => any = () => {};

    let canRender: boolean = false;
    let chatMsgPanelMounted: boolean = false;
    let showLoadingSpinner: boolean = true;
    let mountFinished: boolean = false;
    let loggedInUser: string = $authenticationStore.username;

    let pageIndex: number = 0;
    let resetScrollToBottom:boolean = false;
    let endOfChatReached:boolean = false;
    let messages:MessageDto[] | null = null;
    let client: HttpClient = $httpClient;
    let chatMsgPanel: HTMLElement | null = null
    $: chatMsgPanelSet =  chatMsgPanel !== null;
    let messagesLoaded: boolean = false;

    let msgContent:string = "";



    function dispatchRequest(): void {
        if(!endOfChatReached)
        {
            let request: HttpRequest = new HttpRequest(GET, ApiService.getMessagesForMessageChat(pageIndex, messageChat.messageChatId), true, ApiService.getDefaultHeaders(), null);
            client.dispatchRequest(request, success, error);
        }

    }

    function success(arg: any): void {

        if(isFalsy(messages))
            messages = [];

        messages = [...arg,...messages];

        pageIndex = pageIndex + 1;
        showLoadingSpinner = false;
        messagesLoaded = true;
        resetScrollToBottom = true;
    }

    function error(): void {
        alert("error");
    }


    function dispatchMessageRequest():void{
        let msg:MessageDto = new MessageDto({});

        msg.messageContent = msgContent;
        msg.senderUsername  = loggedInUser;
        msg.messageChatId = messageChat.messageChatId;

        let request:HttpRequest = new HttpRequest(PUT,ApiService.getMessagePutPostDelete(),true,ApiService.getDefaultHeaders(),msg);
        client.dispatchRequest(request, () => successMessage(msg),errorMessage);
    }
    function successMessage(messageDto:MessageDto):void{
        msgContent = "";
        messages = [...messages,messageDto];
        resetScrollToBottom = true;
    }
    function errorMessage():void{}

    function successGetMessagePage(apiResponse:object){
        if( !isFalsy(apiResponse) && Array.isArray(apiResponse))
        {
            if(apiResponse.length > 0 )
            {
                let resp:MessageDto [] = <MessageDto[]> apiResponse;
                messages = [...messages,...resp];
                pageIndex++;
            }
            else
                endOfChatReached = true;
        }
    }
    function errorGetMessagePage(){}
    function dispatchGetMessagePage() {
        let request:HttpRequest = new HttpRequest(GET,ApiService.getMessagesForMessageChat(pageIndex,messageChat.messageChatId),true,ApiService.getDefaultHeaders());
        client.dispatchRequest(request,successGetMessagePage,errorGetMessagePage);
    }



    onMount(() => {dispatchRequest();});
    onDestroy(onDestroyFunction);

    afterUpdate(
        () => {
            chatMsgPanelMounted = true;
            if(resetScrollToBottom)
            {
                chatMsgPanel.scrollTop = Number.MAX_SAFE_INTEGER;
                resetScrollToBottom = false;
            }
        }
    )



</script>

<style>
    .message-drawer
    {

        display: flex;
        flex-direction: column;
        height: 100%;
        top:0px;
        left: 0px;
        padding-top:38px;
        box-sizing: border-box;
        z-index: 2;
        width: 100%;
        position: absolute;
        overflow-x:hidden;
    }

    .chat-messages-wrapper
    {
        overflow-x:hidden !important;
    }

    .chat-header
    {
        height: 15%;
        width: 100%;
    }

    .chat-body
    {
        height: 70%;
        overflow-y: scroll;
        background: rgb(224,224,224);
        background: linear-gradient(90deg, rgba(224,224,224,1) 0%, rgba(240,240,240,1) 100%);
    }

    .chat-footer
    {
        position: absolute;
        height: 15%;
        bottom: 0px;
        width: 100%;
        z-index: 3;
    }

    .img
    {
        height: 50px;
        width: 50px;
    }

    .icon-image
    {
        font-size: 40px;
    }

    .btn-chat
    {
        background-color: #dddddd;
    }
</style>

        <section transition:fly={{x:2000, duration:400}} class=" message-drawer  " >
            <section class=" chat-header d-flex  flex-row justify-content-between border border-bottom  bg-success border shadow">
                <div class="d-flex flex-column justify-content-around p-2">
                    <p class=" text-white text-center">{messageChat.senderUsername}</p>
                </div>
                <section class="img">
                    <i class="bi bi-file-person-fill text-white icon-image "></i>
                </section>
            </section>
            <section class=" bg-white chat-body d-flex flex-column  align-items-center " bind:this={chatMsgPanel}  on:scroll|preventDefault={(e) => ScrollTopAction(e,dispatchGetMessagePage)}>
                {#if chatMsgPanelMounted}
                        <div class="mt-4 border-bottom w-100">
                            {#if !messagesLoaded}
                                    <PlaceholderRectangle  containingElementHeight={chatMsgPanel.offsetHeight + 200} />
                            {:else}
                                    {#if endOfChatReached}
                                        <h4 class="bg-white rounded text-center text-success" in:fade={{duration:400}}>End of Chat</h4>
                                    {/if}
                                    {#each messages as message}
                                        <ChatBubble username={message.senderUsername} msg={message.messageContent} />
                                    {/each}
                            {/if}
                        </div>
                    {/if}
            </section>
            <section class=" chat-footer  container-fluid border-top shadow bg-success ">
                <section class="row">
                        <TextArea col=" col-12 " placeholderValue="..." bind:textValue={msgContent} />
                        <div class="form-group">
                            <button type="button" class="btn btn-sm btn-chat  " value="send" disabled={msgContent === ""} on:click={()=>{dispatchMessageRequest()}}>
                                Send
                                <i class="bi bi-send"></i>
                            </button>
                        </div>
                </section>
            </section>
        </section>
