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
import com.jqjava.lesson5.JsonUtils;
import com.jqjava.lesson5.ToastUtil;
import com.tools.SysApplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LookDetailPositionActivity extends Activity {
	    ProgressDialog dialog;
	    private String information; 
		Packager packager = new Packager(); // 封装
		Parser parser=new Parser();
		private String start_time;
		private String end_time;
		private String space_id;
		private String space_address;
		private String space_state;
		private String price_one="";
		private String price_two;
		private String power_one;
		private String power_two;
		private String space_suc;
		private String space_fail;
		private String imageBuffer="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_look_detail_position);
		SysApplication.getInstance().addActivity(this); 
		 Intent intent=getIntent();  
	        Bundle bundle=intent.getExtras();
	        space_state=bundle.getString("space_state");
	        space_address=bundle.getString("space_address");
	        space_id=bundle.getString("space_id");
	        start_time=bundle.getString("start_time");
	        end_time=bundle.getString("end_time");
   
	        dialog=new ProgressDialog(LookDetailPositionActivity.this);
			dialog.setMessage("正在加载....");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.show();
			
			 information = packager.selectSpaceInfoPackager(space_id);
		       Thread th= new Thread(){
		        	public void run(){
		        		String Web_result="";
			        	String url = IP.URL;
			    		List<Para> list = new ArrayList<Para>();// 存放参数的列表
			    		list.add(new Para("information", information));// 参数打包
			    		Web_result= HttpTools.postVisitWeb(url, list);
			    		List<Map<String, Object>> orderInfo= new ArrayList<Map<String, Object>>();
			 			
                       orderInfo = JsonUtils.listKeyMaps("address",Web_result);
			 			
			 			for(int i=0;i<orderInfo.size();i++){
			 				Map<String, Object> or=new HashMap<String, Object>();
							or=orderInfo.get(i);
							
							 power_one=or.get("power_one").toString();
						     power_two=or.get("power_two").toString();
						     price_one=or.get("price_one").toString();
						     price_two=or.get("price_two").toString();
						     space_suc=or.get("space_suc").toString();
						     space_fail=or.get("space_fail").toString();
						     imageBuffer=or.get("imageBuffer").toString();						    Log.v("space_suc",space_suc+"imageBuffer"+imageBuffer);
						    //dialog.dismiss();

			 			}
			 			dialog.dismiss();

		        	}
				
		        };

		        if(!(Thread.currentThread()==th)){
		        	Log.v("join","等待子线程join...");
		        try {
		        	th.start();
					th.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        Log.v("join","子线程借宿"+imageBuffer );
		        }
		        init();
		        
		        
		        
	}
	
	private void init(){
		ImageView image=(ImageView)findViewById(R.id.image);
		TextView tv_suc=(TextView)findViewById(R.id.textview9);
		TextView tv_state=(TextView)findViewById(R.id.textview0);
		TextView tv_addr=(TextView)findViewById(R.id.textview1);
		TextView tv_time=(TextView)findViewById(R.id.textview2);
		TextView tv_bian=(TextView)findViewById(R.id.textview3);
		TextView tv_pone=(TextView)findViewById(R.id.textview4);
		TextView tv_ptwo=(TextView)findViewById(R.id.textview5);
		Button bt_create=(Button)findViewById(R.id.button);
		tv_suc.setText("成功/失败次数:   "+space_suc+"/"+space_fail);
		tv_addr.setText("充电位地址:  "+space_address);
		tv_time.setText("出租时段:  "+start_time+"--"+end_time);
		tv_bian.setText("充电桩编号:  WER******");
		tv_pone.setText("常规充电 ("+power_one+"kw/h) :   ¥"+price_one);
		tv_ptwo.setText("快速充电 ("+power_two+"kw/h) :   ¥"+price_two);
		if(space_state.equals("0")){
			tv_state.setText("充电位状态：    "+"不出租");
			bt_create.setEnabled(false);
		}else if(space_state.equals("1")){
			tv_state.setText("充电位状态：    "+"可出租");
		}else if(space_state.equals("2")){
			tv_state.setText("充电位状态：    "+"被预定");
			bt_create.setEnabled(false);
			//bt_create.setClickable(false);
		}else if(space_state.equals("3")){
			tv_state.setText("充电位状态：    "+"充电中");
			bt_create.setEnabled(false);
			
		}
		
			Bitmap bitmap = null;
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
		        image.setImageBitmap(bm);
			}
		
		 bt_create.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent intent=new Intent(LookDetailPositionActivity.this,BookPositionActivity.class);
					intent.putExtra("space_id", space_id);
					intent.putExtra("start_time",start_time);
					intent.putExtra("end_time", end_time);
					intent.putExtra("space_address", space_address);
					intent.putExtra("space_state", space_state);
//					intent.putExtra("price_one", price_one);
//					intent.putExtra("price_two",price_two);
//					intent.putExtra("power_one", power_one);
//					intent.putExtra("power_two", power_two);
//					intent.putExtra("space_suc", space_suc);
//					intent.putExtra("space_fail", space_fail);
					startActivity(intent);
					
				}
			});
		
		
	}

	 public void back(View view) {
	        finish();
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.look_detail_position, menu);
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
