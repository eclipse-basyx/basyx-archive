package org.eclipse.basyx.onem2m.manager;

import org.eclipse.basyx.onem2m.xml.protocols.Notification;
import org.eclipse.basyx.onem2m.xml.protocols.Sub;

public interface IOneM2MNotificationHandler {
		
	public void incomingNotification(OneM2MSubscriptionHandler subscriptionHandler, Sub subscription, String handle, Notification notification);

}
