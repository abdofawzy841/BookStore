package application;

import Entities.*;

public class ConfirmOrderController {
	
	private User user;
	
	public void initData(User user) {
		this.user = user;
		System.out.println("I'm " + this.user.getUser_name() + " And I'm in ConfirmOrderController");
	}

}
