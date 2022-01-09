package application;

import Entities.*;
import WithDataBaseLayer.UserDB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;
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
import javafx.util.Pair;

public class CartController extends SceneSwitchController {

	private User user;
	private String destination;
	private UserDB userManager = new UserDB();
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	private ArrayList<Pair<Book,Integer>> booksInCart = new ArrayList<>();

	@FXML
	private Text removeError;

	@FXML
	private Text removeSuccess;
	@FXML
	private VBox booksTable;

	@FXML
	private TextField cardNo;

	@FXML
	private Text infoError;

	@FXML
	private TextField month;

	@FXML
	private Text price;

	@FXML
	private Text success;

	@FXML
	private TextField year;

	@FXML
	private Text saleError;

	@FXML
	void confirmOrder(ActionEvent event) {
		removeError.setVisible(false);
		removeSuccess.setVisible(false);
		infoError.setVisible(false);
		success.setVisible(false);
		saleError.setVisible(false);

		if (checkCardInfo()) {
			int m = Integer.parseInt(month.getText());
			int y = Integer.parseInt(year.getText());
			LocalDate date = LocalDate.of(y, m, 1);
			boolean saleDone = true;
			for(int i=0;i<booksInCart.size();i++) {
				Book book = booksInCart.get(i).getKey();
				if (!userManager.buyBook(user, book.getISBN(), book.getPrice(), cardNo.getText(), date)) {
					saleError.setVisible(true);
					saleDone = false;
				}

			}
			if (saleDone) {
				success.setVisible(true);
				initData(user);
			} else {
				infoError.setVisible(true);
				initData(user);
			}
		} else {
			infoError.setVisible(true);
		}
	}

	private boolean checkCardInfo() {
		if (!pattern.matcher(cardNo.getText()).matches())
			return false;
		if (!(cardNo.getText().length() == 16))
			return false;
		try {
			int m = Integer.parseInt(month.getText());
			int y = Integer.parseInt(year.getText());
			if (m > 12 || m < 1)
				return false;

			LocalDate date = LocalDate.of(y, m, 1);
			LocalDate today = LocalDate.now();
			if (today.isAfter(date))
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
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
		booksInCart = userManager.viewCartItems(user);
		showCart();
		double totalPrice = userManager.viewCartPrice(user);
		price.setText(Double.toString(totalPrice) + " $");
	}

	private void showCart() {
		booksTable.getChildren().clear();
		String cssLayout = "-fx-border-color: blue;\n" + "-fx-border-insets: 3;\n" + "-fx-border-width: 1;\n"
				+ "-fx-border-style: solid;\n" + "-fx-padding : 10 ; \n";
		for(int i=0;i<booksInCart.size();i++) {
			Book book = booksInCart.get(i).getKey();
			int copies = booksInCart.get(i).getValue();
			HBox box = new HBox(20);
			Text text = new Text("Title : " + book.getTitle());
			Text price = new Text("Price : " + book.getPrice() + " $s");
			Text isbn = new Text("ISBN : " + book.getISBN());
			Text count = new Text("Copies : " + copies);

			Button remove = new Button("Remove");
			remove.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					removeSuccess.setVisible(false);
					removeError.setVisible(false);
					if (userManager.removeItemFromCart(user, book.getISBN())) {
						removeSuccess.setVisible(true);
					} else {
						removeError.setVisible(true);

					}
					initData(user);
				}

			});
			box.getChildren().add(text);
			box.getChildren().add(price);
			box.getChildren().add(isbn);
			box.getChildren().add(count);
			box.getChildren().add(remove);
			box.setAlignment(Pos.CENTER);
			box.setStyle(cssLayout);
			booksTable.getChildren().add(box);
		}

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
