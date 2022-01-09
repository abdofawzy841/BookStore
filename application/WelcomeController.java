package application;

import java.util.regex.Pattern;

import Entities.*;
import WithDataBaseLayer.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class WelcomeController extends SceneSwitchController {
	
	private User user = new User();
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
    private Button logInBtn;

    @FXML
    private Text logInError;

    @FXML
    private PasswordField logInPassword;

    @FXML
    private TextField logInUserName;

    @FXML
    private PasswordField password;

    @FXML
    private TextField shippingAddress;

    @FXML
    private Button signUpBtn;

    @FXML
    private Text signUpError;

    @FXML
    private TextField userName;

    @FXML
    private TextField phone;

	@FXML
	void logIn(ActionEvent event) {
		user.setUser_name(logInUserName.getText());
		user.setPassword(logInPassword.getText());
		// check login and either send him to another scene or display error message
		if (userManager.login(user)) {
			logInError.setText("");
			user = userManager.getUser(user.getUser_name());
			if (user.isManager()) {
				changeScene(event,"ManagerHomeScene.fxml");
			} else {
				changeScene(event,"CustomerHomeScene.fxml");
			}

		} else {
			logInError.setText("Incorrect Credentials");
		}
	}

	@FXML
	void signUp(ActionEvent event) {
		// check sign up result and either send him to another scene or display error
		// message
		if(!NumberPattern.matcher(phone.getText()).matches()) {
			signUpError.setText("Invalid Phone Number");
			return;
		}
		if(!EmailPattern.matcher(email.getText()).matches()) {
			signUpError.setText("Invalid Email Address");
			return;
		}
		if(!password.getText().equals(confirmPassword.getText())) {
			signUpError.setText("Passwords Don't Match");
			return;
		}

		user.setUser_name(userName.getText());
		user.setPassword(password.getText());
		user.setFirst_name(firstName.getText());
		user.setLast_name(lastName.getText());
		user.setShipping_address(shippingAddress.getText());
		user.setEmail_address(email.getText());
		user.setPhone(phone.getText());
		user.setManager(false);

		if(userManager.signUp(user)) {
			signUpError.setText("");
			changeScene(event,"CustomerHomeScene.fxml");
		} else {
			signUpError.setText("Error during Sign up please choose another username or email");
		}
	}
	
    @Override
    public void initController(FXMLLoader loader){
    	 if(user.getUser_name() != null) {
    		 if(!user.isManager()){
                 CustomerHomeController controller = loader.getController();
                 controller.initData(this.user);
            }else{
                 ManagerHomeController controller = loader.getController();
                 controller.initData(this.user);
            }
    	 }
    }
    
	

}
