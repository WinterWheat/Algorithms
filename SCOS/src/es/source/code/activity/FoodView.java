package es.source.code.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import es.source.code.model.RepertoryList;
import es.source.code.model.UpdataDish;
import es.source.code.model.User;
import es.source.code.service.ServerObserverService;

public class FoodView extends ActionBarActivity implements ActionBar.TabListener {//Activity {

	//数据传递,用Intent回传数据
	private ViewPager mViewPager;
	private ActionBar mActionBar;
	private List<View> mViews;
	private Intent mIntent;
	private User mUser;
	private Intent mServiceIntent;
	
	private ServiceConnection conn= new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.v("onServiceConnected","onServiceConnected");
			//System.out.println("没有调用,为什么");
			serverMSGer = new Messenger(service);
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.v("onServiceDisconnected","onServiceDisconnected");
			serverMSGer = null;
			
		}
	};
	
	//判断是否绑定
	private boolean isBind = false;
	
	private boolean onlyone = true;
	//处理服务器传回数据
	Handler sMessageHandle = new Handler(){
		public void handleMessage(Message msg) {
			if (msg.what == 10) {
				//做你想做的,收到库存信息,开始更新库存
				RepertoryList repL = (RepertoryList) msg.getData().getSerializable("reptory");
				//设置库存
				mUser.getUserList().setReptory(repL);
				/*if(onlyone){// 只跑一次
					List<UpdataDish> updshs = repL.getUpDishs();
					for(int i=0, size=updshs.size(); i<size; i++){
						mUser.getUserList().addNewDish(updshs.get(i));
					}
					onlyone = false;
				}*/
				//开始更新UI
				//mViewPager.invalidate();
				//初始数据,设置Listview控件
				initData();
				//mViewPager.invalidate();
				//设置Viewpager
				initViewPager();
				mViewPager.setCurrentItem(0);
				/*try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(getApplicationContext(), "Service message received",
						Toast.LENGTH_SHORT).show();*/
			}
		};
	};
	Messenger clientMSGer = new Messenger(sMessageHandle);
	//绑定自己的信使,利用服务器的信使来传递消息
	Messenger serverMSGer = null;
	
	//启动绑定
	private void bindServer(){
		mServiceIntent = new Intent(FoodView.this, ServerObserverService.class);
		bindService(mServiceIntent, conn, Context.BIND_AUTO_CREATE);
	}
	
	public void startUpdata(int i_what) {
		// 启动更新
		
		Message m = Message.obtain();
		m.what = i_what;
		m.replyTo = clientMSGer; //发送通过sm,接受则是cm
		//m.obj = ol;
		try {
			serverMSGer.send(m);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_view);
		//首先进入activity就需要绑定,不能在actionbar初始化之后再去绑定,会是数据为空
		if(!isBind){
			bindServer();
			isBind = true;
		}
		//初始数据,设置Listview控件
		initData();
		//设置Viewpager
		initViewPager();
		//设置actionbar
		initActionbar();
		//设置返回
		setResult(0, mIntent);
		
	}
	

	private void initActionbar() {
		// TODO Auto-generated method stub
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mActionBar.addTab(mActionBar.newTab().setText("冷菜")
                        .setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText("热菜")
                        .setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText("海鲜")
                .setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText("饮料")
                .setTabListener(this));
		
		
	}


	private void initViewPager() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager)findViewById(R.id.food_view_viewpager);
		mViewPager.setAdapter(new MyPageAdapter());
		//设置显示哪一页
		//mViewPager.setCurrentItem(2);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				//翻页侦听器
				mActionBar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	private void initData() {
		// TODO Auto-generated method stub
		mIntent = getIntent();
		//获取用户
		mUser = (User)mIntent.getSerializableExtra("user");
		//mStrings = mUserList.getmDishList();
		mViews = new ArrayList<View>();
		//初始化菜谱
		//用于实例化布局文件
		LayoutInflater mInflater = LayoutInflater.from(this);
		
		for(int i=0,size=mUser.getUserList().getVariety(); i <size; i++){
			//创建ListView,从布局中实例出listview
			ListView mListView = (ListView)mInflater.inflate(R.layout.list, null);
			//设置适配器来填充数据
			mListView.setAdapter(new FoodViewListAdapter(this, mUser, i));
			//mListView.
			mViews.add(mListView);
		}
	}


	public class MyPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mViews.size();
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
//			return super.getItemPosition(object);
		}
		
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager)container).removeView(mViews.get(position));
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			((ViewPager)container).addView(mViews.get(position));
			return mViews.get(position);
		}

	}
	
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.food_menu, menu);
			return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			int id = item.getItemId();
			mIntent.setClass(this, FoodOrderView.class);
			mIntent.putExtra("user", mUser);
			switch(id)
			{
			case R.id.have_order:
				//System.out.println("已点菜品");
				mIntent.putExtra("currentpage", 1 );
				startActivity(mIntent);
				finish();
				break;
			case R.id.view_list:
				//System.out.println("查看菜单");
				mIntent.putExtra("currentpage", 0 );
				startActivity(mIntent);
				finish();
				break;
			case R.id.call_serve:
				//System.out.println("呼叫服务");
				break;
			case R.id.start_updata:
				//启动serverobserverservice,发送msg,what为1
				//使用类来完成操作
				//SCOSClient sc = new SCOSClient(getApplicationContext());
				if(isBind){
					startUpdata(1);
					item.setTitle("停止实时更新");
					unbindService(conn);
					isBind = false;
				}
				else{
					startUpdata(0);
					bindServer();
					item.setTitle("开始实时更新");
					isBind = true;
					//bindService(conn);
					//isBind = false;
				}
				
				break;
			default:
				break;
			}
	        return true;
		}
		
		
		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(arg0.getPosition());
			
		}


		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}

	
	
}
