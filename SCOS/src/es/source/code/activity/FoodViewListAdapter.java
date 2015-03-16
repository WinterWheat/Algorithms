package es.source.code.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import es.source.code.model.Dish;
import es.source.code.model.User;

public class FoodViewListAdapter implements ListAdapter {

	private Context mContext; 			//上下文
	//private int mIndex;						//第几类菜		
	private List<Dish> mCurrentDish;
	private User mUser;
	private LayoutInflater mInflater;
	//private ViewHolder holder;
	public FoodViewListAdapter(Context mContext, User user, int kind) {
		this.mContext = mContext;	//上下文用来实现控件构造和页面跳转
		mInflater = LayoutInflater.from(mContext);
		mCurrentDish = new ArrayList<Dish>();
		mUser = user;
		//获取全部的菜单
		List<Dish> MainDIsh = mUser.getUserList().getDishList();
		for(int i=0,size=MainDIsh.size(); i<size; i++){
			//将属于当前类的菜取出来
			if(kind == MainDIsh.get(i).getKind())
				mCurrentDish.add(MainDIsh.get(i));
		}
	}

	
	static class ViewHolder{
		TextView nameTx;		//菜名
		TextView priceTx;		//价格
		TextView countTx;		//数量
		Button orderBt;			//点菜按钮
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
		//必须初始化,不能为NUll,
		//使用holder机制,效率提高50%,意为有一半几率不更新
		ViewHolder holder = new ViewHolder();
		if( convertView == null){
			//填充布局,找到控件
			convertView = mInflater.inflate(R.layout.food_view_list, null);
			//这里程序一但创建控件,就不会再刷新创建,因此提高效率
		//	System.out.println(convertView.findViewById(R.id.food_view_nametx)+"");
			holder.nameTx = (TextView)convertView.findViewById(R.id.food_view_nametx);
			holder.priceTx = (TextView)convertView.findViewById(R.id.food_view_pricetx);
			holder.countTx = (TextView)convertView.findViewById(R.id.food_view_counttx);
			holder.orderBt = (Button)convertView.findViewById(R.id.food_view_orderbt);
			//设置标签,一个控件一个标签
			convertView.setTag(holder);
			
			holder.nameTx.setText(mCurrentDish.get(position).getName());
			holder.priceTx.setText(""+mCurrentDish.get(position).getPrice());
			holder.countTx.setText(""+mCurrentDish.get(position).getmCount());
			if(mCurrentDish.get(position).isOrder())
				holder.orderBt.setText("退点");
			else
				holder.orderBt.setText("点菜");
			holder.orderBt.setOnClickListener(new OnClickListener() {
				//按钮响应
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mCurrentDish.get(position).isOrder()){
						//点过的菜则去菜
						mUser.removeDish(mCurrentDish.get(position));
						((Button)v).setText("点菜");
					}
					else{	
						mUser.addDish(mCurrentDish.get(position));
						((Button)v).setText("退点");//加菜
						Toast.makeText(mContext, "点菜成功", Toast.LENGTH_SHORT).show();
					}
				}
			});
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//下一activity显示菜品的详细情况
					Intent intent = new Intent(mContext,FoodDetailed.class);
					intent.putExtra("user",mUser);
					//需要获取菜的ID
					intent.putExtra("currentpage",mCurrentDish.get(position).getID());
					//回到原界面,执行刷新
					mContext.startActivity(intent);
					((Activity) mContext).finish();
				}
			});
		}
		else{
			//如果存在就不需要更新了
			holder  = (ViewHolder)convertView.getTag();
		}
		
		return convertView;
	}
	
	
	/*
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//重新布局,line默认方向是0,水平
		LinearLayout line = new LinearLayout(this.mContext);
		
		TextView name_text = new TextView(this.mContext);
		//设置宽度
		name_text.setEms(8);
		//设置字体大小
		name_text.setTextSize(20);
		//text.setText(mTypedArray.getString(position));
		name_text.setText(mCurrentDish.get(position).getName());
		
		//价格
		TextView price_text = new TextView(this.mContext);
		price_text.setEms(4);
		//从数据源中提取价格
		price_text.setText(""+mCurrentDish.get(position).getPrice());
		
		Button order_btn = new Button(this.mContext);
		//根据订单详情来绘制点菜情况
		if(mCurrentDish.get(position).isOrder())
			order_btn.setText("退点");
		else
			order_btn.setText("点菜");
		order_btn.setOnClickListener(new OnClickListener() {
			//按钮响应
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCurrentDish.get(position).isOrder()){
					//点过的菜则去菜
					mUser.removeDish(mCurrentDish.get(position));
					((Button)v).setText("点菜");
				}
				else{	
					mUser.addDish(mCurrentDish.get(position));
					((Button)v).setText("退点");//加菜
					Toast.makeText(mContext, "点菜成功", Toast.LENGTH_SHORT).show();
				}
			}
		});
		line.addView(name_text);
		line.addView(price_text);
		line.addView(order_btn);
		line.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//下一activity显示菜品的详细情况
				Intent intent = new Intent(mContext,FoodDetailed.class);
				intent.putExtra("user",mUser);
				//需要获取菜的ID
				intent.putExtra("currentpage",mCurrentDish.get(position).getID());
				//回到原界面,执行刷新
				mContext.startActivity(intent);
				((Activity) mContext).finish();
			}
		});
		System.out.println("is begin draw...");
		return line;
	}
	*/

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		//显示的个数,必须在这个范围内,超过则算越界错
		return mCurrentDish.size();
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
