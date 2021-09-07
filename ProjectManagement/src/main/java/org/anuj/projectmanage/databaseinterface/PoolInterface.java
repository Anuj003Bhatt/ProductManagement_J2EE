package org.anuj.projectmanage.databaseinterface;

import org.projecttmanage.customexceptions.MaxConnectionsReachedException;

public interface PoolInterface {
	
	public DBConnection getConnection()throws MaxConnectionsReachedException;
	public void setConnectionFree(DBConnection conn);
	public void closePool();	
	
	
}
