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
	
	/* add a product to the inventory
	 * add the same as the previous add product method but with have value
	 */
	public void addProduct(String title,int have,int want) {
		int i =0;
		boolean isExists = false;
		while ( i<size()) {
			InventoryItem item = getIndexedProduct(i);
			if (getIndexedProduct(i).getTitle().equals(title)) {
				System.out.println(title+ " already exists in the inventory");
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
	
	// remove a product from the inventory
	public void removeProduct(int index) {
		if (!list.isEmpty()) list.remove(index);
		else throw new WaitListException("Product list is empty on getFirst");
	}
	public InventoryItem getFirstProduct () throws WaitListException {
		if (!list.isEmpty()) return list.get(0);
		else throw new WaitListException("Product list is empty on getFirst");
		}
	public InventoryItem getIndexedProduct(int index) {
		if (!list.isEmpty()) return list.get(index);
		else throw new WaitListException("Product list is empty on get");
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
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
	
	//Program commands
	private final static String filePath=System.getProperty("user.dir");

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
			System.out.println("The system cannot find the file specified.\nCreating new file");
			restoredInventory = new prodctsList();
		}
		return restoredInventory;
	}
	public void help() {
		System.out.println("Commands:\n"+"I<title> (inquire): Display the inventory information for the specific title"+
	"\n"+"L (list): List the entire inventory."+"\nA <title> (ADD:) add a new title to the inventory with the initial want value\n"
				+"M <title> (modify): Modify the want value for a specific title\n"+"D (delivery)\n"
	+"O (order): purchace order of a shipment of DVD's to the want value\n"+"R (return):  reduce the have value to the return value\n"+"S <title> (sell): sell the title, if the title is sold"
			+ " out putting the name in the waitList for the title\n"+"Q (quit) : save and quit the program");
	}
	
	public String inquire(String title) {
		int i =0;
		while ( i<size()) {
			InventoryItem item = getIndexedProduct(i);
			if (getIndexedProduct(i).getTitle().equals(title)) {
				return item.toString();
			}
			i++;
		}
		return title +" not found";
	}
	public void list() {
		
		for (int i=0;i<size();i++) {
			System.out.println(getIndexedProduct(i).toString());
			
		}
	}
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
	public void sell(String title) throws PhoneNumberNotValidException {
		int i =0;
		boolean found = false;
		while ( i<size()) {
			InventoryItem item =getIndexedProduct(i);
			if (getIndexedProduct(i).getTitle().equals(title)) {
				found=true;
				if (item.getHaveValue()==0) {
					System.out.println(title+" is out of stock. Enter Customer full name and phone number ");
					Scanner kbd = new Scanner (System.in);
					String firstName=kbd.next();
					String lastName = kbd.next();
					String phoneNumber = kbd.next();
					Customers newCustomers = new Customers(firstName,lastName,phoneNumber);
					getIndexedProduct(i).addToWaitList(newCustomers);
					System.out.println(getIndexedProduct(i).get()+" added to the wait list");
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
			System.out.println("Product wasn't found, adding the product to the list.\nEnter want value for new title ");
			Scanner kbd = new Scanner(System.in);
			int want = kbd.nextInt();
			addProduct(title, want);
		}
	}
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
	private void isExists(String title) {
			System.out.println(title+" is not in the inventory, would you like to add a new DVD to the inventory? Y/n");
			Scanner kbd = new Scanner(System.in);

			String answer = kbd.next();
			int want;
			switch (answer) {
			case "Y":
				System.out.println("Enter want value for the new DVD");
				want = kbd.nextInt();
				addProduct(title,want);
				System.out.println("DVD added to the inventory.");
				break;
			case "y":
				System.out.println("Enter want value for the new DVD");
				want = kbd.nextInt();
				addProduct(title,want);
				System.out.println("DVD added to the inventory.");
				break;                          
			case "n":
				System.out.println("DVD is not added to the inventory");
				break;
			case "N":
				System.out.println("DVD is not added to the inventory");
				break;
			default:
				System.out.println("wrong input");
			
		}
	}

	public static void main (String [] args) throws IOException, PhoneNumberNotValidException {
		String fileName = "Incoming shipment.txt";
		prodctsList list =load(fileName);
		System.out.println("\nadding DVD to the inventory:");
		list.addProduct("Iron Man", 9);
		list.addProduct("Metrix", 9, 15);
		list.addProduct("Aladin", 13, 15);
		list.addProduct("Pulp fiction", 13, 17);
		list.list();
		System.out.println("\nOrder +inquire(to show the change in the have value of the DVD):");
		list.Order("Metrix");
		System.out.println(list.inquire("Metrix"));
		System.out.println("\nSell");
		list.sell("Iron Man");
		System.out.println("\nDelivery + inquire(to show the change in the have value of the DVD)");
		list.delivery("Aladin", 10);
		list.inquire("Aladin");
		System.out.println("\nsave and quit");
		list.quit(fileName, list);
		System.out.println("load: (loading the file)");
		prodctsList products = load(fileName);
		products.list();
		System.out.println("\ntrying to add an existing DVD");
		products.addProduct("Aladin", 15);
	
	}
}
