package org.eclipse.basyx.aas.impl.provider.filesystem;

import java.time.LocalDateTime;

public interface TimeProvider {
	public LocalDateTime getCurrentTime();
}
