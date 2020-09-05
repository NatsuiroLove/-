package cn.edu.zucc.personplan.util;

import java.sql.Connection;

public class DBUtil {
	private static final String jdbcUrl="jdbc:mysql://localhost:3306/personalPlan?serverTimezone=UTC";
	private static final String dbUser="root";
	private static final String dbPwd="shengshiyan1311";

	static{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
//			System.out.println("驱动加载成功!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			System.out.println("驱动加载失败!");
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws java.sql.SQLException{
		return java.sql.DriverManager.getConnection(jdbcUrl, dbUser, dbPwd);
	}
}
