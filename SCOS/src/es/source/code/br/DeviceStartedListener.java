package es.source.code.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import es.source.code.service.*;

public class DeviceStartedListener extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//�յ��㲥֮�������·���
		Log.v("Broadcast","���յ������㲥");
		//����intent��������,����
		Intent service = new Intent(context,UpdateService.class);
		context.startService(service);

	}

}
