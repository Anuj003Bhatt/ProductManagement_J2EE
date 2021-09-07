package org.anuj.projectmanage.viewobjects;

import java.sql.ResultSet; 
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import org.anuj.projectmanage.databaseinterface.DBConnection;
import org.anuj.projectmanage.databaseinterface.PoolFactory;
import org.anuj.projectmanage.databaseinterface.PoolInterface;
import org.anuj.projectmanage.databaseinterface.constants.QueryOperators;
import org.anuj.projectmanage.interfaces.dbentities.Employees;
import org.anuj.projectmanage.interfaces.dbentities.ViewCriteriaItem;
import org.anuj.projectmanage.main.Main;
import org.apache.log4j.Logger;
import org.projecttmanage.customexceptions.IncorrectQueryException;
import org.projecttmanage.customexceptions.MaxConnectionsReachedException;

public class EmployeeVO {
	private static final Logger logger = Logger.getLogger(EmployeeVO.class);
	private Vector<Employees> employees;
	private HashMap<String, ViewCriteriaItem[]> viewCriteria = new HashMap<String, ViewCriteriaItem[]>();

	public HashMap<String, ViewCriteriaItem[]> getViewCrteria() {
		return viewCriteria;
	}

	public void setViewCrteria(String clause, ViewCriteriaItem[] criteria) {
		this.viewCriteria.put(clause, criteria);
	}

	public Vector<Employees> getEmployees() {
		return employees;
	}

	public void setEmployees(Vector<Employees> employees) {
		this.employees = employees;
	}

	public void executeQuery() throws IncorrectQueryException, SQLException {
		StringBuffer query = new StringBuffer();

		boolean nullConjunction = false;
		if (viewCriteria != null && !(viewCriteria.isEmpty())) {
			for (ViewCriteriaItem item : viewCriteria.get(QueryOperators.WHERE_CLAUSE)) {
				if (item.conjunction != null) {
					query.append(" " + item.criteriaString());
				} else {
					if (nullConjunction) {
						throw new IncorrectQueryException();
					} else {
						nullConjunction = true;
						query.insert(0, item.criteriaString());
					}
				}
			}

			query.insert(0, Employees.baseQuery + " " + QueryOperators.WHERE_CLAUSE + " ");
		}else {
			query.insert(0, Employees.baseQuery);
		}
		System.out.println("Query: " + query.toString());
		DBConnection connection = null;
		PoolInterface conn = PoolFactory.getPoolInterface("DB");
		ResultSet rs = null;
		try {
			connection = conn.getConnection();
			rs = connection.selectQuery(query.toString());
			Main.printTable(rs);
		} catch (MaxConnectionsReachedException e) {
			// TODO Auto-generated catch block

		} finally {
			if (rs != null)
				rs.close();
			if (conn != null)
				conn.closePool();
		}
	}

}
