package es.source.code.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import es.source.code.model.User;
import es.source.code.service.UpdateService;

@SuppressLint("WorldReadableFiles") public class MainScreen extends Activity {

	private Intent mIntent = new Intent();
	//private int width;
	private String data="";
	private GridView gridview;
	private User user;
	private static final int LOGIN_ACTIVITY_REQUEST_CODE = 100;
	private static final int LOGIN_ACTIVITY_RESULT_CODE = 110;
	private static final int REGISTER_ACTIVITY_RESULT_CODE = 120;
	//private static final int RETURN_ACTIVITY_RESULT_CODE = 130;
	private static final int ORDER_ACTIVITY_REQUEST_CODE = 200;
	private static final int VIEW_ACTIVITY_REQUEST_CODE =300;
	private static final int HELP_ACTIVITY_REQUEST_CODE =400;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscreen);
		initData();
		judgeData();
		//绘制gridview
		initGridView();
		//startService(new Intent(this,UpdateService.class));
		Intent service = new Intent(this,UpdateService.class);
		startService(service);
       }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		/*传递返回值
		欢迎新用户
		用传过来的intent代替现在的intent,userlist借此更新*/
		if(requestCode == LOGIN_ACTIVITY_REQUEST_CODE &&
				resultCode>100){
			data = intent.getStringExtra("data");
			switch(resultCode)
			{
			case LOGIN_ACTIVITY_RESULT_CODE:
				if(data.equals("LoginSuccess"))
					user = (User)intent.getSerializableExtra("user");
				break;
			case REGISTER_ACTIVITY_RESULT_CODE:
				if(data.equals("RegisterSuccess")){
					user = (User)intent.getSerializableExtra("user");
					//新用户提醒
					Toast.makeText(getApplicationContext(), "欢迎您成为 SCOS 新用户",
					     Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				user = (User) intent.getSerializableExtra("user");
				break;
			
			}
			mIntent = intent;
			//获取登录注册页面设置的shareprefecrence中的值,模式为可读
			@SuppressWarnings("deprecation")
			SharedPreferences preference = getSharedPreferences("scos_lite_data"
					, Context.MODE_WORLD_READABLE);
			//获取登录状态,默认返回0
			if(preference.getInt("loginState", 0) == 1)
				user.setHide(false);
			else
				user.setHide(true);
			
			initGridView();
		}
		else if(requestCode==HELP_ACTIVITY_REQUEST_CODE){
			//从帮助页返回的,不需要任何处理
		}
		else{
			//其他页面传递user对象,包括了点菜和查看菜单页面
			user = (User) intent.getSerializableExtra("user");
		}
	}
	
	private void judgeData() {
		//检查DATA,判断是个什么字符串
		data = mIntent.getStringExtra("data");
		if(data.equals("FromEntry"))
			user.setHide(false);
		else{
			user.setHide(true);
		}
			
	}
	private void initData(){
		mIntent = getIntent();
		
        user = new User(getResources());
       // userList = new UserList(this);
        //传递序列化对象
        mIntent.putExtra("user",user);
        mIntent.putExtra("currentpage", 0);
       // mIntent.putExtra("userlist",userList);
        }
	private void initGridView(){
		//获取屏幕宽度
		//DisplayMetrics metric = new DisplayMetrics();
       // getWindowManager().getDefaultDisplay().getMetrics(metric);
       // width = metric.widthPixels;  // 屏幕宽度（像素）
		//获取,设置参数及适配器
  		gridview = (GridView) findViewById(R.id.gridview);
  		//gridview.setColumnWidth(width/2-20);
  		gridview.setAdapter(new MainScreenAdapter(this,user));
  		//设置监听响应
  		gridview.setOnItemClickListener(new OnItemClickListener() {
  			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
  				
  				mIntent.putExtra("user", user);
  				switch(position)
  				{
  				case 0://点菜  					
  					mIntent.setClass(MainScreen.this, FoodView.class);
  					startActivityForResult(mIntent, ORDER_ACTIVITY_REQUEST_CODE);
  					break;
  				case 1://查看菜单
  					//mIntent.putExtra("dishlist", mDishList);
  					mIntent.setClass(MainScreen.this, FoodOrderView.class);
  					mIntent.putExtra("index", 0);
  					startActivityForResult(mIntent, VIEW_ACTIVITY_REQUEST_CODE);
  					break;
  				case 2:
  					//登录
  					mIntent.setClass(MainScreen.this, LoginOrRegister.class);
  					startActivityForResult(mIntent, LOGIN_ACTIVITY_REQUEST_CODE);
  					break;
  				case 3:
  					//帮助
  					mIntent.setClass(MainScreen.this, SCOSHelper.class);
  					startActivityForResult(mIntent, HELP_ACTIVITY_REQUEST_CODE);
  					break;
  				default:
  					break;
  				}
  				//Toast.makeText(getApplication(), "" + position, Toast.LENGTH_SHORT).show();
  		    }
  		});
	}

}
