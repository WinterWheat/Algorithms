package es.source.code.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.util.Log;
import es.source.code.activity.R;

public class CancelNotification extends IntentService{

	
	public CancelNotification() {
		super("CancelNotification");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//nm.cancelAll();
		nm.cancel(R.layout.notification);
		Log.v("CancelNotification","É¾³ýÍê³É"+R.layout.notification);
	}
	

}
