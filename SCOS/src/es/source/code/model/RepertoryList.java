package es.source.code.model;

import java.io.Serializable;
import java.util.List;

public class RepertoryList implements Serializable{

	/**
	 * 序列化,保存订单信息,菜名,数量
	 */
	private static final long serialVersionUID = 1L;
	
	private int dishRepertory[] = new int[46];
	
	//private List<UpdataDish> upDishs;
	
	/**
	 * @return the upDishs
	 */
	/*public List<UpdataDish> getUpDishs() {
		return upDishs;
	}
	*//**
	 * @param upDishs the upDishs to set
	 *//*
	public void setUpDishs(List<UpdataDish> upDishs) {
		this.upDishs = upDishs;
	}*/
	public RepertoryList(){
		//假设库存全为5
		for(int i=0; i<46 ; i++){
			dishRepertory[i] = 5;
		}
	}
	public void setRepertory(int[] repertory){
		dishRepertory = repertory;
	}
	public int[] getRepertory(){
		return dishRepertory;
	}
	public void setRepertoryValue(int value){
		for(int i=0; i<46 ; i++){
			dishRepertory[i] = value;
		}
	}
}
