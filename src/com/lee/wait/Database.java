package com.lee.wait;

import java.sql.*;

public class Database {
	Connection conn;
	ResultSet rs;
	public Database()
	{
		this("root","","mysql");
	}
	public Database(String user,String pwd,String database){
		try
		{
			String url="jdbc:mysql://localhost/"+database;

			//加载驱动，这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//建立到MySQL的连接
			conn = DriverManager.getConnection(url,user, pwd);
		}
		catch (Exception ex)
		{
			System.out.println("Error : " + ex.toString());
		}	
	}
	public void executeQuery(String query) throws SQLException{
		Statement stmt = conn.createStatement();//创建语句对象，用以执行sql语言
		rs = stmt.executeQuery(query);
	}
	public int executeUpdate(String query) throws SQLException{
		Statement stmt = conn.createStatement();//创建语句对象，用以执行sql语言
		return stmt.executeUpdate(query);
	}
	public int getResultNum(String query) throws SQLException{
		Statement stmt = conn.createStatement();//创建语句对象，用以执行sql语言
		ResultSet rs = stmt.executeQuery(query);
		int num=0;
		//处理结果集
		while (rs.next())
		{
			num++;
		}
		rs.close();//关闭数据库
		return num;
	}

	public void closeConn() throws SQLException{
		conn.close();
	}
}
