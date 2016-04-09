package com.example.bookziyou;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentOrderDetail  extends Fragment{
	private String order_id;
	private String station_name; 
	private String space_address;
	private String total_price;
	private String use_power;
	private String use_electric;
	private String begin_time;
	private String final_time;
	private String real_price;
	private String order_time;	
	
	private String order_state;
	
	private String price="12";
	private String use_state;//1时间0电量方式
	private String start_time;//预约时长
	private String end_time;
	private String electric;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.tab03, container, false);
		
	}
//begin_time,final_time,real_price会有null
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 order_id = getArguments().getString("order_id");
		 station_name=getArguments().getString("station_name");
		 space_address= getArguments().getString("space_address");
		 total_price=getArguments().getString("total_price");
		 use_power= getArguments().getString("use_power");
		 use_electric=getArguments().getString("use_electric");
		 begin_time = getArguments().getString("begin_time");
		 final_time=getArguments().getString("final_time");
		 real_price = getArguments().getString("real_price");
		 order_time=getArguments().getString("order_time");
		 order_state=getArguments().getString("order_state");
		 
		 use_state=getArguments().getString("use_state");
		 price=getArguments().getString("price");
		 start_time=getArguments().getString("start_time");
		 end_time=getArguments().getString("end_time");
		 electric=getArguments().getString("electric");
		//Log.v("order_id",order_id+order_time);
		Log.v("order_state",order_state+";;"+order_time);
		init();
		
	}
	private void init(){
		TextView tv_real_price=(TextView)getView().findViewById(R.id.tv_real_price);
		
		tv_real_price.setText(real_price);
		if(real_price.equals("null")){
			tv_real_price.setText("无");
		}
		TextView tv_yi_price=(TextView)getView().findViewById(R.id.tv_yifu_price);
		tv_yi_price.setText(total_price);
		TextView tv_tui_price=(TextView)getView().findViewById(R.id.tv_tui_price);
		tv_tui_price.setText("无");
		if(!real_price.equals("null")){
			if(Double.parseDouble(real_price)>Double.parseDouble(total_price)){
				Double dprice=Double.parseDouble(real_price)-Double.parseDouble(total_price);
				//String i=String.valueOf(dprice);
				 DecimalFormat df = new DecimalFormat("####.00");  
					String tui=String.valueOf(dprice);
					tv_tui_price.setText(tui);
			}
		}
		
			
		//TextView tv_bufu_price=(TextView)getView().findViewById(R.id.tv_bufu_price);
		TextView tv_station_name=(TextView)getView().findViewById(R.id.tv_station_name);
		TextView tv_space_address=(TextView)getView().findViewById(R.id.tv_space_address);
		TextView tv_type=(TextView)getView().findViewById(R.id.tv_type);
		TextView tv_mei_price=(TextView)getView().findViewById(R.id.tv_mei_price);
		TextView tv_yuding_time=(TextView)getView().findViewById(R.id.tv_yuding_time);
		TextView tv_real_time=(TextView)getView().findViewById(R.id.tv_real_time);
		TextView yuding_time=(TextView)getView().findViewById(R.id.yuding_time);
		TextView real_time=(TextView)getView().findViewById(R.id.real_time);
		TextView tv_order_time=(TextView)getView().findViewById(R.id.tv_order_time);
		
		tv_order_time.setText(order_time);
		tv_station_name.setText(station_name);
		tv_space_address.setText(space_address);
		if(use_power.equals("3.8")){
			tv_type.setText("常规充电(3.8kwh)");
			tv_mei_price.setText(price);
		}else{
			tv_type.setText("快速充电(7kwh)");
			tv_mei_price.setText(price);
		}
		
		String flage=use_state;
		if(flage.equals("0")){
			yuding_time.setText("预定充电量");
			real_time.setText("实际充电量");
			tv_yuding_time.setText(use_electric);
			tv_real_time.setText(electric);
		}else{
			yuding_time.setText("预定充电时长");
			real_time.setText("实际充电时长");
			tv_yuding_time.setText(start_time+"--"+end_time);
			tv_real_time.setText(begin_time+"--"+final_time);
			if(begin_time.equals("null")){
				tv_real_time.setText("无");
			}
		}
		if(order_state.equals("0")){
			//tv_order_state.setText("未支付");
			tv_real_price.setText("无");
			tv_yi_price.setText("无");
			tv_tui_price.setText("无");
			
		}else if(order_state.equals("1")){
			//tv_order_state.setText("已支付");
			tv_real_price.setText("无");
			tv_yi_price.setText(total_price);
			tv_tui_price.setText("无");

			
		} else if(order_state.equals("2")){
			//tv_order_state.setText("充电中");
			tv_real_price.setText("无");
			tv_yi_price.setText(total_price);
			tv_tui_price.setText("无");
			
		}else if(order_state.equals("3")){
			//tv_order_state.setText("充电结束");
			tv_real_price.setText("无");
			tv_yi_price.setText(total_price);
			tv_tui_price.setText("无");
			
			
		}else if(order_state.equals("4")){
			//tv_order_state.setText("订单结束");
			tv_real_price.setText(real_price);
			tv_yi_price.setText(total_price);
			tv_tui_price.setText("无");
			
		}else if(order_state.equals("5")){
			//tv_order_state.setText("已取消");
			tv_real_price.setText("无");
			tv_yi_price.setText("无");
			tv_tui_price.setText("无");
			
		}
		


	}

}
