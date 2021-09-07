package org.anuj.projectmanage.propertiesinterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 *
 * <b>Description</b><br/>
 * This class provides an interface to deal with the properties files to fetch
 * and store properties.
 *
 * @author Anuj Narayan Bhatt <br/>
 *         <br/>
 *
 *
 */
public interface PropertiesInterfaceForFile {

	static final Logger logger = Logger.getLogger(PropertiesInterfaceForFile.class);

	public static Map getProperties(String props_fileName, String... properties)
			throws FileNotFoundException, PropertyNotDefinedException {
		if (props_fileName == null || "".equals(props_fileName)) {
			logger.trace("Invalid properties files hence returning");
			Map errorMap = new HashMap<String, String>();
			errorMap.put("ERROR", "No property provided so returning error !!");
			return errorMap;
		}
		if (properties.length <= 0) {
			logger.trace("No property given in arguments");
			Map errorMap = new HashMap<String, String>();
			errorMap.put("ERROR", "No property provided so returning error !!");
			return errorMap;
		}
		Map props_map = new HashMap<String, String>();
		Properties props = new Properties();
		logger.info("Loading properties from file " + props_fileName);
		try {
			props.load(PropertiesInterfaceForFile.class.getClassLoader().getResourceAsStream(props_fileName));
			logger.trace("Properties file loaded: " + props_fileName);
			for (String property : properties) {
				String property_value = props.getProperty(property);
				if (property_value == null) {
					throw new PropertyNotDefinedException();
				}

				logger.trace("Property fetched successfully <Key>:<Value>: " + property + " : " + property_value);
				props_map.put(property, property_value);
			}
			return props_map;
		} catch (FileNotFoundException ex) {
			logger.error("The properties file could not be found " + props_fileName, ex);
			throw new FileNotFoundException(
					"The properties file passed does not exist please check file: src/main/resource/" + props_fileName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Unable to load properties from " + props_fileName, e);
			return null;
		} finally {
			props.clear();

		}
	}

	public static void main(String args[]) {

	}

}
