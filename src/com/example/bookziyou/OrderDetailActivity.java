package com.example.bookziyou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entity.Para;
import com.httpconnet.HttpTools;
import com.httpconnet.IP;
import com.httpconnet.Packager;
import com.httpconnet.Parser;
import com.jqjava.lesson5.JsonUtils;
import com.tools.SysApplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetailActivity extends FragmentActivity {
	private Fragment[] fragments;
	 private FragmentOrderState orderstatefragment;
	 private FragmentOrderDetail orderdetailfragment;
	 private TextView[] textviews;
	    private int index;
	    // 当前fragment的index
	    private int currentTabIndex=0;
	    private Context otherContext; 
		private String tel;
		private String id;
		private String order_id;
		private String station_name;
		
		  private String information; 
			Packager packager = new Packager(); // 封装
			Parser parser=new Parser();
			private String space_address;
			private String total_price;
			private String use_power;
			private String use_electric;
			private String begin_time;//实际时长
			private String final_time;
			private String real_price;
			private String order_time;			
//			 Handler handler=new Handler(){
//			    	@Override
//			    	public void handleMessage(Message msg){
//			    		//m_pDialog.hide();			    		
//			    		if(msg.obj==null){
//			    			Toast.makeText(getApplicationContext(), "网络连接异常",  
//				                       Toast.LENGTH_SHORT).show(); 
//			    			return;
//			    		}
//			    		List<Map<String, Object>> orderInfo= new ArrayList<Map<String, Object>>();
//						String ss=msg.obj.toString();    			
//						//Toast.makeText(SearchOrderActivity.this,ss, Toast.LENGTH_SHORT).show();
//						orderInfo = JsonUtils.listKeyMaps("address",ss);
////						Toast.makeText(getApplicationContext(), "网络"+orderInfo,  
////			                       Toast.LENGTH_SHORT).show(); 
//						for(int i=0;i<orderInfo.size();i++){
//							
//							Map<String, Object> or=new HashMap<String, Object>();
//							or=orderInfo.get(i);				
//							space_address=or.get("space_address").toString();			
//							total_price=or.get("total_price").toString();						
//							use_power=or.get("use_power").toString();
//							use_electric=or.get("use_electric").toString();	
//							begin_time=or.get("begin_time").toString();	
//							final_time=or.get("final_time").toString();	
//							real_price=or.get("real_price").toString();	
//							order_time=or.get("order_time").toString();		
//							price=or.get("price").toString();
//							use_state=or.get("use_state").toString();
//							start_time=or.get("start_time").toString();
//							end_time=or.get("end_time").toString();
//							electric=or.get("electric").toString();
//						}
//						Bundle arguments=new Bundle();
//				  		arguments.putString("order_id", order_id);
//				  		arguments.putString("station_name", station_name);
//				  		arguments.putString("space_address", space_address);
//				  		arguments.putString("total_price", total_price);
//				  		arguments.putString("use_power", use_power);
//				  		arguments.putString("use_electric", use_electric);
//				  		arguments.putString("begin_time",begin_time);
//				  		arguments.putString("final_time",final_time);
//				  		arguments.putString("real_price",real_price);
//				  		arguments.putString("order_time",order_time);
//				  		arguments.putString("order_state", order_state);
//				  		arguments.putString("price", price);
//				  		arguments.putString("use_state", use_state);
//				  		arguments.putString("start_time", start_time);
//				  		arguments.putString("end_time", end_time);
//				  		arguments.putString("electric", electric);
//				  		orderstatefragment.setArguments(arguments);
//				  		orderdetailfragment.setArguments(arguments);
//						
//			    	}
//			 };
			 
private String order_state;
private String price="15";
//private String flage="2";
private String use_state;//1时间或0电量方式
private String start_time;//预约时长
private String end_time;
private String electric;//实际充电量
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		//取得ActionBar对象
        ActionBar actionBar =getActionBar();
        //调用hide方法，隐藏actionbar
        actionBar.hide();
        //调用show方法，展示actionbar
        //actionBar.show();
        //广播，退出所有
  		SysApplication.getInstance().addActivity(this); 
  		Intent intent=getIntent();  
        Bundle bundle=intent.getExtras();
        order_id=bundle.getString("order_id");
        station_name=bundle.getString("station_name");
        order_state=bundle.getString("order_state");
        Log.v("order+name",order_id+"    "+station_name+ " "+order_state);
        
    	information = packager.selectOrderInfoPackager(order_id);
        Thread th=new Thread(){
     	public void run(){
     		String Web_result="";
         	String url = IP.URL;
     		List<Para> list = new ArrayList<Para>();// 存放参数的列表
     		list.add(new Para("information", information));// 参数打包
     		Web_result= HttpTools.postVisitWeb(url, list);

     		List<Map<String, Object>> orderInfo= new ArrayList<Map<String, Object>>();
			//String ss=Web_result.obj.toString();    			
			//Toast.makeText(SearchOrderActivity.this,ss, Toast.LENGTH_SHORT).show();
			orderInfo = JsonUtils.listKeyMaps("address",Web_result);
//			Toast.makeText(getApplicationContext(), "网络"+orderInfo,  
//                       Toast.LENGTH_SHORT).show(); 
			for(int i=0;i<orderInfo.size();i++){
				
				Map<String, Object> or=new HashMap<String, Object>();
				or=orderInfo.get(i);				
				space_address=or.get("space_address").toString();			
				total_price=or.get("total_price").toString();						
				use_power=or.get("use_power").toString();
				use_electric=or.get("use_electric").toString();	
				begin_time=or.get("begin_time").toString();	
				final_time=or.get("final_time").toString();	
				real_price=or.get("real_price").toString();	
				order_time=or.get("order_time").toString();		
				price=or.get("price").toString();
				use_state=or.get("use_state").toString();
				start_time=or.get("start_time").toString();
				end_time=or.get("end_time").toString();
				electric=or.get("electric").toString();
			}
			Bundle arguments=new Bundle();
	  		arguments.putString("order_id", order_id);
	  		arguments.putString("station_name", station_name);
	  		arguments.putString("space_address", space_address);
	  		arguments.putString("total_price", total_price);
	  		arguments.putString("use_power", use_power);
	  		arguments.putString("use_electric", use_electric);
	  		arguments.putString("begin_time",begin_time);
	  		arguments.putString("final_time",final_time);
	  		arguments.putString("real_price",real_price);
	  		arguments.putString("order_time",order_time);
	  		arguments.putString("order_state", order_state);
	  		arguments.putString("price", price);
	  		arguments.putString("use_state", use_state);
	  		arguments.putString("start_time", start_time);
	  		arguments.putString("end_time", end_time);
	  		arguments.putString("electric", electric);
	  		
	  		orderstatefragment=new FragmentOrderState();
	  		orderdetailfragment=new FragmentOrderDetail();
	  		orderstatefragment.setArguments(arguments);
	  		orderdetailfragment.setArguments(arguments);
	  		 Log.v("order+name",order_id+"    "+station_name+ " "+order_state+" "+space_address);
	  	     
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
     Log.v("join","子线程借宿" );
     }
//  		orderstatefragment=new FragmentOrderState();
//  		orderdetailfragment=new FragmentOrderDetail();
  		fragments = new Fragment[] {orderstatefragment,orderdetailfragment};
//  		Bundle arguments=new Bundle();
//  		arguments.putString("order_id", order_id);
//  		arguments.putString("station_name", station_name);
//  		orderstatefragment.setArguments(arguments);
//  		orderdetailfragment.setArguments(arguments);
  		
  		 textviews = new TextView[2];
         textviews[0] = (TextView) findViewById(R.id.tv_state);
         textviews[currentTabIndex].setTextColor(0xFF45C01A);
         textviews[1] = (TextView) findViewById(R.id.tv_xiang);
         
         getSupportFragmentManager().beginTransaction()
         .add(R.id.fragment_container, orderstatefragment)
        .add(R.id.fragment_container, orderdetailfragment)             
                .show(orderstatefragment).hide(orderdetailfragment).commit();
         
         TextView tv_station_name=(TextView)findViewById(R.id.station_name);
         tv_station_name.setText(station_name);

// 		information = packager.selectOrderInfoPackager(order_id);
//        new Thread(){
//     	public void run(){
//     		String Web_result="";
//         	String url = IP.URL;
//     		List<Para> list = new ArrayList<Para>();// 存放参数的列表
//     		list.add(new Para("information", information));// 参数打包
//     		Web_result= HttpTools.postVisitWeb(url, list);
//        
//     	Message msg=new Message();
//     	msg.obj=Web_result;
//     	handler.sendMessage(msg);
//     	}
// 	
//     }.start();

	}
	 public void back(View view) {
	        finish();
	    }
	 public void onTabClicked(View view) {
	        switch (view.getId()) {
	        case R.id.re_state:
	            index = 0;
	            break;

	        case R.id.re_xiang:
	            index = 1;
	            break;

	        }

	        if (currentTabIndex != index) {
	            FragmentTransaction trx = getSupportFragmentManager()
	                    .beginTransaction();
	            trx.hide(fragments[currentTabIndex]);
	            if (!fragments[index].isAdded()) {
	                trx.add(R.id.fragment_container, fragments[index]);
	            }
	            trx.show(fragments[index]).commit();
	        }
//	        imagebuttons[currentTabIndex].setSelected(false);
//	        // 把当前tab设为选中状态
//	        imagebuttons[index].setSelected(true);
	        textviews[currentTabIndex].setTextColor(0xFF999999);
	        textviews[index].setTextColor(0xFF45C01A);
	        currentTabIndex = index;
	    }

@Override
protected void onPause() {
	// TODO Auto-generated method stub
	//finish();
	super.onPause();
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_detail, menu);
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
