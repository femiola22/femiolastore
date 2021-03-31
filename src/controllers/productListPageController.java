package controllers;



import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import connectivity.ConnectionClass;
import helper.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class productListPageController implements Initializable {

    @FXML
    private TextField fieldSearch;

    @FXML
    private TableView<Products> productListTable;

    @FXML
    private TableColumn<Products, String> colProductName;

    @FXML
    private TableColumn<Products, String> colProductQuantity;

    @FXML
    private TableColumn<Products, String> colProductPrice;
    
    @FXML
    private TableColumn<Products, String> colProductSection;

    @FXML
    private ImageView productImage;
    
    @FXML
    void close(MouseEvent event) {
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
    
    
    /////Resize image mock method.../////////////////////////
    
    public Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        return imageView.snapshot(null, null);
    }
    
    String selectedName, selectedprice, selectedQuantity;
    @FXML
    void getSelectedItem(MouseEvent event) {

    	int row = productListTable.getSelectionModel().getSelectedIndex();

    	try{
    	selectedName = colProductName.getCellData(row);
    	selectedprice = colProductPrice.getCellData(row);
    	System.out.println(selectedName +" "+ selectedprice);
    	
    	ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();

		InputStream image;
		try {
			String query4 = "SELECT * FROM productstable WHERE productName =? AND price =? ";
			PreparedStatement pst4 = con.prepareStatement(query4);
            pst4.setString(1, selectedName);
            pst4.setDouble(2, Double.parseDouble(selectedprice));
            ResultSet rs = pst4.executeQuery();
			while(rs.next()){
				image = rs.getBinaryStream("productImg");
				
				if(image !=null && image.available()>1){
					System.out.println("Image Available");
					Image imge = new Image(image);				

					Image scaled = scale(imge, 350, 500, true);
					productImage.setImage(scaled);
//					productImage.setImage(imge);
					
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

    ObservableList<Products> oList = FXCollections.observableArrayList();
    Connection con;
    @FXML
    void searchDBWithlettersInput(KeyEvent event) {
    	
    	////check for if the search field is empty
    	if (fieldSearch.getText().length() < 1){

    		showAllProducts();
    	} 
    	
    	else{
    		
    		//check for if there is at least one character in the search field.

    	ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();
		
		productListTable.getItems().clear();
		
		try {
			
			String query= "SELECT productName,quantity,price,section, SUM(quantity) AS qty FROM generalproductstable WHERE productName REGEXP ? GROUP BY productName";
			
			PreparedStatement pst = con.prepareStatement(query);            
			pst.setString(1, fieldSearch.getText());            
            ResultSet rs = pst.executeQuery();            
            
			while(rs.next()){
				oList.add(new Products(rs.getString("productName"), rs.getString("price"), rs.getString("qty"), rs.getString("section")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try{			
		colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		colProductSection.setCellValueFactory(new PropertyValueFactory<>("section"));
				
		}catch(Exception ee){
			System.out.println(ee.getMessage());
		}
		
		productListTable.setItems(oList);
		
    	}
    	
    }
    
    public void showAllProducts(){
		
		ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();

		productListTable.getItems().clear(); //clear the table
		
		try {
			ResultSet rs = con.createStatement().executeQuery("select * from generalproductstable");
			while(rs.next()){
				oList.add(new Products(rs.getString("productName"), rs.getString("price"), rs.getString("quantity"), rs.getString("section")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try{			
		colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		colProductSection.setCellValueFactory(new PropertyValueFactory<>("section"));
		
				
		}catch(Exception ee){
			System.out.println(ee.getMessage());
		}
		
		productListTable.setItems(oList);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showAllProducts();
	}
    
}