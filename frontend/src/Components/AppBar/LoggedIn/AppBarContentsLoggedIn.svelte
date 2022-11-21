<script lang="ts">
    import BaseButton from "../../ReusableComponents/Buttons/BaseButton.svelte";
    import ThemeClass from "../../ReusableComponents/ThemeClasses/ThemeClass";
    import NavDrawer from "../NavDrawer/NavDrawer.svelte";
    import NavDropDown from "../NavDropDown/NavDropDown.svelte";
    import NotificationDto from "../../../Dto/NotificationDto";
    import NotificationElement from "../NavDropDown/NotificationElement.svelte";
    import {test} from "svelte/types/compiler/config";
    import {httpClient} from "../../../Stores/HttpClient";
    import ApiService from "../../../Services/ApiService/ApiService";
    import {onMount} from "svelte";
    import {GET, HttpRequest} from "../../../Services/HttpClients/HttpClient";
    import authenticationStore from "../../../Stores/AuthenticationStore";
    import ApplicationUser from "../../../Dto/auth/ApplicationUser";
    import heartBeatStore from "../../HeartBeat/store/HeartBeatStore";

    let authenticatedUser: ApplicationUser = $authenticationStore;
    let toggleDrawer: boolean = false;
    let showNotificationDropdown: boolean = false;
    let showCartDropDown: boolean = false;

    let notificationPage: number = 0;

    let intervalNotificationFetch: boolean = true;
    let userScrollFetch: boolean = false;

    let notifications: NotificationDto [] = authenticatedUser.authenticated ?  authenticatedUser.notifications : [];
    let cart: object [] = authenticatedUser.cart;



    function renderNotifications() {
        showCartDropDown = false;
        showNotificationDropdown = true;
    }

    function hideNotifications() {
        showNotificationDropdown = false;
    }

    function renderCartDropdown() {
        showCartDropDown = true;
        showNotificationDropdown = false;
    }

    function hideCartDropdown() {
        showCartDropDown = false;
    }

    function drawerToggle(): void {
        toggleDrawer = !toggleDrawer;
    }

   function fetchSuccess(apiResponse:object)
   {
       $heartBeatStore.updateSelf(apiResponse);
       $heartBeatStore = $heartBeatStore;



            if(false) //will reuse later
            {
                let respArr: NotificationDto [] = [];
                let notifDtoIter: NotificationDto;

                for (let notif of apiResponse)
                    respArr.push(new NotificationDto(notif));

                if (userScrollFetch)
                    notifications = [...notifications, ...respArr]
                else
                    notifications = [...respArr, ...notifications]

                notificationPage += 1;
            }
   }

   function fetchFail(reason:any):void
   {
       console.log("fail!");
   }


   async function fetchHeartBeat(){
       const REQ:HttpRequest = new HttpRequest(GET,ApiService.getHeartBeatUrl(),true,{"content-type":"application/json"},null);
       $httpClient.httpRequest(REQ,fetchSuccess,fetchFail)
    }



    onMount(
        () => {
            setInterval(()  => !showNotificationDropdown ?
                fetchHeartBeat() : () => {},3000); //every 15s try to fetch notif, if user is notscrolling
            notifications = authenticatedUser.notifications;
        }

    );

</script>

<style>
    .brand-icon
    {
        font-size: 2rem;
    }

    .nav-icon
    {
        font-size: 1.5em;
    }

    .nav-icon:hover
    {
        color: red;
    }
    .nav-icon-button
    {
        background-color: transparent !important;
        border: 1px black !important; ;
        border-radius: 3px !important ;
        color: #ff3e00 !important;;

    }

    .nav-icon-button:hover
    {
        color: white !important;
        background-color: #ff3e00 !important;
    }

</style>



<section  class="col-2">
    <button class="btn btn-outline-success" on:click={() => toggleDrawer = !toggleDrawer}>
        <i class="bi bi-list"></i>
    </button>
</section>
<section class=" col-5 form-group d-flex flex-row justify-self-center ">
    <input type="text" class="form-control" placeholder="Search..." />
    <button class="btn btn-outline-secondary">
        <i class="bi bi-search"></i>
    </button>
</section>
<section class="col-3 navbar-nav d-flex flex-row align-items-center justify-content-end" >
    <button  class="btn btn-outline-secondary mx-1" id="notification_button" on:click={ () => {showNotificationDropdown=true;}}>
        <i class="bi bi-bell"></i>
    </button>
    <button  class="btn btn-success mx-1" id="cart_button" on:click={ () => {showCartDropDown=true;}}>
        <i class="bi bi-cart-check"></i>
    </button>
    <NavDropDown fetchFunction={() => showNotificationDropdown ? fetchHeartBeat()  : () => {}} headerText="Notifications" parentNavBarId="application-navigation-bar" id="notification_dropdown" parentIconId="cart_button" hideFunction={hideNotifications} showDropDown={showNotificationDropdown}>
           <div slot="content">
               {#each notifications as element }
                   <NotificationElement notification={element}/>
               {/each}
           </div>
    </NavDropDown>
    <NavDropDown headerText="Cart" parentNavBarId="application-navigation-bar" id="cart_dropdown" parentIconId="notification_button" hideFunction={hideCartDropdown} showDropDown={showCartDropDown}>
       <h1 slot="content">acart</h1>
    </NavDropDown>


</section>
<NavDrawer onClick={drawerToggle} drawerTucked={toggleDrawer} />