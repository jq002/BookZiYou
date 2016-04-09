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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchStationActivity extends Activity {

    ProgressDialog dialog;

	private TextView text;
	private Button button;
	private String  parklot_name;
	private String   parklot_address;
	private String  all_num;
	private String  using_num;
	private String imageBuffer;
	
	private TextView textview1;
	private TextView textview2;
	private TextView textview3;
	private TextView textview4;
	private ProgressDialog progDialog = null;//进度框
	    private String tel;
	    private String user_id;
	    private String chargeID;
	    private ImageView image;
	    private String station_address2;
	    private String station_id;
	    private String information; 
		Packager packager = new Packager(); // 封装
		Parser parser=new Parser();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
		setContentView(R.layout.activity_search_station);
		SysApplication.getInstance().addActivity(this); 
		DemoApplication app = (DemoApplication)getApplication();  		
		user_id=(String)app.get("id");
		tel=(String)app.get("tel");
		
        Intent intent=getIntent();  
        Bundle bundle=intent.getExtras();
        chargeID=bundle.getString("chargeID");
        
        information = packager.LookStationPackager(chargeID);
        
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
					
					 parklot_name=or.get("station_name").toString();
				     parklot_address=or.get("station_address1").toString();
				     station_address2=or.get("station_address2").toString();
				     all_num=or.get("all_num").toString();
				     using_num=or.get("using_num").toString();
				     imageBuffer=or.get("imageBuffer").toString();
				     Log.v("imageBuffer",imageBuffer);
				     station_id=or.get("station_id").toString();
	 			}
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
        textview1=(TextView)findViewById(R.id.textview1);
        
        textview2=(TextView)findViewById(R.id.textview2);
        textview3=(TextView)findViewById(R.id.textview3);
        textview4=(TextView)findViewById(R.id.textview4);
        image=(ImageView)findViewById(R.id.image);
        
        WindowManager wm = this.getWindowManager();
        
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        image.setMaxWidth(width);
        image.setMinimumWidth(height/3);
        //显示图片
        if(!imageBuffer.equals("")){
        	showPhoto();
        }
        //showPhoto();
        //image.setImageBitmap(bitmap);
		textview1.setText("车场名称：    "+parklot_name);
        textview2.setText("帮助地址：    "+parklot_address);
        textview3.setText("总充电位：        "+all_num);
        textview4.setText("已充电位："+using_num);
     
         button=(Button)findViewById(R.id.button);//此按钮，进入查看停车位页面
 		 button.setOnClickListener(new Button.OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				Intent intent=new Intent(SearchStationActivity.this,LookPositionActivity.class);
 				intent.putExtra("chargeID",chargeID);
				
 				startActivity(intent);
 				
 				

 				
 			}
 		});
    
	}
	 public void back(View view) {
	        finish();
	    }
	/**
	 * 显示进度条对话框
	 */
	public void showDialog() {
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在加载");
		progDialog.show();
	}

	/**
	 * 隐藏进度条对话框
	 */
	public void dismissDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}
	public void showPhoto(){
		 Bitmap bitmap = null;
		String result;
		result=imageBuffer;
	//	Log.v("imageBuffer",imageBuffer);
		byte[] buffer = new Base64().decode(result);   
		
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
//	public void showPhoto(){
//		
//		 Bitmap bitmap = null;
//  		String result;
//  		result=imageBuffer;
//  	//	Log.v("imageBuffer",imageBuffer);
//  		byte[] buffer = new Base64().decode(result);   
//
//  	    BitmapFactory bitmap1=new BitmapFactory();
//  	    bitmap=bitmap1.decodeByteArray(buffer, 0, buffer.length);
////          baos.write(result);
//  		//imageviews.setImageBitmap(bitmap);
//  		//image.setImageBitmap(bitmap);
//  	 // ToastUtil.show(SearchParkActivity.this, bitmap.getWidth()+";;;;"+bitmap.getHeight());
//  	 // Log.v("bitmap",bitmap.toString());
//  		image.setImageBitmap(bitmap);
//  		//useloginWeb(result);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_station, menu);
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
