package application;

import Entities.*;
import WithDataBaseLayer.UserDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class ManagerHomeController extends SceneSwitchController {

	private User user;
	private String destination;
	private UserDB userManager = new UserDB();

	@FXML
	private Button addBookBtn;

	@FXML
	private Button cartBtn;

	@FXML
	private Button ordersBtn;

	@FXML
	private Button editProfileBtn;

	@FXML
	private Button logOutBtn;

	@FXML
	private Button modifyBookBtn;

	@FXML
	private Button promoteCustomerBtn;

	@FXML
	private Button viewBooksBtn;

	@FXML
	private Button reportsBtn;

	@FXML
	void editProfile(ActionEvent event) {
		destination = "ProfileScene.fxml";
		changeScene(event, destination);
	}

	@FXML
	void shoppingCart(ActionEvent event) {
		destination = "CartScene.fxml";
		changeScene(event, destination);
	}

	@FXML
	void viewBooks(ActionEvent event) {
		destination = "BooksScene.fxml";
		changeScene(event, destination);
	}

	@FXML
	void logOut(ActionEvent event) {
		destination = "WelcomeScene.fxml";
		userManager.logOut(user);
		changeScene(event, destination);
	}

	@FXML
	void addBook(ActionEvent event) {
		destination = "AddBookScene.fxml";
		changeScene(event, destination);
	}

	@FXML
	void orders(ActionEvent event) {
		destination = "OrderScene.fxml";
		changeScene(event, destination);
	}

	@FXML
	void modifyBook(ActionEvent event) {
		destination = "ModifyBookScene.fxml";
		changeScene(event, destination);
	}

	@FXML
	void promoteCustomer(ActionEvent event) {
		destination = "PromoteCustomerScene.fxml";
		changeScene(event, destination);
	}

	@FXML
	void getReports(ActionEvent event) {
		destination = "ReportsScene.fxml";
		changeScene(event, destination);
	}

	public void initData(User user) {
		this.user = user;
	}

	@Override
	public void initController(FXMLLoader loader) {
		if (destination.equals("ProfileScene.fxml")) {
			ProfileController controller = loader.getController();
			controller.initData(this.user);
		} else if (destination.equals("CartScene.fxml")) {
			CartController controller = loader.getController();
			controller.initData(this.user);
		} else if (destination.equals("BooksScene.fxml")) {
			BooksController controller = loader.getController();
			controller.initData(this.user);
		} else if (destination.equals("AddBookScene.fxml")) {
			AddBookController controller = loader.getController();
			controller.initData(this.user);
		} else if (destination.equals("OrderScene.fxml")) {
			OrderController controller = loader.getController();
			controller.initData(this.user);
		} else if (destination.equals("ModifyBookScene.fxml")) {
			ModifyBookController controller = loader.getController();
			controller.initData(this.user);
		} else if (destination.equals("PromoteCustomerScene.fxml")) {
			PromoteCustomerController controller = loader.getController();
			controller.initData(this.user);
		} else if (destination.equals("ReportsScene.fxml")) {
			ReportsController controller = loader.getController();
			controller.initData(this.user);
		}
	}

}
