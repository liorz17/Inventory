import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class Inventory_System_GUI extends JFrame{
	
	private prodctsList product = new prodctsList();
	private JMenuBar menuBar;
	private JToolBar toolBar;
	private final String filePath=System.getProperty("user.dir");
	private JTextField filename = new JTextField();
	private JTextField dir = new JTextField();
	private prodctsList list = new prodctsList();
	private String[][] data = new String [15][5];
	private JTable table;
	private TableModel model;
	public Inventory_System_GUI() throws IOException {
		
		loadToData(list);
		//Creating the model
		model = new TableModel(data);
		//Creating the JTable
		table = new JTable(model);
		//Adding the scrolling table to the JFrame
		this.add(new JScrollPane(table));
		this.setTitle("Inventory");
		//Creating the menu bar and its position.
		menuBar = new JMenuBar();
	    menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Calling the Menu and toolbar adding method. 
		Menu();
		JToolBar();
		this.setJMenuBar(menuBar);
		//adding the toolbar and giving the position of the tool bar 
		this.add(toolBar,BorderLayout.NORTH);
		this.pack();
		//Setting the JFrame program to be visible.
		this.setVisible(true);
	}
	
	/**
	 * Creating the Menus
	 */
	public void Menu () {
		//Creating the JMenus
		JMenu menu1 = new JMenu("File");
		JMenu menu2 = new JMenu("Help");
		menuBar.add(menu1);
		menuBar.add(menu2);
		
		//Creating the JMenusItems and adding them to the JMenu  
		JMenuItem menu11 = new JMenuItem ("Open");
		JMenuItem menu12 = new JMenuItem("Save");
		JMenuItem menu13 = new JMenuItem("quit");
		JMenuItem menu21 = new JMenuItem("help");
		menu1.add(menu11);
		menu1.add(menu12);
		menu1.add(menu13);
		menu2.add(menu21);
		
		//creating the JFileChooser of a path:user.dir
		JFileChooser fc = new JFileChooser(filePath);
		
		//Saving to the file that was Chosen.
		menu12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
    	      // The "Save" dialog:
    	      int rVal = fc.showSaveDialog(null);
    	      //if the user chose a file
    	      if (rVal == JFileChooser.APPROVE_OPTION) {
    	    	  //get the selected file
    	    	  File file = fc.getSelectedFile();
    	    	  String fileName = file.getName();
    	    	  //setting the file name to the selected file
    	        filename.setText(fc.getSelectedFile().getName());
    	        //setting the directory to the directory of the selected file
    	        dir.setText(fc.getCurrentDirectory().toString());
    	        try {
    	        	//try to save the file
					list.quit(fileName, list);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null,"Error","File not found",JOptionPane.ERROR_MESSAGE);
				}
    	      }
    	      if (rVal == JFileChooser.CANCEL_OPTION) {
    	        filename.setText("You pressed cancel");
    	        dir.setText("");
    	      }
    	    }
		});
		
		//Terminate the program
		menu13.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(e.getSource()==menu13) {
						System.exit(0);
	    		}
	    	}
	    });
		      
		//Loading the file and updating the table
		menu11.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
          	int status = fc.showOpenDialog(null);
          	if (status == JFileChooser.APPROVE_OPTION) {
          		File file = fc.getSelectedFile();
          		String fileName = file.getName();
          		try {
					list = list.load(fileName);
					loadToData(list);
					table.updateUI();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
          	}
	          	
          else if (status == JFileChooser.CANCEL_OPTION) {
	          			System.out.println("calceled");
	          	}
          }
         
        });
      
		//Help message containing an explanation about the actions 
		menu21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) 
			{ 
              if (evt.getSource() == menu21)  
              { 
                  // Code To popup Dialog. 
                  JOptionPane.showMessageDialog(null,list.help(),  
                                                "HELP",  
                                                JOptionPane.INFORMATION_MESSAGE); 
              } 
			}
        });
     
	    menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));
	}
	
	/**
	 * loading the list to a 2 dimentional array of data
	 * @param product
	 */
	public void loadToData(prodctsList product) {
		if (!product.isEmpty()) {
			for (int i = 0;i<product.size();i++) {
				data[i][0]= Integer.toString(i+1);
				data[i][1]= product.getIndexedProduct(i).getTitle();
				data[i][2]=Integer.toString(product.getIndexedProduct(i).getHaveValue());
				data[i][3]=Integer.toString(product.getIndexedProduct(i).getWantValue());
				data[i][4]=Integer.toString(product.getIndexedProduct(i).WaitListSize());
		}
		}
	}
	
	/**
	 * Creating the toolbar
	 */
	public void JToolBar(){

		  toolBar = new JToolBar();
		  toolBar.setBorder(new EtchedBorder());

		  	// creating the JButtons of the toolbar.
		    JButton addButton = new JButton("Add");
		    JButton removeButton = new JButton("Remove");
		    JButton changeWantButton = new JButton("change want");
		    JButton deliveryButton = new JButton("Delivery");
		    JButton orderButton = new JButton("Order");
		    JButton sellButton = new JButton("sell");
		    //Adding action listener to every JButton and the action each one will do when pressed
		    
		    addButton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if(e.getSource()==addButton && list!=null) {
		    			String[] inputs = inputAdd();
		    			if (inputs[0]!=null && inputs[1]!=null && inputs[2]!=null) {
		    			 int have = Integer.parseInt(inputs[1]);
		    			 int want = Integer.parseInt(inputs[2]);
		    			 //adding the product to the list and loading to the data
		    			 list.addProduct(inputs[0], want,have);
		    			 loadToData(list);
		    			 //updatinf the table
		    			 table.updateUI();
		    			}
		    		
		    		}
		    		else {
		    		JOptionPane.showMessageDialog(null, "Load a file first");
		    		}	
		    	}
		    });
		    
		    changeWantButton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if(e.getSource()==changeWantButton && list!=null) {
		    			String[] inputs = stringAndIntInput("want");
		    			if (inputs[0]!=null && inputs[1]!=null) {
		    				int want = Integer.parseInt(inputs[1]);
		    				list.modify(inputs[0], want);
		    				loadToData(list);
		    				table.updateUI();
		    			}	
		    		}
		    		else {
		    		JOptionPane.showMessageDialog(null, "Load a file first");
		    		}
		    	}
		    });

		    deliveryButton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if(e.getSource()==deliveryButton && list!=null) {
		    			String[] inputs = stringAndIntInput("count");
		    			if ((inputs[0]!=null && inputs[1]!=null)) {
		    				int count = Integer.parseInt(inputs[1]);
		    				list.delivery(inputs[0],count);
		    				loadToData(list);
		    				table.updateUI();
		    			}	
		    		}
		    		else {
		    		JOptionPane.showMessageDialog(null, "Load a file first");
		    		}
		    	}
		    });
    
		    removeButton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if(e.getSource()==removeButton && list!=null) {
		    				String title = StringInput();
		    				list.removeProduct(title);
		    				loadToData(list);
		    				data[list.size()][0] ="";
		    				data[list.size()][1]="";
		    				data[list.size()][2]="";
		    				data[list.size()][3]="";
		    				data[list.size()][4]="";
		    				table.updateUI();
		    				
		    		}else {
		    		JOptionPane.showMessageDialog(null, "Load a file first");
		    		}
		    	}
		    });
		   
		    orderButton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if(e.getSource()==orderButton && list!=null) {
		    			String string = StringInput();
		    			if (string!=null) {
		    				list.Order(string);
		    				loadToData(list);
		    				table.updateUI();
		    			}
		    		}else {
		    		JOptionPane.showMessageDialog(null, "Load a file first");
		    		}
		    	}
		    });
		    
		    sellButton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if(e.getSource()==sellButton && list!=null) {
		    			String s = StringInput();
		    			if(s!=null) {
		    				try {
		    					list.sell(s);
		    					loadToData(list);
		    					table.updateUI();
		    				} catch (PhoneNumberNotValidException e1) {			
		    					JOptionPane.showMessageDialog(null, "Wrong phone number input\n\nAction cancelled");
		    					
		    				}
		    			}
		    		}else {
		    		JOptionPane.showMessageDialog(null, "Load a file first");
		    		}
		    	}
		    });
		    
		    //Adding the JButtons to the toolbar
		    toolBar.add(addButton);
		    toolBar.add(orderButton);
		    toolBar.add(sellButton);
		    toolBar.add(deliveryButton);
		    toolBar.add(changeWantButton);
		    toolBar.add(removeButton);
	  }
	
	/**
	 * Method that gets the user input.
	 * @return String that was input.
	 */
	private static String StringInput() {
		  String title=null;
		  //creating text input field
		  JTextField string = new JTextField (10);
		  Object fields[] = {"Please Enter title value",string};
		  //input message
		  int result = JOptionPane.showConfirmDialog(null, fields, 
	              "Input",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
		  //if user clicked on the ok button
	     if (result == JOptionPane.OK_OPTION) {
	   	  title = string.getText();
	     	if (title.equalsIgnoreCase("")) {
			  JOptionPane.showMessageDialog(null, "Need to enter title name.\n\n try Again");
			  title=null;
			  StringInput();
	     	}
			  } 
	     	return title;
	   	  }
	
	/**
	 * Method that gets a String and int from the user
	 * @param valName
	 * @return String list with the string input and String of the integer input
	 */
	public static String[] stringAndIntInput(String valName) {
		
		String[] inputs = new String[2];
		JTextField string = new JTextField (10);
		JTextField Int = new JTextField(5);
		Object[] fields={"Please Enter Title Value:",string,"Please Enter "+valName+" Value:",Int};
		String title=null;
		String want=null;
		//Input message with the textfields
		int result = JOptionPane.showConfirmDialog(null, fields,
				"Inputs", JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
	    if (result == JOptionPane.OK_OPTION) {
	    	title = string.getText();
	   	  	want=Int.getText(); 
	   	  	//if the user hasn't entered anything and clicked OK 
	   	  	if (title.equalsIgnoreCase("") && want.equalsIgnoreCase("")){
	   	  		JOptionPane.showMessageDialog(null, "Need to enter both fields\n\nTry again");
	   	  		//reseting the values
	   	  		want=null;title=null;
	   	  		//restarting the method.
	   	  		stringAndIntInput(valName);
	   	  	} 
	   	  	inputs[0]=title;inputs[1]=want;
	   	  	// check if the user entered an int 
	   	  	try {
	  		  Integer.parseInt(inputs[1]);
	   	  	}
	   	  	catch(NumberFormatException e1) {
	   	  		JOptionPane.showMessageDialog(null, "Wrong input (number)\n\nTry again");
	   	  		stringAndIntInput(valName);
	   	  	}	
	     }
	     return inputs;
	  }
	
	/**
	 * Method that gets a list of 3 inputs, string,int and int.
	 * @return a list of the 3 input entered by the user.
	 */
	public static String[] inputAdd() {
		  String[] strings = new String[4]; 
	      JTextField title= new JTextField(10);
	      JTextField have = new JTextField(5);
	      JTextField want = new JTextField(5);
	      Object[] fields= {"please enter title,have and want values","Enter title",title,"Enter have",have,"Enter want",want};
	    //Input message with the textfields
	      int result = JOptionPane.showConfirmDialog(null, fields, 
	               "Add Product", JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
	      if (result == JOptionPane.OK_OPTION) {
	    	  String titles = title.getText();
	    	  String wants=want.getText();
	    	  String haves=have.getText();
	    	//if the user hasn't entered anything and clicked OK 
	    	  if (titles == ""&& wants==""&& haves=="") {
	    		  JOptionPane.showMessageDialog(null, "Wrong input\n\n try Again");	
	    		//restarting the method.
	    		  inputAdd();
	    		  } 	  
	    	  	  
	    	 strings[0]=titles;strings[2]=wants;strings[1]=haves;
	    	 try {
	    	// check if the user entered an 2 ints 
	   		  Integer.parseInt(strings[2]);
	   		  Integer.parseInt(strings[1]);
	   	  }
	   	  catch(NumberFormatException e1) {
	   		  JOptionPane.showMessageDialog(null, "Wrong input\n\nTry again");
	   		  //reseting the values 
	   		  strings[0]=null;strings[1]=null;strings[2]=null;
	   		  //restarting the method
	   		  inputAdd();
	   	  }
	      }
	      return strings;
	  }
	public static void main (String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new Inventory_System_GUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
/**
 * A table model for JTable
 * @author liorz
 *
 */
class TableModel extends AbstractTableModel{
	
	private Object[][] data = new Object [10][5];
	private String [] columnName = { "","Title", "Have", "Want","Waiting list"};
	public TableModel(String[][]data) {
		this.data=data; 
	}
	public int getRowCount() {
		return data.length;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return data[0].length;
	}
	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		return (String)data[rowIndex][columnIndex];
	}
	public String getColumnName(int columnIndex) {
		return columnName[columnIndex];
	}
	public void setValueAt(Object value, int rowIndex,int columnIndex) {
		if(1 == columnIndex) {
	           data[rowIndex][1]=value.toString();
	       }
	       else if(2 == columnIndex) {
	    	   data[rowIndex][2]=value.toString();
	       }
	       else if(3 == columnIndex) {
	    	   data[rowIndex][3]=value.toString();
	       }
	       else if(4 == columnIndex) {
	    	   data[rowIndex][4]=value.toString();
	       }
	}
	public boolean isCellEditable(int rowIndex,int ColumnIndex) {
		return false;
	}
	public void fireTableCellUpdated() {
		super.fireTableDataChanged();
	}
	
}
