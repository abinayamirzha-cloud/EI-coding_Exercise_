public class Main {
    public static void main(String[] args) {
        // Client code does not know the concrete class
        Notification notification1 = NotificationFactory.createNotification("email");
        notification1.notifyUser("Welcome to our service!");

        Notification notification2 = NotificationFactory.createNotification("sms");
        notification2.notifyUser("Your OTP is 123456");

        Notification notification3 = NotificationFactory.createNotification("push");
        notification3.notifyUser("You have a new message!");
    }
}

