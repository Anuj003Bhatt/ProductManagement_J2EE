package org.anuj.projectmanage.databaseinterface;

public class ConnectionNode {
	private DBConnection conn;
	private ConnectionNode next;
	private Boolean free = false;

	public ConnectionNode() {
		this.conn = null;
		this.next = null;
	}

	public ConnectionNode(DBConnection conn) {
		this.conn = conn;
		this.free = false;
	}

	public DBConnection getConn() {
		return conn;
	}

	public void setConn(DBConnection conn) {
		this.conn = conn;
		this.next = null;
	}

	public ConnectionNode getNext() {
		return next;
	}

	public void setNext(ConnectionNode next) {
		this.next = next;
	}

	public Boolean getFree() {
		return free;
	}

	public void setFree(Boolean free) {
		this.free = free;
	}

}