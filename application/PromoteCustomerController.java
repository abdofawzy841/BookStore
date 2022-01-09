package application;

import Entities.*;
import WithDataBaseLayer.ManagerDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class PromoteCustomerController extends SceneSwitchController {

	private User user;
	private String destination;
	ManagerDB DBManager = new ManagerDB();

    @FXML
    private Text demoteSucc;

    @FXML
    private Text error;

    @FXML
    private Button homeBtn;

    @FXML
    private Text promoteSucc;

    @FXML
    private TextField userName;

    @FXML
    void demote(ActionEvent event) {
		error.setVisible(false);
		demoteSucc.setVisible(false);
		promoteSucc.setVisible(false);
    	if(DBManager.setCustomer(userName.getText())) {
    		demoteSucc.setVisible(true);
    		return;
    	}
		error.setVisible(true);


    }

    @FXML
    void promote(ActionEvent event) {
		error.setVisible(false);
		demoteSucc.setVisible(false);
		promoteSucc.setVisible(false);
    	if(DBManager.setManager(userName.getText())) {
    		promoteSucc.setVisible(true);
    		return;
    	}

		error.setVisible(true);

    }

	@FXML
	void returnToHome(ActionEvent event) {
		destination = "ManagerHomeScene.fxml";
		changeScene(event, destination);

	}

	public void initData(User user) {
		this.user = user;
	}

	@Override
	public void initController(FXMLLoader loader) {
		if (destination.equals("ManagerHomeScene.fxml")) {
			ManagerHomeController controller = loader.getController();
			controller.initData(this.user);
		}
	}

}
