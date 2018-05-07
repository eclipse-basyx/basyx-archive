package org.eclipse.basyx.aas.impl.resources.connected;

import org.eclipse.basyx.aas.impl.resources.basic.Property;

public abstract class ConnectedProperty extends Property {
    public abstract void push();
    public abstract void pull();
}
