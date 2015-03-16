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
	//ҳ����,0δ��,1��
	private int kind;
	//��ǰҳ����ʾ�Ĳ�Ʒ
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
			//�����ڵ�ǰ��Ĳ�ȡ����
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
		//����,�۸�,����
		LinearLayout line = new LinearLayout(this.mContext);
		//0,������,Ĭ����0
		//������xmlʵ��ʵ��������
		TextView name = new TextView(this.mContext);
		TextView price = new TextView(this.mContext);
		TextView listnum = new TextView(this.mContext);
		TextView remark = new TextView(this.mContext);
		line.addView(name);
		line.addView(price);
		line.addView(listnum);
		line.addView(remark);
		name.setText(mCurrentDish.get(position).getName());
		price.setText(""+mCurrentDish.get(position).getPrice());//���ü۸�
		remark.setText("��ע");
		
		listnum.setText("1");
		//���������С
		name.setTextSize(20);
		//���ÿ��
		switch(kind)
		{
		case 1:
			name.setEms(6);
			price.setEms(3);
			listnum.setEms(3);
			remark.setEms(2);
			Button cancelDishBt = new Button(this.mContext);
			cancelDishBt.setEms(2);
			cancelDishBt.setText("�˵�");
			line.addView(cancelDishBt);
			cancelDishBt.setOnClickListener(new OnClickListener() {
				//����¼�,��Ҫ���²˵���,���¼۸�,
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//v.setVisibility(8);
					/*(Button)*/
					//ȥ��ѡ��
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
