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
		//settext�еĲ�����ΪSTRING��int,int��ʾ��ԴR.
		dishCountTx.setText(""+mUser.getUserList().getOrderCount());
		totalPriceTx.setText(" "+mUser.getUserList().getOrderPrices());
		submitBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(submitFlag){
					if(((Button)v).getText().equals("����")){
						if(mUser!=null && mUser.getOldUser())
							Toast.makeText(getApplicationContext(), 
								"����,�Ͽͻ�,�����������7���Ż�", Toast.LENGTH_SHORT).show();
						//�����������Ի���,ʹ��AsyncTask��̨����,�ڶ�������Ϊ��ʽ
						ProgressDialog pDailog = new ProgressDialog(v.getContext());
						pDailog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						pDailog.setMessage("���ڽ���,���Ժ�...");
						//���������������Ľ���������ֵ�ɲ����ԸĶ�,���Ƿ�ģ������,
						//
		            	//pDailog.setIndeterminate(false);
		            	pDailog.setCancelable(false);
		            	//��ʾ
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

        actionBar.addTab(actionBar.newTab().setText("���µ���")
                        .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("δ�µ���")
                        .setTabListener(this));
        int currentpage = mIntent.getIntExtra("currentpage", 0);
        actionBar.setSelectedNavigationItem(currentpage);
        mIntent.putExtra("currentpage", 0);
       // actionBar.setCustomView(1);
	}
	private void initData() {
		// TODO Auto-generated method stub
		mIntent = getIntent();
		//��ȡ�û�
		mUser = (User)mIntent.getSerializableExtra("user");
		mViews = new ArrayList<View>();
		//����ʵ���������ļ�
		LayoutInflater mInflater = LayoutInflater.from(this);
		
		for(int i = 0; i < 2; i++){
			//����ListView,�Ӳ�����ʵ����listview
			ListView mListView = (ListView)mInflater.inflate(R.layout.list, null);
			//���ñ���ͼƬ
			//mListView.setBackgroundResource(R.drawable.n10312);
			mListView.setAdapter(new FoodOrderViewListAdapter(this, mUser, i));
			//0�����˵ĵ�,1��û�µĵ�
			mViews.add(mListView);
		}
		
	}
	private void initViewPager() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager)findViewById(R.id.food_order_view_viewpager);
		//���ü���
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	//�����¼�,�޸�actionbar�Ͱ�ť
            	//System.out.println("wefwef");
                actionBar.setSelectedNavigationItem(position);
                switch(position)
    			{
    			case 0:
    				submitBt.setText("����");
    				//�������������˵�����
    				dishCountTx.setText(""+mUser.getUserList().getOrderCount());
    				totalPriceTx.setText(" "+mUser.getUserList().getOrderPrices());
    				break;
    			case 1:
    				submitBt.setText("�ύ����");
    				totalPriceTx.setText(" "+mUser.getUserList().getOrderPrices());
    				
                    //initViewPager();
    				break;
    				default:break;
    			}
            }
            
        });
		//����������
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
		//����viewpager�ķ�ҳ
		mViewPager.setCurrentItem(arg0.getPosition());
	}
	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
