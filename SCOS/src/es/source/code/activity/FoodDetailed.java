package es.source.code.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import es.source.code.model.Dish;
import es.source.code.model.User;

public class FoodDetailed extends ActionBarActivity{//Activity {

	
	private ViewPager mViewPager;
	private Intent mIntent;
	//��Щ�Ǳ�Ҫ��,��userlist����ʾ����
	//����װһ�������
	private List<ViewGroup> mViews;
	private User mUser;
	//���ǲ���userlist,ֻ������ʾ
	//private List<Dish> mTestDish;		//����һ��Ա����λ��
	//private List<Dish> mTestDish;
	private List<Dish> mCurrentDish;//= new Dish[3];
	//private List<Dish> mCurrentDish1;
	//private List<Dish> mTestDish;
	//int TestPosition=5;
 	//private int dishkind=0,dishnum=0; //��ʾ��ǰҳ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_detailed);
		initData();
		initViewPager();
		//���÷���
		mIntent.setClass(FoodDetailed.this, MainScreen.class);
		setResult(0, mIntent);
	}
	
	
	private void initViewPager() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager)findViewById(R.id.food_detailed_viewpager);
		mViewPager.setAdapter(new MyDetailAdapter());
		
		int currentpage = mIntent.getIntExtra("currentpage", 0);
		mViewPager.setCurrentItem(currentpage);
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				//mViewPager.setCurrentItem(3);
				//initDetail(mCurrentDish1);
				
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
		//mViewPager.setVisibility(4);
		//mViewPager.layout(0, 0, 0, 0);
	}


	private void initData() {
		// TODO Auto-generated method stub
		mIntent = getIntent();
		//�����״�,��ô���û���ҵ��ͻ᷵��NULL
		mUser = (User)mIntent.getSerializableExtra("user");
		
		//mCurrentDish = new ArrayList<Dish>();
		try{
		mCurrentDish = mUser.getUserList().getDishList();
		}catch(Exception e){
		Log.v("FoodDetailed","����Muser����",e);
		mUser = new User(getResources());
		mCurrentDish = mUser.getUserList().getDishList();
		}
		//mCurrentDish = new ArrayList<Dish>();/*
	//	mCurrentDish.add(mTestDish.get(0));
	//	mCurrentDish.add(mTestDish.get(1));
	//	mCurrentDish1.add(mTestDish.get(7));
		//mCurrentDish1.add(mTestDish.get(8));
		//mCurrentDish1.add(mTestDish.get(9));
		//mCurrentDish.
		
		
		
		mViews = new ArrayList<ViewGroup>();
		//����ʵ���������ļ�,��ʼ��mVIEWS
		initDetail();
	}


	private void initDetail() {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = LayoutInflater.from(this);
		//���в�Ʒҳ������Դ
		//ֻ����һ���տ�,�ȵ�ʹ�õ�ʱ���ٸ���,ʵ�ֶ�̬����
		for(int i=0,size=mCurrentDish.size(); i<size; i++){
			ViewGroup root = (ViewGroup)mInflater.
					inflate(R.layout.food_detailed_list, null);
			mViews.add(root);
		}
		//��ʱ���1s,�����ʱ�ĵط�,�����instantiateItem����,��Ϊ��̬����,�ٶȺܿ�
		//System.out.println(System.currentTimeMillis());
	}


	class MyDetailAdapter extends PagerAdapter {

		//����ʵ��4������,count,����,is:�ж���ʾ,des,����,ins,����
		
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mCurrentDish.size();//����
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			ImageView img = (ImageView)mViews.get(position).findViewById(R.id.food_detailed_img);
			img.setBackgroundDrawable(null);
			//mViews.get(position).destroyDrawingCache();
			((ViewPager)container).removeView(mViews.get(position));
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			View v = mViews.get(position);
			//����ͼƬ
			ImageView img = (ImageView) v.findViewById(R.id.food_detailed_img);
			//��ΪͼƬ����л���,���Լ���ʱ��Ҫ���µ���ͼƬ
			img.setBackgroundResource(mCurrentDish.get(position).getImgSrc());
			
			//���ò���
			//�Ӳ�����ʼ�ж�,���Ϊԭʼ����,����Ҫ�޸�,���򲻱��޸�
			TextView name = (TextView) v.findViewById(R.id.food_detailed_name);
			if(name.getText().equals("dishname")){
				name.setText(mCurrentDish.get(position).getName());
				//���ü۸�
				TextView price = (TextView) v.findViewById(R.id.food_detailed_price);
				price.setText(""+mCurrentDish.get(position).getPrice());
				//���ð�ť
				Button orderBt = (Button) v.findViewById(R.id.food_detailed_orderbt);
				if(mCurrentDish.get(position).isOrder())
					orderBt.setText("�˵�");
				else
					orderBt.setText("���");
				orderBt.setOnClickListener(new MyClickListen(position));
			}
			((ViewPager)container).addView(mViews.get(position),0);
			return mViews.get(position);
		}
	}
	class MyClickListen implements OnClickListener {

		private int index;
		public MyClickListen(int i) {
			// TODO Auto-generated constructor stub
			index = i;
		}
		@Override
		public void onClick(View v) {
			//Button b = (Button)v;
			if(mCurrentDish.get(index).isOrder()){
				//����Ĳ���ȥ��
				mUser.removeDishById(index);
				((Button)v).setText("���");
				Toast.makeText(getApplicationContext(),
						"��˳ɹ�", Toast.LENGTH_SHORT).show();
			}
			else{
				mUser.addDishById(index);
				((Button)v).setText("�˵�");
			}
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
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
			//System.out.println("�ѵ��Ʒ");
			mIntent.putExtra("currentpage", 1 );
			startActivity(mIntent);
			finish();
			break;
		case R.id.view_list:
			//System.out.println("�鿴�˵�");
			mIntent.putExtra("currentpage", 0 );
			startActivity(mIntent);
			finish();
			break;
		case R.id.call_serve:
			//System.out.println("���з���");
			break;
		}
        return true;
	}
	
	
}
