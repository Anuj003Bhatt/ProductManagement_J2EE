package org.anuj.projectmanage.databaseinterface;

public abstract class PoolFactory {
	public static PoolInterface getPoolInterface(String pool) {
		if(pool == null || "".equals(pool)) {
			return null;
		}
		
		return new ConnectionPool();
	}
}
