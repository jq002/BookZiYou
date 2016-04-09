package com.example.bookziyou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kobjects.base64.Base64;

import com.entity.Para;

import com.httpconnet.HttpTools;
import com.httpconnet.IP;
import com.httpconnet.Packager;
import com.httpconnet.Parser;
import com.jqjava.lesson5.DemoApplication;
import com.jqjava.lesson5.JsonUtils;
import com.jqjava.lesson5.ToastUtil;
import com.tools.SysApplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LookPositionActivity extends Activity {
	private String chargeID;
	ProgressDialog dialog;
	   private String information; 
		Packager packager = new Packager(); // 封装
		Parser parser=new Parser();
		private String start_time;
		private String end_time;
		private String space_id;
		private String space_address;
		private String space_state;
		
		List<Map<String, Object>> orderInfo= new ArrayList<Map<String, Object>>();
		MyAdapter adapter;
		
		 Handler handler=new Handler(){
		    	@Override
		    	public void handleMessage(Message msg){
		    		if(msg.obj==null){
		    			ToastUtil.show(LookPositionActivity.this, "网络连接异常");
		    			return;
		    		}else{
		    			String ss=msg.obj.toString();    
		    			orderInfo = JsonUtils.listKeyMaps("address",ss);
		    			Log.v("orderInfo",orderInfo.toString());

		    			
//		    			ToastUtil.show(getApplicationContext(), "order订单总数："+orderInfo.size());
		    			
		    			ListView listview=(ListView) findViewById(R.id.listview);
		    			adapter=new MyAdapter(LookPositionActivity.this);
		    			listview.setAdapter(adapter);
		    			dialog.dismiss();

		    		}
		    	}
		    };


		 private String imageBuffer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_look_position);
		SysApplication.getInstance().addActivity(this); 
		    Intent intent=getIntent();  
	        Bundle bundle=intent.getExtras();
	        chargeID=bundle.getString("chargeID");
	        
	        Log.v("look_position chargeID ",chargeID);
	        
	    	dialog=new ProgressDialog(LookPositionActivity.this);
			dialog.setMessage("正在加载....");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.show();
			
			 information = packager.LookPositionPackager(chargeID);
		        new Thread(){
		        	public void run(){
		        		String Web_result="";
			        	String url = IP.URL;
			    		List<Para> list = new ArrayList<Para>();// 存放参数的列表
			    		list.add(new Para("information", information));// 参数打包
			    		Web_result= HttpTools.postVisitWeb(url, list);
			       
		        	Message msg=new Message();
		        	msg.obj=Web_result;
		        	handler.sendMessage(msg);
		        	}
				
		        }.start();
	}
	 public void back(View view) {
	        finish();
	    }
	class MyAdapter extends BaseAdapter{
		LayoutInflater inflater;
		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			inflater=LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return orderInfo.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView=inflater.inflate(R.layout.order_item, null);


			 start_time=(String) orderInfo.get(position).get("start_time");
			 end_time=(String) orderInfo.get(position).get("end_time");
			 space_id=(String) orderInfo.get(position).get("space_id");
			 space_address=(String) orderInfo.get(position).get("space_address");
			 space_state=(String) orderInfo.get(position).get("space_state");
			 imageBuffer=(String) orderInfo.get(position).get("imageBuffer");
 
			 Button bt_look=(Button)convertView.findViewById(R.id.look);
			   bt_look.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 start_time=(String) orderInfo.get(position).get("start_time");
					 end_time=(String) orderInfo.get(position).get("end_time");
					 space_id=(String) orderInfo.get(position).get("space_id");
					 space_address=(String) orderInfo.get(position).get("space_address");
					 space_state=(String) orderInfo.get(position).get("space_state");
					 Log.v("position ","spaceid"+space_id);

					Intent intent=new Intent(LookPositionActivity.this,LookDetailPositionActivity.class);
					intent.putExtra("space_id", space_id);
					intent.putExtra("start_time",start_time);
					intent.putExtra("end_time", end_time);
					intent.putExtra("space_address", space_address);
					intent.putExtra("space_state", space_state);
					startActivity(intent);
					
				}
			});
			 Button bt_create=(Button)convertView.findViewById(R.id.create_order);
			 bt_create.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 start_time=(String) orderInfo.get(position).get("start_time");
					 end_time=(String) orderInfo.get(position).get("end_time");
					 space_id=(String) orderInfo.get(position).get("space_id");
					 space_address=(String) orderInfo.get(position).get("space_address");
					 space_state=(String) orderInfo.get(position).get("space_state");
					 Log.v("position ","spaceid"+space_id);

					Intent intent=new Intent(LookPositionActivity.this,BookPositionActivity.class);
					intent.putExtra("space_id", space_id);
					intent.putExtra("start_time",start_time);
					intent.putExtra("end_time", end_time);
					intent.putExtra("space_address", space_address);
					intent.putExtra("space_state", space_state);

					startActivity(intent);
					
				}
			});
			 ImageView imageview=(ImageView)convertView.findViewById(R.id.imageview3);

			TextView tv_position_address=(TextView) convertView.findViewById(R.id.position_address);
			TextView tv_time=(TextView) convertView.findViewById(R.id.time);
			TextView tv_state=(TextView) convertView.findViewById(R.id.state);
			tv_position_address.setText(space_address);
			tv_time.setText(start_time+"--"+end_time);
			if(space_state.equals("0")){
				tv_state.setText("不出租");
				bt_create.setEnabled(false);
			}else if(space_state.equals("1")){
				tv_state.setText("可出租");
			}else if(space_state.equals("2")){
				tv_state.setText("被预定");
				bt_create.setEnabled(false);
				//bt_create.setClickable(false);
			}else if(space_state.equals("3")){
				tv_state.setText("充电中");
				bt_create.setEnabled(false);
				
			}

			 if(!imageBuffer.equals("")){
				 byte[] buffer = new Base64().decode(imageBuffer);   
					
				   BitmapFactory.Options options = new BitmapFactory.Options();
			        options.inJustDecodeBounds = true;
			        Bitmap bmp = BitmapFactory.decodeByteArray(buffer , 0, buffer.length, options);//.decodeFile(paths.get(0), options);
			        
			        int height = options.outHeight * 200 / options.outWidth;
			        options.inSampleSize = options.outWidth / 200;
			        options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
			        options.inPurgeable = true;
			        options.inInputShareable = true;
			        options.outWidth = 200;
			        options.outHeight = height; 
			        options.inJustDecodeBounds = false;
			    	Log.v("height",String.valueOf(height));
			        //Bitmap bm = BitmapFactory.decodeFile(paths.get(0), options);
			        Bitmap bm = BitmapFactory.decodeByteArray(buffer , 0, buffer.length, options);//.decodeFile(paths.get(0), options);
			    	//Bitmap bm = BitmapFactory.decodeFile(imageBuffer, options);
			        imageview.setBackgroundResource(0);
			        imageview.setImageBitmap(bm);
			 }
			return convertView;
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.look_position, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
