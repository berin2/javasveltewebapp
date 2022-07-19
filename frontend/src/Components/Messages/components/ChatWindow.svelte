<script lang="ts">

    import Graph from "../../../Datastructures/Graph";

    enum SubViews {
        INACTIVE = 0,
        MESSAGE_CHATS,
        EMPTY_INBOX,
        EMPTY_MESSAGE_LIST,
        MESSAGE_LIST
    }

    enum TuckedView {
        TUCKED,
        UNTUCKED
    }

    enum LoadingMessageChatsEnum {
        LOADING_CHATS,
        LOADING_COMPLETE
    }

    let loadingGraph: Graph<LoadingMessageChatsEnum> = new Graph<LoadingMessageChatsEnum>(LoadingMessageChatsEnum.LOADING_COMPLETE);
    loadingGraph.createEdge(LoadingMessageChatsEnum.LOADING_CHATS,LoadingMessageChatsEnum.LOADING_COMPLETE);

    let tuckedGraph:Graph<TuckedView> = new Graph<TuckedView>(TuckedView.UNTUCKED);
    tuckedGraph.createBiDirectionalEdge(TuckedView.TUCKED,TuckedView.UNTUCKED);
    tuckedGraph.currentVertex = TuckedView.TUCKED;

    let viewGraph:Graph<SubViews> = new Graph<SubViews>(SubViews.MESSAGE_LIST.valueOf());
    viewGraph.createBiDirectionalEdge(SubViews.INACTIVE,SubViews.MESSAGE_CHATS);
    viewGraph.createBiDirectionalEdge(SubViews.INACTIVE,SubViews.EMPTY_INBOX);
    viewGraph.createBiDirectionalEdge(SubViews.MESSAGE_CHATS,SubViews.EMPTY_MESSAGE_LIST);
    viewGraph.createEdge(SubViews.MESSAGE_LIST,SubViews.INACTIVE);
    viewGraph.createBiDirectionalEdge(SubViews.MESSAGE_CHATS,SubViews.MESSAGE_LIST);

    viewGraph.currentVertex = SubViews.INACTIVE;

    import {scale} from "svelte/transition";
    import PlaceholderRectangle from "../../LoadingPlaceHolders/PlaceholderRectangle.svelte";
    import {afterUpdate, onMount} from "svelte";
    import heartBeatStore from "../../HeartBeat/store/HeartBeatStore";
    import RectanglePanelComponent from "../../ReusableComponents/ListPanels/RectanglePanelComponent.svelte";
    import Inbox from "./Inbox.svelte";
    import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";
    import {fly, fade} from "svelte/transition";
    import MessageDto from "../dto/MessageDto";
    import {MessageChatDto} from "../dto/MessageChatDto";

    import {DELETE, HttpClient, HttpRequest} from "../../../Services/HttpClients/HttpClient";
    import ApiService from "../../../Services/ApiService/ApiService";
    import {httpClient} from "../../../Stores/HttpClient";
    import HeartBeatDto from "../../HeartBeat/dto/HeartBeatDto";
    import UUIDdto from "../../../Dto/UUIDdto";
    import NoChatsFound from "./EmptyInbox.svelte";
    import MessageChat from "./MessageChat.svelte";

    const RENDER_CHAT: string = "RENDER_CHAT";

    let msgPanelElement = null;
    let panelNode: HTMLElement = null;
    let mounted: boolean = false;
    let client: HttpClient = $httpClient;
    let hearBeatDto: HeartBeatDto = $heartBeatStore;
    let messageChatToRender: MessageChatDto = null;

    $: msgPanelElementHeight = msgPanelElement != null ? msgPanelElement.offsetHeight : 0;
    $: profileImage = null;
    $: tuckedGraphVertex = tuckedGraph.currentVertex;
    $: viewGraphCurrentVertex = viewGraph.currentVertex;

    $: chatPanelElement = null;
    $: chatHeightClass = tuckedGraph.currentVertex == TuckedView.TUCKED ? ' chat-list-slid-in ' : ' chat-list-slid-out ';

    //Whenwe untuck a graph and the subview graph is inactive state
    $: if(tuckedGraph.currentVertex == TuckedView.UNTUCKED && viewGraph.currentVertex  == SubViews.INACTIVE)
    {
            if(hearBeatDto.isCompletelyEmpty) viewGraph = viewGraph.transition(SubViews.EMPTY_INBOX);
            else if(!isFalsy($heartBeatStore.messages) && $heartBeatStore.messages.length > 0 ) viewGraph = viewGraph.transition(SubViews.MESSAGE_CHATS);
            else {viewGraph = viewGraph.transition(SubViews.EMPTY_MESSAGE_LIST);}
    }

    //Whenever we tuck windowslider in, set the subview graph to INACTIVE state.
    $: if(tuckedGraph.currentVertex == TuckedView.TUCKED) { viewGraph = viewGraph.transition(SubViews.INACTIVE);}

    function renderMessageChatList(messageChat: MessageChatDto): void {
        messageChatToRender = messageChat;
        viewGraph = viewGraph.transition(SubViews.MESSAGE_LIST);
    }


    function successDeleteMessageChat(messageChat: MessageChatDto): void {
        $heartBeatStore.deleteMessageChat(messageChat.messageChatId);
        $heartBeatStore = $heartBeatStore;
        if($heartBeatStore.messages.length != 0) viewGraph = viewGraph.transition(SubViews.EMPTY_MESSAGE_LIST);
    }

    function errorDeleteMessageChat(): void {
        alert("error  deleting apparently.")
    }

    function dispatchDeleteMessageChat(messageChatToDelete: MessageChatDto): void {
        let messageChatId: string = messageChatToDelete.messageChatId;
        let request: HttpRequest = new HttpRequest(DELETE, ApiService.getMessagePutPostDelete(), true, ApiService.getDefaultHeaders(), new UUIDdto(messageChatToDelete.messageChatId));
        client.dispatchRequest(request, () => successDeleteMessageChat(messageChatToDelete), errorDeleteMessageChat);
    }

    function transitionToMessageChats():void
    {
        viewGraph = viewGraph.transition(SubViews.MESSAGE_CHATS);
    }

    onMount(() => {
        mounted = true;
        if(!isFalsy($heartBeatStore.messages) && $heartBeatStore.messages.length == 0) {viewGraph = viewGraph.transition(SubViews.EMPTY_MESSAGE_LIST);}
        $heartBeatStore = $heartBeatStore;
    });

