package com.luoye.service;
import java.net.*;
import java.io.*;

public class MyServer extends ServerSocket
{
	
	public MyServer(int port) throws IOException{
		super(port);
		new ServerThread(this).start();
	}
	
}
