package controllers;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;

import connectivity.ConnectionClass;
import helper.Products;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class userPageController implements Initializable{

    @FXML
    private Button btnSignout;

    @FXML
    void btnCartigories(ActionEvent event) {
  
    	try{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/messagePage.fxml"));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage ();
    		stage.initStyle(StageStyle.UNDECORATED);
    		stage.setTitle("Message Page");
    		stage.setScene(new Scene(root1));
    		
    		//drag it here
	        root1.setOnMousePressed(events -> {
	            x = events.getSceneX();
	            y = events.getSceneY();
	        });
	        root1.setOnMouseDragged(events -> {

	            stage.setX(events.getScreenX() - x);
	            stage.setY(events.getScreenY() - y);

	        });
	        
    		stage.show();
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }

    @FXML
    void btnDashboard(ActionEvent event) {
    	System.out.println("Ilerioluwa has done it again...");
    }

    @FXML
    void btnManageUsers(ActionEvent event) {

    }
    
    @FXML
    private Label labUsername;


    
    @FXML
    void btnPos(ActionEvent event) {

    	try{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/posPage.fxml"));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Scene sceneXX = new Scene(root1);
    		Stage stage = new Stage ();
    		stage.initStyle(StageStyle.UNDECORATED);
    		stage.setTitle("POS Page");
    		stage.setScene(sceneXX);
    		
    	
    		
    		//drag it here
	        root1.setOnMousePressed(events -> {
	            x = events.getSceneX();
	            y = events.getSceneY();
	        });
	        root1.setOnMouseDragged(events -> {

	            stage.setX(events.getScreenX() - x);
	            stage.setY(events.getScreenY() - y);

	        });
	        
    		stage.show();
    		
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }

    @FXML
    void btnProduct(ActionEvent event) {

    	try{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/productListPage.fxml"));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage ();
    		stage.initStyle(StageStyle.UNDECORATED);
    		stage.setTitle("POS Page");
    		stage.setScene(new Scene(root1));
    		
    		//drag it here
	        root1.setOnMousePressed(events -> {
	            x = events.getSceneX();
	            y = events.getSceneY();
	        });
	        root1.setOnMouseDragged(events -> {

	            stage.setX(events.getScreenX() - x);
	            stage.setY(events.getScreenY() - y);

	        });
    		
    		stage.show();
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }

    @FXML
    void btnReports(ActionEvent event) {
    	
    	setCaller("reports");

    	try{
    		
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/salesPage.fxml"));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage ();
    		stage.initStyle(StageStyle.UNDECORATED);
    		stage.setTitle("POS Page");
    		stage.setScene(new Scene(root1));
    		
    		//drag it here
	        root1.setOnMousePressed(events -> {
	            x = events.getSceneX();
	            y = events.getSceneY();
	        });
	        root1.setOnMouseDragged(events -> {

	            stage.setX(events.getScreenX() - x);
	            stage.setY(events.getScreenY() - y);

	        });
    		
    		stage.show();
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
    
    static String caller;

    public static String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		userPageController.caller = caller;
	}

	@FXML
    void btnSales(ActionEvent event) {

    	try{
    		setCaller("salesPage");
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/salesPage.fxml"));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage ();
    		stage.initStyle(StageStyle.UNDECORATED);
    		stage.setTitle("POS Page");
    		stage.setScene(new Scene(root1));
    		
    		//drag it here
	        root1.setOnMousePressed(events -> {
	            x = events.getSceneX();
	            y = events.getSceneY();
	        });
	        root1.setOnMouseDragged(events -> {

	            stage.setX(events.getScreenX() - x);
	            stage.setY(events.getScreenY() - y);

	        });
    		
    		stage.show();
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }

    @FXML
    void btnSettings(ActionEvent event) {

    }

    @FXML
    void btnSupportLink(ActionEvent event) {

    }

    @FXML
    void btnAddCashier(ActionEvent event) {

    	try{
    		setCaller("registerUserPage");
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/registerUserPage.fxml"));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage ();
    		stage.initStyle(StageStyle.UNDECORATED);
    		stage.setTitle("Register Page");
    		stage.setScene(new Scene(root1));
    		
    		//drag it here
	        root1.setOnMousePressed(events -> {
	            x = events.getSceneX();
	            y = events.getSceneY();
	        });
	        root1.setOnMouseDragged(events -> {

	            stage.setX(events.getScreenX() - x);
	            stage.setY(events.getScreenY() - y);

	        });
    		
    		stage.show();
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
    @FXML
    void close(MouseEvent event) {
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    private TableView<Products> lowEpiryDateTable;

    @FXML
    private TableColumn<Products, String> colProductNameExpiry;

    @FXML
    private TableColumn<Products, String> colDaysLeftExpiry;


    double x,y;
    @FXML
    void btnUpload(ActionEvent event) {

    	try{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/uploadPage.fxml"));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage ();
    		stage.initStyle(StageStyle.UNDECORATED);
    		stage.setTitle("POS Page");
    		stage.setScene(new Scene(root1));
    		
    		//drag it here
	        root1.setOnMousePressed(events -> {
	            x = events.getSceneX();
	            y = events.getSceneY();
	        });
	        root1.setOnMouseDragged(events -> {

	            stage.setX(events.getScreenX() - x);
	            stage.setY(events.getScreenY() - y);

	        });
    		
    		stage.show();
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
    
    
    @FXML
    private Label textfieldTodaySale;

    @FXML
    private Label textfieldInStock;

    @FXML
    private Label textfieldManageUsers;

    @FXML
    private TableView<Products> lowStockTable;

    @FXML
    private BarChart<?, ?> graphMonthly;

    @FXML
    private LineChart<?, ?> graphItem;

    @FXML
    private Label dateLabel;
    
    @FXML
    private ImageView profileImg;
    


    @FXML
    void btnInStockAction(ActionEvent event) {

    	try{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/productListPage.fxml"));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage ();
    		stage.initStyle(StageStyle.UNDECORATED);
    		stage.setTitle("POS Page");
    		stage.setScene(new Scene(root1));
    		
    		//drag it here
	        root1.setOnMousePressed(events -> {
	            x = events.getSceneX();
	            y = events.getSceneY();
	        });
	        root1.setOnMouseDragged(events -> {

	            stage.setX(events.getScreenX() - x);
	            stage.setY(events.getScreenY() - y);

	        });
    		
    		stage.show();
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }


    @FXML
    void btnManageUsersAction(ActionEvent event) {

    }

    @FXML
    void btnOpenAPOSAction(ActionEvent event) {
    	try{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/posPage.fxml"));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage ();
    		stage.initStyle(StageStyle.UNDECORATED);
    		stage.setTitle("POS Page");
    		stage.setScene(new Scene(root1));
    		
    		//drag it here
	        root1.setOnMousePressed(events -> {
	            x = events.getSceneX();
	            y = events.getSceneY();
	        });
	        root1.setOnMouseDragged(events -> {

	            stage.setX(events.getScreenX() - x);
	            stage.setY(events.getScreenY() - y);

	        });
    		
    		stage.show();
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }


    @FXML
    void btnTodaySaleAction(ActionEvent event) {
    	
    }
    @FXML
    private TableColumn<Products, String> collAlertItemName;

    @FXML
    private TableColumn<Products, String> colAlertItemCurrentStock;

    @FXML
    private Button buttonSalesPage;

    @FXML
    private Button buttonReportPage;

    @FXML
    private Button buttonMessagePage;

    @FXML
    private Button buttonAddCashierPage;


    String cap;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		String uName = loginPageController.getName();
		
//		profileImg.setImage(new Image("file:cart2E.png"));
		
		
		
		
		getNumberOfItemsInStock();
		getTodaysSales();
		showNumberOfUsers();
		showLowProducts();
		loadGraphForSales();
		loadExpiryDateTable();
//		labUsername.setText("Software Tester");
//		labUsername.setText(loginPageController.getName());
		
		cap = uName.substring(0,1).toUpperCase()+uName.substring(1);
		labUsername.setText(cap);
		if(labUsername.getText().toString().equalsIgnoreCase("admin")){
//			System.out.println("Is Admin");
			System.out.println(loginPageController.getName());
			showButtons();
					
		}
		
		setProfileImage(cap);
		
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.millis(1),ae -> displayTime()));
				timeline.setCycleCount(Timeline.INDEFINITE);
			
				timeline.play();
				
			

	}
	
	public void setProfileImage(String username){
		try{
	    	
	    	ConnectionClass cc = new ConnectionClass();
			con = cc.getConnection();

			InputStream image;
			try {
				String query4 = "SELECT * FROM uandp WHERE username =? ";
				PreparedStatement pst4 = con.prepareStatement(query4);
	            pst4.setString(1, username);
	            ResultSet rs = pst4.executeQuery();
				while(rs.next()){
					image = rs.getBinaryStream("profileImage");
					
					if(image !=null && image.available()>1){
						System.out.println("Image Available");
						Image imge = new Image(image);				

//						Image scaled = scale(imge, 350, 500, true);
						profileImg.setImage(imge);
						
					}
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
	    	}catch(Exception ee){
	    		System.out.println(ee.getMessage());
	    	}
	}

	Connection con;
	int total = 0 ;
    
	public void showButtons(){
		
		buttonSalesPage.setVisible(true);
		buttonReportPage.setVisible(true);
		buttonAddCashierPage.setVisible(true);
		

		Platform.runLater(() -> 
		buttonSalesPage.setVisible(true));
		
		Platform.runLater(() -> 
		buttonReportPage.setVisible(true));
		
		Platform.runLater(() -> 
		buttonMessagePage.setVisible(true));
		
		Platform.runLater(() -> 
		buttonAddCashierPage.setVisible(true));
		
		
		/**
		 * 
		buttonSalesPage.setVisible(true);
		buttonReportPage.setVisible(true);
		buttonMessagePage.setVisible(true);
		buttonAddCashierPage.setVisible(true);
		
		**/
	}
	//////////////////////////////////////////////////////////////////////get Number Of Items in Stock...////////////////////////////////////////
	private void getNumberOfItemsInStock(){
    	ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();
		
		try{
			Statement myStat2 = con.createStatement();
			ResultSet ds2 = myStat2.executeQuery("SELECT SUM(quantity) FROM generalproductstable");
			while(ds2.next()){
				ds2.last();
				total = ds2.getInt("SUM(quantity)");
				}
			
			textfieldInStock.setText(Integer.toString(total));
		
			} catch (Exception e) {
				System.out.println(e.getMessage());
				}
    	}
	
	
	////////////////////////////////////////////////////////////method to display date and time at the top of the stage.
	
	int xxx;
		private void displayTime(){
			DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String sss = dateformat.format(date);
			dateLabel.setText(sss);
			}

		//////////////////////////////////////////////get today's sales...//////////////////////////////////////////////
		
		private void getTodaysSales(){
			String sqlxx = "SELECT SUM(quantity) FROM salestable WHERE date = ? ";
			
			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String sss = dateformat.format(date);
			
			try{
			PreparedStatement pst4 = con.prepareStatement(sqlxx);
            pst4.setString(1, sss);
            ResultSet rs4 = pst4.executeQuery();
            while (rs4.next()){
            xxx = rs4.getInt("SUM(quantity)");
            }
            pst4.execute();
            pst4.close();
            rs4.close();
            
                textfieldTodaySale.setText(Integer.toString(xxx));
			}catch (SQLException sqlE){
				System.out.println(sqlE);
			}
            
		}
		
		//////////////////////////////////////////////////////////////////////get Number Of USERS...////////////////////////////////////////
		private void showNumberOfUsers(){
			
			ConnectionClass cc = new ConnectionClass();
			con = cc.getConnection();
			
			try{
				Statement myStat2 = con.createStatement();
				ResultSet ds2 = myStat2.executeQuery("SELECT COUNT(*) AS 'Count' FROM uandp");
				while(ds2.next()){
					ds2.last();
					total = ds2.getInt("Count");
					}
				
				textfieldManageUsers.setText(Integer.toString(total));
			
				} catch (Exception e) {
					System.out.println(e.getMessage());
					}
		}

		ObservableList<Products> oList = FXCollections.observableArrayList();
		
		
		///////////////////////////////////////////////////////////////////////Show Low  in quantity products in table....///////////////////////////////////
		public void showLowProducts(){
			
			ConnectionClass cc = new ConnectionClass();
			con = cc.getConnection();
			
			try {
				ResultSet rs = con.createStatement().executeQuery("select * from generalproductstable WHERE quantity<=lowStockLevel");
				while(rs.next()){
					oList.add(new Products(rs.getString("productName"), rs.getString("quantity")));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			
			try{			
			collAlertItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
			colAlertItemCurrentStock.setCellValueFactory(new PropertyValueFactory<>("quantity"));
					
			}catch(Exception ee){
				System.out.println(ee.getMessage());
			}
			
			lowStockTable.setItems(oList);
		}
		
		
		////////////////////////////////////////////////////////Load Graph for Sales../////////////////////////
		
		@SuppressWarnings("unchecked")
		public void loadGraphForSales(){
			
			graphMonthly.getXAxis().setLabel("Item Name");
			graphMonthly.getYAxis().setLabel("Quantity");
			
			
			
			@SuppressWarnings("rawtypes")
			XYChart.Series dataSeries1 = new XYChart.Series();		
			ConnectionClass cc = new ConnectionClass();
			con = cc.getConnection();

			
			try {
				ResultSet rs = con.createStatement().executeQuery("SELECT productName, SUM(quantity) AS qty FROM salestable GROUP BY productName LIMIT 10");
				while(rs.next()){
					dataSeries1.getData().add(new XYChart.Data<String, Integer>(rs.getString("productName").substring(0, Math.min(rs.getString("productName").length(), 10))+"...", Integer.parseInt(rs.getString("qty"))));
//					dataSeries1.getData().add(new XYChart.Data<String, Integer>(rs.getString("productName"), Integer.parseInt(rs.getString("qty"))));
//					.substring(0, Math.min(itemname.length(), 15))+"..."
//					oList.add(new Products(rs.getString("productName"), rs.getString("quantity")));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			graphMonthly.getData().add(dataSeries1);
			
			}
		
		
		////////////////////////////////////////////////////////Load Expiry date table../////////////////////////
		

		ObservableList<Products> oListExp = FXCollections.observableArrayList();
		private void loadExpiryDateTable(){
			ConnectionClass cc = new ConnectionClass();
			con = cc.getConnection();
			
			try {
				ResultSet rs = con.createStatement().executeQuery("select *, SUM(quantity) AS qty from productstable GROUP BY productName");
				while(rs.next()){
					Long remaingingDate = ChronoUnit.DAYS.between(LocalDate.now(),LocalDate.parse(rs.getString("expiryDate")));
					if(remaingingDate<=15){
					oListExp.add(new Products(rs.getString("productName"), Long.toString(remaingingDate)));
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			
			try{			
			colProductNameExpiry.setCellValueFactory(new PropertyValueFactory<>("name"));
			colDaysLeftExpiry.setCellValueFactory(new PropertyValueFactory<>("quantity"));
					
			}catch(Exception ee){
				System.out.println(ee.getMessage());
			}
			
			lowEpiryDateTable.setItems(oListExp);
		}
			
		
		

}
