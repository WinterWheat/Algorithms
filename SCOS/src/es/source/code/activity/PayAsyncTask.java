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
        	//�ܹ���Ҫ6S,6����ʱ
        	publishProgress((int)i); 
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//��ӡ����
				Log.v("doInBackground","��̨��������",e);
			} 
        }  
        return i + 0 + "";
	}

	//����
	@Override
	protected void onPostExecute(String result) {
		//����ʱ��ʾ������Ϣ,���ٽ�����
		Toast.makeText(pd.getContext()
				,"���θ���:"+price+"Ԫ.��������"+price/10+"��!", Toast.LENGTH_SHORT).show();
		pd.dismiss();
	}
	//����
	@Override
	protected void onProgressUpdate(Integer... values) {
		//���½�����״̬ 
        pd.setProgress(values[0]); 
	}
	
}
