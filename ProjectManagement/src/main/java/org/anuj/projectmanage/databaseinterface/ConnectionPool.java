package org.anuj.projectmanage.databaseinterface;

import org.apache.log4j.Logger;
import org.projecttmanage.customexceptions.MaxConnectionsReachedException;

public class ConnectionPool implements PoolInterface {

	private static final Logger logger = Logger.getLogger(ConnectionPool.class);
	public ConnectionsList connections = new ConnectionsList();

	@Override
	public void closePool(){
		// TODO Auto-generated method stub
		
		this.connections.closeAllConnections();
	}
	
	@Override
	public DBConnection getConnection() throws MaxConnectionsReachedException {
		// TODO Auto-generated method stub
		ConnectionNode connectionNode = connections.getFirst();
		if (connectionNode == null || (!connectionNode.getFree())) {
			logger.debug("No free connections, adding a new one");
			ConnectionNode newnode = new ConnectionNode(new DBConnection());
			connections.add(newnode);
			newnode.getConn().initiate();
			logger.info("Connection initiation successful");
			return newnode.getConn();
		} else {
			logger.info("Existing connection issued");
			connectionNode.setFree(false);
			this.connections.moveFirstToLast();
			return connectionNode.getConn();
		}
	}

	@Override
	public void setConnectionFree(DBConnection conn) {
		// TODO Auto-generated method stub
		logger.trace("Setting connection free: Thread: [" + Thread.currentThread().getName() + "]");
		this.connections.setConnectionFree(conn);
	}

}
