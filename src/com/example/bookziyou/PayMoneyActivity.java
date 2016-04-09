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
	Packager packager = new Packager(); // ��װ
	Parser parser=new Parser();
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			//m_pDialog.hide();
			
			if(msg.obj==null){
				Toast.makeText(PayMoneyActivity.this, "���������쳣",  
	                      Toast.LENGTH_SHORT).show(); 
				return;
			}	    		
				String sss=msg.obj.toString();  
				String ss=parser.getreturn(sss);
				if(ss.equals("error")){
					Toast.makeText(PayMoneyActivity.this, "֧��ʧ��",  
		                       Toast.LENGTH_SHORT).show(); 
//					   Intent intent = new Intent();  
//				       intent.setClass(PayMoneyActivity.this,LookmainActivity.class);  
//				       startActivity(intent);
					//return;
					
				}else if(ss.equals("success")){
					//order_id=ss;
					Toast.makeText(PayMoneyActivity.this, "֧���ɹ� ",  
		                       Toast.LENGTH_SHORT).show(); 
					flagesucc=true;
					Intent intent = new Intent();  
			       intent.setClass(PayMoneyActivity.this,LookmainActivity.class);  
			       startActivity(intent);  
					
				}else if(ss.equals("password")){
					//order_id=ss;
					Toast.makeText(PayMoneyActivity.this, "������������� ",  
		                       Toast.LENGTH_SHORT).show(); 
					mDialogWidget=new DialogWidget(PayMoneyActivity.this, getDecorViewDialog());
					mDialogWidget.show();
					//return;
					
				}else if(ss.equals("price")){
					//order_id=ss;
					Toast.makeText(PayMoneyActivity.this, "�˻����� ",  
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
	            // ��һ���������ܵĵ���ʱʱ��
	            // �ڶ���������ÿ������ʱ��(ms)����һ��onTick()����
	            public void onTick(long millisUntilFinished) {
	            	tv_daojishi.setText(millisUntilFinished / 1000 + " s");
	            	//tv_daojishi.setEnabled(false);
	            }
	 
	            public void onFinish() {
//	            	mDialogWidget.dismiss();
//					mDialogWidget=null;
					
					Toast.makeText(PayMoneyActivity.this, "����ȡ�� ",  
		                       Toast.LENGTH_SHORT).show(); 
					Intent intent = new Intent();  
				       intent.setClass(PayMoneyActivity.this,LookmainActivity.class);  
				       startActivity(intent);
	            	//tv_daojishi.setText("���»�ȡ��֤��");
	            	//tv_daojishi.setEnabled(true);
	            }
	        }.start();
	        
		   bt_zhifu=(Button)findViewById(R.id.tv_zhifu);//��ʼʱ��
			bt_zhifu.setOnClickListener(this);
	}
	 public void back(View view) {
//		 mDialogWidget.dismiss();
//			mDialogWidget=null;
			//ȡ������
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
					    		List<Para> list = new ArrayList<Para>();// ��Ų������б�
					    		list.add(new Para("information", information));// �������
					    		Web_result= HttpTools.postVisitWeb(url, list);
					    		Message msg=new Message();
					        	msg.obj=Web_result;
					        	handler.sendMessage(msg);
					    	}
						
					    }.start();
					//Toast.makeText(getApplicationContext(), "���׳ɹ�", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onCancelPay() {
					// TODO Auto-generated method stub
					mDialogWidget.dismiss();
					mDialogWidget=null;
					Toast.makeText(getApplicationContext(), "������ȡ��", Toast.LENGTH_SHORT).show();
					
				}
			}).getView();
		}
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			//ȡ������
//			Toast.makeText(PayMoneyActivity.this, "����ȡ�� ",  
//                    Toast.LENGTH_SHORT).show(); 
			Log.v("fla....",flagesucc.toString());
			if(!flagesucc){
			 information = packager.cancelOrderPackager(order_id); 	
			 
			  Thread   th= new Thread(){
			    	public void run(){
			    		String Web_result="";
			        	String url = IP.URL;
			    		List<Para> list = new ArrayList<Para>();// ��Ų������б�
			    		list.add(new Para("information", information));// �������
			    		Web_result= HttpTools.postVisitWeb(url, list);
			    		cancelflage=parser.getreturn(Web_result);
			    	}
				
			    };
			    if(!(Thread.currentThread()==th)){
		        	Log.v("join","�ȴ����߳�join...");
		        try {
		        	th.start();
					th.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        Log.v("join","���߳̽���" );
		        }
			    if(cancelflage.equals("1")){
			    	Toast.makeText(getApplicationContext(), "������ȡ��", Toast.LENGTH_SHORT).show();

			    }else {
			    	Toast.makeText(getApplicationContext(), "����ȡ��ʧ��", Toast.LENGTH_SHORT).show();

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
