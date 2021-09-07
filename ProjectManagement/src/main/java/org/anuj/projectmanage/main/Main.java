package org.anuj.projectmanage.main;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.anuj.projectmanage.databaseinterface.DBConnection;
import org.anuj.projectmanage.databaseinterface.PoolFactory;
import org.anuj.projectmanage.databaseinterface.PoolInterface;
import org.anuj.projectmanage.databaseinterface.constants.QueryOperators;
import org.anuj.projectmanage.interfaces.dbentities.ViewCriteriaItem;
import org.anuj.projectmanage.viewobjects.EmployeeVO;
import org.projecttmanage.customexceptions.IncorrectQueryException;
import org.projecttmanage.customexceptions.MaxConnectionsReachedException;

public class Main {

	public static void printTable(ResultSet rs) {
		try {
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				System.out.print(rs.getMetaData().getColumnName(i) + " ");
			}
			System.out.println("");

			while (rs.next()) {

				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.print(rs.getString(i) + " ");
				}
				System.out.println("");
			}
		} catch(Exception ex){
			
		}finally {
			if (rs != null)
				try {
					rs.getStatement().close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static void main(String args[]) throws SQLException, IncorrectQueryException {
//		DBConnection connection = null;
//		PoolInterface conn = PoolFactory.getPoolInterface("DB");
//		ResultSet rs = null;
//		try {
//			connection = conn.getConnection();
//			rs = connection.selectQuery("select * from employees");
//			printTable(rs);
//			// conn.setConnectionFree(connection);
//
//			connection = conn.getConnection();
//			rs = connection.selectQuery("select * from roles");
//			printTable(rs);
//			conn.setConnectionFree(connection);
//
//			connection = conn.getConnection();
//			rs = connection.selectQuery("select * from employees");
//			printTable(rs);
//
//		} catch (MaxConnectionsReachedException e) {
//			// TODO Auto-generated catch block
//
//		} finally {
//
//			conn.closePool();
//		}
		EmployeeVO employees = new EmployeeVO();
//		ViewCriteriaItem[] items = {new ViewCriteriaItem("first_name",QueryOperators.WHERE_EQUALS,"\'Anmol\'")};
//		employees.setViewCrteria(QueryOperators.WHERE_CLAUSE, items);
		employees.executeQuery();
		
	}

}
