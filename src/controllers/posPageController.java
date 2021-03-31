package controllers;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JTable;

import connectivity.ConnectionClass;
import helper.Cart;
import helper.Printsupport;
import helper.Printsupport.MyPrintable;
import helper.Products;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.proteanit.sql.DbUtils;

public class posPageController implements Initializable{

	@FXML
    private AnchorPane anchPane;

    @FXML
    private TableColumn<Cart, String> cartProductNameCol;

    @FXML
    private TableColumn<Cart, String> cartQuantityCol;

    @FXML
    private TableColumn<Cart, String> cartPriceCol;
	
    @FXML
    private TableColumn<Cart, String> cartSNCol;

    @FXML
    private TableView<Cart> cartTable;

    @FXML
    private Label labelTotalPrice;

    @FXML
    private CheckBox checkboxHasBpaid;
    
    @FXML
    private CheckBox printComboBox;

    @FXML
    private Button btnCheckout;

    @FXML
    private Button btnBarcode;

    @FXML
    private Button btnInsert;

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
    private Button btnAddToCart;

    @FXML
    private Label processSelection;
    
    @FXML
    private TextField quantityBuying;
    
    @FXML
    private Button btnClearCart;

    @FXML
    private Spinner<Integer> spinnerQuantity;
    
    @FXML
    private CheckBox checkBarcode;
    

    @FXML
    private CheckBox checkKeyInput;

    
    static String totalPrice;
    ObservableList<String> quantityList = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    
    
    public static String getTotalPrice() {
		return totalPrice;
	}

	public static void setTotalPrice(String totalPrice) {
		posPageController.totalPrice = totalPrice;
	}
	
	
	
	@FXML
    void clearCart(ActionEvent event) {
    	
    		///////////////////////////////////////////////////Clear cart table...////////////////////
    	ConnectionClass cc = new ConnectionClass();
    	con = cc.getConnection();

    	PreparedStatement pst3;
    	try {
    		pst3 = con.prepareStatement("DELETE FROM carttable");
    		pst3.execute();
    		System.out.println("Done!!..");
    		pst3.close();
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		System.out.println(e.getMessage());
    		Platform.runLater(() -> 
    		fieldSearch.requestFocus());
    	}
    	
    	
    	PreparedStatement pst34;
		try {
			pst34 = con.prepareStatement("TRUNCATE carttable");
			pst34.execute();
	        System.out.println("Done!!..");
	        pst34.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			Platform.runLater(() -> 
			fieldSearch.requestFocus());
		}
    	
		Platform.runLater(() -> 
		fieldSearch.requestFocus());
    	updateCartTable();
		
    	
    }
    
