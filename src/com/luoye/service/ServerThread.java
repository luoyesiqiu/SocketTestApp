package com.luoye.service;
import java.net.*;
import java.io.*;
import android.os.*;
import android.widget.*;

public class ServerThread extends Thread 
{
	MyHandler handler;
	ServerSocket ss;
	InputStream in;
	BufferedOutputStream fs;
	Socket s = null;
	final int BUF=8192;
	public ServerThread(ServerSocket ss)
	{
		this.ss = ss;
		handler = new MyHandler();
	}

	private void sendMsg(String text)
	{
		Message msg=new Message();
		Bundle bd=new Bundle();
		//bd.putString("data",sb.toString());
		bd.putString("data", text);
		msg.setData(bd);
		handler.sendMessage(msg);
	}


	@Override
	public void run()
	{
		// TODO: Implement this method
		while (true)
		{
			int size=0;
			try
			{
				s = ss.accept();
				sendMsg("ip:" + s.getRemoteSocketAddress().toString() + "。。。。。。已连接");
				//服务器获得客户端数据
				in = s.getInputStream();
				if (ServerActivity.isShowText)
				{
					char[] buf=new char[BUF];
					int len=0;
					InputStreamReader isr=new InputStreamReader(in);
					while((len=isr.read(buf))!=-1)
					{
						sendMsg("\n"+new String(buf,0,len));
						sendMsg("\n");
						
					}
				}
				else
				{
					fs = new BufferedOutputStream(new FileOutputStream(ServerActivity.SAVE_PATH));
					byte[] buf=new byte[BUF];
					int len=0;
					//StringBuilder sb=new StringBuilder();
					while ((len = in.read(buf, 0, buf.length)) != -1)
					{
						size += len;
						fs.write(buf, 0, len);
						fs.flush();
						sendMsg("\n文件正在传输,已传输" + size + "字节");
					}

					//给主线程发送消息
					sendMsg("\n文件传输完毕,大小" + size + "字节");
				}
			}
			catch (IOException e)
			{
			}
			finally
			{


				try
				{
					if (in != null)
						in.close();
				}
				catch (IOException e)
				{}
				try
				{
					if (fs != null)
						fs.close();
				}
				catch (IOException e)
				{}

				try
				{
					if (s != null)
						s.close();
				}
				catch (IOException e)
				{}
			}
		}
	}

}
