package com.example.bookziyou;

//import java.security.Timestamp;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.entity.Para;
import com.httpconnet.HttpTools;
import com.httpconnet.IP;
import com.httpconnet.Packager;
import com.httpconnet.Parser;
import com.jqjava.lesson5.DemoApplication;
import com.pay.dialog.DialogWidget;
import com.pay.dialog.PayPasswordView;
import com.pay.dialog.PayPasswordView.OnPayListener;
import com.tools.SysApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetailActivity extends Activity implements OnClickListener {
	private String space_id;
	private String start_time;
	private String end_time;
	private String use_power;
	private String total_price;
	private String space_address;
	private String power_price;
	private String price_one;
	private String price_two;
	private String elec;
	
	private String information; 
	Packager packager = new Packager(); // 封装
	Parser parser=new Parser();
	private String id;
	private String tel;
	private String beginTime;
	private String endTime;
	private TextView tv_total_price;
	private TextView tv_space_address;
	private TextView tv_type;
	private TextView tv_chuzu_time;
	private TextView tv_yuding_time;
	private TextView tv_per_price;
	private TextView textView8;
	private Button bt_yuding;
	 Handler handler=new Handler(){
	@Override
	public void handleMessage(Message msg){
		//m_pDialog.hide();
		
		if(msg.obj==null){
			Toast.makeText(BookDetailActivity.this, "网络连接异常",  
                       Toast.LENGTH_SHORT).show(); 
			return;
		}	    		
			String sss=msg.obj.toString(); 
			if(sss.equals("")){
				Toast.makeText(BookDetailActivity.this, "创建失败",  
	                       Toast.LENGTH_SHORT).show(); 

				return;
			}
			
			if(sss.length()==0){
				Toast.makeText(BookDetailActivity.this, "创建失败",  
	                       Toast.LENGTH_SHORT).show(); 
				return;
				
			}
			String ss=parser.getreturn(sss);
			if(ss.equals("0")){
				Toast.makeText(BookDetailActivity.this, "创建失败",  
	                       Toast.LENGTH_SHORT).show(); 

				return;
			}else{
				order_id=ss;
				Toast.makeText(BookDetailActivity.this, "订单ID "+ss,  
	                       Toast.LENGTH_SHORT).show(); 
				
			}
//			mDialogWidget=new DialogWidget(BookDetailActivity.this, getDecorViewDialog());
//			mDialogWidget.show();
			Intent intent = new Intent();  
        intent.setClass(BookDetailActivity.this, PayMoneyActivity.class);  
        intent.putExtra("order_id", order_id);
        intent.putExtra("total_price", total_price);
        startActivity(intent);  
finish();
	}
};
//Handler handler2=new Handler(){
//	@Override
//	public void handleMessage(Message msg){
//		//m_pDialog.hide();
//		
//		if(msg.obj==null){
//			Toast.makeText(BookDetailActivity.this, "网络连接异常",  
//                      Toast.LENGTH_SHORT).show(); 
//			return;
//		}	    		
//			String sss=msg.obj.toString();  
//			String ss=parser.getreturn(sss);
//			if(ss.equals("error")){
//				Toast.makeText(BookDetailActivity.this, "支付失败",  
//	                       Toast.LENGTH_SHORT).show(); 
//				   Intent intent = new Intent();  
//			       intent.setClass(BookDetailActivity.this,LookmainActivity.class);  
//			       startActivity(intent);
//				//return;
//				
//			}else if(ss.equals("success")){
//				//order_id=ss;
//				Toast.makeText(BookDetailActivity.this, "支付成功 ",  
//	                       Toast.LENGTH_SHORT).show(); 
//				Intent intent = new Intent();  
//		       intent.setClass(BookDetailActivity.this,LookmainActivity.class);  
//		       startActivity(intent);  
//				
//			}else if(ss.equals("password")){
//				//order_id=ss;
//				Toast.makeText(BookDetailActivity.this, "密码错误，请输入 ",  
//	                       Toast.LENGTH_SHORT).show(); 
//				mDialogWidget=new DialogWidget(BookDetailActivity.this, getDecorViewDialog());
//				mDialogWidget.show();
//				//return;
//				
//			}else if(ss.equals("price")){
//				//order_id=ss;
//				Toast.makeText(BookDetailActivity.this, "账户余额不足 ",  
//	                       Toast.LENGTH_SHORT).show(); 
//				Intent intent = new Intent();  
//			       intent.setClass(BookDetailActivity.this,LookmainActivity.class);  
//			       startActivity(intent);
//			}
//			Log.v("zhifu",ss+"--");
//	}
//};
private DialogWidget mDialogWidget;
private String order_id;
private String type;//1、按时间；0、电量
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_book_detail);
		SysApplication.getInstance().addActivity(this); 
		Intent intent=getIntent();  
        Bundle bundle=intent.getExtras();
        start_time=bundle.getString("start_time");
        end_time=bundle.getString("end_time");
        space_id=bundle.getString("space_id");
        use_power=bundle.getString("use_power");
        total_price=bundle.getString("total_price");
        beginTime=bundle.getString("beginTime");
        endTime=bundle.getString("endTime");
        power_price=bundle.getString("power_price");
        space_address=bundle.getString("space_address");
        price_one=bundle.getString("price_one");
        price_two=bundle.getString("price_two");
        elec=bundle.getString("elec");
        type=bundle.getString("type");
        
        
        DemoApplication app = (DemoApplication)getApplication();  		
		id=(String)app.get("id");
		tel=(String)app.get("tel");
		
		init();

	}
