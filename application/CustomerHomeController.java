package application;
import Entities.*;
import WithDataBaseLayer.UserDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;


public class CustomerHomeController extends SceneSwitchController{
	
	private User user;
	private String destination;
	UserDB userManager = new UserDB();
	
    @FXML
    private Button cartBtn;

    @FXML
    private Button editProfileBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button viewBooksBtn;

    @FXML
    void editProfile(ActionEvent event) {
    	destination= "ProfileScene.fxml";
    	changeScene(event, destination);
    }

    @FXML
    void shoppingCart(ActionEvent event) {
    	destination= "CartScene.fxml";
    	changeScene(event, destination);
    }

    @FXML
    void viewBooks(ActionEvent event) {
    	destination= "BooksScene.fxml";
    	changeScene(event, destination);
    }
    
    @FXML
    void logOut(ActionEvent event) {
    	destination= "WelcomeScene.fxml";
    	userManager.logOut(user);
    	changeScene(event, destination);

    }
	
    public void initData(User user){
    	this.user = user;
    	System.out.println("In home of user " + user.getUser_name() + "p: " + user.getPassword());
    }
    
    @Override
    public void initController(FXMLLoader loader){
    	if(destination.equals("ProfileScene.fxml")) {
            ProfileController controller = loader.getController();
            controller.initData(this.user);
    	}
    	else if(destination.equals("CartScene.fxml")) {
            CartController controller = loader.getController();
            controller.initData(this.user);
    	}
    	else if(destination.equals("BooksScene.fxml")) {
            BooksController controller = loader.getController();
            controller.initData(this.user);
    	}
    }
    	
    
    

}
