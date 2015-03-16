
package es.source.code.activity;


import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import es.source.code.model.User;
import es.source.code.util.LoginValidator;

@SuppressLint("WorldWriteableFiles") 
public class LoginOrRegister extends Activity {

	private EditText username;
	private EditText passward;
	String userStr = null;
	String passStr = null;
	//所以只要user修改了,原来的intent就修改了,所以不用往回传intent
	private Intent intent;
	//private User loginUser;
	private static final int LOGIN_ACTIVITY_RESULT_CODE = 110;
	private static final int REGISTER_ACTIVITY_RESULT_CODE = 120;
	private static final int RETURN_ACTIVITY_RESULT_CODE = 130;
	
	//sharepreferences用于存储一些简单的数据
	private SharedPreferences mPreferences;
	private SharedPreferences.Editor mEditor;
	
	//处理注册发送的网络信息
	Handler logHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//提示相关信息,
			if(msg.what == 1){
				Toast.makeText(getApplicationContext(),"注册成功" , Toast.LENGTH_SHORT).show();
				finish();
			}
			else
				Toast.makeText(getApplicationContext(),"注册失败" , Toast.LENGTH_SHORT).show();
			
		};
	};
	
	class MyValidator extends Thread{
		//用户名密码,通过构造函数传入
		String name,pass;
		public MyValidator(String userStr,String pasStr) {
			// TODO Auto-generated constructor stub
			name = userStr;
			pass = pasStr;
		}
		@Override
		public void run() {
			try {
				//判断用户名密码是否正确,向handler发送数据
				if(LoginValidator.isValidator(name, pass))
					logHandler.sendEmptyMessage(1);
				else
					logHandler.sendEmptyMessage(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@SuppressLint("WorldReadableFiles") @SuppressWarnings("deprecation")
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.loginorregister);
	        try{
	        	intent = getIntent();
		       // loginUser = (User)intent.getSerializableExtra("user");
	        }
	        catch(Exception e){
	        	e.printStackTrace();//打印错误信息
	        }
	        intent.setClass(this, MainScreen.class);
	        username = (EditText)findViewById(R.id.usernamebox);
	        passward = (EditText)findViewById(R.id.passwardbox);
	        Button logBt = (Button)findViewById(R.id.loginpage_loginbt);
	        Button regBt = (Button)findViewById(R.id.loginpage_registerbt);
	        //获取sharedpreferences,模式为可读写
	        mPreferences = getSharedPreferences("scos_lite_data"
	        		, Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
	        //获取编辑器,编辑器用于写数据,而prefernce用于读
	        mEditor = mPreferences.edit();
	        userStr = mPreferences.getString("userName", null);
	        if(userStr==null){
	        	logBt.setVisibility(View.GONE);
	        }
	        else{
	        	regBt.setVisibility(View.GONE);
	        	username.setText(userStr);
	        }
	        
	        //Intent intent = new Intent(this,DeviceStartedListener.class);
			//intent.setAction(Intent.ACTION_EDIT);
			this.sendBroadcast(intent);
	}
	
	
	
	public void registerClick(View v) throws IOException{

		/*验证“登录名”和“登
		** 录密码”是否符合以下规则：不为空，只包含英文大小写和数字。当输入内容
		** 不符合规则时，则使用 setError 方法在当前输入框出提示错误信息“输入内容
		** 不符合规则” 。当符合规则后，屏幕跳转至 MainScreen，并向 MainScreen 类传
		** 递一个数据 String 值为“LoginSuccess”*/
		userStr = username.getText().toString();
		passStr = passward.getText().toString();
		if(checkRules(userStr,passStr)){
			//用户名密码检查无错则新建User对象
			User loginUser = new User(this.getResources());
			loginUser.setUserName(userStr);
			loginUser.setPassword(passStr);
			loginUser.setOldUser(true);
			//loginUser.setHide(false);
			//loginUser.setUser(usertest, passtest, false, false);
			intent.putExtra("data","RegisterSuccess");
			intent.putExtra("user",loginUser);
	        //设置回退模式
			setResult(REGISTER_ACTIVITY_RESULT_CODE, intent);
			//将用户名写入sharepreference
			mEditor.putString("userName", userStr);
			//登录状态写为1
			mEditor.putInt("loginState", 1);
			mEditor.commit();//提交
			//验证
			MyValidator validator = new MyValidator(userStr, passStr);
			validator.start();
		}
		else 
			return;
	}
	

	public void loginOkClick(View v) throws IOException{
		
		/*以下是完成注册功能,当注册成功是olduser为false
		 * 传递registersuccess,
		 */
		userStr = username.getText().toString();
		passStr = passward.getText().toString();
		if(checkRules(userStr,passStr)){
			//用户名密码检查无错则新建User对象
			User loginUser = new User(this.getResources());
			loginUser.setUserName(userStr);
			loginUser.setPassword(passStr);
			loginUser.setOldUser(true);
			//loginUser.setHide(false);
			//loginUser.setUser(usertest, passtest, false, false);
			intent.putExtra("data","LoginSuccess");
			intent.putExtra("user",loginUser);
	        //设置回退模式
			setResult(LOGIN_ACTIVITY_RESULT_CODE, intent);
			//将用户名写入sharepreference
			mEditor.putString("userName", userStr);
			//登录状态写为1
			mEditor.putInt("loginState", 1);
			mEditor.commit();//提交
			//验证
			MyValidator validator = new MyValidator(userStr, passStr);
			validator.start();
		}
		else 
			return;
		
	}
	
	public void returnClick(View v){
		User loginUser = new User(getResources());
		loginUser.setUser(null, null, false, false);
		intent.putExtra("data","RegisterSuccess");
		intent.putExtra("user",loginUser);
        //设置回退模式
		setResult(RETURN_ACTIVITY_RESULT_CODE, intent);
		if(mPreferences.getString("userName", null).isEmpty()){
			mEditor.putInt("loginState", 0);
			mEditor.commit();
		}
		finish();
	}
	
	
	
	//判断是否为字符和数字
	private boolean isAlaphNum(char[] str){
		int size = str.length;
		for(int i=0; i<size; i++)
			//0-9,a-z,A-Z
			if( !(str[i]>47 && str[i]<58 ||
				str[i]>64 && str[i]<92 ||
				str[i]>96 && str[i]<123) )
				return false;
		return true;
	}
	

	private boolean checkRules(String userStr, String passStr) {
		
		if(userStr.isEmpty()){
			username.setError("用户名不能为空!");
			return false;
		}
		else if(passStr.isEmpty()){
			passward.setError("密码不能为空!");
			return false;
		}
		else if(!isAlaphNum(userStr.toCharArray())){
			username.setError("只能由字母和数字组成!");
			return false;
		}
		else if(!isAlaphNum(passStr.toCharArray())){
			passward.setError("只能由字母和数字组成!");
			return false;
		}
		return true;
	}
}
