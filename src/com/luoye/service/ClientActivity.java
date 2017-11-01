package com.luoye.service;
import java.io.*;
import java.net.*;
import android.widget.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.CompoundButton.*;

public class ClientActivity 
extends Activity 
implements Runnable,OnClickListener
{

	Socket s = null;
	OutputStream os = null;
	EditText tb_path,tb_ip;
	Button bn_send;
	String path,ip;
	BufferedReader br;
	int size=0;
	//InputStream is;
	StringBuilder serverMsg=new StringBuilder();
	ProgressDialog pb;
	CheckBox cb;
	boolean isFileAsFile=true;
	String msg="";
	final int BUF=8192;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_client);
		tb_path = (EditText)findViewById(R.id.tb_path);
		tb_ip = (EditText)findViewById(R.id.tb_ip);
		bn_send = (Button)findViewById(R.id.bn_send);
		cb = (CheckBox)findViewById(R.id.layout_client_is_send_file);
		bn_send.setOnClickListener(this);
		cb.setChecked(true);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					// TODO: Implement this method
					isFileAsFile=isFileAsFile?false:true;
					
				}
				
			
		});
    }

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		if (p1.getId() == R.id.bn_send)
		{

			path = tb_path.getText().toString();
			msg=path;
			ip = tb_ip.getText().toString();
			if (!new File(path).exists()&&isFileAsFile)
			{
				Toast.makeText(this, "文件不存在", 2000).show();
			}
			else if (ip.equals(""))
			{
				Toast.makeText(this, "ip不能为空", 2000).show();
			}
			else
			{
				if(isFileAsFile)
				pb = ProgressDialog.show(ClientActivity.this, "", "已传输" + size + "字节");
				new Thread(this).start();
			}
		}
	}

	@Override
	protected void onStart()
	{
		// TODO: Implement this method

		super.onStart();
	}

	private void sendFile()
	{
		FileInputStream fin=null;
		size = 0;
		try
		{
			s = new Socket(ip, 3574);

			os = s.getOutputStream();
			fin = new FileInputStream(path);
			byte[] buf=new byte[BUF];
			int len=0;
			while ((len = fin.read(buf)) != -1)
			{
				size += len;
				os.write(buf, 0, len);
				os.flush();
				ClientActivity.this.runOnUiThread(new Runnable(){

						@Override
						public void run()
						{
							// TODO: Implement this method
							pb.setMessage("已传输" + size + "字节");
						}


					});
			}
			ClientActivity.this.runOnUiThread(new Runnable(){

					@Override
					public void run()
					{
						// TODO: Implement this method
						pb.hide();
						Toast.makeText(ClientActivity.this, "传输完成", 2000).show();
					}


				});

		}
		catch (IOException e)
		{}
		finally
		{
			if (fin != null)
				try
				{

					fin.close();
				}
				catch (IOException e)
				{}

			if (os != null)
				try
				{

					os.close();
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

	private void sendMsg()
	{
		PrintWriter pw=null;
		try
		{
			s = new Socket(ip, 3574);
			os=s.getOutputStream();
			 pw=new PrintWriter(os);
			pw.write(msg);
			
		}
		catch (IOException e)
		{}
		finally{
			if(pw!=null)
			{
				pw.close();
			}
			try
			{
				s.close();
			}
			catch (IOException e)
			{}
		}
		
		ClientActivity.this.runOnUiThread(new Runnable(){

				@Override
				public void run()
				{
					// TODO: Implement this method
					Toast.makeText(ClientActivity.this, "传输完成", 2000).show();
				}


			});

	}
	@Override
	public void run()
	{
		// TODO: Implement this method
		if(isFileAsFile)
			sendFile();
			else
				sendMsg();
	}
	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		finish();
		System.exit(0);
	}
}
