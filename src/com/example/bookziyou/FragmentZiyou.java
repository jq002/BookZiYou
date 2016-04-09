package com.example.bookziyou;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kobjects.base64.Base64;

import com.entity.Para;
import com.example.bookziyou.LookPositionActivity.MyAdapter;
import com.httpconnet.HttpTools;
import com.httpconnet.IP;
import com.httpconnet.Packager;
import com.httpconnet.Parser;
import com.jqjava.lesson5.DemoApplication;
import com.jqjava.lesson5.JsonUtils;
import com.jqjava.lesson5.ToastUtil;

import android.app.FragmentManager;
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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentZiyou extends Fragment{
	    ProgressDialog dialog;
	    private String information; 
		Packager packager = new Packager(); // 封装
		Parser parser=new Parser();
		MyAdapter adapter;
		List<Map<String, Object>> orderInfo= new ArrayList<Map<String, Object>>();

		    private String station_name;
		    private String order_state;
		    private String image;
		    private String total_price;
		    private String order_time;
		    private String space_address; 
		    
		    private String order_id;
		    private String space_id;
		    private String order_evaluation;
		    
		    private String user_id;
		    private String tel;
		    ListView listview;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("fragmentziyou...........","onCreateView");
		return inflater.inflate(R.layout.tab01, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		DemoApplication app = (DemoApplication)getActivity().getApplication();  		
		user_id=(String)app.get("id");
		tel=(String)app.get("tel");
		Log.v("user_id",user_id);
          information = packager.selectOrderPackager(user_id);
        
        Thread th= new Thread(){
        	public void run(){
        		String Web_result="";
	        	String url = IP.URL;
	    		List<Para> list = new ArrayList<Para>();// 存放参数的列表
	    		list.add(new Para("information", information));// 参数打包
	    		Web_result= HttpTools.postVisitWeb(url, list);

	    		orderInfo = JsonUtils.listKeyMaps("address",Web_result);
    			
//    			ToastUtil.show(getActivity().getApplicationContext(), "order订单总数："+orderInfo.size());
    			//Log.v("orderInfo",user_id+"use"+orderInfo.toString());
    			 listview=(ListView) getActivity().findViewById(R.id.listview);
    			adapter=new MyAdapter(getActivity());
    			listview.setAdapter(adapter);
    			//item的点击监听
//    			listview.setOnItemClickListener(new OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> parent,
//							View view, int position, long id) {
//						String orderid=(String) orderInfo.get(position).get("order_id");
//						Log.v("order_id",orderid);
//						Intent intent=new Intent(getActivity(),OrderDetailActivity.class);
//						intent.putExtra("order_id", orderid);
//		            	startActivity(intent);	
//						 //ToastUtil.show(getActivity().getApplicationContext(), "order_id"+position+"="+(String) orderInfo.get(position).get("order_id"));
//					}
//				});
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

        Log.v("join","子线程借宿");
        }
        Log.v("fragmentziyou...........","onActivityCreated");
	}
	@Override
	public void onStart() {
	// TODO Auto-generated method stub

	super.onStart();
	Log.v("fragmentziyou...........","onStart()");
	}
	@Override
	public void onResume() {
	// TODO Auto-generated method stub
		
	super.onResume();
	//listview.setAdapter(adapter);
	Log.v("fragmentziyou...........","onResume() ");
	}
	@Override
	public void onPause() {
	// TODO Auto-generated method stub
		
	super.onPause();
	Log.v("fragmentziyou...........","onPause() ");
	}
	@Override
	public void onStop() {
	// TODO Auto-generated method stub
		
	super.onStop();
	Log.v("fragmentziyou...........","onStop()");
	}
	@Override
	public void onDestroyView() {
	// TODO Auto-generated method stub
		
	super.onDestroyView();
	Log.v("fragmentziyou...........","onDestroyView()");
	}
	@Override
	public void onDestroy() {
	// TODO Auto-generated method stub
		
	super.onDestroy();
	Log.v("fragmentziyou...........","onDestroy() ");
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	Log.v("fragmentziyou...........","onActivityResult");
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
	// TODO Auto-generated method stub
	super.onSaveInstanceState(outState);
	Log.v("fragmentziyou...........","onSaveInstanceState");
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
//		public void showPhoto(){
//			 Bitmap bitmap = null;
//			String result;
//			result=image;
//		//	Log.v("imageBuffer",imageBuffer);
//			  byte[] buffer = new Base64().decode(result);   
//			
//			  BitmapFactory.Options options = new BitmapFactory.Options();
//		        options.inJustDecodeBounds = true;
//		        Bitmap bmp = BitmapFactory.decodeByteArray(buffer , 0, buffer.length, options);//.decodeFile(paths.get(0), options);
//		        
//		        int height = options.outHeight * 200 / options.outWidth;
//		        options.inSampleSize = options.outWidth / 200;
//		        options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
//		        options.inPurgeable = true;
//		        options.inInputShareable = true;
//		        options.outWidth = 200;
//		        options.outHeight = height; 
//		        options.inJustDecodeBounds = false;
//		    	Log.v("height",String.valueOf(height));
//		        //Bitmap bm = BitmapFactory.decodeFile(paths.get(0), options);
//		        Bitmap bm = BitmapFactory.decodeByteArray(buffer , 0, buffer.length, options);//.decodeFile(paths.get(0), options);
//		    	//Bitmap bm = BitmapFactory.decodeFile(imageBuffer, options);
//		        image.setImageBitmap(bm);
//		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView=inflater.inflate(R.layout.activity_order, null);

			 station_name=(String) orderInfo.get(position).get("station_name");
			 total_price=(String) orderInfo.get(position).get("total_price");
			 order_state=(String) orderInfo.get(position).get("order_state");
			 order_time=(String) orderInfo.get(position).get("order_time");
			 space_address=(String) orderInfo.get(position).get("space_address");
			 
			 order_evaluation=(String) orderInfo.get(position).get("order_evaluation");
			 space_id=(String) orderInfo.get(position).get("space_id");
			 order_id=(String) orderInfo.get(position).get("order_id");
			 Log.v("Fragment........... ",order_id+"--posiion"+position);
			 image=(String) orderInfo.get(position).get("imageBuffer");
			 //Log.v("image ",image);
			 
//			 Toast.makeText(getApplicationContext(), "parklot_address="+parklot_address, Toast.LENGTH_LONG).show();
					 
			TextView tv_station_name=(TextView) convertView.findViewById(R.id.station_name);
			TextView tv_order_state=(TextView) convertView.findViewById(R.id.order_state);
		
			TextView tv_total_price=(TextView) convertView.findViewById(R.id.order_price);
			TextView tv_position_address=(TextView) convertView.findViewById(R.id.position_address);
			TextView tv_order_time=(TextView) convertView.findViewById(R.id.order_time);
			ImageView imageview=(ImageView)convertView.findViewById(R.id.imageview);
			
			Button cancle_item=(Button) convertView.findViewById(R.id.goto_item);
			Button start_item=(Button) convertView.findViewById(R.id.ping_item);
			tv_station_name.setText(station_name);
			 DecimalFormat df = new DecimalFormat("####.00");  
				total_price=df.format(Double.parseDouble(total_price));
			tv_total_price.setText("¥"+total_price);
			tv_order_time.setText(order_time);
			tv_position_address.setText(space_address);
			if(order_state.equals("0")){
				tv_order_state.setText("未支付");
				cancle_item.setEnabled(false);
				
				
			}else if(order_state.equals("1")){
				tv_order_state.setText("已支付");
				cancle_item.setEnabled(true);
				
			} else if(order_state.equals("2")){
				tv_order_state.setText("充电中");
				cancle_item.setEnabled(false);

				
			}else if(order_state.equals("3")){
				tv_order_state.setText("充电结束");
				cancle_item.setEnabled(false);

				
			}else if(order_state.equals("4")){
				tv_order_state.setText("订单结束");
				cancle_item.setEnabled(false);

				
			}else if(order_state.equals("5")){
				tv_order_state.setText("已取消");
				cancle_item.setEnabled(false);

				
			}

			if(!image.equals("")){
			   byte[] buffer = new Base64().decode(image);   
				
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
			
			 //Button bt_goto_item=(Button)convertView.findViewById(R.id.goto_item);
			 cancle_item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					Intent intent=new Intent(getActivity(),OrderDetailActivity.class);
//					intent.putExtra("order_id", order_id);
//					intent.putExtra("station_name", station_name);
//					intent.putExtra("order_state", order_state);
//	            	startActivity(intent);	
//	            	Log.v("position",String.valueOf(position));
				}
			 });
			RelativeLayout re_position=(RelativeLayout) convertView.findViewById(R.id.re_xiang);
			re_position.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					order_id=(String) orderInfo.get(position).get("order_id");
					station_name=(String) orderInfo.get(position).get("station_name");
					 order_state=(String) orderInfo.get(position).get("order_state");
					Intent intent=new Intent(getActivity(),OrderDetailActivity.class);
					intent.putExtra("order_id", order_id);
					intent.putExtra("station_name", station_name);
					intent.putExtra("order_state", order_state);
	            	startActivity(intent);	
					//FragmentManager fragmentma=getSupportFragmentManager();
					
					
				}
			});
//			//点击监听：
//			goto_item.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Intent intent=new Intent(OrderActivity.this,OrderItemDetailsActivity.class);
//					intent.putExtra("order_id", order_id);
//					startActivity(intent);
//				}
//			});
//			//点击监听：
//			goto_item.setOnClickListener(new MyListener(position));
			
			return convertView;
		}
		
	}

}
