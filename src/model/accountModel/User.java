package model.accountModel;

public class User {

	public static int userId;
	private String userName;
	private String email;
	private String password;
	private String oldPassword;
	public static int expectedExpense;
	
	public User(String userName, String email, String password, String oldPassword) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.oldPassword = oldPassword;
	}
	
	public User(String userName, String email, String oldPassword) {
		super();
		this.userName = userName;
		this.email = email;
		this.oldPassword = oldPassword;
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
