package es.source.code.model;

import java.io.Serializable;

import android.content.res.Resources;

public class User implements Serializable {

	/**重写数据源,user应该有一个userlist,而userlist中有一个list<dish>
	 * dish为一个单独的类,
	 */
	private static final long serialVersionUID = 1L;
	/**要实现序列化,序列化需要一个ID
	 * 
	 */
	//用户名,密码
	private String userName,password;
	//是否隐藏     是否老用户   菜单UserList
	private boolean isHide;
	private boolean oldUser;
	//可以使用透明方法,不传递菜单表,而是使用数据库查询来返回菜单
	private UserList mUserList;
	
	public User(Resources resource) {
		this.userName = " ";
		this.password = "";
		this.oldUser = false;
		this.isHide = true;
		mUserList = new UserList(resource);//初始化菜单,需要资源
	}
	//更新用户,包括菜单
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
	
	//添加菜
	public void addDish(Dish dish){
		mUserList.addDish(dish);
	}
	public void addDishById(int id){
		mUserList.addDish(id);
	}
	//取消菜
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
