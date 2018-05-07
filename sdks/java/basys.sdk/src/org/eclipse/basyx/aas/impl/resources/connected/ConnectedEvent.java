package org.eclipse.basyx.aas.impl.resources.connected;

import org.eclipse.basyx.aas.impl.resources.basic.Event;

public abstract class ConnectedEvent extends Event {
    public abstract void throwEvent(Object value) throws Exception;
}
