class NotificationDto {
    sender:string;
    notificationDate:string;
    notificationMessage:string;
    message:string;

    constructor(notifList:object) {
        this.sender = notifList["sender"] ? notifList["sender"] : "John Doe";
        this.notificationDate =  notifList["notificationDate"] ? notifList["notificationDate"] : "2 days ago";
        this.notificationMessage = notifList["notificationMessage"] ? notifList["notificationMessage"] : "John Doe placed an order for 2 test products.";
    }

}

export default NotificationDto;