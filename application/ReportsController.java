package application;

import java.util.ArrayList;

import Entities.*;
import WithDataBaseLayer.ManagerDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportsController extends SceneSwitchController {

	private User user;
	private String destination;
	ArrayList<Tuple> reportData = new ArrayList<Tuple>();
	ManagerDB DBManager = new ManagerDB();
	

	@FXML
	private Button homeBtn;
	
	@FXML
    private TableView<Tuple> table;

    @FXML
    void getTopCustomers(ActionEvent event) {
    	table.getColumns().clear();
    	table.getItems().clear();
    	TableColumn<Tuple, String> user = new TableColumn<Tuple, String>("User Name");
        TableColumn<Tuple, Integer> amount = new TableColumn<Tuple, Integer>("Total Purchase Amount");
        user.setMinWidth(300);
        amount.setMinWidth(200);
        user.setCellValueFactory(new PropertyValueFactory<Tuple,String>("name"));
        amount.setCellValueFactory(new PropertyValueFactory<Tuple,Integer>("id"));
        table.getColumns().add(user);
        table.getColumns().add(amount);
        reportData = DBManager.topCustomers();
        for(Tuple t: reportData) {
        	table.getItems().add(t);
        }
    }

    @FXML
    void getTopSellingBooks(ActionEvent event) {
    	table.getColumns().clear();
    	table.getItems().clear();
    	TableColumn<Tuple, String> ISBN = new TableColumn<Tuple, String>("Book Title");
        TableColumn<Tuple, Integer> amount = new TableColumn<Tuple, Integer>("Number of sales");
        ISBN.setMinWidth(150);
        amount.setMinWidth(150);
        ISBN.setCellValueFactory(new PropertyValueFactory<Tuple,String>("name"));
        amount.setCellValueFactory(new PropertyValueFactory<Tuple,Integer>("id"));
        table.getColumns().add(ISBN);
        table.getColumns().add(amount);
        reportData = DBManager.topSellingBooks();
        for(Tuple t: reportData) {
        	table.getItems().add(t);
        }
    }

    

    @FXML
    void getTotalBookSales(ActionEvent event) {
    	table.getColumns().clear();
    	table.getItems().clear();
    	TableColumn<Tuple, String> ISBN = new TableColumn<Tuple, String>("Book Title");
        TableColumn<Tuple, Integer> amount = new TableColumn<Tuple, Integer>("Number of sales");
        ISBN.setMinWidth(150);
        amount.setMinWidth(150);
        ISBN.setCellValueFactory(new PropertyValueFactory<Tuple,String>("name"));
        amount.setCellValueFactory(new PropertyValueFactory<Tuple,Integer>("id"));
        table.getColumns().add(ISBN);
        table.getColumns().add(amount);
        reportData = DBManager.totalBooksSales();
        for(Tuple t: reportData) {
        	table.getItems().add(t);
        }
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
