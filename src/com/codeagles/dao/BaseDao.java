/**
 * 
 */
package com.codeagles.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author hcn
 * 数据库连接基础层
 */
public class BaseDao {
	protected static Connection conn = null;
	protected static Statement stmt = null;

	public BaseDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/jsspiderdata?user=root&password=root";
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	// 关闭连接
	public static void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}
}
