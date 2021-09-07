package org.anuj.projectmanage.interfaces.dbentities;

import org.apache.log4j.Logger;
import org.projecttmanage.customexceptions.InvalidCriteriaException;

public class ViewCriteriaItem {
	private static final Logger logger = Logger.getLogger(ViewCriteriaItem.class);
	public static final String[] SUPPORTED_CRITERIA_TYPES= {"FILTER","ORDER","GROUP"};
	public String conjunction;
	public String column;
	public String operator;
	public String value;

	public void setCriteriaType(String type) throws InvalidCriteriaException {
		if(ViewCriteriaItem.SUPPORTED_CRITERIA_TYPES[0].equals(type)) {
			this.conjunction=" WHERE ";
			return;
		}
		if(ViewCriteriaItem.SUPPORTED_CRITERIA_TYPES[1].equals(type)) {
			this.conjunction=" ORDER BY ";
			return;
		}	
		if(ViewCriteriaItem.SUPPORTED_CRITERIA_TYPES[2].equals(type)) {
			this.conjunction=" GROUP BY ";
			return;
		}
		
		throw new InvalidCriteriaException();
	}
	
	public ViewCriteriaItem(String type, String column, String operator, String value) {
		try {
			this.setCriteriaType(type);
		} catch (InvalidCriteriaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.conjunction = null;
		}
		this.column = column;
		this.operator = operator;
		this.value = value;
	}

	public ViewCriteriaItem(String column, String operator, String value) {
		this.conjunction = null;
		this.column = column;
		this.operator = operator;
		this.value = value;
	}

	public String criteriaString() {
		logger.debug("Criteria item for inputs: " + conjunction + " : " + column + " : " + operator + " : " + value);
		StringBuffer criteria = new StringBuffer("");
		if ( null == conjunction || "".equals(conjunction)) {
			return criteria.toString();
		}
			
		criteria.append(conjunction);
		criteria.append(column).append(operator).append(value);
		return criteria.toString();
	}

}