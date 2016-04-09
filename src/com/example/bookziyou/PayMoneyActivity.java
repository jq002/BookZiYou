package com.example.bookziyou;

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
import android.os.CountDownTimer;
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
import android.widget.TextView;
import android.widget.Toast;

public class PayMoneyActivity extends Activity implements OnClickListener {
	private String order_id;
	private String id;
	private String tel;
	private Button bt_zhifu;
	
	private DialogWidget mDialogWidget;
	private String total_price;
	
	private String information; 
	Packager packager = new Packager(); // 封装
	Parser parser=new Parser();
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			//m_pDialog.hide();
			
			if(msg.obj==null){
				Toast.makeText(PayMoneyActivity.this, "网络连接异常",  
	                      Toast.LENGTH_SHORT).show(); 
				return;
			}	    		
				String sss=msg.obj.toString();  
				String ss=parser.getreturn(sss);
				if(ss.equals("error")){
					Toast.makeText(PayMoneyActivity.this, "支付失败",  
		                       Toast.LENGTH_SHORT).show(); 
//					   Intent intent = new Intent();  
//				       intent.setClass(PayMoneyActivity.this,LookmainActivity.class);  
//				       startActivity(intent);
					//return;
					
				}else if(ss.equals("success")){
					//order_id=ss;
					Toast.makeText(PayMoneyActivity.this, "支付成功 ",  
		                       Toast.LENGTH_SHORT).show(); 
					flagesucc=true;
					Intent intent = new Intent();  
			       intent.setClass(PayMoneyActivity.this,LookmainActivity.class);  
			       startActivity(intent);  
					
				}else if(ss.equals("password")){
					//order_id=ss;
					Toast.makeText(PayMoneyActivity.this, "密码错误，请输入 ",  
		                       Toast.LENGTH_SHORT).show(); 
					mDialogWidget=new DialogWidget(PayMoneyActivity.this, getDecorViewDialog());
					mDialogWidget.show();
					//return;
					
				}else if(ss.equals("price")){
					//order_id=ss;
					Toast.makeText(PayMoneyActivity.this, "账户余额不足 ",  
		                       Toast.LENGTH_SHORT).show(); 
//					Intent intent = new Intent();  
//				       intent.setClass(PayMoneyActivity.this,LookmainActivity.class);  
//				       startActivity(intent);
				}
				Log.v("zhifu",ss+"--");
		}
	};
	private TextView tv_daojishi;
	private TextView tv_real_price;
	private String cancelflage;
	private Boolean flagesucc=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_pay_money);
		SysApplication.getInstance().addActivity(this); 
		Intent intent=getIntent();  
        Bundle bundle=intent.getExtras();
        order_id=bundle.getString("order_id");
        total_price=bundle.getString("total_price");
        
        DemoApplication app = (DemoApplication)getApplication();  		
		id=(String)app.get("id");
		tel=(String)app.get("tel");
		 tv_daojishi=(TextView)findViewById(R.id.tv_daojishi);
		 
		 tv_real_price=(TextView)findViewById(R.id.tv_real_price);
		 tv_real_price.setText(total_price);
		 
		  new CountDownTimer(90000, 1000) {
	            // 第一个参数是总的倒计时时间
	            // 第二个参数是每隔多少时间(ms)调用一次onTick()方法
	            public void onTick(long millisUntilFinished) {
	            	tv_daojishi.setText(millisUntilFinished / 1000 + " s");
	            	//tv_daojishi.setEnabled(false);
	            }
	 
	            public void onFinish() {
//	            	mDialogWidget.dismiss();
//					mDialogWidget=null;
					
					Toast.makeText(PayMoneyActivity.this, "订单取消 ",  
		                       Toast.LENGTH_SHORT).show(); 
					Intent intent = new Intent();  
				       intent.setClass(PayMoneyActivity.this,LookmainActivity.class);  
				       startActivity(intent);
	            	//tv_daojishi.setText("重新获取验证码");
	            	//tv_daojishi.setEnabled(true);
	            }
	        }.start();
	        
		   bt_zhifu=(Button)findViewById(R.id.tv_zhifu);//开始时间
			bt_zhifu.setOnClickListener(this);
	}
	 public void back(View view) {
//		 mDialogWidget.dismiss();
//			mDialogWidget=null;
			//取消订单
	        finish();
	    }
	 public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mDialogWidget=new DialogWidget(PayMoneyActivity.this, getDecorViewDialog());
			mDialogWidget.show();
			
		}
		protected View getDecorViewDialog() {
			// TODO Auto-generated method stub
			return PayPasswordView.getInstance(total_price,this,new OnPayListener() {
				
				@Override
				public void onSurePay(String password) {
					// TODO Auto-generated method stub
					mDialogWidget.dismiss();
					mDialogWidget=null;
					//payTextView.setText(password);
					Log.v("password",password+", "+order_id+","+tel+","+total_price);
					 information = packager.payOrderPackager(order_id, tel, password, total_price); 		      
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
					//Toast.makeText(getApplicationContext(), "交易成功", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onCancelPay() {
					// TODO Auto-generated method stub
					mDialogWidget.dismiss();
					mDialogWidget=null;
					Toast.makeText(getApplicationContext(), "交易已取消", Toast.LENGTH_SHORT).show();
					
				}
			}).getView();
		}
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			//取消订单
//			Toast.makeText(PayMoneyActivity.this, "订单取消 ",  
//                    Toast.LENGTH_SHORT).show(); 
			Log.v("fla....",flagesucc.toString());
			if(!flagesucc){
			 information = packager.cancelOrderPackager(order_id); 	
			 
			  Thread   th= new Thread(){
			    	public void run(){
			    		String Web_result="";
			        	String url = IP.URL;
			    		List<Para> list = new ArrayList<Para>();// 存放参数的列表
			    		list.add(new Para("information", information));// 参数打包
			    		Web_result= HttpTools.postVisitWeb(url, list);
			    		cancelflage=parser.getreturn(Web_result);
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
			    if(cancelflage.equals("1")){
			    	Toast.makeText(getApplicationContext(), "订单已取消", Toast.LENGTH_SHORT).show();

			    }else {
			    	Toast.makeText(getApplicationContext(), "订单取消失败", Toast.LENGTH_SHORT).show();

			    }
			}
			super.onPause();
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pay_money, menu);
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
