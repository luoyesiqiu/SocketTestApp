package com.luoye.service;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.net.*;
import java.io.*;
import android.widget.TabHost.*;
import android.content.*;

public class MainActivity extends TabActivity
{
		TabHost tabHost; 
		TabSpec spec;
		Intent intent;// Reusable Intent for each tab
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		//getWindow().setFlags(1024,1024);
        setContentView(R.layout.main);
		//overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);

		tabHost = getTabHost();
		//Resources res = getResources();

        //第一个TAB
		intent = new Intent(this, ServerActivity.class);//新建一个Intent用作Tab1显示的内容
		spec = tabHost.newTabSpec("tab1")//新建一个 Tab
			.setIndicator("服务端")//设置名称以及图标
			.setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
		tabHost.addTab(spec);//添加进tabHost

        //第二个TAB
		intent = new Intent(this, ClientActivity.class);//第二个Intent用作Tab1显示的内容
		spec = tabHost.newTabSpec("tab2")//新建一个 Tab
			.setIndicator("客户端")//设置名称以及图标
			.setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
		tabHost.addTab(spec);//添加进tabHost


		tabHost.setCurrentTab(0);
    }

	
		
}
