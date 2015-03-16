package es.source.code.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.source.code.model.RepertoryList;
import es.source.code.model.UpdataDish;
public class ServerObserverService extends Service {

	private final String TAG = "ServerObserverService";
	private boolean quit = false;
	//��Ʒ������ϸ��Ϣ
	private RepertoryList order = null;
	private Message fromClientMsg = null;
	private Message sendMSG = null;
	
	class MakeData extends Thread{
		@Override
		public void run() {
			while(!quit){
				Log.v("��������","SCOS����������");
				Message m = Message.obtain();
				m.what = 10;
				//���ÿ��
				RepertoryList rep = new RepertoryList();
				rep.setRepertoryValue(new Random().nextInt(10));
				/*try {
					rep.setUpDishs(requestData());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				Bundle bund = new Bundle();
				
				//����bundle��
				bund.putSerializable("reptory", rep);
				m.setData(bund);
				
				try {
					//������ʹ������Ϣ
					obSendM.send(m);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					Log.v(TAG,"��ʱ�����ж�");
				}
			}
		};
	};
	
	Handler cMessageHandler = new Handler(){
		
		@Override
		public void handleMessage(final Message msg) {
			if (msg.what == 1 && scosAppIsRun()) {
				Log.v(TAG,"�յ�msg,whatΪ1");
				//��MSF�л�ȡ������ʹ
				obSendM = msg.replyTo;
				//���߳̽��з���
				new MakeData().start();
			}
			else if(msg.what == 0){
				quit = true;
			}
			
		};
	};
	
	
	Messenger observerM = new Messenger(cMessageHandler);
	Messenger obSendM = null;
	
	//�ж�scos�Ƿ�Ϊ����̬
	public boolean scosAppIsRun(){
		//��ȡ��Ľ���
		//���Կ���
		ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
	    List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
	    for(RunningAppProcessInfo rapi : infos){
	        if(rapi.processName.equals("es.source.code.activity"))
	            return true;
	        }
	    return false;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		//����server��ʹ
		Log.v("onBind",observerM.getBinder().toString());
		Log.v("onBind","�󶨳ɹ�");
		return observerM.getBinder();
	}
	
	@Override
	public void onDestroy() {
		Log.v("onDestroy","onDestroy");
		observerM = null;//�ر���ʹ
	}
	
	

}
