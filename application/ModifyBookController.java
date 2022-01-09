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

public class ModifyBookController extends SceneSwitchController {

	private User user;
	private String destination;
	private ArrayList<Tuple> categoryList;
	private ArrayList<Tuple> publisherList;
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	private int oldISBN;
	ManagerDB DBManager = new ManagerDB();

	@FXML
	private TextField ISBN;

	@FXML
	private ChoiceBox<String> category;

	@FXML
	private Button confirmBtn;

	@FXML
	private Button homeBtn;

	@FXML
	private Button loadBtn;

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
	private Text success;

	@FXML
	private Text isbnError;

	@FXML
	private Text dataError;

	@FXML
	void confirmChanges(ActionEvent event) {
		dataError.setVisible(false);
		success.setVisible(false);
		isbnError.setVisible(false);
		if (!pattern.matcher(year.getText()).matches() || !pattern.matcher(price.getText()).matches()
				|| !pattern.matcher(minQuantity.getText()).matches() || !pattern.matcher(quantity.getText()).matches()
				|| year.getText().length() != 4) {
			dataError.setVisible(true);
			return;
		}
		int isbn = oldISBN;
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
		double salePrice = Double.parseDouble(price.getText());
		int minQ = Integer.parseInt(minQuantity.getText());
		int q = Integer.parseInt(quantity.getText());
		Book b = new Book(isbn, title.getText(), publisherId, year.getText(), salePrice, categoryId, minQ, q);
		if (!DBManager.modifyBook(b)) {
			dataError.setText("Error while modifying book");
			dataError.setVisible(true);
			return;
		}
		success.setVisible(true);
		ISBN.setDisable(false);
		clearFields();

	}

	private void clearFields() {
		ISBN.clear();
		category.setValue("");
		minQuantity.clear();
		price.clear();
		publisher.setValue("");
		quantity.clear();
		title.clear();
		year.clear();

	}

	@FXML
	void loadBook(ActionEvent event) {
		dataError.setVisible(false);
		success.setVisible(false);
		isbnError.setVisible(false);
		if (!pattern.matcher(ISBN.getText()).matches()) {
			isbnError.setText("Invalid ISBN");
			isbnError.setVisible(true);
			return;
		}
		int isbn;
		try {
			isbn = Integer.parseInt(ISBN.getText());
		} catch (Exception e) {
			isbnError.setText("Invalid ISBN");
			isbnError.setVisible(true);
			return;
		}
		Book book = DBManager.getBook(isbn);
		if (book == null) {
			isbnError.setText("ISBN not found");
			isbnError.setVisible(true);
			return;
		}
		isbnError.setVisible(false);
		success.setVisible(false);
		oldISBN = isbn;
		title.setText(book.getTitle());
		String publisherName = "";
		String categoryName = "";
		for (Tuple p : publisherList) {
			if (p.getId() == book.getPublisherId())
				publisherName = p.getName();
		}
		for (Tuple c : categoryList) {
			if (c.getId() == book.getCategory_id())
				categoryName = c.getName();
		}
		publisher.setValue(publisherName);
		category.setValue(categoryName);
		year.setText(book.getPublication_year().substring(0, 4));
		price.setText(Double.toString(book.getPrice()));
		quantity.setText(Integer.toString(book.getCur_quantity()));
		minQuantity.setText(Integer.toString(book.getMin_quantity()));
		ISBN.setDisable(true);
	}

	@FXML
	void returnToHome(ActionEvent event) {
		destination = "ManagerHomeScene.fxml";
		changeScene(event, destination);

	}

	public void initData(User user) {
		this.user = user;
		initCategory();
		initPublisher();
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

	@Override
	public void initController(FXMLLoader loader) {
		if (destination.equals("ManagerHomeScene.fxml")) {
			ManagerHomeController controller = loader.getController();
			controller.initData(this.user);
		}
	}

}
