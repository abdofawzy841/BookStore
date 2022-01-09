package application;

import java.util.ArrayList;
import java.util.regex.Pattern;

import Entities.*;
import WithDataBaseLayer.ManagerDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class OrderController extends SceneSwitchController {

	private User user;
	private String destination;
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	private ManagerDB DBManager = new ManagerDB();
	private ArrayList<Order> orders = new ArrayList<>();

	@FXML
	private VBox ordersTable;

	@FXML
	private Button homeBtn;

	@FXML
	private TextField ISBN;

	@FXML
	private Text isbnError;

	@FXML
	private Text placedSuccess;

	@FXML
	private TextField Quantity;

	@FXML
	private Text confirmErr;

	@FXML
	private Text confirmSucc;

	@FXML
	void addOrder(ActionEvent event) {
		isbnError.setVisible(false);
		placedSuccess.setVisible(false);
		confirmErr.setVisible(false);
		confirmSucc.setVisible(false);
		if (!pattern.matcher(ISBN.getText()).matches()) {
			isbnError.setText("Invalid ISBN");
			isbnError.setVisible(true);
			return;
		}
		if (!pattern.matcher(Quantity.getText()).matches() || Quantity.getText().length() > 3) {
			isbnError.setText("Invalid Quantity");
			isbnError.setVisible(true);
			return;
		}
		int isbn;
		int quantity;
		try {
			isbn = Integer.parseInt(ISBN.getText());
			quantity = Integer.parseInt(Quantity.getText());
			if (quantity < 0)
				throw new Exception();
		} catch (Exception e) {
			isbnError.setText("Invalid ISBN");
			isbnError.setVisible(true);
			return;
		}
		if (!DBManager.orderBook(isbn, quantity)) {
			isbnError.setText("ISBN not found");
			isbnError.setVisible(true);
			return;
		}
		placedSuccess.setVisible(true);
		initData(user);

	}

	@FXML
	void returnToHome(ActionEvent event) {
		destination = "ManagerHomeScene.fxml";
		changeScene(event, destination);

	}

	public void initData(User user) {
		this.user = user;
		orders = DBManager.getOrders();
		showOrders();
	}

	private void showOrders() {
		ordersTable.getChildren().clear();
		String cssLayout = "-fx-border-color: blue;\n" + "-fx-border-insets: 3;\n" + "-fx-border-width: 1;\n"
				+ "-fx-border-style: solid;\n" + "-fx-padding : 10 ; \n";
		for (Order order : orders) {
			HBox box = new HBox(20);
			Text isbn = new Text("ISBN : " + order.getISBN());
			Text quan = new Text("Order Quantity : " + order.getNum_copies());
			Button confirm = new Button("Confirm Order");
			confirm.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					isbnError.setVisible(false);
					placedSuccess.setVisible(false);
					confirmErr.setVisible(false);
					confirmSucc.setVisible(false);
					if (DBManager.confirmOrder(order.getId())) {
						confirmSucc.setVisible(true);
					} else {
						confirmErr.setVisible(true);
					}
					initData(user);
				}

			});
			box.getChildren().add(isbn);
			box.getChildren().add(quan);
			box.getChildren().add(confirm);
			box.setAlignment(Pos.CENTER);
			box.setStyle(cssLayout);
			ordersTable.getChildren().add(box);
		}

	}

	@Override
	public void initController(FXMLLoader loader) {
		if (destination.equals("ManagerHomeScene.fxml")) {
			ManagerHomeController controller = loader.getController();
			controller.initData(this.user);
		}
	}

}
