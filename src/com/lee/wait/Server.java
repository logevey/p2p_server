package com.lee.wait;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
	public static int portNo = 3333;
	public static void main(String args[]) throws IOException, SQLException {
		ServerSocket s = new ServerSocket(portNo);
		System.out.println("The Server is start: " + s);
		// 阻塞,直到有客户端连接
		try
		{
			for (;;)
			{
				Socket socket = s.accept();
				new ServerThread(socket);
			}
		}
		finally
		{
			s.close();
		}
	}
}
