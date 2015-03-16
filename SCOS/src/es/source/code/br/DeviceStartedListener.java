package es.source.code.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import es.source.code.service.*;

public class DeviceStartedListener extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//收到广播之后开启更新服务
		Log.v("Broadcast","接收到开机广播");
		//设置intent启动服务,提醒
		Intent service = new Intent(context,UpdateService.class);
		context.startService(service);

	}

}