//	protected View getDecorViewDialog() {
//		// TODO Auto-generated method stub
//		return PayPasswordView.getInstance(total_price,this,new OnPayListener() {
//			
//			@Override
//			public void onSurePay(String password) {
//				// TODO Auto-generated method stub
//				mDialogWidget.dismiss();
//				mDialogWidget=null;
//				//payTextView.setText(password);
//				Log.v("password",password+", "+order_id+","+tel+","+total_price);
//				 information = packager.payOrderPackager(order_id, tel, password, total_price); 		      
//				    new Thread(){
//				    	public void run(){
//				    		String Web_result="";
//				        	String url = IP.URL;
//				    		List<Para> list = new ArrayList<Para>();// 存放参数的列表
//				    		list.add(new Para("information", information));// 参数打包
//				    		Web_result= HttpTools.postVisitWeb(url, list);
//				    		Message msg=new Message();
//				        	msg.obj=Web_result;
//				        	handler2.sendMessage(msg);
//				    	}
//					
//				    }.start();
//				//Toast.makeText(getApplicationContext(), "交易成功", Toast.LENGTH_SHORT).show();
//			}
//			
//			@Override
//			public void onCancelPay() {
//				// TODO Auto-generated method stub
//				mDialogWidget.dismiss();
//				mDialogWidget=null;
//				Toast.makeText(getApplicationContext(), "交易已取消", Toast.LENGTH_SHORT).show();
//				
//			}
//		}).getView();
//	}
//	
	private void init(){
		tv_total_price=(TextView)findViewById(R.id.tv_total_price);
		//tv_total_price.setText(total_price);
		tv_space_address=(TextView)findViewById(R.id.tv_space_address);
		tv_space_address.setText(space_address);
		tv_chuzu_time=(TextView)findViewById(R.id.tv_chuzu_time);
		tv_chuzu_time.setText(start_time+"--"+end_time);
		tv_yuding_time=(TextView)findViewById(R.id.tv_yuding_time);
		textView8=(TextView)findViewById(R.id.textView8);
		
		if(elec.equals("book")){
			Log.v("beginTime",beginTime+parser.getHour(beginTime)+"m"+parser.getMin(beginTime));
			if(beginTime.equals("book")){
				Toast.makeText(BookDetailActivity.this, "未设置开始时间",  
	                       Toast.LENGTH_SHORT).show(); 
				return;
			}
			if(endTime.equals("book")){
				Toast.makeText(BookDetailActivity.this, "未设置结束时间",  
	                       Toast.LENGTH_SHORT).show(); 
				return;
			}
			String bth=parser.getHour(beginTime);
			String btm=parser.getMin(beginTime);
			
			
			int bt_hour=Integer.parseInt(bth);
			Log.v("beginTime",String.valueOf(bt_hour));
			int bt_min=Integer.valueOf(parser.getMin(beginTime));
			
			int et_hour=Integer.parseInt(parser.getHour(endTime));
			int et_min=Integer.parseInt(parser.getMin(endTime));
			int hour=(et_hour-bt_hour);
			int min=et_min-bt_min;
			Log.v("hour",String.valueOf(hour)+"  "+String.valueOf(min));
			double i=figureoutprice(hour, min,Double.parseDouble(power_price));
			
			 DecimalFormat df = new DecimalFormat("####.00");  
			total_price=String.valueOf(i);
			 total_price=df.format(i);
			tv_total_price.setText(total_price);
		tv_yuding_time.setText(beginTime+"--"+endTime);
		}else{
			double i=Double.parseDouble(elec)*Double.parseDouble(power_price)/Double.parseDouble(use_power);
			total_price=String.valueOf(i);
			 DecimalFormat df = new DecimalFormat("####.00");  
			total_price=df.format(i);
			tv_total_price.setText(total_price);
			
			textView8.setText("预定充电量：  ");
			tv_yuding_time.setText(elec+" 度");
		}
		
		tv_per_price=(TextView)findViewById(R.id.tv_per_price);
		 DecimalFormat df = new DecimalFormat("####.00");  
		 power_price=df.format(Double.parseDouble(power_price));
		tv_per_price.setText("¥"+power_price);
		tv_type=(TextView)findViewById(R.id.tv_type);
        if(power_price.equals(df.format(Double.parseDouble(price_one)))){
			tv_type.setText("常规充电  ("+use_power+"kw)");
		}
        if(power_price.equals(df.format(Double.parseDouble(price_two)))){
			tv_type.setText("快速充电  ("+use_power+"kw)");
		}
		
        bt_yuding=(Button)findViewById(R.id.yuding);//开始时间
		bt_yuding.setOnClickListener(this);
	}

	public double figureoutprice(int hour,int min,double time_price){
		double price=0;
		price=((hour*60+min)/60.0)*time_price;
//		price=(hour*time_price)+(min*time_price/60);
		return price;
	}

	 public void back(View view) {
	        finish();
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_detail, menu);
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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Log.v("bbb",id+space_id+beginTime+endTime+use_power+total_price);
		
		 information = packager.createOrderPackager(id, space_id, beginTime, endTime, use_power, elec,power_price,type,total_price);   		      
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
}
