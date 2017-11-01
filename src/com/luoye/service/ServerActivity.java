package com.luoye.service;
import android.app.*;
import android.widget.*;
import android.os.*;
import java.io.*;
import android.util.*;
import android.view.View.*;
import android.view.*;
import android.net.wifi.*;
import android.content.*;
import android.widget.CompoundButton.*;

public class ServerActivity extends Activity 
{

    /** Called when the activity is first created. */
	public static TextView tv,tv_ip;
	public Button bn_clear;
	public EditText edit;
	public static String SAVE_PATH;
	MyHandler handler;
	WifiManager wifiManager;
	MyServer serv;
	CheckBox cb;
	static boolean isShowText=false;
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_server);
		tv = (TextView)findViewById(R.id.tv_msg);
		bn_clear=(Button)findViewById(R.id.bn_clear);
		edit=(EditText)findViewById(R.id.edit_save_path);
		tv_ip=(TextView)findViewById(R.id.tv_ip);
		cb=(CheckBox)findViewById(R.id.layout_server_is_show_content);
		wifiManager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
		
		tv_ip.setText("本机ip:"+int2Ip(wifiManager.getConnectionInfo().getIpAddress()));
		
		SAVE_PATH=edit.getText().toString();
		handler=new MyHandler();
		try
		{
			serv=new MyServer(3574);
			tv.setText("服务器开启成功");
		}
		catch (IOException e)
		{
			Toast.makeText(this,"服务器启动失败："+e.toString(),2000).show();
		}
		Toast.makeText(this,"软件制作：落叶似秋\n本软件服务器端口：3574",2000).show();
		bn_clear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					tv.setText("");
					
				}
			});
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					// TODO: Implement this method
					isShowText=isShowText?false:true;
				}
				
				
			});
    }

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		SAVE_PATH=edit.getText().toString();
	}
	/**
     * 将int型ip转成String型ip
     * @param intIp
     * @return
     */
    public static String int2Ip(int intIp){
        byte[] bytes = int2byte(intIp);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 4; i++){
            sb.append(bytes[i] & 0xFF);
            if(i < 3){
                sb.append(".");
            }
        }
        return sb.toString();
    }

    private static byte[] int2byte(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (0xff & i);
        bytes[1] = (byte) ((0xff00 & i) >> 8);
        bytes[2] = (byte) ((0xff0000 & i) >> 16);
        bytes[3] = (byte) ((0xff000000 & i) >> 24);
        return bytes;
    }

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		finish();
		//System.exit(0);
		
		try
		{
			serv.close();
		}
		catch (IOException e)
		{}

	}
	
	
}
