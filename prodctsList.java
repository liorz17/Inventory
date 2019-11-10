import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class prodctsList implements Serializable{
	
	private ArrayList<InventoryItem> list;
	public prodctsList() {
		list= new ArrayList<InventoryItem>();
	}

	/**
	 * add a product to the inventory 
	 * precondition : name(title) , want value
	 * post condition: product added
	 * @param name is the title of the DVD
	 * @param want is the ideal number of DVD that should be in the inventory 
	 */
	public void addProduct(String name,int want) {
		int i =0;
		boolean isExists =false;
		while ( i<size()) {
			InventoryItem item = getIndexedProduct(i);
			if (getIndexedProduct(i).getTitle().equals(name)) {
				System.out.println(name+ " already exists in the inventory");
				isExists = true;
			}
			i++;
		}
		if (isExists == false) {
			InventoryItem product = new InventoryItem(name,0,want);
			if (list.isEmpty()) list.add(product);
			else list.add(addSortIndex(product.getTitle()), product);		
		}
	}
	/**
	 * add a product to the inventory 
	 * @precondition  title , want, value
	 * @param name is the title of the DVD
	 * @param want is the ideal number of DVD that should be in the inventory
	 * @param have- the number of DVD current in stock 
	 */
	
	public void addProduct(String title,int have,int want) {
		int i =0;
		boolean isExists = false;
		while ( i<size()) {
			InventoryItem item = getIndexedProduct(i);
			if (getIndexedProduct(i).getTitle().equals(title)) {
				JOptionPane.showMessageDialog(null,title+ " already exists in the inventory");
				isExists = true;
			}
			i++;
		}
		if (isExists == false) {
			InventoryItem product = new InventoryItem(title,0,want);
			if (list.isEmpty()) list.add(product);
			else list.add(addSortIndex(product.getTitle()), product);		
		}
	}
	
	/** remove a product from the inventory at the index specified
	 * @param index
	 */
	public void removeProduct(String title) {
		if (!list.isEmpty()) {
			for (int i=0;i<size();i++) {
				InventoryItem item = getIndexedProduct(i);
				if (getIndexedProduct(i).getTitle().equals(title)) {
					list.remove(i);
					return;
				}
			}
			isExists(title);
			
		}
		else throw new WaitListException("Product list is empty on getFirst");
	}
	/**
	 * 
	 * @return if list is not empty, return product at index=1
	 * @throws WaitListException
	 */
	public InventoryItem getFirstProduct () throws WaitListException {
		if (!list.isEmpty()) return list.get(0);
		else throw new WaitListException("Product list is empty on getFirst");
		}
	/**
	 * @param index
	 * @return Inventory Item at the specified index
	 */
	public InventoryItem getIndexedProduct(int index) {
		if (!list.isEmpty()) return list.get(index);
		else throw new WaitListException("Product list is empty on get");
	}
	/**
	 * @return true if the list is empty, otherwise false
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	/**
	 * remove all the Inventory items from the list
	 */
	public void removeAll() {
		list.removeAll(list);
	}
	public int size() {
		return list.size();
	}
	private int addSortIndex(String i) {
		int index = 0;
		while (list.get(index).getTitle().compareToIgnoreCase(i)<0 && index<list.size()-1) {
			index++;
		}
		return index;
		
	}
	
	// the path to the directory of the java file 
	private final static String filePath=System.getProperty("user.dir");
/**
 * load the products list; 
 * @param fileName
 * @return the list of products uploaded from the file
 * @throws IOException
 */
public static prodctsList load (String fileName) throws IOException{
		
		prodctsList restoredInventory;
		try {
		FileInputStream file = new FileInputStream(fileName);
		ObjectInputStream input = new ObjectInputStream(file);
		Object o = input.readObject();
		restoredInventory = (prodctsList) o;
		System.out.println(fileName+ " loaded");

		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,"The system cannot find the file specified.\nCreating new file");
			restoredInventory = new prodctsList();
		}
		return restoredInventory;
	}
	public String help() {
		return("Commands:\n"+"Search <title> (inquire): Display the inventory information for the specific title"+
	"\n"+" add <title>: add a new title to the inventory with the initial want value\n"
				+"Modify <title>: Modify the want value for a specific title\n"+"Delivery <title>\n"
	+"Order: purchace order of a shipment of DVD's to the want value\n"+"Sell <title>: sell the title, if the title is sold"
			+ " out putting the name in the waitList for the title\n");
	}
	/**
	 * find the product with the specific title
	 * @param title
	 * @return the product or null if cannot find the searched product.
	 */
	public InventoryItem inquire(String title) {
		int i =0;
		while ( i<size()) {
			InventoryItem item = getIndexedProduct(i);
			if (getIndexedProduct(i).getTitle().equals(title)) {
				return item;
			}
			i++;
		}
		return null;
	}
	public void list() {
		
		for (int i=0;i<size();i++) {
			System.out.println(getIndexedProduct(i).toString());
			
		}
	}
	/**
	 * change the want value of the specific product 
	 * @param title
	 * @param want - the ideal number of DVD in stock.
	 */
	public void modify(String title, int want) {
		int i =0;
		boolean found = false;
		while ( i<size()) {
			if (getIndexedProduct(i).getTitle().equals(title)) {
				getIndexedProduct(i).setWantValue(want);
				found = true; 
			}
			i++;
		}
		if (found==false) {
			isExists(title);
		}
		
		
	}
	public void Return(String title) {
		int i =0;
		while ( i<size()) {
			InventoryItem item = getIndexedProduct(i);
			if (getIndexedProduct(i).getTitle().equals(title)) {
				int haveMinusWant = item.getHaveValue()-item.getWantValue();
				if (haveMinusWant>0) {
					getIndexedProduct(i).setHaveValue(getIndexedProduct(i).getWantValue());
				}
			}
			i++;
		}
	}
	/**
	 * sell one product of the specific title, if the product is out of stock, add the customer to the wait list
	 * @param title
	 * @throws PhoneNumberNotValidException
	 */
	public void sell(String title) throws PhoneNumberNotValidException {
		int i =0;
		boolean found = false;
		while ( i<size()) {
			InventoryItem item =getIndexedProduct(i);
			if (getIndexedProduct(i).getTitle().equals(title)) {
				found=true;
				if (item.getHaveValue()==0) {
					String[] inputs=nameAndPhoneNumber(title);
					Customers newCustomers = new Customers(inputs[0],inputs[1],inputs[2]);
					getIndexedProduct(i).addToWaitList(newCustomers);
					JOptionPane.showMessageDialog(null, " added to the wait list");
				}
				else {
					getIndexedProduct(i).setHaveValue(item.getHaveValue()-1);
				}
			}
			
			i++;
			
		}
		if (found == false) {
			isExists(title);
		}
	}
	/**
	 * save the products list to a file.
	 * @param fileName
	 * @param products
	 * @throws IOException
	 */
	public void quit (String fileName,prodctsList products) throws IOException{
		try {
		FileOutputStream file = new FileOutputStream(fileName);
		ObjectOutputStream output = new ObjectOutputStream (file);
		output.writeObject(products);
		file.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * subtract by 1 the number of a specific product. if there are customers on the wait list, remove as much
	 * customers as possible from the list. if the product is not found asking to add a new product with the title that was input. 
	 * @param title
	 * @param count
	 */
	public void delivery(String title,int count) {
		int i =0;
		boolean isExists = false; 
		while ( i<size()) {
			InventoryItem item = getIndexedProduct(i);
			if (getIndexedProduct(i).getTitle().equals(title)) {
				isExists = true;
				if (count>item.getHaveValue()) {
					int have = item.getHaveValue();
					for (int r = 0;r<item.getHaveValue();r++) {
						getIndexedProduct(i).removeFromWaitList();
					}
					getIndexedProduct(i).setHaveValue(0);
				}
					else {
						count = item.getHaveValue()-count;
						for (int r = 0;r<item.WaitListSize();r++) {
							getIndexedProduct(i).removeFromWaitList();
					}
						getIndexedProduct(i).setHaveValue(count);
				}
				
			}
			i++;
		}
		if (isExists== false) {
			isExists(title);
			
		}
	}
	/**
	 * method to get the have to be equal to the want value of the product. 
	 * if the product does not exist, ask to add the product to the inventory 
	 * @param title
	 */
	public void Order (String title) {
		int i =0;
		boolean found= false;
		while ( i<size()) {
			if (getIndexedProduct(i).getTitle().equals(title)) {
				getIndexedProduct(i).setHaveValue(getIndexedProduct(i).getWantValue());
				found= true;
			}
			
			i++;
		}
		if(found==false) {
			isExists(title);
		}
	}
	//find 
	private void isExists(String title) {
		if (title==null) {
			return;
		}
		JTextField string = new JTextField (10);
		  JPanel panel = new JPanel();
		  panel.add(new JLabel(title+" is not in the inventory, would you like to add a new DVD to the inventory? Y/n"));
		  panel.add(string);
		  String answer= null;
		  int result = JOptionPane.showConfirmDialog(null, panel, 
	              "Please Enter Title Values", JOptionPane.OK_CANCEL_OPTION);
	     if (result == JOptionPane.OK_OPTION) {
	    	  answer = string.getText();
	     }
			
			int want=-1;
			switch (answer.toLowerCase()) {
			case "y":
				System.out.println("Enter want value for the new DVD");
				want = intInput();
				addProduct(title,want);
				JOptionPane.showMessageDialog(null,title+" added to the inventory.");
				break;                        
			case "n":
				JOptionPane.showMessageDialog(null,title+" is not added to the inventory");
				break;
			default:
				JOptionPane.showMessageDialog(null, "wrong input\n\nTry again");
				isExists(title);
		}
	}
	
	private static String[] nameAndPhoneNumber(String title) {
		String[] string = new String[3] ;
		JTextField firstName = new JTextField(10);
		JTextField lastName = new JTextField(15);
		JTextField phoneNumber = new JTextField (12);
		JPanel panel = new JPanel();
		panel.add(new JLabel("First Name:"));
		panel.add(firstName);
		panel.add(Box.createHorizontalStrut(10));
		panel.add(new JLabel("Last Name"));
		panel.add(lastName);
		panel.add(Box.createHorizontalStrut(10));
		panel.add(new JLabel("Phone Number"));
		panel.add(phoneNumber);
		int result = JOptionPane.showConfirmDialog(null, panel, 
				title+" is out of stock. Enter Customer full name and phone number ", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	    	  String fName = firstName.getText();
	    	  String lName=lastName.getText();
	    	  String phNumber=phoneNumber.getText();
	    	  if (fName == "" || fName==null|| lName==null||lName==""||phNumber==null||phNumber=="") {
	    		  JOptionPane.showMessageDialog(null, "Wrong input\n\n try Again");	
	    		  nameAndPhoneNumber(title);
	    		  } 
	    	  string[0]=fName;string[1]=lName;string[2]=phNumber;
	      }
	      return string;
	}
	private static int  intInput() {
		  JTextField Int = new JTextField(5);
		  JPanel panel = new JPanel();
		  int want =-1;
		  panel.add(new JLabel("want:"));
		  panel.add(Int);
		  int result = JOptionPane.showConfirmDialog(null, panel, 
	              "Please Enter want Value", JOptionPane.OK_CANCEL_OPTION);
	     if (result == JOptionPane.OK_OPTION) {
	    	 try {
		  want = Integer.parseInt(Int.getText());
	    	 }
	     catch(NumberFormatException e){
		  JOptionPane.showMessageDialog(null,"Not a number input");
	     }
	  }
	  if (want==-1) {
		  JOptionPane.showMessageDialog(null, "Wrong have or want input\n\n try Again");	
		  intInput();
		  }
	  return want;
	  }
	
}
