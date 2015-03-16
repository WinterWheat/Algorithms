package es.source.code.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class PayAsyncTask extends AsyncTask<Double, Integer, String> {

	private int price;
	private ProgressDialog pd;
	public PayAsyncTask(int orderPrices, ProgressDialog pDailog) {
		price = orderPrices;
		pd = pDailog;
	}

	@Override
	protected String doInBackground(Double... params) {
		double i = params[0];
        for ( ; i <= 100; i+=16.67) {
        	//总共需要6S,6次延时
        	publishProgress((int)i); 
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//打印错误
				Log.v("doInBackground","后台进出出错",e);
			} 
        }  
        return i + 0 + "";
	}

	//结束
	@Override
	protected void onPostExecute(String result) {
		//结束时提示积分信息,销毁进度条
		Toast.makeText(pd.getContext()
				,"本次付款:"+price+"元.积分增加"+price/10+"点!", Toast.LENGTH_SHORT).show();
		pd.dismiss();
	}
	//更新
	@Override
	protected void onProgressUpdate(Integer... values) {
		//更新进度条状态 
        pd.setProgress(values[0]); 
	}
	
}
