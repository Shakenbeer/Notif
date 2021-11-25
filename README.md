# Notif

Notif application shows 20 latest push notifications received. List could be filtered to show only active notification.

This project implemented based on assumption, that we are living in a perfect world and Android Framework works as expected and other apps developers do everything per documentation and reasonably.

Unfortunately real world is not perfect, and real application should be polished accordingly to the most popular apps at least.

For example, Gmail "fires" two identical notifications, that have only different ids (one id is always null). Some application put information under different keys in Notification extras, and so on. 
