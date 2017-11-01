package com.luoye.service;
import android.os.*;

public class MyHandler extends Handler
{

	@Override
	public void handleMessage(Message msg)
	{
		// TODO: Implement this method
		if (msg.what == 0)
		{
			//主线程获得并显示数据
			Bundle bd=msg.peekData();
			ServerActivity.tv.append(bd.getString("data"));
		}
	}
}
