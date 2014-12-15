package com.lee.wait;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

class ServerThread extends Thread {
	// 数据库
	Database sql;
	String query;
	// 客户端的socket
	private Socket clientSocket;
	// IO句柄
	private BufferedReader sin;
	private PrintWriter sout;

	// 默认的构造函数
	public ServerThread() {

	}

	public ServerThread(Socket s) throws IOException, SQLException {
		sql = new Database("root", "root", "p2p");

		clientSocket = s;
		sin = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		sout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
				clientSocket.getOutputStream())), true);

		// 开启线程
		start();
	}

	// 线程执行的主体函数
	public void run() {
		try {

			String name = null;
			// 用循环来监听通讯内容
			for (;;) {
				String str = sin.readLine();
				if (str != null) {
					String[] strarray = str.split("%");
					if (strarray[0].equals("1")) {
						System.out.println("登录操作" + str);
						query = "select * from user where username='"
								+ strarray[1] + "'";
						sql.executeQuery(query);
						int flag = 0;
						while (sql.rs.next()) {
							String pw = sql.rs.getString("password");
							if (pw.equals(strarray[2])) {
								flag = 1;
							}
						}
						if (flag == 1) {
							String ip = clientSocket.getInetAddress()
									.getHostAddress();
							query = "UPDATE user SET online = 1 WHERE username = '"
									+ strarray[1] + "'";
							sql.executeUpdate(query);
							query = "UPDATE user SET userip = '"
									+ strarray[4] + "' WHERE username = '"
									+ strarray[1] + "'";
							sql.executeUpdate(query);
							query = "UPDATE user SET userport = '"
									+ strarray[3] + "' WHERE username = '"
									+ strarray[1] + "'";
							sql.executeUpdate(query);
							name = strarray[1];
							sout.println("yes");
						} else {
							sout.println("no");
						}
					} else if (strarray[0].equals("2")) {
						System.out.println("注册操作" + str);
						if (sql.getResultNum("select * from user where username='"
								+ strarray[1] + "'") > 0) {
							sout.println("no");
						} else {
							query = "insert into user values ('" + strarray[1]
									+ "','" + strarray[2] + "','" + strarray[4] + "','"
									+ strarray[3] + "','0')";
							int res = sql.executeUpdate(query);
							if (res > 0) {
								sout.println("yes");
							}
						}
					} else if (strarray[0].equals("3")) {
						System.out.println("搜索操作" + str);
						query = "select filename,filesize,user.username,online from metadata,user "
								+ "where metadata.username=user.username and filename='"
								+ strarray[1] + "'";
						sql.executeQuery(query);
						while (sql.rs.next()) {
							String filename = sql.rs.getString("filename");
							String filesize = sql.rs.getString("filesize");
							String username = sql.rs.getString("username");
							String online = sql.rs.getString("online");
							online = online.equals("1") ? "online" : "offline";
							if (!username.equals(name)) {
								sout.println(filename + "%" + filesize + "%"
										+ username + "%" + online);
							}
						}
						sout.println("eof");
					} else if (strarray[0].equals("4")) {
						System.out.println("我的上传操作" + str);
						if (name != null) {
							query = "select filename,filepath,filesize from metadata where username ='"
									+ name + "'";
							sql.executeQuery(query);
							while (sql.rs.next()) {
								String filename = sql.rs.getString("filename");
								String filepath = sql.rs.getString("filepath");
								String filesize = sql.rs.getString("filesize");
								sout.println(filename + "%" + filepath + "%"
										+ filesize);
							}
						}
						sout.println("eof");

					} else if (strarray[0].equals("5")) {
						if (name != null) {
							query = "UPDATE user SET online = 0 WHERE username = '"
									+ name + "'";
							sql.executeUpdate(query);
							name = null;
						}
						System.out.println("退出操作" + str);
						break;
					} else if (strarray[0].equals("6")) {
						if (name != null) {
							query = "UPDATE user SET online = 0 WHERE username = '"
									+ name + "'";
							sql.executeUpdate(query);
							name = null;
						}
						System.out.println("注销操作" + str);
					} else if (strarray[0].equals("7")) {
						System.out.println("上传操作" + str);
						if (sql.getResultNum("select * from metadata where filename='"
								+ strarray[1] + "' and username='" + name + "'") > 0) {
							sout.println("no");
						} else {
							query = "insert into metadata values ('"
									+ strarray[1] + "','" + strarray[2] + "','"
									+ strarray[3] + "','" + name + "')";
							int res = sql.executeUpdate(query);
							if (res > 0) {
								sout.println("yes");
							}
						}
					} else if (strarray[0].equals("8")) {
						System.out.println("删除操作" + str);
						if (name != null) {
							query = "delete from metadata where filename ='"
									+ strarray[1] + "' and username ='" + name
									+ "'";
							int res = sql.executeUpdate(query);
							if (res > 0) {
								sout.println("yes");
							} else {
								sout.println("no");
							}
							query = "select filename,filepath,filesize from metadata where username ='"
									+ name + "'";
							sql.executeQuery(query);
							while (sql.rs.next()) {
								String filename = sql.rs.getString("filename");
								String filepath = sql.rs.getString("filepath");
								String filesize = sql.rs.getString("filesize");
								sout.println(filename + "%" + filepath + "%"
										+ filesize);
							}
						}
						sout.println("eof");

					} else if (strarray[0].equals("9")) {
						System.out.println("下载操作" + str);
						if (name != null) {
							query = "select userip,userport,filepath from metadata,user "
									+ "where metadata.username=user.username and user.username='"
									+ strarray[2]
									+ "' and filename='"
									+ strarray[1] + "'";

							sql.executeQuery(query);

							while (sql.rs.next()) {
								String userip = sql.rs.getString("userip");
								String userport = sql.rs.getString("userport");
								String filepath = sql.rs.getString("filepath");
								sout.println(userip + "%" + userport+"%"+filepath);
							}
						}
					} else {
						System.out.println("无效操作" + str);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				sql.closeConn();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
