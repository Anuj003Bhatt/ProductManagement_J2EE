package org.anuj.projectmanage.databaseinterface;

import org.apache.log4j.Logger;

public class ConnectionsList {
	private ConnectionNode start = null;
	private ConnectionNode last = null;
	private static final Logger logger = Logger.getLogger(ConnectionsList.class);
	public ConnectionsList() {
		this.start = this.last = null;
	}

	public void add(DBConnection conn) {
		synchronized (this) {
			ConnectionNode node = new ConnectionNode(conn);
			if (last == null) {
				this.last = this.start = node;
			} else {
				this.last.setNext(node);
				this.last = node;
			}
		}
	}

	public void add(ConnectionNode node) {
		synchronized (this) {
			if (last == null) {
				this.last = this.start = node;
			} else {
				this.last.setNext(node);
				this.last = node;
			}
		}
	}

	public ConnectionNode getFirst() {
		synchronized (this) {
			return this.start;
		}
	}

	public void moveFirstToLast() {
		synchronized (this) {
			if (this.start == null)
				return;
			if (this.start == this.last)
				return;
			this.last.setNext(this.start);
			this.start = this.start.getNext();
			this.last.getNext().setNext(null);
			this.last = this.last.getNext();
		}
	}

	public void moveToFirst(ConnectionNode conn, ConnectionNode prev) {
		synchronized (this) {
			if (this.start == null || conn == null || prev == null || this.start == conn)
				return;
			if (this.start == this.last)
				return;

			prev.setNext(conn.getNext());
			conn.setNext(this.start);
			this.start = conn;
		}
	}

	public void setConnectionFree(DBConnection conn) {
		synchronized (this) {
			if (this.start == null)
				return;

			ConnectionNode tmp = this.start;
			ConnectionNode prev = null;
			while (tmp != null) {
				if (tmp.getConn() == conn) {
					tmp.setFree(true);
					moveToFirst(tmp, prev);
					return;
				}
				prev = tmp;
				tmp = tmp.getNext();
			}

		}
	}

	public void closeAllConnections() {
		logger.error("Closing all connections");
		ConnectionNode tmp = this.start;
		
		while (tmp != null) {
			try {
				tmp.getConn().closeConnection();
			} catch (Exception ex) {
				logger.error("Error while closing connection");
			}
			tmp = tmp.getNext();
		}

	}

}
