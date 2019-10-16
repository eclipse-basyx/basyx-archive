package org.eclipse.basyx.vab.modelprovider.filesystem;

import java.time.LocalDateTime;

public interface TimeProvider {
	public LocalDateTime getCurrentTime();
}
