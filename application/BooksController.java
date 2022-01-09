package application;
import java.util.ArrayList;
import java.util.regex.Pattern;

import Entities.*;
import WithDataBaseLayer.UserDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class BooksController extends SceneSwitchController{
	
	private User user;
	private String destination;
	private UserDB userManager = new UserDB();
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	private ArrayList<Book> books = new ArrayList<>();

    @FXML
    private VBox booksTable;

    @FXML
    private ToggleGroup SearchType;

    @FXML
    private Button homeBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchString;

    @FXML
    void returnToHome(ActionEvent event) {
    	if(user.isManager()) {
    		destination = "ManagerHomeScene.fxml";
    		changeScene(event,destination);
    	}
    	else {
    		destination = "CustomerHomeScene.fxml";
    		changeScene(event,destination);
    	}
    }
    @FXML
    void search(ActionEvent event) {
    	RadioButton selectedRadioButton = (RadioButton) SearchType.getSelectedToggle();
    	String searchType = selectedRadioButton.getText();
    	switch (searchType) {
    	case "Category":
    		books = userManager.getBooksByCategory(searchString.getText());
    		showBooks();
    		break;
    	case "ISBN":
    		if(!pattern.matcher(searchString.getText()).matches()) {
    			books = new ArrayList<Book>();
    			break;
    		}
    		else {
    			int isbn = Integer.parseInt(searchString.getText());
    			books = userManager.getBookByISBN(isbn);
    			showBooks();
    			break;
    		}
    	case "Title":
    		books = userManager.getBooksByTitle(searchString.getText());
    		showBooks();
    		break;
    	case "Author":
    		books = userManager.getBooksByAuthor(searchString.getText());
    		showBooks();
    		break;
    	case "Publisher":
    		books = userManager.getBooksByPublisher(searchString.getText());
    		showBooks();
    		break;
    	}
    	
    }
	
    private void showBooks() {
		booksTable.getChildren().clear();
    	String cssLayout = "-fx-border-color: red;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-style: dashed;\n" + 
                "-fx-padding : 10 ; \n";
    	booksTable.setStyle(cssLayout); 
    	for(Book book:books) {
    		HBox box = new HBox(20);
    		Text text = new Text("Title : " + book.getTitle());
    		Text price = new Text("price : " + book.getPrice());
    		Button remove = new Button("Add");
    		remove.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					userManager.addBookToCart(user, book.getISBN(), 1);
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

    
	public void initData(User user) {
		this.user = user;
		books = userManager.getBooksByCategory("");
		showBooks();
	}
	
    @Override
    public void initController(FXMLLoader loader){
    	if(destination.equals("ManagerHomeScene.fxml")) {
    		ManagerHomeController controller = loader.getController();
            controller.initData(this.user);
    	}
    	else if(destination.equals("CustomerHomeScene.fxml")) {
    		CustomerHomeController controller = loader.getController();
            controller.initData(this.user);
    	}
    }
	
	
	
}
