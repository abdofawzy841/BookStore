package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitchController {

	public void changeScene(ActionEvent event, String path) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(path));
			Parent newRoot;
			newRoot = loader.load();
			
			initController(loader);

			Scene scene = new Scene(newRoot);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initController(FXMLLoader loader) {

	}

}
