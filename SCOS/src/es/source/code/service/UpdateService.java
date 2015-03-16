package es.source.code.service;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.util.Xml;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.MainScreen;
import es.source.code.activity.R;
import es.source.code.model.UpdataDish;
public class UpdateService extends IntentService {

	
	private final String TAG = "UpdateService";
	/*A Notification object must contain the following:
	//�������������
		A small icon, set by setSmallIcon()
		A title, set by setContentTitle()
		Detail text, set by setContentText()*/
	
	//NotificationManager no = getSystemService("ewfe");
	/*
	 * �������������ʱ���Զ��ж�,�ж��Ƿ���Ҫ����,�����Ҫ,�����ѿͻ�,
	 * Ȼ���Զ��رշ���
	 */
	
	void showNotification(){
		//��ʾ������
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//��ʾ,����Ϊͼ��,��ʾ�ڶ�����ʾ����Ϣ,������Ϊ��ǰʱ��
		Notification notif = new Notification(R.drawable.notification, "�и���Ŷ��~",
                System.currentTimeMillis());
		//���ñ��,���Ա��µ�����ȡ��
		//notif.flags = Notification.FLAG_AUTO_CANCEL;
		notif.flags = Notification.FLAG_AUTO_CANCEL;
		//��������,ʹ��Ĭ��
		notif.defaults = Notification.DEFAULT_ALL;
		//������ת,
		Intent intent = new Intent(this, FoodDetailed.class);
		//��ʾҳ��,������35
		intent.putExtra("currentpage", 34);
		//��װintent
		PendingIntent contentIntent = PendingIntent.getActivity(
		        this, 			//Context
		        0, 				//������
		        intent, 		//��ͼ
		        PendingIntent.FLAG_UPDATE_CURRENT);	//��־,���µ�ǰ
		
		notif.setLatestEventInfo(
				this,		//context
		        "��Ʒ�ϼ�:", 		//����
		        "����:ѩ��˿���ȱ�,�۸�:36,����:����", 	//����
		        contentIntent);		//��װ�õ���ͼ
		
		
		nm.notify(R.string.app_name, notif);
				
	}
	
