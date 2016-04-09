package com.example.bookziyou;

import com.autonavi.tbt.v;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class FragmentOrderState  extends Fragment{
	 private String order_state;
	 private String order_time;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.tab02, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		order_state = getArguments().getString("order_state");
		order_time=getArguments().getString("order_time");
		Log.v("order_state",order_state+";;"+order_state);
		TextView tv_time1=(TextView)getView().findViewById(R.id.tv_time1);
		TextView tv_time2=(TextView)getView().findViewById(R.id.tv_time2);
		TextView tv_time3=(TextView)getView().findViewById(R.id.tv_time3);
		TextView tv_time4=(TextView)getView().findViewById(R.id.tv_time4);
		TextView tv_word1=(TextView)getView().findViewById(R.id.tv_word1);
		TextView tv_word2=(TextView)getView().findViewById(R.id.tv_word2);
		TextView tv_word3=(TextView)getView().findViewById(R.id.tv_word3);
		TextView tv_word4=(TextView)getView().findViewById(R.id.tv_word4);
		TextView tv_word5=(TextView)getView().findViewById(R.id.tv_word5);
		TextView tv_xin1=(TextView)getView().findViewById(R.id.tv_xin1);
		TextView tv_xin2=(TextView)getView().findViewById(R.id.tv_xin2);
		TextView tv_xin3=(TextView)getView().findViewById(R.id.tv_xin3);
		TextView tv_xin4=(TextView)getView().findViewById(R.id.tv_xin4);
		TextView tv_xin5=(TextView)getView().findViewById(R.id.tv_xin5);
		//�����ύʱ��
		tv_time1.setText(order_time);
		//δ֧��
		LinearLayout ll_1=(LinearLayout)getView().findViewById(R.id.ll_1);
		
		LinearLayout ll_2=(LinearLayout)getView().findViewById(R.id.ll_2);
		LinearLayout ll_3=(LinearLayout)getView().findViewById(R.id.ll_3);
		LinearLayout ll_4=(LinearLayout)getView().findViewById(R.id.ll_4);
		LinearLayout ll_5=(LinearLayout)getView().findViewById(R.id.ll_5);
		ll_2.setVisibility(View.INVISIBLE);
		ll_3.setVisibility(View.INVISIBLE);
		ll_4.setVisibility(View.INVISIBLE);
		ll_5.setVisibility(View.INVISIBLE);
		if(order_state.equals("0")){
			//tv_order_state.setText("δ֧��");
			
		}else if(order_state.equals("1")){
			//tv_order_state.setText("��֧��");
			tv_word2.setText("������֧��");
			tv_xin2.setText("");
			ll_2.setVisibility(View.VISIBLE);
			tv_word3.setText("�̼�ȷ�϶���");
			tv_xin3.setText("");
			ll_3.setVisibility(View.VISIBLE);
			
		} else if(order_state.equals("2")){
			//tv_order_state.setText("�����");
			tv_word2.setText("������֧��");
			tv_xin2.setText("");
			ll_2.setVisibility(View.VISIBLE);
			tv_word3.setText("�̼�ȷ�϶���");
			tv_xin3.setText("");
			ll_3.setVisibility(View.VISIBLE);
			tv_word4.setText("���ڽ���");
			tv_xin4.setText("");
			ll_4.setVisibility(View.VISIBLE);
			
		}else if(order_state.equals("3")){
			//tv_order_state.setText("������");
			tv_word2.setText("������֧��");
			tv_xin2.setText("");
			ll_2.setVisibility(View.VISIBLE);
			tv_word3.setText("�̼�ȷ�϶���");
			tv_xin3.setText("");
			ll_3.setVisibility(View.VISIBLE);
			tv_word4.setText("������");
			tv_xin4.setText("");
			ll_4.setVisibility(View.VISIBLE);
			
			
		}else if(order_state.equals("4")){
			//tv_order_state.setText("��������");
			tv_word2.setText("������֧��");
			tv_xin2.setText("");
			ll_1.setVisibility(View.VISIBLE);
			tv_word3.setText("�̼�ȷ�϶���");
			tv_xin3.setText("");
			ll_3.setVisibility(View.VISIBLE);
			tv_word4.setText("������");
			tv_xin4.setText("");
			ll_4.setVisibility(View.VISIBLE);
			tv_word5.setText("��������");
			tv_xin5.setText("��л����ʹ��");
			ll_5.setVisibility(View.VISIBLE);
			
			
		}else if(order_state.equals("5")){
			//tv_order_state.setText("��ȡ��");
			tv_word2.setText("����δ֧��");
			tv_xin2.setText("����15���������֧��");
			ll_2.setVisibility(View.VISIBLE);
			tv_word3.setText("������ȡ��");
			tv_xin3.setText("֧����ʱ������ȡ��");
			ll_3.setVisibility(View.VISIBLE);
			
		}
		
	}

}
