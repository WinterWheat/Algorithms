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
	 * 重写的数据源,工作量,呵呵
	 */
	private static final long serialVersionUID = 1L;

	//transient private Resources mResources;//上下文,不能序列化,transient掉
	
	private List<Dish> mDishList;  //菜品对象数组,所有数据基本要
	
	final private int variety = 4; //菜的种类数 
	//private int orderCount=0;
	//private int orderPrices=0;//菜单总价格
	public UserList(Resources resource) {
		mDishList = new ArrayList<Dish>();
		//获取资源,string,,int,resource
		int id=0;
		for(int i=0; i<variety; i++){
			//对应数组ID为顺序生成,所以可以用+i的方法来找到数据
			String[] dishtmp = resource.getStringArray(R.array.cold_dish_name + i);
			int[] prtmp = resource.	getIntArray(R.array.cold_dish_price + i);
			TypedArray imgtmp = resource.obtainTypedArray(R.array.cold_dish_img + i);
			for(int j=0; j<dishtmp.length; j++){
				//每一个Dish都需要传入5个参数来进行构造
				//分别是    菜的类别,菜名,图片ID,价格,和菜的ID
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
	 * 买单->价格
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
			if(mDishList.get(i).equals(dish)) //equals需要重写
				mDishList.get(i).setOrder(true);//下单
		}
	}
	
	public void remove(Dish dish){
		for(int i=0, size=mDishList.size(); i<size; i++){
			if(mDishList.get(i).equals(dish)) //equals需要重写
				mDishList.get(i).setOrder(false);//去掉菜
		}
	}
	public void addDish(int id) {
		mDishList.get(id).setOrder(true);
	}
	
	public void remove(int id){
		mDishList.get(id).setOrder(false);
	}
	
	public void setReptory(RepertoryList repL){
		//获取库存数组
		int[] rep = repL.getRepertory();
		for(int i=0,size=mDishList.size(); i<size; i++){
			mDishList.get(i).setmCount(rep[i]);
		}
	}
	
	public void addNewDish(UpdataDish upDish){
		//更新库存
		Dish d = new Dish(upDish.getmKind()
				, upDish.getmName()
				, 0x7f02008a
				, upDish.getmPrice()
				, false
				, mDishList.size());
		//加入菜
		mDishList.add(d);
	}
	
}
