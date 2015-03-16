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

	private Context mContext; 			//������
	//private int mIndex;						//�ڼ����		
	private List<Dish> mCurrentDish;
	private User mUser;
	private LayoutInflater mInflater;
	//private ViewHolder holder;
	public FoodViewListAdapter(Context mContext, User user, int kind) {
		this.mContext = mContext;	//����������ʵ�ֿؼ������ҳ����ת
		mInflater = LayoutInflater.from(mContext);
		mCurrentDish = new ArrayList<Dish>();
		mUser = user;
		//��ȡȫ���Ĳ˵�
		List<Dish> MainDIsh = mUser.getUserList().getDishList();
		for(int i=0,size=MainDIsh.size(); i<size; i++){
			//�����ڵ�ǰ��Ĳ�ȡ����
			if(kind == MainDIsh.get(i).getKind())
				mCurrentDish.add(MainDIsh.get(i));
		}
	}

	
	static class ViewHolder{
		TextView nameTx;		//����
		TextView priceTx;		//�۸�
		TextView countTx;		//����
		Button orderBt;			//��˰�ť
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
		//�����ʼ��,����ΪNUll,
		//ʹ��holder����,Ч�����50%,��Ϊ��һ�뼸�ʲ�����
		ViewHolder holder = new ViewHolder();
		if( convertView == null){
			//��䲼��,�ҵ��ؼ�
			convertView = mInflater.inflate(R.layout.food_view_list, null);
			//�������һ�������ؼ�,�Ͳ�����ˢ�´���,������Ч��
		//	System.out.println(convertView.findViewById(R.id.food_view_nametx)+"");
			holder.nameTx = (TextView)convertView.findViewById(R.id.food_view_nametx);
			holder.priceTx = (TextView)convertView.findViewById(R.id.food_view_pricetx);
			holder.countTx = (TextView)convertView.findViewById(R.id.food_view_counttx);
			holder.orderBt = (Button)convertView.findViewById(R.id.food_view_orderbt);
			//���ñ�ǩ,һ���ؼ�һ����ǩ
			convertView.setTag(holder);
			
			holder.nameTx.setText(mCurrentDish.get(position).getName());
			holder.priceTx.setText(""+mCurrentDish.get(position).getPrice());
			holder.countTx.setText(""+mCurrentDish.get(position).getmCount());
			if(mCurrentDish.get(position).isOrder())
				holder.orderBt.setText("�˵�");
			else
				holder.orderBt.setText("���");
			holder.orderBt.setOnClickListener(new OnClickListener() {
				//��ť��Ӧ
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mCurrentDish.get(position).isOrder()){
						//����Ĳ���ȥ��
						mUser.removeDish(mCurrentDish.get(position));
						((Button)v).setText("���");
					}
					else{	
						mUser.addDish(mCurrentDish.get(position));
						((Button)v).setText("�˵�");//�Ӳ�
						Toast.makeText(mContext, "��˳ɹ�", Toast.LENGTH_SHORT).show();
					}
				}
			});
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//��һactivity��ʾ��Ʒ����ϸ���
					Intent intent = new Intent(mContext,FoodDetailed.class);
					intent.putExtra("user",mUser);
					//��Ҫ��ȡ�˵�ID
					intent.putExtra("currentpage",mCurrentDish.get(position).getID());
					//�ص�ԭ����,ִ��ˢ��
					mContext.startActivity(intent);
					((Activity) mContext).finish();
				}
			});
		}
		else{
			//������ھͲ���Ҫ������
			holder  = (ViewHolder)convertView.getTag();
		}
		
		return convertView;
	}
	
	
	/*
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//���²���,lineĬ�Ϸ�����0,ˮƽ
		LinearLayout line = new LinearLayout(this.mContext);
		
		TextView name_text = new TextView(this.mContext);
		//���ÿ��
		name_text.setEms(8);
		//���������С
		name_text.setTextSize(20);
		//text.setText(mTypedArray.getString(position));
		name_text.setText(mCurrentDish.get(position).getName());
		
		//�۸�
		TextView price_text = new TextView(this.mContext);
		price_text.setEms(4);
		//������Դ����ȡ�۸�
		price_text.setText(""+mCurrentDish.get(position).getPrice());
		
		Button order_btn = new Button(this.mContext);
		//���ݶ������������Ƶ�����
		if(mCurrentDish.get(position).isOrder())
			order_btn.setText("�˵�");
		else
			order_btn.setText("���");
		order_btn.setOnClickListener(new OnClickListener() {
			//��ť��Ӧ
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCurrentDish.get(position).isOrder()){
					//����Ĳ���ȥ��
					mUser.removeDish(mCurrentDish.get(position));
					((Button)v).setText("���");
				}
				else{	
					mUser.addDish(mCurrentDish.get(position));
					((Button)v).setText("�˵�");//�Ӳ�
					Toast.makeText(mContext, "��˳ɹ�", Toast.LENGTH_SHORT).show();
				}
			}
		});
		line.addView(name_text);
		line.addView(price_text);
		line.addView(order_btn);
		line.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//��һactivity��ʾ��Ʒ����ϸ���
				Intent intent = new Intent(mContext,FoodDetailed.class);
				intent.putExtra("user",mUser);
				//��Ҫ��ȡ�˵�ID
				intent.putExtra("currentpage",mCurrentDish.get(position).getID());
				//�ص�ԭ����,ִ��ˢ��
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
		//��ʾ�ĸ���,�����������Χ��,��������Խ���
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
