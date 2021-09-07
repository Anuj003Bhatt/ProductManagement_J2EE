package org.anuj.projectmanage.databaseinterface;

import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import org.anuj.projectmanage.databaseinterface.constants.PoolConstants;
import org.apache.log4j.Logger;
import org.projecttmanage.customexceptions.MaxConnectionsReachedException;

/**
 * This is an abstract class. The purpose of this class is to maintain a
 * register for the active connection and the classes that have been loaded so
 * far.<br/>
 * <br/>
 *
 * On every new connection created by the system this increments the count of
 * connection by one and register the driver class used.<br/>
 * <br/>
 * 
 * When any connection is closed by the system this decrements the count of
 * connection by one.<br/>
 * While registering connection close no need register driver because that will
 * stay in the system once loaded.
 * 
 * 
 * @author Anuj Narayan Bhatt
 *
 */
public abstract class ConnectionRegister {
	private static final Logger logger = Logger.getLogger(ConnectionRegister.class);
	private static int ACTIVE_CONNECTIONS = 0;

	public static int get_ACTIVE_CONNECTIONS() {
		return ACTIVE_CONNECTIONS;
	}

	/**
	 * Every time a new connection is established the active connection count must
	 * be incremented. Doing so tracks the active connection at a time and avoids
	 * traffic
	 * 
	 * Since this is a static method and could be used by multiple threads hence
	 * this is synchronized
	 * 
	 * @throws MaxConnectionsError
	 */
	public static int requestConnection() throws MaxConnectionsReachedException {
		logger.trace(
				"Connection Requested [Thread: " + Thread.currentThread().getName() + "]: Entering synchronized block");
		synchronized (ConnectionPool.class) {
			if (ACTIVE_CONNECTIONS == PoolConstants.MAX_CONNECTIONS) {
				// logging handled inside the constructore
				throw new MaxConnectionsReachedException();
			}
			logger.trace("Request Connection successful: Count Incremented: Thread [" + Thread.currentThread().getName()
					+ "]");
			ACTIVE_CONNECTIONS++;
			return ACTIVE_CONNECTIONS;
		}
	}

	/**
	 * Every time a connection is closed the active connection count must be
	 * decremented. Doing so tracks the active connection at a time and avoids
	 * traffic
	 * 
	 * Since this is a static method and could be used by multiple threads hence
	 * this is synchronized
	 */
	public static void connectionClosed() {
		synchronized (ConnectionPool.class) {
			if (ACTIVE_CONNECTIONS > 0)
				ACTIVE_CONNECTIONS--;
		}
	}

	/**
	 * Checks whether the class is already loaded or not.
	 * 
	 * @param driver
	 * @return
	 * 
	 */
	public static boolean isClassLoaded(String driver) {
		java.lang.reflect.Method m = null;
		try {
			m = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
			m.setAccessible(true);
		} catch (NoSuchMethodException exception) {
			logger.error(
					"Could not load the function \"findLoadedClass\", system retured NoSuchMethod exception hence returning false so that driver will be loaded and functionalities continues: \n",
					exception);
			return false;
		} catch (SecurityException ex) {
			logger.error(
					"Could not load the function \"findLoadedClass\" due to security exception, hence returning false so that driver will be loaded and functionalities continues: \n",
					ex);
			return false;
		}
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		Object test1 = null;
		try {
			test1 = m.invoke(cl, driver);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error(
					"Could not invoke the function \"findLoadedClass\", hence returning false so that driver will be loaded and functionalities continues: \n",
					e);
			return false;
		}
		return (test1 != null);
	}

}
