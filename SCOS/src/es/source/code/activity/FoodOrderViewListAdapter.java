package es.source.code.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import es.source.code.model.Dish;
import es.source.code.model.User;

public class FoodOrderViewListAdapter implements ListAdapter {

	private Context mContext;
	private User mUser;
	//页面标号,0未下,1下
	private int kind;
	//当前页面显示的菜品
	private List<Dish> mCurrentDish;
	
	public FoodOrderViewListAdapter(Context context,
			User user, int k) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mUser = user;
		kind = k;
		mCurrentDish = new ArrayList<Dish>();
		List<Dish> MainDIsh = mUser.getUserList().getDishList();
		for(int i=0,size=MainDIsh.size(); i<size; i++){
			//将属于当前类的菜取出来
			if( MainDIsh.get(i).isOrder())
				mCurrentDish.add(MainDIsh.get(i));
			//else if(1==kind && MainDIsh.get(i).isOrder())
			//	mCurrentDish.add(MainDIsh.get(i));
		}
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCurrentDish.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mCurrentDish.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//菜名,价格,数量
		LinearLayout line = new LinearLayout(this.mContext);
		//0,左右向,默认是0
		//可以用xml实现实例化布局
		TextView name = new TextView(this.mContext);
		TextView price = new TextView(this.mContext);
		TextView listnum = new TextView(this.mContext);
		TextView remark = new TextView(this.mContext);
		line.addView(name);
		line.addView(price);
		line.addView(listnum);
		line.addView(remark);
		name.setText(mCurrentDish.get(position).getName());
		price.setText(""+mCurrentDish.get(position).getPrice());//设置价格
		remark.setText("备注");
		
		listnum.setText("1");
		//设置字体大小
		name.setTextSize(20);
		//设置宽度
		switch(kind)
		{
		case 1:
			name.setEms(6);
			price.setEms(3);
			listnum.setEms(3);
			remark.setEms(2);
			Button cancelDishBt = new Button(this.mContext);
			cancelDishBt.setEms(2);
			cancelDishBt.setText("退点");
			line.addView(cancelDishBt);
			cancelDishBt.setOnClickListener(new OnClickListener() {
				//点击事件,需要更新菜单表,更新价格,
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//v.setVisibility(8);
					/*(Button)*/
					//去掉选菜
					mUser.removeDish(mCurrentDish.get(position));
					v.setVisibility(8);
				}
			});
			break;
		case 0:
			name.setEms(8);
			price.setEms(4);
			listnum.setEms(4);
			break;
			
		}
		
		return line;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return mCurrentDish.isEmpty();
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return true;
	}

}
