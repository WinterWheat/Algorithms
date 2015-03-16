
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
	//����ֻҪuser�޸���,ԭ����intent���޸���,���Բ������ش�intent
	private Intent intent;
	//private User loginUser;
	private static final int LOGIN_ACTIVITY_RESULT_CODE = 110;
	private static final int REGISTER_ACTIVITY_RESULT_CODE = 120;
	private static final int RETURN_ACTIVITY_RESULT_CODE = 130;
	
	//sharepreferences���ڴ洢һЩ�򵥵�����
	private SharedPreferences mPreferences;
	private SharedPreferences.Editor mEditor;
	
	//����ע�ᷢ�͵�������Ϣ
	Handler logHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//��ʾ�����Ϣ,
			if(msg.what == 1){
				Toast.makeText(getApplicationContext(),"ע��ɹ�" , Toast.LENGTH_SHORT).show();
				finish();
			}
			else
				Toast.makeText(getApplicationContext(),"ע��ʧ��" , Toast.LENGTH_SHORT).show();
			
		};
	};
	
	class MyValidator extends Thread{
		//�û�������,ͨ�����캯������
		String name,pass;
		public MyValidator(String userStr,String pasStr) {
			// TODO Auto-generated constructor stub
			name = userStr;
			pass = pasStr;
		}
		@Override
		public void run() {
			try {
				//�ж��û��������Ƿ���ȷ,��handler��������
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
	        	e.printStackTrace();//��ӡ������Ϣ
	        }
	        intent.setClass(this, MainScreen.class);
	        username = (EditText)findViewById(R.id.usernamebox);
	        passward = (EditText)findViewById(R.id.passwardbox);
	        Button logBt = (Button)findViewById(R.id.loginpage_loginbt);
	        Button regBt = (Button)findViewById(R.id.loginpage_registerbt);
	        //��ȡsharedpreferences,ģʽΪ�ɶ�д
	        mPreferences = getSharedPreferences("scos_lite_data"
	        		, Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
	        //��ȡ�༭��,�༭������д����,��prefernce���ڶ�
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

		/*��֤����¼�����͡���
		** ¼���롱�Ƿ�������¹��򣺲�Ϊ�գ�ֻ����Ӣ�Ĵ�Сд�����֡�����������
		** �����Ϲ���ʱ����ʹ�� setError �����ڵ�ǰ��������ʾ������Ϣ����������
		** �����Ϲ��� �������Ϲ������Ļ��ת�� MainScreen������ MainScreen �ഫ
		** ��һ������ String ֵΪ��LoginSuccess��*/
		userStr = username.getText().toString();
		passStr = passward.getText().toString();
		if(checkRules(userStr,passStr)){
			//�û����������޴����½�User����
			User loginUser = new User(this.getResources());
			loginUser.setUserName(userStr);
			loginUser.setPassword(passStr);
			loginUser.setOldUser(true);
			//loginUser.setHide(false);
			//loginUser.setUser(usertest, passtest, false, false);
			intent.putExtra("data","RegisterSuccess");
			intent.putExtra("user",loginUser);
	        //���û���ģʽ
			setResult(REGISTER_ACTIVITY_RESULT_CODE, intent);
			//���û���д��sharepreference
			mEditor.putString("userName", userStr);
			//��¼״̬дΪ1
			mEditor.putInt("loginState", 1);
			mEditor.commit();//�ύ
			//��֤
			MyValidator validator = new MyValidator(userStr, passStr);
			validator.start();
		}
		else 
			return;
	}
	

	public void loginOkClick(View v) throws IOException{
		
		/*���������ע�Ṧ��,��ע��ɹ���olduserΪfalse
		 * ����registersuccess,
		 */
		userStr = username.getText().toString();
		passStr = passward.getText().toString();
		if(checkRules(userStr,passStr)){
			//�û����������޴����½�User����
			User loginUser = new User(this.getResources());
			loginUser.setUserName(userStr);
			loginUser.setPassword(passStr);
			loginUser.setOldUser(true);
			//loginUser.setHide(false);
			//loginUser.setUser(usertest, passtest, false, false);
			intent.putExtra("data","LoginSuccess");
			intent.putExtra("user",loginUser);
	        //���û���ģʽ
			setResult(LOGIN_ACTIVITY_RESULT_CODE, intent);
			//���û���д��sharepreference
			mEditor.putString("userName", userStr);
			//��¼״̬дΪ1
			mEditor.putInt("loginState", 1);
			mEditor.commit();//�ύ
			//��֤
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
        //���û���ģʽ
		setResult(RETURN_ACTIVITY_RESULT_CODE, intent);
		if(mPreferences.getString("userName", null).isEmpty()){
			mEditor.putInt("loginState", 0);
			mEditor.commit();
		}
		finish();
	}
	
	
	
	//�ж��Ƿ�Ϊ�ַ�������
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
			username.setError("�û�������Ϊ��!");
			return false;
		}
		else if(passStr.isEmpty()){
			passward.setError("���벻��Ϊ��!");
			return false;
		}
		else if(!isAlaphNum(userStr.toCharArray())){
			username.setError("ֻ������ĸ���������!");
			return false;
		}
		else if(!isAlaphNum(passStr.toCharArray())){
			passward.setError("ֻ������ĸ���������!");
			return false;
		}
		return true;
	}
}
