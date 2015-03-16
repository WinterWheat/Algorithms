package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import es.source.code.util.*;

public class SCOSHelper extends Activity implements OnItemClickListener {

	//帮助请求码
	private static final int HELP_ACTIVITY_RESULT_CODE = 400;
	private static final int DIAL_ACTIVITY_REQUEST_CODE = 410;
	private static final int SMS_ACTIVITY_REQUEST_CODE = 420;
	//条目图标
	private int[] itemImg ={
    		R.drawable.userprotocol,R.drawable.aboutsystem,
    		R.drawable.mobile,R.drawable.sms,
    		R.drawable.e_mail
    };
	//条目名字
	private int[] itemName ={
			R.string.help_userprotocol,R.string.help_aboutsystem,
			R.string.help_mobile,R.string.help_sms,
			R.string.help_e_mail
	};
	//处理消息
	Handler h=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1)
				//返回结果,发送成功
				Toast.makeText(getBaseContext(), "求助邮件已发送成功", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getBaseContext(), "求助邮件已发送失败", Toast.LENGTH_SHORT).show();
			
		}
    	
    };
	private GridView mGridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoshelper);
		//找到控件
		mGridView = (GridView) this.findViewById(R.id.scoshelper_gridview);
		mGridView.setAdapter(new HelperpAdapter());
		//加入侦听接口,在类中实现接口方法,方便调用线程
		mGridView.setOnItemClickListener(this);
		//设置返回码
		setResult(HELP_ACTIVITY_RESULT_CODE);
		/*mGridView.setOnItemClickListener(new OnItemClickListener() {
*/
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == SMS_ACTIVITY_REQUEST_CODE)
			if(resultCode == RESULT_OK)
				Toast.makeText(getBaseContext(), "求助短信发送成功", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getBaseContext(), "发送失败", Toast.LENGTH_SHORT).show();
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			//判断点击的条目
			Intent intent = new Intent();
			Uri uri = null;
			switch(position){
			case 0:
				break;
			case 1:
				break;
			case 2:
				//电话人工服务
				uri = Uri.parse("tel:\\5554");
				//intent = new Intent(Intent.ACTION_DIAL,uri);
				intent = new Intent(Intent.ACTION_CALL,uri);
				startActivityForResult(intent,DIAL_ACTIVITY_REQUEST_CODE);
				break;
			case 3:
				//短信服务
				uri = Uri.parse("smsto:5554");     
				intent = new Intent(Intent.ACTION_SENDTO, uri);     
				intent.putExtra("sms_body", "test scos helper"); 
				startActivityForResult(intent, SMS_ACTIVITY_REQUEST_CODE);
				break;
			case 4:
				SendEmail se = new SendEmail();
				se.start();
				//se.run();
				break;
			default:
				break;
			}
	}
	
	class SendEmail extends Thread{

		@Override
		public void run() {
			//使用javamail发送邮件
			//这个类主要是设置邮件   
			MailSenderInfo mailInfo = new MailSenderInfo(); 
			//设置邮件服务器
			mailInfo.setMailServerHost("smtp.163.com");    
			//设置端口号,smtp默认使用25
			mailInfo.setMailServerPort("25");    
			mailInfo.setValidate(true);    
			//帐号密码
			mailInfo.setUserName("18660338928@163.com");    
			mailInfo.setPassword("5imj5170989");//您的邮箱密码    
			mailInfo.setFromAddress("18660338928@163.com"); 
			mailInfo.setToAddress(  "103435965@qq.com");    
			mailInfo.setSubject("来自点餐客户的求助");    
			mailInfo.setContent("我的宫爆鸡丁呢?");    
			//这个类主要来发送邮件   
			SimpleMailSender sms = new SimpleMailSender();   
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Log.v("send_mail","发送邮件休眠异常",e);
			}
			//发送文体格式    
			if(sms.sendTextMail(mailInfo))
				//如果发送成功,则发送提示
				h.sendEmptyMessage(1);	
			else
				h.sendEmptyMessage(2);
		}
    	
    }
	
	
	
	public class HelperpAdapter extends BaseAdapter implements ListAdapter {


		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemName.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return itemName[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout line = new LinearLayout(getBaseContext());
			if(convertView == null)
	    	{	
		    	//设置垂直放组件
		    	line.setOrientation(1);
		        ImageView image = new ImageView(getBaseContext());
		        image.setImageResource(itemImg[position]);
		        TextView text = new TextView(getBaseContext());
		        text.setText(itemName[position]);
		        //设置字符宽度
		        text.setTextSize(15);
		        //设置字体黑色
		        text.setTextColor(Color.BLACK);
		        //居中
		        text.setGravity(Gravity.CENTER);
		        line.addView(image);
		        line.addView(text);
	    	}
	    	else {
	    		line = (LinearLayout)convertView;
	    	}
	        
	        return line;
		}

	}
	
	

	
	
}
