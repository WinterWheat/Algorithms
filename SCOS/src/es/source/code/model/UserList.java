package es.source.code.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;
import es.source.code.activity.R;

@SuppressLint("Recycle") public class UserList implements Serializable {

	/**
	 * ��д������Դ,������,�Ǻ�
	 */
	private static final long serialVersionUID = 1L;

	//transient private Resources mResources;//������,�������л�,transient��
	
	private List<Dish> mDishList;  //��Ʒ��������,�������ݻ���Ҫ
	
	final private int variety = 4; //�˵������� 
	//private int orderCount=0;
	//private int orderPrices=0;//�˵��ܼ۸�
	public UserList(Resources resource) {
		mDishList = new ArrayList<Dish>();
		//��ȡ��Դ,string,,int,resource
		int id=0;
		for(int i=0; i<variety; i++){
			//��Ӧ����IDΪ˳������,���Կ�����+i�ķ������ҵ�����
			String[] dishtmp = resource.getStringArray(R.array.cold_dish_name + i);
			int[] prtmp = resource.	getIntArray(R.array.cold_dish_price + i);
			TypedArray imgtmp = resource.obtainTypedArray(R.array.cold_dish_img + i);
			for(int j=0; j<dishtmp.length; j++){
				//ÿһ��Dish����Ҫ����5�����������й���
				//�ֱ���    �˵����,����,ͼƬID,�۸�,�Ͳ˵�ID
				mDishList.add(new Dish(i, dishtmp[j],
						imgtmp.getResourceId(j, 0), prtmp[j], false ,id++));
			}
		}
		
	}
	/**
	 * @return the mDishList,
	 * 
	 */
	public List<Dish> getDishList() {
		return mDishList;
	}
	
	
	/**
	 * @param mDishList the mDishList to set
	 */
	public void setDishList(List<Dish> mDishList) {
		this.mDishList = mDishList;
	}
	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return mDishList.size();
	}
	
	public int getOrderCount(){
		int count=0;
		for(int i=0, size=mDishList.size(); i<size; i++)
			if(mDishList.get(i).isOrder())
				count++;
		return count;
	}
	
	public int getVariety(){
		return variety;
	}

	/**
	 * @return the totalprices
	 * ��->�۸�
	 */
	public int getOrderPrices() {
		int Prices=0;
		for(int i=0, size=mDishList.size(); i<size; i++)
			if(mDishList.get(i).isOrder())
				Prices += mDishList.get(i).getPrice();
		return Prices;
	}
	public void addDish(Dish dish){
		for(int i=0, size=mDishList.size(); i<size; i++){
			if(mDishList.get(i).equals(dish)) //equals��Ҫ��д
				mDishList.get(i).setOrder(true);//�µ�
		}
	}
	
	public void remove(Dish dish){
		for(int i=0, size=mDishList.size(); i<size; i++){
			if(mDishList.get(i).equals(dish)) //equals��Ҫ��д
				mDishList.get(i).setOrder(false);//ȥ����
		}
	}
	public void addDish(int id) {
		mDishList.get(id).setOrder(true);
	}
	
	public void remove(int id){
		mDishList.get(id).setOrder(false);
	}
	
	public void setReptory(RepertoryList repL){
		//��ȡ�������
		int[] rep = repL.getRepertory();
		for(int i=0,size=mDishList.size(); i<size; i++){
			mDishList.get(i).setmCount(rep[i]);
		}
	}
	
	public void addNewDish(UpdataDish upDish){
		//���¿��
		Dish d = new Dish(upDish.getmKind()
				, upDish.getmName()
				, 0x7f02008a
				, upDish.getmPrice()
				, false
				, mDishList.size());
		//�����
		mDishList.add(d);
	}
	
}
