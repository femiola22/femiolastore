package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
	private double x, y;
	


	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Parent root = FXMLLoader.load(getClass().getResource("../views/loginPage.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Image image = new Image("file:cart.png"); 
			primaryStage.getIcons().add(image);
			
			primaryStage.initStyle(StageStyle.UNDECORATED);
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			
			//drag it here
	        root.setOnMousePressed(event -> {
	            x = event.getSceneX();
	            y = event.getSceneY();
	        });
	        root.setOnMouseDragged(event -> {

	            primaryStage.setX(event.getScreenX() - x);
	            primaryStage.setY(event.getScreenY() - y);
	            
	        });
			primaryStage.show();
		
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
