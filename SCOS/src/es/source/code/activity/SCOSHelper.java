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

	//����������
	private static final int HELP_ACTIVITY_RESULT_CODE = 400;
	private static final int DIAL_ACTIVITY_REQUEST_CODE = 410;
	private static final int SMS_ACTIVITY_REQUEST_CODE = 420;
	//��Ŀͼ��
	private int[] itemImg ={
    		R.drawable.userprotocol,R.drawable.aboutsystem,
    		R.drawable.mobile,R.drawable.sms,
    		R.drawable.e_mail
    };
	//��Ŀ����
	private int[] itemName ={
			R.string.help_userprotocol,R.string.help_aboutsystem,
			R.string.help_mobile,R.string.help_sms,
			R.string.help_e_mail
	};
	//������Ϣ
	Handler h=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1)
				//���ؽ��,���ͳɹ�
				Toast.makeText(getBaseContext(), "�����ʼ��ѷ��ͳɹ�", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getBaseContext(), "�����ʼ��ѷ���ʧ��", Toast.LENGTH_SHORT).show();
			
		}
    	
    };
	private GridView mGridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoshelper);
		//�ҵ��ؼ�
		mGridView = (GridView) this.findViewById(R.id.scoshelper_gridview);
		mGridView.setAdapter(new HelperpAdapter());
		//���������ӿ�,������ʵ�ֽӿڷ���,��������߳�
		mGridView.setOnItemClickListener(this);
		//���÷�����
		setResult(HELP_ACTIVITY_RESULT_CODE);
		/*mGridView.setOnItemClickListener(new OnItemClickListener() {
*/
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == SMS_ACTIVITY_REQUEST_CODE)
			if(resultCode == RESULT_OK)
				Toast.makeText(getBaseContext(), "�������ŷ��ͳɹ�", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getBaseContext(), "����ʧ��", Toast.LENGTH_SHORT).show();
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			//�жϵ������Ŀ
			Intent intent = new Intent();
			Uri uri = null;
			switch(position){
			case 0:
				break;
			case 1:
				break;
			case 2:
				//�绰�˹�����
				uri = Uri.parse("tel:\\5554");
				//intent = new Intent(Intent.ACTION_DIAL,uri);
				intent = new Intent(Intent.ACTION_CALL,uri);
				startActivityForResult(intent,DIAL_ACTIVITY_REQUEST_CODE);
				break;
			case 3:
				//���ŷ���
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
			//ʹ��javamail�����ʼ�
			//�������Ҫ�������ʼ�   
			MailSenderInfo mailInfo = new MailSenderInfo(); 
			//�����ʼ�������
			mailInfo.setMailServerHost("smtp.163.com");    
			//���ö˿ں�,smtpĬ��ʹ��25
			mailInfo.setMailServerPort("25");    
			mailInfo.setValidate(true);    
			//�ʺ�����
			mailInfo.setUserName("18660338928@163.com");    
			mailInfo.setPassword("5imj5170989");//������������    
			mailInfo.setFromAddress("18660338928@163.com"); 
			mailInfo.setToAddress(  "103435965@qq.com");    
			mailInfo.setSubject("���Ե�Ϳͻ�������");    
			mailInfo.setContent("�ҵĹ���������?");    
			//�������Ҫ�������ʼ�   
			SimpleMailSender sms = new SimpleMailSender();   
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Log.v("send_mail","�����ʼ������쳣",e);
			}
			//���������ʽ    
			if(sms.sendTextMail(mailInfo))
				//������ͳɹ�,������ʾ
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
		    	//���ô�ֱ�����
		    	line.setOrientation(1);
		        ImageView image = new ImageView(getBaseContext());
		        image.setImageResource(itemImg[position]);
		        TextView text = new TextView(getBaseContext());
		        text.setText(itemName[position]);
		        //�����ַ����
		        text.setTextSize(15);
		        //���������ɫ
		        text.setTextColor(Color.BLACK);
		        //����
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
