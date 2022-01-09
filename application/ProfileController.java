package application;

import java.util.regex.Pattern;

import Entities.*;
import WithDataBaseLayer.UserDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ProfileController extends SceneSwitchController {

	private User user;
	private String oldUserName;
	private String destination;
	private UserDB userManager = new UserDB();
	private Pattern NumberPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	private Pattern EmailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

	@FXML
	private PasswordField confirmPassword;

	@FXML
	private TextField email;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private PasswordField oldPassword;

	@FXML
	private PasswordField password;

	@FXML
	private Text passwordError;

	@FXML
	private TextField phone;

	@FXML
	private Text success;

	@FXML
	private TextField shippingAddress;

	@FXML
	private TextField userName;

	@FXML
	void updateInfo(ActionEvent event) {
		if (!NumberPattern.matcher(phone.getText()).matches()) {
			passwordError.setText("Invalid Phone Number");
			return;
		}
		if (!EmailPattern.matcher(email.getText()).matches()) {
			passwordError.setText("Invalid Email Address");
			return;
		}
		if (!oldPassword.getText().equals(user.getPassword())) {
			passwordError.setText("Incorrect Password");
			success.setVisible(false);
			return;
		}
		if (!password.getText().equals(confirmPassword.getText())) {
			passwordError.setText("Passwords Don't Match");
			success.setVisible(false);
			return;
		}
		oldPassword.clear();
		oldUserName = user.getUser_name();
		User newUserInfo = new User();
		newUserInfo.setFirst_name(firstName.getText());
		newUserInfo.setLast_name(lastName.getText());
		newUserInfo.setUser_name(userName.getText());
		newUserInfo.setEmail_address(email.getText());
		newUserInfo.setPassword(password.getText());
		newUserInfo.setPhone(phone.getText());
		newUserInfo.setShipping_address(shippingAddress.getText());
		newUserInfo.setManager(user.isManager());
		if (userManager.editUserData(oldUserName, newUserInfo)) {
			success.setVisible(true);
			initData(newUserInfo);
		} else {
			passwordError.setText("Something Went wrong try another username or email");
			success.setVisible(false);
		}

	}

	@FXML
	void returnToHome(ActionEvent event) {
		if (user.isManager()) {
			destination = "ManagerHomeScene.fxml";
			changeScene(event, destination);
		} else {
			destination = "CustomerHomeScene.fxml";
			changeScene(event, destination);
		}
	}

	public void initData(User user) {
		this.user = user;
		oldUserName = user.getUser_name();
		passwordError.setText("");
		firstName.setText(user.getFirst_name());
		lastName.setText(user.getLast_name());
		email.setText(user.getEmail_address());
		password.setText(user.getPassword());
		confirmPassword.setText(user.getPassword());
		userName.setText(user.getUser_name());
		phone.setText(user.getPhone());
		shippingAddress.setText(user.getShipping_address());
		oldPassword.clear();
	}

	@Override
	public void initController(FXMLLoader loader) {
		if (destination.equals("ManagerHomeScene.fxml")) {
			ManagerHomeController controller = loader.getController();
			controller.initData(this.user);
		} else if (destination.equals("CustomerHomeScene.fxml")) {
			CustomerHomeController controller = loader.getController();
			controller.initData(this.user);
		}
	}

}
