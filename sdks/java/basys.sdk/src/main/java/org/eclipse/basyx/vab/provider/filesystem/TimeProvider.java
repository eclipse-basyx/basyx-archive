package org.eclipse.basyx.vab.provider.filesystem;

import java.time.LocalDateTime;

public interface TimeProvider {
	public LocalDateTime getCurrentTime();
}
