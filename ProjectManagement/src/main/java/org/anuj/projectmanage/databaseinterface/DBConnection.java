package org.anuj.projectmanage.databaseinterface;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;

import org.anuj.projectmanage.constants.DatabaseConstants;
import org.anuj.projectmanage.propertiesinterface.PropertiesInterfaceForFile;
import org.anuj.projectmanage.propertiesinterface.PropertyNotDefinedException;
import org.apache.log4j.Logger;
import org.projecttmanage.customexceptions.MaxConnectionsReachedException;

/**
 *
 * <b>Description:</b><br/>
 * This class implements a single database connection and provides with three
 * major functionalities i.e., <br/>
 *
 * <b>1. Initiate a connection:</b> i.e., via a method initiate()<br/>
 * <b>2. Execute any raw query provided:</b> via 'query' methods<br/>
 * <b>3. Close connection. </b>: via CloseConnection() method<br/>
 * <br/>
 * </b>
 *
 * Apart from the above it also registers connection with the ConnectionPool.
 * 
 * @author Anuj Narayan Bhatt<br>
 *         <br>
 *
 *
 *
 */
public class DBConnection {

	private static final Logger logger = Logger.getLogger(DBConnection.class);
	private Connection conn = null;

	public Connection getConn() {
		return conn;
	}

	public DBConnection() {

	}

	public DBConnection(Connection conn) {
		this.conn = conn;
	}

	@SuppressWarnings("rawtypes")
	String databaseWithParams(String databaseName, Map connectionargs) {
		if (databaseName == null || connectionargs == null) {
			return null;
		}
		StringBuffer connectionString = new StringBuffer(databaseName);
		Iterator iter = connectionargs.keySet().iterator();
		connectionString.append(DatabaseConstants.APPEND_ARGUMENTS_START);
		while (iter.hasNext()) {

			String key = iter.next().toString();
			connectionString.append(key + DatabaseConstants.DB_ARGUMENT_ASSIGN_OPERATOR + connectionargs.get(key));
			if (iter.hasNext()) {
				connectionString.append(DatabaseConstants.APPEND_ARGUMENTS_MORE);
			}
		}
		return connectionString.toString();
	}

	private void createConnection(String driver, String url, String user, String password, String databaseWitParams)
			throws ClassNotFoundException, SQLException {

		// Since a new connection is being created close the previous one
		this.closeConnection();

		logger.info("Creating database connection");
		logger.debug("[Driver: " + driver + ", Database URL: " + url + ", Username: " + user + ", DB: "
				+ databaseWitParams + "]");
		// Load driver class
		if (!ConnectionRegister.isClassLoaded(driver)) {
			Class.forName(driver);
		}
		// Create connection
		// jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
		Connection conn = DriverManager.getConnection(url + databaseWitParams, user, password);
		logger.info("Connection established");
		this.conn = conn;
	}

	@SuppressWarnings("rawtypes")
	void initiate() throws MaxConnectionsReachedException {

		logger.trace("DatabaseConnection getConnection");

		// Fetch db properties and connect to the database.
		String driver = null;
		String database = null;

		try {

			logger.info("Fetching properties from interface");
			Map properties = PropertiesInterfaceForFile.getProperties(DatabaseConstants.DB_PROPERTIES_FILE,
					DatabaseConstants.URL, DatabaseConstants.DRIVER, DatabaseConstants.USER, DatabaseConstants.PASSWORD,
					DatabaseConstants.DATABASE, DatabaseConstants.CHARACTER_SET, DatabaseConstants.TIME_ZONE_SHIFT,
					DatabaseConstants.LEGACY_DATE_TIME_CODE, DatabaseConstants.SERVER_TIME_ZONE);
			// Get properties from map
			driver = properties.get(DatabaseConstants.DRIVER).toString();
			database = properties.get(DatabaseConstants.DATABASE).toString();
			// Create the db connection string using the args provided
			String databaseWitParams = this.databaseWithParams(database, properties);

			// Once all the above information is fetched successfully, continue to connect
			// to DB
			// First of all request a connection in pool so that it registers a new entry
			ConnectionRegister.requestConnection();
			this.createConnection(driver, properties.get(DatabaseConstants.URL).toString(),
					properties.get(DatabaseConstants.USER).toString(),
					properties.get(DatabaseConstants.PASSWORD).toString(), databaseWitParams);

		} catch (PropertyNotDefinedException ex) {
			logger.fatal("Some DB property values in the DB.properties file are not found. Please check");
			logger.error(" Error while fetching properties: ", ex);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.fatal("The DB property values entered in the DB.properties file are not correct. Please check");
			logger.error("Error while fetching properties", e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.fatal(
					"The database driver entered could not be loaded. Please check if a valid name is provided in DB.properties");
			logger.error("Error Class not found " + driver, e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.fatal("The database connection could not be completed. Please check DB.properties");
			logger.error("Error while creating connection to database ", e);
		}

	}

	void closeConnection() {
		if (this.conn != null) {
			try {
				conn.close();
				ConnectionRegister.connectionClosed();
				logger.info("Database connection closed, active connection now are "
						+ ConnectionRegister.get_ACTIVE_CONNECTIONS());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("Could not close connection", e);
				logger.info("Number of active connections in the system are "
						+ ConnectionRegister.get_ACTIVE_CONNECTIONS());
			}
		}
	}

	public ResultSet selectQuery(String query) {
		Statement statement = null;
		try {
			statement = this.conn.createStatement();
			return statement.executeQuery(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Could not execute query ", e);
			return null;
		}
	}
}
