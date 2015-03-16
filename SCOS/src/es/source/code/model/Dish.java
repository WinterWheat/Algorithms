package es.source.code.model;

import java.io.Serializable;

public class Dish implements Serializable {

	/**
	 * 可序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private int mKind;			//菜的类别,冷菜,热菜,海鲜,饮料
	private String mName;
	private int mImgSrc;
	private int mPrice;
	private boolean isOrder;//是否下单
	private int mID; //菜的ID;
	private int mCount = 0;//菜品的个数,默认为0
	
	//private String remark //备注
	public Dish(int mKind, String mName,int mImgSrc
			, int mPrice, boolean isOrder,int id) {
		super();
		this.mKind =mKind;
		this.mName = mName;
		this.mImgSrc = mImgSrc;
		this.mPrice = mPrice;
		this.isOrder = isOrder;
		this.mID = id;
	}
	
	//返回菜的ID
	public int getID(){
		return mID;
	}
	
	public String toString() {
        return mName+":"+mPrice+" state:"+isOrder;
    }
	
	/**
	 * @param object
	 * @return
	 * @see java.lang.String#equals(java.lang.Object)
	 */
	public boolean equals(String name) {
		return mName.equals(name);
	}
	
	public boolean equals(Dish object) {
		return mName.equals(object.getName());
	}
	
	
	
	/**
	 * @return the mKind
	 */
	public int getKind() {
		return mKind;
	}

	/**
	 * @param mKind the mKind to set
	 */
	public void setKind(int mKind) {
		this.mKind = mKind;
	}
	
	
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	public int getPrice() {
		return mPrice;
	}
	public void setPrice(int mPrice) {
		this.mPrice = mPrice;
	}
	public boolean isOrder() {
		return isOrder;
	}
	public void setOrder(boolean isOrder) {
		this.isOrder = isOrder;
	}


	public int getImgSrc() {
		return mImgSrc;
	}




	public void setImgSrc(int mImgSrc) {
		this.mImgSrc = mImgSrc;
	}

	/**
	 * @return the mCount
	 */
	public int getmCount() {
		return mCount;
	}

	/**
	 * @param mCount the mCount to set
	 */
	public void setmCount(int mCount) {
		this.mCount = mCount;
	}
	
	
	
}
