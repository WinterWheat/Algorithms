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
	//必须包含的内容
		A small icon, set by setSmallIcon()
		A title, set by setContentTitle()
		Detail text, set by setContentText()*/
	
	//NotificationManager no = getSystemService("ewfe");
	/*
	 * 在这里程序启动时会自动判断,判断是否需要更新,如果需要,则提醒客户,
	 * 然后自动关闭服务
	 */
	
	void showNotification(){
		//提示管理器
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//提示,参数为图标,提示在顶上显示的信息,第三个为当前时间
		Notification notif = new Notification(R.drawable.notification, "有更新哦亲~",
                System.currentTimeMillis());
		//设置标记,可以被新的提醒取缔
		//notif.flags = Notification.FLAG_AUTO_CANCEL;
		notif.flags = Notification.FLAG_AUTO_CANCEL;
		//设置声音,使用默认
		notif.defaults = Notification.DEFAULT_ALL;
		//设置跳转,
		Intent intent = new Intent(this, FoodDetailed.class);
		//显示页面,假设是35
		intent.putExtra("currentpage", 34);
		//包装intent
		PendingIntent contentIntent = PendingIntent.getActivity(
		        this, 			//Context
		        0, 				//请求码
		        intent, 		//意图
		        PendingIntent.FLAG_UPDATE_CURRENT);	//标志,更新当前
		
		notif.setLatestEventInfo(
				this,		//context
		        "新品上架:", 		//标题
		        "菜名:雪绒丝蒸扇贝,价格:36,类型:海鲜", 	//内容
		        contentIntent);		//包装好的意图
		
		
		nm.notify(R.string.app_name, notif);
				
	}
	
	void showMyNotification(){
		//如果需要添加按钮的话,那就要自己设计布局,然后加入响应
		RemoteViews myView = new RemoteViews(this.getPackageName(), R.layout.notification);
		//新建一个提醒,布局用自己定制的
		//可以设置设置图标,文本之类的,图标在布局里设置了,就不用再设置
		//myView.setImageViewResource(viewId, srcId);
		myView.setTextViewText(R.id.notif_text_title, "新品上架:");
		myView.setTextViewText(R.id.notif_text_content, "菜名:雪绒丝蒸扇贝,价格:36,类型:海鲜");
		//设置点击事件,点击后返回原来activity即可
        Intent intent = new Intent(this, CancelNotification.class); 
        // 这里是设置activity的启动模式，分别有4种，
        //我这里是用的FLAG_ACTIVITY_SINGLE_TOP作用是在返回的时候用原来的activity的实例而不是建立新的 
        //原来使用的FLAG_ACTIVITY_NO_HISTORY也可以达到效果
        // 应为这个时候当前的activity在当前的activity栈顶 
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); 
        PendingIntent clickIntent = PendingIntent.getService(this
        		, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		/*PendingIntent clickIntent = PendingIntent.getActivity(this
				, 0, intent
				, PendingIntent.FLAG_UPDATE_CURRENT);*/
		//点击时间,setonclickpendingintent,API上说相当于setOnclicklistener
		myView.setOnClickPendingIntent(R.id.notif_button, clickIntent);
		
		//点击其他地方跳转详细页面
		myView.setOnClickPendingIntent(R.id.notification
				, PendingIntent.getActivity(this, 0
						, new Intent(this, MainScreen.class).
						putExtra("data", "FromEntry")
				, PendingIntent.FLAG_UPDATE_CURRENT));
		//创建提醒
		Notification notify = new Notification(R.drawable.notification, "有更新哦亲~",
                System.currentTimeMillis());
		//notify.icon = R.drawable.notification;
		notify.flags = Notification.FLAG_AUTO_CANCEL;
		notify.defaults = Notification.DEFAULT_ALL;
		notify.contentView = myView;
		//提示管理器
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(R.layout.notification, notify);
		
		Log.v(TAG,"提醒完成"+R.layout.notification);
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
	//设置测试判断,默认需要提示有新菜
	private boolean needNotif = true;
	@Override
	protected void onHandleIntent(Intent intent) {
		//自动开worker线程处理事件
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
		//写出链接,这里请求的是updata服务,
		URL url = new URL("http://192.168.137.1:8080/SCOSServer/updata");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		//使用GET方法
		conn.setRequestMethod("GET");//设置请求方法
		conn.setReadTimeout(5000);//链接超时
		//conn.setDoInput(true);
		Log.v(TAG,"获取JSON数据");
		//获取输入流
		InputStream input = conn.getInputStream();
		Gson gson = new Gson();
		//Gson解析数据
		List<UpdataDish> upDishs = gson.fromJson(
				new InputStreamReader(input)
				, new TypeToken<List<UpdataDish>>(){}.getType()); 
		//打印收取的数据
		for(int i = 0; i < upDishs.size() ; i++)  
		{  
			UpdataDish d = upDishs.get(i);  
		     System.out.println(d.getmName()+" "+d.getmPrice()+" "
		    		 +d.getmKind());  
		}
		return upDishs;
	}
	
	/*public static byte[] readInputStream(InputStream input) throws IOException{
		//写到缓冲区,每次读1Kb,直到结束
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
		//Gson解析数据
		List<UpdataDish> upDishs = gson.fromJson(
				new InputStreamReader(input)
				, new TypeToken<List<UpdataDish>>(){}.getType()); 
		long end = System.currentTimeMillis();
		System.out.println(upDishs.size()+"");
		Log.v("JSON解析时间",(end - start)+"ms");
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
		//Log.v("测试时间",""+System.currentTimeMillis());
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
		Log.v("XML解析时间",(end - start)+"ms");
	}
	

}
