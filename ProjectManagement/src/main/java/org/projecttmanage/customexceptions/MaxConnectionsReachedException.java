package org.projecttmanage.customexceptions;

import org.apache.log4j.Logger;

public class MaxConnectionsReachedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MaxConnectionsReachedException.class);

	public MaxConnectionsReachedException() {
		super("Maximum number of connections allowed has reached, No further connections can be permitted");
		logger.error("Maximum connections reached: No more connections allowed: [Thread: "
				+ Thread.currentThread().getName());
		
	}
}
