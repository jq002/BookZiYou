package com.example.bookziyou;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entity.Para;
import com.httpconnet.HttpTools;
import com.httpconnet.IP;
import com.httpconnet.Packager;
import com.httpconnet.Parser;
import com.jqjava.lesson5.JsonUtils;
import com.jqjava.lesson5.ToastUtil;
import com.tools.SysApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class BookPositionActivity extends Activity implements OnClickListener {
	    ProgressDialog dialog;
	    private String information; 
		Packager packager = new Packager(); // 封装
		Parser parser=new Parser();
		private String start_time;
		private String end_time;
		private String space_id;
		private String space_address;
		private String space_state;
		private String price_one;
		private String price_two;
		private String power_one;
		private String power_two;
		private String space_suc;
		private String space_fail;
//		List<Map<String, Object>> orderInfo= new ArrayList<Map<String, Object>>();
//
//		
//		 Handler handler=new Handler(){
//		    	@Override
//		    	public void handleMessage(Message msg){
//		    		dialog.dismiss();
//		    		if(msg.obj==null){
//		    			ToastUtil.show(BookPositionActivity.this, "网络连接异常");
//		    			return;
//		    		}else{
//		    			String ss=msg.obj.toString();    
//		    			orderInfo = JsonUtils.listKeyMaps("address",ss);
//		    			Log.v("orderInfo",orderInfo.toString());
//		    		
//		    		}
//		    	}
//		 };
		private RadioGroup rg,rg_two;
		private RadioButton rb_changgui,rb_kuaisu,rb_e,rb_time;
//		private RadioButton rb_money;,rb_zidong
		private LinearLayout ll;
		
		private Button begintimeBtn;//开始时间
		private Button endtimeBtn;//结束时间
		private Button add;//添加
		
		//设置时间
		private Calendar calendar ;
		private String beginTime="book";
		private String endTime="book";
		//设置电量
		private String elec="book";
		private LinearLayout ll_setok;
		//设置金额
//		private String money="";
		private TextView tv_setok;
		private String flagetv="0";
		private Button bt_create;
		//选择的方式
		private String power="book";
		private String power2="3.8";
		private String total_price="100";
		private String power_price="";
		private String type;//1、按时间；0、电量
		private String maxelec;//最大充电量
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
		setContentView(R.layout.activity_book_position);
		SysApplication.getInstance().addActivity(this); 
		  Intent intent=getIntent();  
	        Bundle bundle=intent.getExtras();
	        start_time=bundle.getString("start_time");
	        end_time=bundle.getString("end_time");
	        space_id=bundle.getString("space_id");
	        space_address=bundle.getString("space_address");
	        space_state=bundle.getString("space_state");
//	        power_one=bundle.getString("power_one");
//	        power_two=bundle.getString("power_two");
//	        price_one=bundle.getString("price_one");
//	        price_two=bundle.getString("price_two");
//	        space_suc=bundle.getString("space_suc");
//	        space_fail=bundle.getString("space_fail");
//	        Log.v("space_id ",space_id+".."+space_address+"price_one"+price_one);
	        
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
						     //imageBuffer=or.get("imageBuffer").toString();						    Log.v("space_suc",space_suc+"imageBuffer"+imageBuffer);
						    //dialog.dismiss();

			 			}
			 			//dialog.dismiss();

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
	        init();
	}
	 public void back(View view) {
	        finish();
	    }
	 private void init(){
//		    String bth=parser.getHour(start_time);
//			String btm=parser.getMin(start_time);
			
			
	
		 rg=(RadioGroup)findViewById(R.id.rg);
		 rg_two=(RadioGroup)findViewById(R.id.rg_two);
		 rb_changgui=(RadioButton)findViewById(R.id.rb_changgui);
		 rb_changgui.setText(rb_changgui.getText()+"   ¥"+price_one);
		 rb_kuaisu=(RadioButton)findViewById(R.id.rb_kuaisu);
		 rb_kuaisu.setText(rb_kuaisu.getText()+"   ¥"+price_two);
		// rb_zidong=(RadioButton)findViewById(R.id.rb_zidong);
		 rb_e=(RadioButton)findViewById(R.id.rb_e);
		 //rb_money=(RadioButton)findViewById(R.id.rb_money);
		 rb_time=(RadioButton)findViewById(R.id.rb_time);
		 rb_time.setText(rb_time.getText()+"   出租时段    "+start_time+"--"+end_time);
		 
		 ll=(LinearLayout)findViewById(R.id.ll_settime);
		 ll_setok=(LinearLayout)findViewById(R.id.ll_setok);
		 tv_setok=(TextView)findViewById(R.id.tv_setok);
		 //获取日历实例
	        calendar = Calendar.getInstance();
	        begintimeBtn=(Button)findViewById(R.id.setTimeBtn);//开始时间
			endtimeBtn=(Button)findViewById(R.id.endTimeBtn);//结束时间
			bt_create=(Button)findViewById(R.id.bt_create);//添加
			
			begintimeBtn.setOnClickListener(this);
			endtimeBtn.setOnClickListener(this);
			bt_create.setOnClickListener(this);
			bt_create.setEnabled(false);
		   rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					 if(checkedId==rb_changgui.getId()){
						 power=power_one;
						 power_price=price_one;
						 power2=power;
						
					 }else if(checkedId==rb_kuaisu.getId()){
						 power=power_two;
						 power_price=price_two;
						 power2=power;
					 }
					
					
				}
			});
		   rg_two.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				
				public void onCheckedChanged(RadioGroup group, int checkedId) {

					if(checkedId==rb_e.getId()){
						maxelectric();
						 ll.setVisibility(View.INVISIBLE);
						 ll_setok.setVisibility(View.INVISIBLE);
						 flagetv="2";
						 type="0";//1、按时间；0、电量
						 showEDialog();
						 
					 }else  if(checkedId==rb_time.getId()){
						 ll.setVisibility(View.VISIBLE);
						 ll_setok.setVisibility(View.INVISIBLE);
						 flagetv="4";
						 type="1";
						 elec="book";
						 bt_create.setEnabled(true);
						
					 }
					
					
				}
			});
	 }
	 private void maxelectric(){
			int bt_hour=Integer.parseInt(parser.getHour(start_time));
			//Log.v("beginTime",String.valueOf(bt_hour));
			int bt_min=Integer.parseInt(parser.getMin(start_time));
			
			int et_hour=Integer.parseInt(parser.getHour(end_time));
			int et_min=Integer.parseInt(parser.getMin(end_time));
			int hour=(et_hour-bt_hour);
			int min=et_min-bt_min;
			Log.v("hour",String.valueOf(hour)+"  "+String.valueOf(min));
			
			double i=figureoutprice(hour, min,Double.parseDouble(power2));
			
			 DecimalFormat df = new DecimalFormat("####.00");  
			 maxelec=String.valueOf(i);
			 maxelec=df.format(i);
	 }
	 //最大充电量，默认7kwh，若已选择，根据选择
		public double figureoutprice(int hour,int min,double time_price){
			double price=0;
			price=((hour*60+min)/60.0)*time_price;
//			price=(hour*time_price)+(min*time_price/60);
			return price;
		}
	 private void flag(){
         if(flagetv.equals("2")){
			 tv_setok.setText("设置充电量：  "+elec+" °");
			 ll_setok.setVisibility(View.VISIBLE);
//			 tv_setok.setText("设置充电量：  "+elec);
		 }else  if(flagetv.equals("3")){
//			 ll_setok.setVisibility(View.VISIBLE);
//			 tv_setok.setText("设置充电金额： "+money);
		 }else  if(flagetv.equals("4")){
			 tv_setok.setText("设置充电时间：  "+start_time+"--"+end_time);
			 ll_setok.setVisibility(View.VISIBLE);
//			 tv_setok.setText("设置充电时间：  "+start_time+"--"+end_time);
		 }
		 
	 }
	 public void onClick(View v) {
			// TODO Auto-generated method stub
			  switch (v.getId()) {
	          case R.id.setTimeBtn:
	        	  Log.v("begintime", "click the time button to set begin time");
					calendar.setTimeInMillis(System.currentTimeMillis());
					new TimePickerDialog(BookPositionActivity.this,new TimePickerDialog.OnTimeSetListener() {
						@Override
						public void onTimeSet(TimePicker arg0, int h, int m) {
							//更新按钮上的时间
							//begintimeBtn.setText(formatTime(h,m));
							begintimeBtn.setText(formatTime(h,m));
							beginTime=formatTime(h,m).toString();
							//设置日历的时间，主要是让日历的年月日和当前同步
							calendar.setTimeInMillis(System.currentTimeMillis());
							//设置日历的小时和分钟
							calendar.set(Calendar.HOUR_OF_DAY, h);
							calendar.set(Calendar.MINUTE, m);
							//将秒和毫秒设置为0
							calendar.set(Calendar.SECOND, 0);
							calendar.set(Calendar.MILLISECOND, 0);
							//beginTime=formatTime(h,m).toString();
						}
					},calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();				
				

	              break;
	          case R.id.endTimeBtn:
	        	 
	        	  Log.v("endtime", "click the time button to set end time");
					calendar.setTimeInMillis(System.currentTimeMillis());
					new TimePickerDialog(BookPositionActivity.this,new TimePickerDialog.OnTimeSetListener() {
						@Override
						public void onTimeSet(TimePicker arg0, int h, int m) {
							//更新按钮上的时间
							//endtimeBtn.setText(formatTime(h,m));
							endtimeBtn.setText(formatTime(h,m));
							endTime=formatTime(h,m).toString();
							//设置日历的时间，主要是让日历的年月日和当前同步
							calendar.setTimeInMillis(System.currentTimeMillis());
							//设置日历的小时和分钟
							calendar.set(Calendar.HOUR_OF_DAY, h);
							calendar.set(Calendar.MINUTE, m);
							//将秒和毫秒设置为0
							calendar.set(Calendar.SECOND, 0);
							calendar.set(Calendar.MILLISECOND, 0);
							//endTime=formatTime(h,m).toString();
						
						}
					},calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();				
				
					//flag();
	              break;
	          case R.id.bt_create:
	        	  Log.v("startTime",beginTime+"--"+endTime);
	        	  if(power.equals("book")){
	        		  Toast.makeText(getApplicationContext(),"未选择充电功率",  
	                          Toast.LENGTH_SHORT).show(); 
	        		  return;
	        		  
	        	  }
                  if(elec.equals("book")&&beginTime.equals("book")){
                	  Toast.makeText(getApplicationContext(),"未选择充电方式",  
	                          Toast.LENGTH_SHORT).show(); 
	        		  return;
	        		  
	        	  }
                 Log.v("space_id",space_id+","+beginTime+","+power+","+endTime+","+total_price);
	        	  Intent intent=new Intent(BookPositionActivity.this,BookDetailActivity.class);
	        	  intent.putExtra("space_id",space_id);
	        	  intent.putExtra("beginTime",beginTime);
	        	  intent.putExtra("use_power",power);
	        	  intent.putExtra("endTime",endTime);
	        	  intent.putExtra("total_price",total_price);
	        	  intent.putExtra("start_time",start_time);
	        	  intent.putExtra("elec",elec);
	        	  intent.putExtra("power_price",power_price);
	        	  intent.putExtra("end_time",end_time);
	        	  intent.putExtra("space_address",space_address);
	        	  intent.putExtra("price_one",price_one);
	        	  intent.putExtra("price_two",price_two);
	        	  intent.putExtra("type", type);//1、时间  0、电量
	        	  
	        	  
	        	  startActivity(intent);

	        	  finish();
	              break;


	          }
		}
	 public String formatTime(int h , int m) {
	    	StringBuffer buf = new StringBuffer();
	    	if(h<10){
	    		buf.append("0"+h);
	    	}else {
				buf.append(h);
			}
	    	buf.append(" : ");
	    	if(m<10){
	    		buf.append("0"+m);
	    	}else {
	    		buf.append(m);
			}
			return buf.toString();
		}
	 private void showEDialog() {
	        final AlertDialog dlg = new AlertDialog.Builder(this).create();
	        dlg.show();
	      //下面两行代码加入后即可弹出输入法 
	        dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
	        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	        Window window = dlg.getWindow();
	        // *** 主要就是在这里实现这种效果的.
	        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
	        window.setContentView(R.layout.alertdialogtwo);
	        LinearLayout ll_title = (LinearLayout) window
	                .findViewById(R.id.ll_title);
	        ll_title.setVisibility(View.VISIBLE);
	        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
	        tv_title.setText("设置充电电量             最大值 "+maxelec+" °");
	        // 为确认按钮添加事件,执行退出应用操作
	        
	        final EditText tv_e = (EditText) window.findViewById(R.id.tv_content1);
	        //dlg.setView(tv_e);
	        Button bt_ok=(Button)window.findViewById(R.id.bt_ok);
	        bt_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					elec=tv_e.getText().toString();
					if(elec.equals("")){
						ToastUtil.show(BookPositionActivity.this, "请重新设置");
                         return;
					}
					 Double delec=Double.parseDouble(elec);
					 
					 if(maxelec.equals("")){
							ToastUtil.show(BookPositionActivity.this, "请重新设置");
	                         return;
						}
					 Double dmaxelec=Double.parseDouble(maxelec);
					
					 bt_create.setEnabled(true);
					 if(delec>dmaxelec){
						 ToastUtil.show(BookPositionActivity.this, "超过最大电量，请重新设置");
						 bt_create.setEnabled(false);
					 }
				        Log.v("elec",elec);
				        dlg.dismiss();
				        flag();
				}
	        });
	        
	       
	        
	       

	    }
	 private void showMoneyDialog() {
	        final AlertDialog dlg = new AlertDialog.Builder(this).create();
	        dlg.show();
	      //下面两行代码加入后即可弹出输入法 
	        dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
	        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	        Window window = dlg.getWindow();
	        // *** 主要就是在这里实现这种效果的.
	        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
	        window.setContentView(R.layout.alertdialogtwo);
	        LinearLayout ll_title = (LinearLayout) window
	                .findViewById(R.id.ll_title);
	        ll_title.setVisibility(View.VISIBLE);
	        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
	        tv_title.setText("设置充电金额");
	        // 为确认按钮添加事件,执行退出应用操作
	        
	        final EditText tv_e = (EditText) window.findViewById(R.id.tv_content1);
	        //dlg.setView(tv_e);
	        Button bt_ok=(Button)window.findViewById(R.id.bt_ok);
	        bt_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					 money=tv_e.getText().toString();
//				        Log.v("money",money);
//				        dlg.dismiss();
				}
	        });
	        
	       
	        
	       

	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_position, menu);
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
