# Notif

Notif application shows 20 latest push notifications received. List could be filtered to show only active notification.

Before the application actually works, one should provide notification access to the app. This could be done through system settings, and notification routes user to dedicated settings in the convenien way.

In the early years of Android it was possible to access notification history, this could be the simplest solution. Now it's not possible.

`NotificationListenerService` is the tool to achieve, what we need. _A service that receives calls from the system when new notifications are posted or removed..._ Also it can provide currently active notifications (that could be seen by user from a status bar).

The application's business logic is splitted between two interfaces: `NotificationCollector` and `NotificationProvider`, whiches names are pretty selfexplenatory.

Following Clean Architecture and programming principles like "program to interfaces, not implementations" and "concern separation", those interaces implemented in separate layer. Thanks to depenceny inhection and control inversion, implementations could be adjusted or even replaced without touching other parts of the app. 

Behind the curatains SQLite (with Room) + `NotificationListenerService` do the heavy lifting.

# Expectations vs Reality :)

This project implemented based on assumption, that we are living in a perfect world and Android Framework works as expected and other apps developers do everything per documentation and reasonably.

Unfortunately real world is not perfect, and real application should be polished accordingly to the most popular apps at least.

For example, Gmail "fires" two identical notifications, that have only different ids (one id is always null). Some application put information under different keys in Notification extras, and so on. 
