package es.source.code.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import es.source.code.model.User;

//@SuppressWarnings("deprecation")
public class FoodOrderView extends ActionBarActivity implements ActionBar.TabListener{//TabActivity {
	
	
	private ViewPager mViewPager;
	private Intent mIntent;
    private List<View> mViews;
    private Button submitBt;
    private TextView dishCountTx,totalPriceTx;
    private ActionBar actionBar;
    private User mUser;
    private boolean submitFlag=true;
   // private int index=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.food_order_view);
		initData();
		initStatics();
		initViewPager();
		initActionBar();
		setResult(0, mIntent);

		
		
	}
	private void initStatics() {
		// TODO Auto-generated method stub
		dishCountTx = (TextView)findViewById(R.id.dishcount);
		totalPriceTx = (TextView)findViewById(R.id.prices);
		submitBt = (Button)findViewById(R.id.food_order_view_submitbt);
		//settext中的参数分为STRING和int,int表示资源R.
		dishCountTx.setText(""+mUser.getUserList().getOrderCount());
		totalPriceTx.setText(" "+mUser.getUserList().getOrderPrices());
		submitBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(submitFlag){
					if(((Button)v).getText().equals("结账")){
						if(mUser!=null && mUser.getOldUser())
							Toast.makeText(getApplicationContext(), 
								"您好,老客户,本次你可享受7折优惠", Toast.LENGTH_SHORT).show();
						//创建进度条对话框,使用AsyncTask后台更新,第二个参数为样式
						ProgressDialog pDailog = new ProgressDialog(v.getContext());
						pDailog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						pDailog.setMessage("正在结账,请稍后...");
						//这个方法是设置你的进度条进度值可不可以改动,即是否模糊不清,
						//
		            	//pDailog.setIndeterminate(false);
		            	pDailog.setCancelable(false);
		            	//显示
		            	pDailog.show();
						PayAsyncTask payATask = new PayAsyncTask(
								mUser.getUserList().getOrderPrices(), pDailog);
						//Double ;
						payATask.execute(0.0);
						//submitFlag = false;
						//v.setClickable(false);
						v.setEnabled(false);
					}
					else {
						initViewPager();
						//v.setClickable(false);
					}	
				}
					
			}
		});
	}
	private void initActionBar() {  
		// TODO Auto-generated method stub
		actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText("已下单菜")
                        .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("未下单菜")
                        .setTabListener(this));
        int currentpage = mIntent.getIntExtra("currentpage", 0);
        actionBar.setSelectedNavigationItem(currentpage);
        mIntent.putExtra("currentpage", 0);
       // actionBar.setCustomView(1);
	}
	private void initData() {
		// TODO Auto-generated method stub
		mIntent = getIntent();
		//获取用户
		mUser = (User)mIntent.getSerializableExtra("user");
		mViews = new ArrayList<View>();
		//用于实例化布局文件
		LayoutInflater mInflater = LayoutInflater.from(this);
		
		for(int i = 0; i < 2; i++){
			//创建ListView,从布局中实例出listview
			ListView mListView = (ListView)mInflater.inflate(R.layout.list, null);
			//设置背景图片
			//mListView.setBackgroundResource(R.drawable.n10312);
			mListView.setAdapter(new FoodOrderViewListAdapter(this, mUser, i));
			//0是下了的单,1是没下的单
			mViews.add(mListView);
		}
		
	}
	private void initViewPager() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager)findViewById(R.id.food_order_view_viewpager);
		//设置监听
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	//滑动事件,修改actionbar和按钮
            	//System.out.println("wefwef");
                actionBar.setSelectedNavigationItem(position);
                switch(position)
    			{
    			case 0:
    				submitBt.setText("结账");
    				//用这个这个更新账单数据
    				dishCountTx.setText(""+mUser.getUserList().getOrderCount());
    				totalPriceTx.setText(" "+mUser.getUserList().getOrderPrices());
    				break;
    			case 1:
    				submitBt.setText("提交订单");
    				totalPriceTx.setText(" "+mUser.getUserList().getOrderPrices());
    				
                    //initViewPager();
    				break;
    				default:break;
    			}
            }
            
        });
		//设置适配器
		mViewPager.setAdapter(new MyPageradapter());
	}
	
	
	public class MyPageradapter extends PagerAdapter {

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mViews.size();
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
			try{
				((ViewPager)container).addView(mViews.get(position));
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return mViews.get(position);
		}
		
	}


	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		//设置viewpager的翻页
		mViewPager.setCurrentItem(arg0.getPosition());
	}
	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
