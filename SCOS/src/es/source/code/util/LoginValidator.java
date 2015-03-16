package es.source.code.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class LoginValidator {

	public static boolean isValidator(String username,String password) throws IOException{
		//设置请求路径和提交数据
		String path = new String("http://192.168.137.1:8080/SCOSServer/login");
		path +="?username="+username+"&&password="+password;
		//通过get方法提交的数据是在地址栏中显示的不超过255个字符
		URL url = new URL(path);
		//开启链接
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");//设置请求方法
		//设置用户名密码
		Log.v("Validator",username+" "+password);
		conn.setReadTimeout(5000);//设置链接超时
		//conn.get
		int flag = conn.getHeaderFieldInt("flag", -1);
		//打印信息
		Log.v("Validator",""+flag);
		if(flag == 1)
			return true;
		return false;
	}
}