	void showMyNotification(){
		//�����Ҫ��Ӱ�ť�Ļ�,�Ǿ�Ҫ�Լ���Ʋ���,Ȼ�������Ӧ
		RemoteViews myView = new RemoteViews(this.getPackageName(), R.layout.notification);
		//�½�һ������,�������Լ����Ƶ�
		//������������ͼ��,�ı�֮���,ͼ���ڲ�����������,�Ͳ���������
		//myView.setImageViewResource(viewId, srcId);
		myView.setTextViewText(R.id.notif_text_title, "��Ʒ�ϼ�:");
		myView.setTextViewText(R.id.notif_text_content, "����:ѩ��˿���ȱ�,�۸�:36,����:����");
		//���õ���¼�,����󷵻�ԭ��activity����
        Intent intent = new Intent(this, CancelNotification.class); 
        // ����������activity������ģʽ���ֱ���4�֣�
        //���������õ�FLAG_ACTIVITY_SINGLE_TOP�������ڷ��ص�ʱ����ԭ����activity��ʵ�������ǽ����µ� 
        //ԭ��ʹ�õ�FLAG_ACTIVITY_NO_HISTORYҲ���ԴﵽЧ��
        // ӦΪ���ʱ��ǰ��activity�ڵ�ǰ��activityջ�� 
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); 
        PendingIntent clickIntent = PendingIntent.getService(this
        		, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		/*PendingIntent clickIntent = PendingIntent.getActivity(this
				, 0, intent
				, PendingIntent.FLAG_UPDATE_CURRENT);*/
		//���ʱ��,setonclickpendingintent,API��˵�൱��setOnclicklistener
		myView.setOnClickPendingIntent(R.id.notif_button, clickIntent);
		
		//��������ط���ת��ϸҳ��
		myView.setOnClickPendingIntent(R.id.notification
				, PendingIntent.getActivity(this, 0
						, new Intent(this, MainScreen.class).
						putExtra("data", "FromEntry")
				, PendingIntent.FLAG_UPDATE_CURRENT));
		//��������
		Notification notify = new Notification(R.drawable.notification, "�и���Ŷ��~",
                System.currentTimeMillis());
		//notify.icon = R.drawable.notification;
		notify.flags = Notification.FLAG_AUTO_CANCEL;
		notify.defaults = Notification.DEFAULT_ALL;
		notify.contentView = myView;
		//��ʾ������
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(R.layout.notification, notify);
		
		Log.v(TAG,"�������"+R.layout.notification);
	}
	
	
	private NotificationManager nm;
	public UpdateService() {
		super("UpdateService");
		// TODO Auto-generated constructor stub
		//nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	//on
	//���ò����ж�,Ĭ����Ҫ��ʾ���²�
	private boolean needNotif = true;
	@Override
	protected void onHandleIntent(Intent intent) {
		//�Զ���worker�̴߳����¼�
		if(needNotif)
			//showNotification();
			showMyNotification();
		//JsonReader reader = new 
		try {
			requestByXML();
			//requestByGson();
			//requestData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private List<UpdataDish> requestData() throws IOException{
		//д������,�����������updata����,
		URL url = new URL("http://192.168.137.1:8080/SCOSServer/updata");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		//ʹ��GET����
		conn.setRequestMethod("GET");//�������󷽷�
		conn.setReadTimeout(5000);//���ӳ�ʱ
		//conn.setDoInput(true);
		Log.v(TAG,"��ȡJSON����");
		//��ȡ������
		InputStream input = conn.getInputStream();
		Gson gson = new Gson();
		//Gson��������
		List<UpdataDish> upDishs = gson.fromJson(
				new InputStreamReader(input)
				, new TypeToken<List<UpdataDish>>(){}.getType()); 
		//��ӡ��ȡ������
		for(int i = 0; i < upDishs.size() ; i++)  
		{  
			UpdataDish d = upDishs.get(i);  
		     System.out.println(d.getmName()+" "+d.getmPrice()+" "
		    		 +d.getmKind());  
		}
		return upDishs;
	}
	
	/*public static byte[] readInputStream(InputStream input) throws IOException{
		//д��������,ÿ�ζ�1Kb,ֱ������
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len = input.read(buffer))!=-1 ){
			output.write(buffer, 0, len);
		}
		input.close();
		return output.toByteArray();
	}*/
	private void requestByGson() throws ClientProtocolException, IOException{
		//HttpClient client  = null;
		HttpClient client = new DefaultHttpClient();
		
		HttpGet get = new HttpGet("http://192.168.56.1:8080/SCOSServer/updata");
		//HttpPut put = new HttpPut("http://192.168.56.1:8080/SCOSServer/updata");
		HttpResponse response = client.execute(get);
		//HttpRequest request = null;
		InputStream input = response.getEntity().getContent();
		long start = System.currentTimeMillis();
		Gson gson = new Gson();
		//Gson��������
		List<UpdataDish> upDishs = gson.fromJson(
				new InputStreamReader(input)
				, new TypeToken<List<UpdataDish>>(){}.getType()); 
		long end = System.currentTimeMillis();
		System.out.println(upDishs.size()+"");
		Log.v("JSON����ʱ��",(end - start)+"ms");
	}
	
	private void requestByXML() throws ClientProtocolException, IOException, XmlPullParserException{
		//HttpClient client  = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://192.168.56.1:8080/SCOSServer/updataxml");
		HttpResponse response = client.execute(get);
		InputStream input = response.getEntity().getContent();
		
		List<UpdataDish> upDishs = null;
		UpdataDish d = null;
		
		long start = System.currentTimeMillis();
		
		XmlPullParser paser = Xml.newPullParser();
		paser.setInput(input, "utf-8");
		int eventType = paser.getEventType();
		//Log.v("����ʱ��",""+System.currentTimeMillis());
		while(eventType != XmlPullParser.END_DOCUMENT){
			switch(eventType){
			case XmlPullParser.START_DOCUMENT:
				upDishs = new ArrayList<UpdataDish>();
				break;
			case XmlPullParser.START_TAG:
				String str = paser.getName();
				if(str.equals("dish")){
					d = new UpdataDish();
				}
				if(d != null){
					if(str.equals("mName"))
						d.setmName(paser.nextText());
					else if(str.equals("mPrice"))
						d.setmPrice(new Integer(paser.nextText()));
					else if(str.equals("mKind"))
						d.setmKind(new Integer(paser.nextText()));
				}
				break;
			case XmlPullParser.END_TAG:
				if(paser.getName().equals("dish")){
					upDishs.add(d);
					d = null;
				}
				break;
			case XmlPullParser.END_DOCUMENT:
				break;
			default:
				break;
			}
			eventType = paser.next();
		}
		
		
		long end = System.currentTimeMillis();
		System.out.println(upDishs.size()+"");
		Log.v("XML����ʱ��",(end - start)+"ms");
	}
	

}
