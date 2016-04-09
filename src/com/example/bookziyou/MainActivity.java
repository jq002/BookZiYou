package com.example.bookziyou;

import com.tools.SysApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	 private SharedPreferences sharedPreferences;  
	 private Button main_login_btn;
	 private Button main_regist_btn;
   	 private TextView tv;	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ȥ������															
      	this.requestWindowFeature(Window.FEATURE_NO_TITLE);				

        SysApplication.getInstance().addActivity(this); //�˳���������
        sharedPreferences = this.getSharedPreferences("userInfo",Context.MODE_WORLD_READABLE);
        if (sharedPreferences.getBoolean("AUTO_ISCHECK", false)) {  
            
            Intent intent = new Intent();  
            intent.setClass(MainActivity.this, LookmainActivity.class);  
            startActivity(intent);  
            finish(); 
        } else {  
            setContentView(R.layout.activity_main);
            // SysApplication.getInstance().addActivity(this); 
             main_regist_btn=(Button) findViewById(R.id.main_regist_btn);
             main_login_btn=(Button)findViewById(R.id.main_login_btn);
             tv=(TextView)findViewById(R.id.main_tv);
//             AssetManager mgr=getAssets();//�õ�AssetManager
//             Typeface tf=Typeface.createFromAsset(mgr, "fonts/words.TTF");//����·���õ�Typeface
//             tv.setTypeface(tf);//��������
//             main_regist_btn.setTypeface(tf);//��������
//             main_login_btn.setTypeface(tf);//��������
             main_login_btn.setOnClickListener(new Button.OnClickListener() {
         			@Override
         			public void onClick(View v) {
         		            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
         				    startActivity(intent);
         				    
         			}
         		});
             
             main_regist_btn.setOnClickListener(new Button.OnClickListener() {
     			@Override
     			public void onClick(View v) {
     		            Intent intent=new Intent(MainActivity.this,ChooseRegisterActivity.class);
     				    startActivity(intent);
     			}
     		});
        } 
  
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    private long exitTime = 0;
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (KeyEvent.KEYCODE_BACK == keyCode) {  
            // �ж��Ƿ�������֮������������ؼ��������˳��������˳�  
            if (System.currentTimeMillis() - exitTime > 2000) {  
                Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",  
                        Toast.LENGTH_SHORT).show();  
                // ��ϵͳ��ǰ��ʱ�丳ֵ��exitTime  
                exitTime = System.currentTimeMillis();  
            } else {  
            	SysApplication.getInstance().exit();
            }  
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    }
}
