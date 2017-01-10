package tedxperiments.math.entrenamente;

import java.util.Calendar;


public class DataSubject {

	private String userID;
	private String userName;
	private String userEmail;
	private Calendar userBirth;
	private String userGender;
	private String userStudies;
	private String userDex;
	private int askinlevel;
	//private int userCantChidren;
	//private int userOrderChildren;

	public void setuserID(String UID) {
		userID = UID;
	}

	public String getUserID() {
		
		return userID;
		
	}
	
	public void setuserName(String N) {
		userName = N;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserEmail(String Em) {
		userEmail = Em;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserBirth(Calendar Birth) {
		userBirth = Birth;
	}

	public Calendar getUserBirth() {
		return userBirth;
	}

	public void setUserGender(String Gen) {
		userGender = Gen;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserStudies(String Est) {
		userStudies = Est;
	}

	public String getUserStudies() {
		return userStudies;
	}

	public void setUserDex(String Dex) {
		userDex = Dex;
	}

	public String getUserDex() {
		return userDex;
	}

	//public void setUserCantChidren(int CChil) {
	//	userCantChidren = CChil;
	//}

	//public int getUserCantChidren() {
	//	return userCantChidren;
	//}

	//public void setUserOrderChildren(int OChil) {
	//	userOrderChildren = OChil;
	//}

	//public int getUserOrderChildren() {
	//	return userOrderChildren;
	//}

}
