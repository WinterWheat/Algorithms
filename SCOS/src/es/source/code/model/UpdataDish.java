package es.source.code.model;

import java.io.Serializable;

public class UpdataDish implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mKind;			//�˵����,���,�Ȳ�,����,����
	private String mName;
	//private int mImgSrc;
	private int mPrice;
	//private boolean isOrder;//�Ƿ��µ�
	//private int mID; //�˵�ID;
	//private int mCount = 0;//��Ʒ�ĸ���,Ĭ��Ϊ0
	
	public static final int HOT_FOOD = 1;
	public static final int COLD_FOOD = 0;
	public static final int SEA_FOOD = 2;
	public static final int DRINK = 3;

	public int getmKind() {
		return mKind;
	}
	public void setmKind(int mKind) {
		this.mKind = mKind;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public int getmPrice() {
		return mPrice;
	}
	public void setmPrice(int mPrice) {
		this.mPrice = mPrice;
	}
	public UpdataDish(String mName, int mPrice, int mKind) {
		super();
		this.mKind = mKind;
		this.mName = mName;
		this.mPrice = mPrice;
	}
	public UpdataDish() {
		// TODO Auto-generated constructor stub
	}
	
}
