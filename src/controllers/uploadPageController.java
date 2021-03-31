package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import helper.Products;
import org.controlsfx.control.textfield.TextFields;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class uploadPageController implements Initializable{

    @FXML
    private TextField fieldProductName;

    @FXML
    private TextField fieldQuantity;

    @FXML
    private ComboBox<String> fieldSection;

    @FXML
    private TextField fieldPrice;
    
    @FXML
    private TextField fieldLowStockLevel;


    @FXML
    private DatePicker fieldExpiryDate;

    @FXML
    private Button btnScanBarcode;
    
    @FXML
    private CheckBox listenBC;


    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnCancel;

    @FXML
    private ImageView fieldImage;

    @FXML
    private Button btnSelectImage;
    
    
    @FXML
    private Button btnUpdate;
    
    @FXML
    private Button btnShowUpdate;

    @FXML
    private Button btnShowAdd;

    
    ObservableList<Products> oList = FXCollections.observableArrayList();
    
    
///////////////////////////////////////////////////////////////////////////As Search textField is typed into, Table is populated//////////////////
   
    @FXML
    void searchDBWithlettersInput(KeyEvent event) {

    		//check for if there is at least one character in the search field.
    	String text = fieldProductName.getText().toString();

    	ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();

		try {
			
//			String query= "SELECT * FROM generalproductstable WHERE LOWER(productName) REGEXP ? LIMIT 3";
			
			String query= "SELECT * FROM generalproductstable WHERE LOWER(productName) LIKE ? LIMIT 3";
			
			
			PreparedStatement pst = con.prepareStatement(query);
            
			pst.setString(1, text+"%");
            
            ResultSet rs = pst.executeQuery();
			while(rs.next()){
				
				oList.add(new Products(rs.getString("productName"), rs.getString("price"), rs.getString("quantity")));
				
				System.out.println(rs.getString("productName"));
				TextFields.bindAutoCompletion(fieldProductName, rs.getString("productName"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try{		
			
//			System.out.println(oList.get(0));
//			fieldProductName.bin
//		colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
//		colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
//		colProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				
		}catch(Exception ee){
			System.out.println(ee.getMessage());
		}
		
//		productListTable.setItems(oList);
    	
    }
    
    /////////////////////////////////////////////////////////////////////////////////////End

    @FXML
    private CheckBox bcSc;

    
    @FXML
    void close(MouseEvent event) {
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
    
    Connection con;
	PreparedStatement  pst, pst2, pst3, pst4, pst5;
	ResultSet rs,rs2,rs3, rs4, rs5;
	int len;
	FileInputStream fin;
	
    @FXML
    void add(ActionEvent event) {    	 
    	
//Assignments of fields before uploading to DB
    	try {
    	String prodName = fieldProductName.getText();
    	int quantity = Integer.parseInt(fieldQuantity.getText());
    	String sect = fieldSection.getValue().toString();
    	double price = Double.parseDouble(fieldPrice.getText());
    	Date expDate = Date.valueOf(fieldExpiryDate.getValue());
    	int lowstocklev = Integer.parseInt(fieldLowStockLevel.getText());
    	
    	
//the Assignments for the image
    	try {
			fin = new FileInputStream(file2);
			len = (int) file2.length();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
			System.out.println(e1.getMessage());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Message");
			alert.setContentText("No image selected");
			alert.show();
		}
    	
    	System.out.println(prodName +" "+ quantity+" "+sect+ " "+price+" "+expDate ); //test print
    	
//to connect to DB and upload
    	
			ConnectionClass cc = new ConnectionClass();
			con = cc.getConnection();
			
			pst = con.prepareStatement("insert into productstable (productName,quantity,section,price,expiryDate,productImg,lowStockLevel,barcode) values(?,?,?,?,?,?,?,?)");
			
			pst.setString(1, prodName);
			pst.setInt(2, quantity);
			pst.setString(3, sect);
			pst.setDouble(4, price);
			pst.setDate(5, expDate);
			pst.setBinaryStream(6, fin,len);
			pst.setInt(7, lowstocklev);
			pst.setString(8, getBarCd());
			
			int status = pst.executeUpdate();
			
			if(status>0){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Information dialog");
				alert.setContentText("Db products Table Updated successfully.");
//				alert.show();
			}
			
			
			pst2 = con.prepareStatement("select * from generalproductstable where productName=? ");				
			pst2.setString(1, prodName);				
			rs2 = pst2.executeQuery();	
			
			///if the product exists,///////// D8WQWS2 ///////////////////////////////
			if(rs2.next()){
				
				////do these.... 
				String queryNow = "SELECT * FROM generalproductstable WHERE productName = ?";				
				PreparedStatement pstNow = con.prepareStatement(queryNow);            
				pstNow.setString(1, prodName);            
	            ResultSet rsNow = pstNow.executeQuery();            
				while(rsNow.next()){
					int quantityNow = rsNow.getInt("quantity");			//get the product quantity.....
					int newQuantity = quantity+quantityNow;			//set new quantity = old + new (usually increased by 1).....
					
					pst4 = con.prepareStatement("UPDATE generalproductstable SET quantity = ?, price = ?, section = ?, lowStockLevel = ?, expiryDate = ? WHERE productName = ? ");
					
					pst4.setInt(1, newQuantity);//update the quantity....
					pst4.setDouble(2, price);
					pst4.setString(3, sect);
					pst4.setInt(4, lowstocklev);
					pst4.setDate(5, expDate);
					pst4.setString(6, prodName);
					
					pst4.execute();
					int status3 = pst4.executeUpdate();
					
					if(status3>0){
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("Information dialog");
							alert.setContentText("Updated successfully.");
							alert.show();
							}
					
				}
			}
			///////////////////////////////////////////////////////////////////////////////////////
			
			else {
				pst5 = con.prepareStatement("insert into generalproductstable (productName,section,quantity,price,lowStockLevel,expiryDate) values(?,?,?,?,?,?)");
				
				pst5.setString(1, prodName);
				pst5.setString(2, sect);
				pst5.setInt(3, quantity);
				pst5.setDouble(4, price);
				pst5.setInt(5, lowstocklev);
				pst5.setDate(6, expDate);
				
				int status2 = pst5.executeUpdate();
				
				if(status2>0){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("Information dialog");
					alert.setContentText("Uploaded successfully. (new product added)");
					alert.show();
				}
			}
			listenBC.setSelected(false);
			bcSc.setSelected(false);
			setDefaultItemImage();
			
			
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
    		System.out.println(e.getMessage());
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Information Dialog");
			alert.setHeaderText("Error Message");
			alert.setContentText("Empty field(s) detected.");
			alert.show();
    	} catch (Exception ee){
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Information Dialog");
			alert.setHeaderText("Error Message");
			alert.setContentText("Empty field(s) detected.");
			alert.show();
    	}
    	
    }
    
    @FXML
    void chooseImg(ActionEvent event) {

    	chooseimage();
    }
    @FXML
    void clear(ActionEvent event) {

    	fieldProductName.setText(null);
    	fieldQuantity.setText(null);
    	fieldSection.setValue(null);
    	fieldPrice.setText(null);
    	fieldExpiryDate.setValue(null);
    	fieldImage.setImage(null);
    	setDefaultItemImage();
    }
    
    @FXML
    void cancel(ActionEvent event) {
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.close();

    }
    
    private String barCd;
    
    private static final long THRESHOLD = 100;
    private static final int MIN_BARCODE_LENGTH = 8;

    private final StringBuffer barcode = new StringBuffer();
    private long lastEventTimeStamp = 0L;
    
    
    @FXML
    public void keyTyped(KeyEvent keyEvent) {
        long now = Instant.now().toEpochMilli();

        // events must come fast enough to separate from manual input
        if (now - this.lastEventTimeStamp > uploadPageController.THRESHOLD) {
            barcode.delete(0, barcode.length());
        }
        this.lastEventTimeStamp = now;

        // ENTER comes as 0x000d
        if (keyEvent.getCode()==KeyCode.ENTER) {

			System.out.println("Enter is pressedXXX");
            if (barcode.length() >= uploadPageController.MIN_BARCODE_LENGTH) {
                System.out.println("barcode: " + barcode);
                try{
//                addToCartBarcode(barcode.toString().trim());
                	setBarCd(barcode.toString().trim());
                	bcSc.setSelected(true);
                	System.out.println(getBarCd());
                	
                }catch(Exception e){
                	System.out.println(e.getMessage());
                }
            }
            barcode.delete(0, barcode.length());
        } else {
            barcode.append(keyEvent.getText());
        }
        keyEvent.consume();
    }
    
    public String getBarCd() {
		return barCd;
	}


	public void setBarCd(String barCd) {
		this.barCd = barCd;
	}

	ObservableList<String> hList = FXCollections.observableArrayList("Soft Drinks", 
    		"Mineral Water", 
    		"Fruit Juices", 
    		"Starionery", 
    		"Party Accessories", 
    		"Baby Toys", 
    		"Girls Toys", 
    		"Chocholates", 
    		"Chips", 
    		"Sweets", 
    		"Glassware",
    		"Crockery", 
    		"Sauces", 
    		"Spices", 
    		"Cooking Oil", 
    		"Kitchen Utensils", 
    		"Coffee", 
    		"Small Appliances", 
    		"Biscuits", 
    		"Light Bulbs", 
    		"Baby Foods", 
    		"Dental Care", 
    		"Insecticides", 
    		"Cleaning Aids", 
    		"Brush ware", 
    		"Air Fresheners", 
    		"Form Fresh Foods",
    		"Outfit Accessories",
    		"Beverages",
    		"Spices & Seasonings",
    		"Tomato Paste",
    		"Nodules & Spaghetti",
    		"Swallow",
    		"Vegetable Oil",
    		"Detergents & Soaps",
    		"Toothpaste & Brushes",
    		"Toiletries & Pampers",
    		"Body & Hair Creams",
    		"Perfumes, Spray & Roll-On",
    		"Biscuits, Sweets & Snacks",
    		"Stationary",
    		"Drinks & Juices",
    		"Insecticides & Air Freshner").sorted();
    
    /*"2", 
	"3", 
	"4", 
	"5", 
	"6", 
	"7", 
	"8", 
	"9",*/
    
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	// TODO Auto-generated method stub

    	fieldSection.setItems(hList);


    	btnUpdate.setVisible(false);
    	setDefaultItemImage();
    	
    	
    }
    
    public void setDefaultItemImage(){
    	file2 = new File("/src/javaResized.png");
        Image image = new Image(file2.toURI().toString());
        fieldImage.setImage(image);
    }
    
    public File file,file2;
    BufferedImage bufferedImage, bImage;
    

    /////Resize image mock method.../////////////////////////
    Image scaled, scaled1;
    public Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        return imageView.snapshot(null, null);
    }
    
    
    public void chooseimage(){
    	FileChooser fileChooser = new FileChooser();
    	try{
    	file = fileChooser.showOpenDialog(null);
    	
    		bufferedImage = ImageIO.read(file);
    		Image image = SwingFXUtils.toFXImage(bufferedImage,null);
    		
    		scaled = scale(image, 145, 145, false);
    		
    		file2 = new File("formattedImg.png");
    		bImage = SwingFXUtils.fromFXImage(scaled, null);
    		try{
    			ImageIO.write(bImage, "png", file2);
    		}catch(IOException ioe){
    			System.out.println(ioe.getMessage());
    		}
    		fieldImage.setImage(scaled);
//    		fieldImage.setFitWidth(200);
//    		fieldImage.setFitHeight(200);
//    		fieldImage.preserveRatioProperty().setValue(true);
    		
    	} catch(IOException ex){
    		System.out.println(ex.getMessage());
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    	
    	
     	}
    
    @FXML
    public void setAllFieldsVisible(ActionEvent event){
    	
    	fieldProductName.setVisible(true);
    	fieldExpiryDate.setVisible(true);
    	fieldImage.setVisible(true);
    	fieldLowStockLevel.setVisible(true);
    	fieldPrice.setVisible(true);
    	fieldQuantity.setVisible(true);
    	fieldSection.setVisible(true);
    	btnAdd.setVisible(true);
    	listenBC.setVisible(true);
    	bcSc.setVisible(true);
    	btnSelectImage.setVisible(true);
    	btnUpdate.setVisible(false);
    	
    }
    
    @FXML
    public void setSpecificFieldsVisible(ActionEvent event){
    	
    	fieldProductName.setVisible(true);
    	fieldPrice.setVisible(true);
    	fieldQuantity.setVisible(true);
    	btnUpdate.setVisible(true);
    	fieldExpiryDate.setVisible(false);
    	fieldImage.setVisible(false);
    	fieldLowStockLevel.setVisible(false);
    	fieldSection.setVisible(false);
    	btnAdd.setVisible(false);
    	listenBC.setVisible(false);
    	bcSc.setVisible(false);
    	btnSelectImage.setVisible(false);
    	
    }
    
    @FXML
    public void updatePriceAndQuantity(ActionEvent event){
    	ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();
    	
    	String prodName = fieldProductName.getText();
    	int quantity = Integer.parseInt(fieldQuantity.getText());
    	double price = Double.parseDouble(fieldPrice.getText());
    	
    	try{
    	pst2 = con.prepareStatement("select * from generalproductstable where productName=? ");				
		pst2.setString(1, prodName);				
		rs2 = pst2.executeQuery();	
		
		///if the product exists,///////// D8WQWS2 ///////////////////////////////
		if(rs2.next()){
			
			////do these.... 
			String queryNow = "SELECT * FROM generalproductstable WHERE productName = ?";				
			PreparedStatement pstNow = con.prepareStatement(queryNow);            
			pstNow.setString(1, prodName);            
            ResultSet rsNow = pstNow.executeQuery();            
			while(rsNow.next()){
				int quantityNow = rsNow.getInt("quantity");			//get the product quantity.....
				int newQuantity = quantity+quantityNow;			//set new quantity = old + new (usually increased by 1).....
				
				pst4 = con.prepareStatement("UPDATE generalproductstable SET quantity = ?, price = ? WHERE productName = ? ");
				
				pst4.setInt(1, newQuantity);//update the quantity....
				pst4.setDouble(2, price);
				pst4.setString(3, prodName);
				
				pst4.execute();
				int status3 = pst4.executeUpdate();
				
				if(status3>0){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("Information dialog");
						alert.setContentText("Updated successfully.");
						alert.show();
						}
			}
		}
    } catch (Exception ee){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error Information Dialog");
		alert.setHeaderText("Error Message");
		alert.setContentText("Empty field(s) detected.");
		alert.show();
	}
			
    }	
    
    

}
