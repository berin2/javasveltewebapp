<script lang="ts">
    import NotificationDto from "../../../Dto/NotificationDto";

    export let notification:NotificationDto|null = null;
</script>
<style>
    .notification-entry {
        min-height:60px;
        height: auto;
    }
    .notification-entry:hover{
        background-color: white !important;
        border: 1px solid #8f8f8f !important;
    }
    .notification-small-text {
        font-size: 0.8rem;
    }

    .notification-image {
        height:50px;
        width: 50px;
    }
</style>

<div class="notification-entry container-fluid mx-0 border-bottom">
    <div class="row align-items-center h-100">
        <div class="col-8 text-black notification-small-text d-flex flex-column align-items-start justify-content-around">
            <p>{notification.notificationMessage}</p>
            <span class="text-muted text-end w-100 ">{notification.notificationDate}</span>
        </div>
        <div class="col-3 text-end">
            <img class="rounded-circle notification-image col-3" src="/images/user_test_icon.jpeg"/>
        </div>
    </div>
</div>