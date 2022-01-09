package application;

import java.util.ArrayList;
import java.util.regex.Pattern;

import Entities.*;
import WithDataBaseLayer.ManagerDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddBookController extends SceneSwitchController {
	private User user;
	private String destination;
	private ArrayList<Tuple> categoryList;
	private ArrayList<Tuple> publisherList;
	private ArrayList<Tuple> authorList;
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	ManagerDB DBManager = new ManagerDB();

	@FXML
	private TextField ISBN;

	@FXML
	private Button addBookBtn;

	@FXML
	private ChoiceBox<String> author;

	@FXML
	private ChoiceBox<String> category;

	@FXML
	private TextField minQuantity;

	@FXML
	private TextField price;

	@FXML
	private ChoiceBox<String> publisher;

	@FXML
	private TextField quantity;

	@FXML
	private TextField title;

	@FXML
	private TextField year;

	@FXML
	private Text dataError;

	@FXML
	private Text success;

	@FXML
	private Button homeBtn;

	@FXML
	void returnToHome(ActionEvent event) {
		destination = "ManagerHomeScene.fxml";
		changeScene(event, destination);

	}

	@FXML
	void addBook(ActionEvent event) {
		success.setVisible(false);
		dataError.setVisible(false);
		if (!pattern.matcher(year.getText()).matches() || !pattern.matcher(ISBN.getText()).matches()
				|| !pattern.matcher(price.getText()).matches() || !pattern.matcher(minQuantity.getText()).matches()
				|| !pattern.matcher(quantity.getText()).matches() || year.getText().length() != 4) {
			dataError.setVisible(true);
			return;
		}
		int isbn = Integer.parseInt(ISBN.getText());
		int publisherId = 0;
		for (Tuple p : publisherList) {
			if (p.getName().equals(publisher.getValue()))
				publisherId = p.getId();
		}
		int categoryId = 0;
		for (Tuple c : categoryList) {
			if (c.getName().equals(category.getValue()))
				categoryId = c.getId();
		}
		int authorId = 0;
		for (Tuple a : authorList) {
			if (a.getName().equals(author.getValue()))
				authorId = a.getId();
		}
		double salePrice = Double.parseDouble(price.getText());
		int minQ = Integer.parseInt(minQuantity.getText());
		int q = Integer.parseInt(quantity.getText());
		Book b = new Book(isbn, title.getText(), publisherId, year.getText(), salePrice, categoryId, minQ, q);
		if (!DBManager.addNewBook(b, authorId)) {
			dataError.setText("Error while inserting book");
			dataError.setVisible(true);
			return;
		}
		success.setVisible(true);

	}

	public void initData(User user) {
		this.user = user;
		initCategory();
		initPublisher();
		initAuthors();
	}

	private void initCategory() {
		categoryList = DBManager.getCategories();
		for (Tuple c : categoryList) {
			category.getItems().add(c.getName());
		}
	}

	private void initPublisher() {
		publisherList = DBManager.getPublishers();
		for (Tuple p : publisherList) {
			publisher.getItems().add(p.getName());
		}
	}

	private void initAuthors() {
		authorList = DBManager.getAuthors();
		for (Tuple p : authorList) {
			author.getItems().add(p.getName());
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
