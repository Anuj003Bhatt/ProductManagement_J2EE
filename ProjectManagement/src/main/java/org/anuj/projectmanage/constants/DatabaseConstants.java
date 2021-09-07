package org.anuj.projectmanage.constants;


/**
 * 
 * <b>Description:</b><br/>
 *  This interface contains all the dababase constants like<br/>
 *  1. Keys used in the DB.properties file to store the database information required to connect to the database<br/>
 *  2. Character constants to create the argument string for the database connection<br/>
 * @author Anuj Narayan Bhatt
 * <br/><br/>
 * 
 * 
 */
public interface DatabaseConstants {
	// file that contains db properties
	public static final String DB_PROPERTIES_FILE="DB.properties";
	
	// Keys for Connection parameters in the "DB_PROPERTIES_FILE"
	public static final String URL="URL";
	public static final String DRIVER="Driver";
	public static final String DATABASE="Database_Name";
	public static final String USER="Username";
	public static final String PASSWORD="Password";
	
	// Keys for Connection arguments in the DB_PROPERTIES_FILE to create argument string 
	public static final String CHARACTER_SET="useUnicode";
	public static final String TIME_ZONE_SHIFT="useJDBCCompliantTimezoneShift";
	public static final String LEGACY_DATE_TIME_CODE="useLegacyDatetimeCode";
	public static final String SERVER_TIME_ZONE="serverTimezone";
	public static final String DB_ARGUMENT_ASSIGN_OPERATOR="=";
	
	// Character constants to append arguments to db string and also the conjuction to add more
	public static final String APPEND_ARGUMENTS_MORE="&";
	public static final String APPEND_ARGUMENTS_START="?";
	
}