    @FXML
    void close(MouseEvent event) {
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    void getSelectedItemInCart(MouseEvent event) {

    	rowInCart = cartTable.getSelectionModel().getSelectedIndex();

    	try{
    	selectedProductNameInCart = cartProductNameCol.getCellData(rowInCart);
    	selectedPriceInCart = cartPriceCol.getCellData(rowInCart);
    	selectedSnInCart = cartSNCol.getCellData(rowInCart);
    	
//    	System.out.println(selectedProductNameInCart +" "+ selectedPriceInCart);
    	}catch(Exception ee){
    		System.out.println(ee.getMessage());
    	}
    }
    int rowInCart;
    String selectedProductNameInCart, selectedPriceInCart, selectedSnInCart;
    
    ///////////////////////////////////////////////////////////////////////////Remove Selected item from Cart...//////////////////////////////
    
//    String selectedSNInCart = null;
    @FXML
    void removeFromCart(ActionEvent event) {
    	
    	try {
			ConnectionClass cc = new ConnectionClass();
			con = cc.getConnection();
			
//			int selRowInCart = cartTable.getSelectionModel().getSelectedIndex()+1;
			
//			String sSn = Integer.toString(selRowInCart);
			String sSn = selectedSnInCart;
			
			pst = con.prepareStatement("DELETE from carttable WHERE productName =? AND price =? AND sn =?");
			
			pst.setString(1, selectedProductNameInCart);
			pst.setDouble(2, Double.parseDouble(selectedPriceInCart));
			pst.setString(3, sSn);
			
			int status = pst.executeUpdate();
			if(status>0){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Information dialog");
				alert.setContentText("Item successfully.");
//				alert.show();
			}
			
			Platform.runLater(() -> 
			fieldSearch.requestFocus());
			
			updateCartTable();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
    		System.out.println(e.getMessage());
    		Platform.runLater(() -> 
    		fieldSearch.requestFocus());
		}

    }
    ////////////////////////////////////////////////////////////////End.../////////////////////////////////////////////////////////////
    
    
	@FXML
    private Button btnRemoveFromCart;

    

    ObservableList<Cart> oListxx = FXCollections.observableArrayList();
    double total;

    
    
    ////////////////////////////////////////////////////////////////////////////////////ADD to CART BUTTON//////////////////////////////////////////////
    @FXML
    void addToCart(ActionEvent event) {

//    	selectedQuantity= quantityBuying.getText();
    	selectedQuantity = Integer.toString(spinnerQuantity.getValue());
    	System.out.println(selectedName+" // "+ selectedQuantity +" // "+ selectedprice);
    	

    	//to connect to DB and upload
    	    	try {
    				ConnectionClass cc = new ConnectionClass();
    				con = cc.getConnection();
    				
    				pst = con.prepareStatement("insert into carttable (productName,quantity,price) values(?,?,?)");
    				
    				pst.setString(1, selectedName);
    				pst.setInt(2, Integer.parseInt(selectedQuantity));
    				pst.setDouble(3, Double.parseDouble(selectedprice));
    				
    				int status = pst.executeUpdate();
    				if(status>0){
    					Alert alert = new Alert(AlertType.INFORMATION);
    					alert.setTitle("Information Dialog");
    					alert.setHeaderText("Information dialog");
    					alert.setContentText("Db Updated successfully.");
//    					alert.show();
    				}
    				
    	    	} catch (SQLException e) {
    				// TODO Auto-generated catch block
    	    		System.out.println(e.getMessage());
    	    		Platform.runLater(() -> 
    	    		fieldSearch.requestFocus());
    			}
    	    	
    	    	
    	  
    	    		ConnectionClass cc = new ConnectionClass();
    	    		con = cc.getConnection();

    	    		cartTable.getItems().clear();//clear the table
    	    		
    	    		try {
    	    			ResultSet rs = con.createStatement().executeQuery("select * from carttable");
    	    			while(rs.next()){
    	    				oListxx.add(new Cart(rs.getString("sn"), rs.getString("productName"), rs.getString("quantity"), rs.getString("price")));
    	    			}
    	    		} catch (SQLException e) {
    	    			// TODO Auto-generated catch block
    	    			System.out.println(e.getMessage());
    	    			Platform.runLater(() -> 
    	    			fieldSearch.requestFocus());
    	    		}
    	    		
    	    		Platform.runLater(() -> 
    	    		fieldSearch.requestFocus());
    	    		
    	    		updateCartTable();
    	    } 
    	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////END!!??????????/////////
    	
    
    
    
    ///////////////////////////////////////////////////////////////////////////// Add to Cart (Barcode) //////////////////////////////
    
    private void addToCartBarcode(String bC){
    	
    	ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();
		
		productListTable.getItems().clear();

		String selectedItemNameBarCode = null;
		String selectedItemPriceBarCode = null;
		String selectedItemQuantityBarCode = null;
		

		fieldSearch.setText("");
		try {
			
			String query= "SELECT sum(quantity) as totalQuantity, productName,quantity,price FROM productstable WHERE barcode=? group by productName ";
			
			
			PreparedStatement pst = con.prepareStatement(query);
            
			pst.setString(1, bC);
            
            ResultSet rs = pst.executeQuery();
            
            
			while(rs.next()){
				oList.add(new Products(rs.getString("productName"), rs.getString("price"), rs.getString("totalQuantity")));
				selectedItemNameBarCode = rs.getString("productName");
				selectedItemPriceBarCode = rs.getString("price");
				selectedItemQuantityBarCode = "1";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			Platform.runLater(() -> 
			fieldSearch.requestFocus());
		}
		
		//to connect to DB and upload
    	try {
			ConnectionClass ccYY = new ConnectionClass();
			con = ccYY.getConnection();
			
			pst = con.prepareStatement("insert into carttable (productName,quantity,price) values(?,?,?)");
			
			pst.setString(1, selectedItemNameBarCode);
			pst.setInt(2, Integer.parseInt(selectedItemQuantityBarCode));
			pst.setDouble(3, Double.parseDouble(selectedItemPriceBarCode));
			
			int status = pst.executeUpdate();
			if(status>0){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Information dialog");
				alert.setContentText("Cart updated with barcode");
//				alert.show();
			}
			
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
    		System.out.println(e.getMessage());
    		Platform.runLater(() -> 
    		fieldSearch.requestFocus());
		}
    	
    	
  
    		ConnectionClass ccX = new ConnectionClass();
    		con = ccX.getConnection();

    		cartTable.getItems().clear();//clear the table
    		
    		try {
    			ResultSet rs = con.createStatement().executeQuery("select * from carttable");
    			while(rs.next()){
    				oListxx.add(new Cart(rs.getString("sn"), rs.getString("productName"), rs.getString("quantity"), rs.getString("price")));
    			}
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			System.out.println(e.getMessage());
    			Platform.runLater(() -> 
    			fieldSearch.requestFocus());
    		}
    		
    		Platform.runLater(() -> 
//    		fieldSearch.requestFocus());
    		
    		updateCartTable());
    		fieldSearch.setText(null);
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    ////////////////////////////////////// END//////////////////////////////////////////////////////////////////
    
    @FXML
    void checkout(ActionEvent event) {

    	if(checkboxHasBpaid.isSelected()){
    	checkoutAllCartTableContents();
    	updateCartTable();
    	Platform.runLater(() -> 
		fieldSearch.requestFocus());
    	}else{
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Payment Check");
			alert.setHeaderText("Information dialog");
			alert.setContentText("Check the 'Paid' box and ensure buyer has paid before checkout");
			alert.show();
    	}
    	
    }
    
    
    ///////////////////////////////////////////////////////////////////////////get Selected row items ///////////////////////////////////
    String selectedName, selectedprice, selectedQuantity;
    @FXML
    void getSelectedItem(MouseEvent event) {

    	int row = productListTable.getSelectionModel().getSelectedIndex();

    	try{
    	selectedName = colProductName.getCellData(row);
    	selectedprice = colProductPrice.getCellData(row);
    	System.out.println(selectedName +" "+ selectedprice);
    	}catch(Exception ee){
    		System.out.println(ee.getMessage());
    	}

    }
    /////////////////////////////////////////////////////////////////////////////////////////end.................///////////////////////////

    public String statusforCheckout;
    


	public String getStatusforCheckout() {
		return statusforCheckout;
	}



	public void setStatusforCheckout(String statusforCheckout) {
		this.statusforCheckout = statusforCheckout;
	}



	//////////////////////////////////////////////////////////.......CHECKOUT Cart table contents....///////////////////////////
    public void checkoutAllCartTableContents(){
    	
    	setTotalPrice(labelTotalPrice.getText());
    	
    	for(int i = 0; i<cartTable.getItems().size(); i++){
    		
    	String productNameInCart = cartProductNameCol.getCellData(i);
    	String productPriceInCart = cartPriceCol.getCellData(i);
    	String productQuantityInCart = cartQuantityCol.getCellData(i);
    	String cashier = loginPageController.getName();
    	
    	
    	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String sss = dateformat.format(date);
		
//    	Date date = Date.valueOf(LocalDate.now());
    	Time time = Time.valueOf(LocalTime.now());
    	int x=0;


    	
//    	//to connect to DB and upload to sales///////////////////////
    	try {
    		
			ConnectionClass cc = new ConnectionClass();
			con = cc.getConnection();
			
			pst = con.prepareStatement("insert into salestable (productName, quantity,price,date,time,cashier ) values(?,?,?,?,?,?)");
			
			pst.setString(1, productNameInCart);
			pst.setInt(2, Integer.parseInt(productQuantityInCart));
			pst.setDouble(3, Double.parseDouble(productPriceInCart));
			pst.setString(4, sss);
			pst.setTime(5, time);
			pst.setString(6, cashier);
			
			int status = pst.executeUpdate();
			if(status<=0){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText("Information dialog");
				alert.setContentText("Db Update not successful.");
				alert.show();
			}
				
				///////////////////Get quantity if item in stock.............////////////////////////
			
				String query4 = "SELECT productName,quantity,price FROM generalproductstable WHERE productName =? AND price =? ";
				
				PreparedStatement pst4 = con.prepareStatement(query4);
	            pst4.setString(1, productNameInCart);
	            pst4.setDouble(2, Double.parseDouble(productPriceInCart));
	            ResultSet rs4 = pst4.executeQuery();
	            while (rs4.next()){
	            x = rs4.getInt("quantity");
	            }
	            pst4.execute();
	            pst4.close();
	            rs4.close();
	            
	                int newquantity = x - Integer.parseInt(productQuantityInCart);
	                
	                /////////////////////////////////update new quantity in stock....................//////////////////////////
			
				String query6 = "UPDATE generalproductstable SET quantity = ? WHERE productName =? AND price =? ";
				
				PreparedStatement pst6 =con.prepareStatement(query6);
                pst6.setInt(1, newquantity);
                pst6.setString(2, productNameInCart);
                pst6.setDouble(3, Double.parseDouble(productPriceInCart));
                
                pst6.execute();
                pst6.close();
                System.out.println("Quantity changed successfully.");
                
                if(i<cartTable.getItems().size()){
                	setStatusforCheckout("green");
                }
				
                if (printComboBox.isSelected()==true){
            		printNow();
            		System.out.println("Print");
            	}else{
            		System.out.println("No Print");
            	}
            	
			showAllProducts();
			checkboxHasBpaid.setSelected(false);
			
			
			
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
    		System.out.println(e.getMessage());
		}
    	
    	
    	}
    	
    	
    	///////////////////////////////////////////////////after all update, clear cart table...////////////////////
    	ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();
		
    	PreparedStatement pst3;
		try {
			pst3 = con.prepareStatement("TRUNCATE carttable");
			pst3.execute();
	        System.out.println("Done!!..");
	        pst3.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		printComboBox.setSelected(true);
    	
    }
    ///////////////////////////////////////////////////////////////end..............//////////////////////////////////////////////////
    
    
    
    void printNow(){
    	Printsupport ps=new Printsupport();
    	 Object printitem [][]=ps.getTableData(jTable2);
    	 Printsupport.setItems(printitem);
    	       
    	 PrinterJob pj = PrinterJob.getPrinterJob();
    	 pj.setPrintable(new MyPrintable(),Printsupport.getPageFormat(pj));
    	       try {
    	            pj.print();
    	           
    	            }
    	        catch (PrinterException ex) {
    	                ex.printStackTrace();
    	            } 
   
    }
    
    
    
    ///////////////////////////////////////////////////////////////////////////As Search textField is typed into, Table is populated//////////////////
    @FXML
    void searchDBWithlettersInput(KeyEvent event) {
    	
    	//check for if the search field is empty
    	if (fieldSearch.getText().length() < 1){
    		ConnectionClass cc = new ConnectionClass();
    		con = cc.getConnection();

    		productListTable.getItems().clear();//clear the table
    		
    		try {
    			ResultSet rs = con.createStatement().executeQuery("select * from generalproductstable");
    			while(rs.next()){
    				oList.add(new Products(rs.getString("productName"), rs.getString("price"), rs.getString("quantity")));
    			}
    			
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			System.out.println(e.getMessage());
    		}
    		
    		try{			
    		colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
    		colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    		colProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    				
    		}catch(Exception ee){
    			System.out.println(ee.getMessage());
    		}
    		
    		productListTable.setItems(oList);
    	}
    	
    	else{
    		
    		//check for if there is at least one character in the search field.

    	ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();
		
		productListTable.getItems().clear();

		
		try {
			
			String query= "SELECT productName,quantity,price FROM generalproductstable WHERE productName REGEXP ? ";
			
			
			PreparedStatement pst = con.prepareStatement(query);
            
			pst.setString(1, fieldSearch.getText());
            
            ResultSet rs = pst.executeQuery();
            
            
			while(rs.next()){
				oList.add(new Products(rs.getString("productName"), rs.getString("price"), rs.getString("quantity")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try{			
		colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				
		}catch(Exception ee){
			System.out.println(ee.getMessage());
		}
		
		productListTable.setItems(oList);
    	}
    	
    }
    //////////////////////////////////////////////////////////////////////////////////////////////End.............///////////////////////////////
    

    @FXML
    void throughBarcode(ActionEvent event) {

    }

    @FXML
    void throughInsert(ActionEvent event) {

    	
    }
    
    ObservableList<Products> oList = FXCollections.observableArrayList();
    Connection con;
	PreparedStatement  pst;
	ResultSet rs; 

	int initialValueOfSpinner = 1;
	
	
    @FXML
    void handleKeyPressed(KeyEvent event) {
    	if (event.getCode()==KeyCode.ENTER){
			System.out.println("Enter is pressedXXX");
		}
    }
	

    /////////////////////////////////////////////////////////////////////////////////////// for barcode ////////////////////////////////////////////////
    private static final long THRESHOLD = 100;
    private static final int MIN_BARCODE_LENGTH = 8;

    private final StringBuffer barcode = new StringBuffer();
    private long lastEventTimeStamp = 0L;
    
    @FXML
    public void keyTyped(KeyEvent keyEvent) {
        long now = Instant.now().toEpochMilli();

        // events must come fast enough to separate from manual input
        if (now - this.lastEventTimeStamp > posPageController.THRESHOLD) {
            barcode.delete(0, barcode.length());
//            keyEvent.consume();
        }
        this.lastEventTimeStamp = now;

        // ENTER comes as 0x000d
        if (keyEvent.getCode()==KeyCode.ENTER) {

			System.out.println("Enter is pressed_");
//			fieldSearch.setText(null);
            if (barcode.length() >= posPageController.MIN_BARCODE_LENGTH) {
                System.out.println("barcode: " + barcode);
                try{
                addToCartBarcode(barcode.toString().trim());
                fieldSearch.setText(null);
                }catch(Exception e){
                	System.out.println(e.getMessage());
                }
            }
            barcode.delete(0, barcode.length());
        } else {
            barcode.append(keyEvent.getText());
        }
        
//        keyEvent.consume();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////Initialize Method................//////////////////
	
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		Platform.runLater(() -> 
		fieldSearch.requestFocus());
		
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, initialValueOfSpinner);
		spinnerQuantity.setValueFactory(valueFactory);
		
        jTable2 = new JTable();
		///
		
		
	
		
		ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();
		PreparedStatement pst34;
		try {
			pst34 = con.prepareStatement("DELETE FROM generalproductstable WHERE quantity <1");
			pst34.execute();
	        System.out.println("Done1!!..");
	        pst34.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		try {
			pst34 = con.prepareStatement("DELETE FROM productstable WHERE quantity <1");
			pst34.execute();
	        System.out.println("Done2!!..");
	        pst34.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try {
			ResultSet rs = con.createStatement().executeQuery("select * from generalproductstable");
			while(rs.next()){
				oList.add(new Products(rs.getString("productName"), rs.getString("price"), rs.getString("quantity")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try{			
		colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				
		}catch(Exception ee){
			System.out.println(ee.getMessage());
		}
		
		productListTable.setItems(oList);
		
		updateCartTable();
		quantityBuying.setText("1");
		
		
		cartTable.setEditable(true);
		cartQuantityCol.setEditable(true);
		cartQuantityCol.setCellFactory(TextFieldTableCell.forTableColumn());
		cartQuantityCol.setOnEditCommit(new EventHandler<CellEditEvent<Cart,String>>() {
					
					@Override
					public void handle(CellEditEvent<Cart, String> t) {
						// TODO Auto-generated method stub6
						((Cart) t.getTableView().getItems().get(t.getTablePosition().getRow())).setQuantity(t.getNewValue());

						
						String ab,ac,ad,sn;
						ab = cartProductNameCol.getCellData(t.getTablePosition().getRow());
						ac = cartPriceCol.getCellData(t.getTablePosition().getRow());
						ad = cartQuantityCol.getCellData(t.getTablePosition().getRow());
//						int tempPOS = t.getTablePosition().getRow()+1;
//						sn = Integer.toString(tempPOS);
						sn = selectedSnInCart;
						
						
						
						System.out.println(ab+" "+ ac+" "+ ad);
					try{
						///////change the quantity in cart table../////
						
					PreparedStatement pst4 = con.prepareStatement("UPDATE carttable SET quantity = ? WHERE productName = ? AND price = ? and sn = ? ");
					
					pst4.setInt(1, Integer.parseInt(ad));//update the quantity....
					pst4.setString(2, ab);
					pst4.setDouble(3, Double.parseDouble(ac));
					pst4.setString(4, sn);
					
					pst4.execute();
					int status3 = pst4.executeUpdate();
					
					if(status3>0){
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("Information dialog");
							alert.setContentText("Db General Products Table Updated successfully.");
//							alert.show();
							}
					}catch(SQLException ee){
						System.out.println(ee.getMessage());
					}
					/////////////////////////After new quantity has been updated to the cart table, call updateCart method to refresh..////
					updateCartTable();
					}
				}
				);
		
	}
	//////////////////////////////////////////////////////////////////////////////////////////////End...........///////////////////////////////
	

    private JTable jTable2;
	//////////////////////////////////////////////////////////to update cart table.......//////////////////
	public void updateCartTable(){
		
		ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();

		cartTable.getItems().clear();//clear the table

		try {
			ResultSet rs = con.createStatement().executeQuery("select * from carttable");

			jTable2.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			}
		
		try {
		ResultSet rs = con.createStatement().executeQuery("select * from carttable");

//		jTable2.setModel(DbUtils.resultSetToTableModel(rs));
		while(rs.next()){
		oListxx.add(new Cart(rs.getString("sn"), rs.getString("productName"), rs.getString("quantity"), rs.getString("price")));
		}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
		}
		try{
			cartSNCol.setCellValueFactory(new PropertyValueFactory<>("sn"));
			cartProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
			cartQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			cartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
			
			
			}catch(Exception ee){
				System.out.println(ee.getMessage());
				}
		cartTable.setItems(oListxx);
		try{
			Statement myStat2 = con.createStatement();
			ResultSet ds2 = myStat2.executeQuery("SELECT SUM(price * quantity) FROM carttable");
			while(ds2.next()){
				ds2.last();
				total = ds2.getDouble("SUM(price * quantity)");
				}
			String numString = NumberFormat.getInstance().format(total);
//			labelTotalPrice.setText("₦ "+Double.toString(total));
			labelTotalPrice.setText("₦ "+numString);
			pst.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				}
		Platform.runLater(() -> 
		fieldSearch.requestFocus());
		}

	
	public void showAllProducts(){
		
		ConnectionClass cc = new ConnectionClass();
		con = cc.getConnection();

		productListTable.getItems().clear();//clear the table
		
		PreparedStatement pst34;
		
		try {
			pst34 = con.prepareStatement("DELETE FROM generalproductstable WHERE quantity <1");
			pst34.execute();
	        System.out.println("Done!!..");
	        pst34.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try {
			ResultSet rs = con.createStatement().executeQuery("select * from generalproductstable");
			while(rs.next()){
				oList.add(new Products(rs.getString("productName"), rs.getString("price"), rs.getString("quantity")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try{
		colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				
		}catch(Exception ee){
			System.out.println(ee.getMessage());
		}
		
		productListTable.setItems(oList);
	}

}
