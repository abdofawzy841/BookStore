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

public class CartController extends SceneSwitchController {

	private User user;
	private String destination;
	private UserDB userManager = new UserDB();
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	private ArrayList<Book> booksInCart = new ArrayList<>();


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
	private TextField year;

	@FXML
	void confirmOrder(ActionEvent event) {
		if (checkCardInfo()) {
			infoError.setVisible(false);
			int m = Integer.parseInt(month.getText());
			int y = Integer.parseInt(year.getText());
			LocalDate date = LocalDate.of(y, m, 1);
			boolean saleDone = true;
			for(Book book : booksInCart) {
				if(!
				userManager.buyBook(user, book.getISBN(), book.getPrice(), cardNo.getText(), date)) {
					System.out.println("SALE NOT COMPLETE");
					saleDone = false;
				}
	
			}
			if(saleDone) {
				System.out.println("success");
				//TODO
				//show success message
				initData(user);
			}
		}
		else {
			infoError.setVisible(true);
		}
	}

	private boolean checkCardInfo() {
		if(!pattern.matcher(cardNo.getText()).matches())
			return false;
		if(!(cardNo.getText().length() == 16))
			return false;
		try {
			int m = Integer.parseInt(month.getText());
			int y = Integer.parseInt(year.getText());
			if (m > 12 || m < 1)
				return false;

			LocalDate date = LocalDate.of(y, m, 1);
		    LocalDate today = LocalDate.now();
		    if(today.isAfter(date))
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
    	String cssLayout = "-fx-border-color: red;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-style: dashed;\n" + 
                "-fx-padding : 10 ; \n";
    	booksTable.setStyle(cssLayout); 
    	for(Book book:booksInCart) {
    		HBox box = new HBox(20);
    		Text text = new Text("Title : " + book.getTitle());
    		Text price = new Text("price : " + book.getPrice());
    		Button remove = new Button("Remove");
    		remove.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					userManager.removeItemFromCart(user, book.getISBN());
					initData(user);
				}
    			
    		});
    		box.getChildren().add(text);
    		box.getChildren().add(price);
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
