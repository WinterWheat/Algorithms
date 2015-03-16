package es.source.code.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class LoginValidator {

	public static boolean isValidator(String username,String password) throws IOException{
		//��������·�����ύ����
		String path = new String("http://192.168.137.1:8080/SCOSServer/login");
		path +="?username="+username+"&&password="+password;
		//ͨ��get�����ύ���������ڵ�ַ������ʾ�Ĳ�����255���ַ�
		URL url = new URL(path);
		//��������
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");//�������󷽷�
		//�����û�������
		Log.v("Validator",username+" "+password);
		conn.setReadTimeout(5000);//�������ӳ�ʱ
		//conn.get
		int flag = conn.getHeaderFieldInt("flag", -1);
		//��ӡ��Ϣ
		Log.v("Validator",""+flag);
		if(flag == 1)
			return true;
		return false;
	}
}