</script>

<style>
    .chat-list
    {
        background: rgb(240,240,240);
        background: linear-gradient(90deg, rgba(240,240,240,1) 0%, rgba(255,255,255,1) 100%);
        width:300px;
        position: fixed;
        right:5px;
        z-index: 0 !important;
        opacity: 1;
    }
    .chat-list-slid-in
    {
        height: 38px  !important;
        animation-duration: 0.5s;
        overflow: hidden;
    }

    .chat-list-slid-out
    {
        height: 75vh;
        animation-duration: 0.5s;
        overflow-y: scroll;
    }

    .chat-icon
    {
        font-size: 1.4em;
    }

    .icon-divider
    {
        border-left: 1px solid green;
    }

    .action-icon-size
    {
        height:30px !important;
        width: 30px;
    }
    .action-icon
    {
    }

    .action-icon:hover
    {
        background-color: #99958e;
        color:white !important;
    }

    .message-dropdown
    {
        width: 100%;
        overflow-x:  hidden;
    }

    @keyframes message-drawer-slide-out{
        from{height: 80px}
        to{height: 75vh}
    }
    @keyframes message-drawer-slide-in {
        from{height: 75vh}
        to{height: 80px}
    }

    .message-drawer
    {
        left: 0px;
        position: absolute;
        top:0px;
    }

    .chat-panel-header
    {
        min-height: 38px;
    }

    .chat-panel-body {
        height: 92%;
        z-index: 0;
    }

</style>


<section  class={"fixed-bottom ms-auto chat-list  border-success shadow mx-0 px-0 shadow " + chatHeightClass}>
        <div class="d-flex flex-row justify-content-around chat-panel-header sticky-top bg-white ">
            {#if profileImage == null}
                <i class="bi bi-person-circle text-secondary me-auto chat-icon"></i>
            {:else}
                placeholder!
            {/if}
            <span class="text-black fw-bold text-center">Messages</span>
            <div class="icon-divider d-flex mx-4">
                <div class="mx-1 action-icon-size  action-icon rounded-circle border d-flex flex-row justify-content-center align-items-center">
                    <i class="bi bi-pencil-square "></i>
                </div>
                <div class=" action-icon action-icon-size  rounded-circle border d-flex flex-row justify-content-center align-items-center ">
                    {#if tuckedGraphVertex == TuckedView.TUCKED}
                        <i class="bi bi-chevron-up"  on:click|preventDefault={()  => tuckedGraph =tuckedGraph.transition(TuckedView.UNTUCKED)}></i>
                    {:else}
                        <i class="bi bi-chevron-down" on:click|preventDefault={()  =>{ tuckedGraph = tuckedGraph.transition(TuckedView.TUCKED)}}></i>
                    {/if}
                </div>
                <div class=" action-icon-size d-flex flex-row justify-content-center align-items-center ">
                    {#if tuckedGraphVertex == TuckedView.UNTUCKED && viewGraphCurrentVertex== SubViews.MESSAGE_LIST}
                        <div class=" action-icon action-icon-size rounded-circle border d-flex flex-row justify-content-center align-items-center " transition:fade={{duration:1000}} on:click|preventDefault={() => {viewGraph.transition(SubViews.MESSAGE_CHATS)}}>
                            <i class="bi bi-arrow-right "  on:click|preventDefault={transitionToMessageChats}></i>
                        </div>
                    {/if}
                </div>
            </div>
        </div>
    {#if tuckedGraphVertex == TuckedView.UNTUCKED}
        <div class="chat-panel-body" bind:this={chatPanelElement}>
            <div class="d-flex flew-row justify-content-between align-items-center">
                <div class="d-flex flex-row justify-content-center align-items-center justify-self-start m-2">
                    <h3 class="bi bi-envelope-check-fill"></h3>
                </div>
                <h3 class=" m-2 text-center text-black "> Inbox </h3>
            </div>
            {#if chatPanelElement != null}
                {#if viewGraphCurrentVertex == SubViews.EMPTY_INBOX}
                        <PlaceholderRectangle rowHeight="120" bind:containingElementHeight={chatPanelElement.offsetHeight} />
                 {:else if viewGraphCurrentVertex== SubViews.MESSAGE_LIST}
                    <Inbox messageChat={messageChatToRender} onDestroyFunction={() => {messageChatToRender = null;}}/>
                 {:else if viewGraphCurrentVertex == SubViews.MESSAGE_CHATS}
                        {#each hearBeatDto.messages as messageChat}
                            <MessageChat bind:messageChat={messageChat} on:RENDER_CHAT|once={(e)=> {renderMessageChatList(e.detail);}}  deleteTransitionFunction={transitionToMessageChats}/>
                        {/each}
                 {:else if viewGraphCurrentVertex == SubViews.EMPTY_MESSAGE_LIST  }
                    <NoChatsFound  showSpinner={false} bind:containingElementHeight={chatPanelElement.offsetHeight}/>
                 {/if}
            {/if}
        </div>
    {/if}
</section>


