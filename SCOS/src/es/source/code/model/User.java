package es.source.code.model;

import java.io.Serializable;

import android.content.res.Resources;

public class User implements Serializable {

	/**��д����Դ,userӦ����һ��userlist,��userlist����һ��list<dish>
	 * dishΪһ����������,
	 */
	private static final long serialVersionUID = 1L;
	/**Ҫʵ�����л�,���л���Ҫһ��ID
	 * 
	 */
	//�û���,����
	private String userName,password;
	//�Ƿ�����     �Ƿ����û�   �˵�UserList
	private boolean isHide;
	private boolean oldUser;
	//����ʹ��͸������,�����ݲ˵���,����ʹ�����ݿ��ѯ�����ز˵�
	private UserList mUserList;
	
	public User(Resources resource) {
		this.userName = " ";
		this.password = "";
		this.oldUser = false;
		this.isHide = true;
		mUserList = new UserList(resource);//��ʼ���˵�,��Ҫ��Դ
	}
	//�����û�,�����˵�
	public void setUser(User user){
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.oldUser = user.getOldUser();
		this.isHide = user.isHide();
		setUserList(user.getUserList());
	}
	
	public void setUser(String name, String pw, boolean old, boolean hide){
		this.userName = name;
		this.password = pw;
		this.oldUser = old;
		this.isHide = hide;
	}
	
	//��Ӳ�
	public void addDish(Dish dish){
		mUserList.addDish(dish);
	}
	public void addDishById(int id){
		mUserList.addDish(id);
	}
	//ȡ����
	public void removeDish(Dish dish){
		mUserList.remove(dish);
	}
	
	public void removeDishById(int index) {
		mUserList.remove(index);
		
	}
	
	/**
	 * @return the mUserList
	 */
	public UserList getUserList() {
		return mUserList;
	}
	/*/
	 * @param mUserList the mUserList to set
	 */
	public void setUserList(UserList mUserList) {
		this.mUserList.setDishList(mUserList.getDishList());
	}
	
	
	public boolean isHide() {
		return isHide;
	}
	public void setHide(boolean isHide) {
		this.isHide = isHide;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getOldUser() {
		return oldUser;
	}
	public void setOldUser(Boolean oldUser) {
		this.oldUser = oldUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
