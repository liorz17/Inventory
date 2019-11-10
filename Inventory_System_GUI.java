import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class Inventory_System_GUI extends JPanel {
  private static JTextField filename = new JTextField();
  private static JTextField dir = new JTextField();
  private static JMenuBar menuBar;
  private static JFrame frame;
  private static JToolBar toolBar;
  private static JTable table;
  private final static String filePath=System.getProperty("user.dir");
  private static prodctsList list=new prodctsList();
  
  public Inventory_System_GUI() throws IOException {
    super(true);
    
    frame= new JFrame("Inventory");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Create a menu bar and give it a bevel border.
    menuBar = new JMenuBar();
    menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));
    
    // Create a menu and add it to the menu bar.
    JMenus();
    JToolBar();
    JTable();
    // Create a toolbar and give it an etched border.
    frame.setJMenuBar(menuBar);
    frame.getContentPane().add(toolBar, BorderLayout.NORTH);
    frame.setSize(700, 500);
    frame.setVisible(true);
    
   
  }
  public void JToolBar(){

	  toolBar = new JToolBar();
	    toolBar.setBorder(new EtchedBorder());

	    // Instantiate a sample action with the NAME property of
	    // "Download" and the appropriate SMALL_ICON property.
	    
	    
	    JButton addButton = new JButton("Add");
	    JButton removeButton = new JButton("Remove");
	    JButton changeWantButton = new JButton("change want");
	    JButton deliveryButton = new JButton("Delivery");
	    JButton orderButton = new JButton("Order");
	    JButton quitButton = new JButton(" Save & Quit ");
	    JButton sellButton = new JButton("sell");
	    quitButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(e.getSource()==quitButton) {
	    			try {
	    				System.out.println("quit");
						list.quit("Incoming shipment.txt", list);
						System.exit(0);
					} catch (IOException e1) {
						
						System.out.println("file Not Found");
					}
	    		}
	    	}
	    });
	    addButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(e.getSource()==addButton) {
	    			String[] inputs = inputAdd();
	    			if (inputs[0]!=null && inputs[1]!=null && inputs[2]!=null) {
	    			 int have = Integer.parseInt(inputs[1]);
	    			 int want = Integer.parseInt(inputs[2]);
	    			 list.addProduct(inputs[0], want,have);;	
	    			}
	    		
	    		}
	    	}
	    });
	  
	    changeWantButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(e.getSource()==changeWantButton) {
	    			String[] inputs = stringAndIntInput("want");
	    			if (inputs[0]!=null && inputs[1]!=null) {
	    				int want = Integer.parseInt(inputs[1]);
	    				list.modify(inputs[0], want);	
	    			}	
	    		}
	    	}
	    });

	    deliveryButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(e.getSource()==deliveryButton) {
	    			String[] inputs = stringAndIntInput("count");
	    			if ((inputs[0]!=null && inputs[1]!=null)) {
	    				int count = Integer.parseInt(inputs[1]);
	    				list.delivery(inputs[0],count);	
	    			}	
	    		}
	    	}
	    });
	    
	    
	    removeButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(e.getSource()==removeButton) {
	    				String title = StringInput();
	    				list.removeProduct(title);
	    		}
	    	}
	    });
	    orderButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(e.getSource()==orderButton) {
	    			String string = StringInput();
	    			System.out.println(string);
	    			if (string!=null) {
	    			list.Order(string);
	    			}
	    			
	    		}
	    	}
	    });
	    sellButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(e.getSource()==sellButton) {
	    			String s = StringInput();
	    			if(s!=null) {
	    				try {
	    					list.sell(StringInput());
	    				} catch (PhoneNumberNotValidException e1) {			
	    					JOptionPane.showMessageDialog(null, "Wrong phone number input\n\nAction cancelled");
	    				}
	    			}
	    		}
	    	}
	    });
	    
	    toolBar.add(addButton);
	    toolBar.add(orderButton);
	    toolBar.add(sellButton);
	    toolBar.add(deliveryButton);
	    toolBar.add(changeWantButton);
	    toolBar.add(removeButton);
	    toolBar.add(quitButton);
  }
  public static void Load(String fileName) {
	  try {
			list = list.load(fileName);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found");
			return;
		} 
  }
  
  public static void JTable()  {
	  if (list.isEmpty()){
		  Load("Incoming shipment.txt");
	  }

		table = new JTable();
		// Data to be displayed in the JTable 
		String[][] data = new String [20][6];
	     
		if (list!=null) {
			for (int i=0;i<list.size();i++) {
				Integer r= i+1;
				Integer k,t,q;
				q= list.getIndexedProduct(i).WaitListSize();
				k = list.getIndexedProduct(i).getHaveValue();
				t = list.getIndexedProduct(i).getWantValue();
				data[i][0] = r.toString();
				data[i][1] = list.getIndexedProduct(i).getTitle();
				data[i][2] = k.toString();
				data[i][3] = t.toString();
				data[i][4] =  q.toString();
			}
		}
		table.repaint();
	        for (int i=0;i<6 && i<data.length;i++) {
	        	Integer r = i+1;
	        	data[i][0]= r.toString();
	        	}
	        
	  
	        // Column Names 
	        String[] columnNames = { "","Title", "Have", "Want","Waiting list","Others" }; 
	  
	        // Initializing the JTable 
	        table = new JTable(data, columnNames); 
	        table.setBounds(30, 40, 200, 300); 
	       
	        // adding it to JScrollPane 
	        JScrollPane sp = new JScrollPane(table); 
	        frame.add(sp); 
	        
	}
	
	public static void JMenus() {
		JMenu m1 = new JMenu("File");
		JMenu m2 = new JMenu("Help");
      menuBar.add(m1);
      menuBar.add(m2);
      JPopupMenu popup = new JPopupMenu();
      
      JMenuItem menu2 = new JMenuItem ("Open");
      JMenuItem menu3 = new JMenuItem("Save");
      JMenuItem menu4 = new JMenuItem("quit");
      JMenuItem menu21 = new JMenuItem("help");
      m1.add(menu2);
      m1.add(menu3);
      m1.add(menu4);
      m2.add(menu21);
      JFileChooser fc = new JFileChooser(filePath);
      menu3.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
    	      // Demonstrate "Save" dialog:
    	      int rVal = fc.showSaveDialog(null);
    	      if (rVal == JFileChooser.APPROVE_OPTION) {
    	    	  File file = fc.getSelectedFile();
    	    	  String fileName = file.getName();
    	        filename.setText(fc.getSelectedFile().getName());
    	        dir.setText(fc.getCurrentDirectory().toString());
    	        System.out.println("saved!");
    	        try {
					list.quit(fileName, list);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("file not found");
				}
    	      }
    	      if (rVal == JFileChooser.CANCEL_OPTION) {
    	        filename.setText("You pressed cancel");
    	        dir.setText("");
    	      }
    	    }
      });
      menu4.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(e.getSource()==menu4) {
						System.exit(0);
	    		}
	    	}
	    });
      
      
      
      menu2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
          	int status = fc.showOpenDialog(null);
          	if (status == JFileChooser.APPROVE_OPTION) {
          		 File file = fc.getSelectedFile();
          		 String fileName = file.getName();
          		try {
					list = list.load(fileName);
					//JTable();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
          		
          		
	          	} else if (status == JFileChooser.CANCEL_OPTION) {
	          			System.out.println("calceled");
	          	}
          }
         
        });
      
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
      frame.setJMenuBar(menuBar);
	    menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));
	}

  
  public static String[] inputAdd() {
	  String[] strings = new String[4]; 
      JTextField title= new JTextField(10);
      JTextField have = new JTextField(5);
      JTextField want = new JTextField(5);
      JTextField title2 = new JTextField(10);
      JPanel myPanel = new JPanel();
      JPanel panel = new JPanel();
      myPanel.add(new JLabel("title:"));
      myPanel.add(title);
      myPanel.add(Box.createHorizontalStrut(10));
      myPanel.add(new JLabel("have:"));
      myPanel.add(want);
      myPanel.add(Box.createHorizontalStrut(10));
      myPanel.add(new JLabel("want:"));
      myPanel.add(have);
      panel.add(new JLabel("title:"));
      panel.add(title2);
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Please Enter title and wants and have Values", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
    	  String titles = title.getText();
    	  String wants=want.getText();
    	  String haves=have.getText();
    	  if (titles == ""&& wants==""&& haves=="") {
    		  JOptionPane.showMessageDialog(null, "Wrong input\n\n try Again");	
    		  inputAdd();
    		  } 	  
    	  	  
    	 strings[0]=titles;strings[2]=wants;strings[1]=haves;
    	 try {
   		  Integer.parseInt(strings[2]);
   		  Integer.parseInt(strings[1]);
   	  }
   	  catch(NumberFormatException e1) {
   		  JOptionPane.showMessageDialog(null, "Wrong input\n\nTry again");
   		  strings[0]=null;strings[1]=null;strings[2]=null;
   		  inputAdd();
   	  }
      }
      return strings;
  }
  
  public static String StringInput() {
	  JTextField string = new JTextField (10);
	  JPanel panel = new JPanel();
	  panel.add(new JLabel("title:"));
	  panel.add(string);
	  String title=null;
	  int result = JOptionPane.showConfirmDialog(null, panel, 
              "Please Enter title value",JOptionPane.OK_CANCEL_OPTION);
     if (result == JOptionPane.OK_OPTION) {
   	  title = string.getText();
     	if (title == "") {
		  JOptionPane.showMessageDialog(null, "Wrong input\n\n try Again");	
		  title=null;
		  StringInput();
     	}
		  } 
     	return title;
   	  }
  public static int  intInput(String valName) {
	  JTextField Int = new JTextField(5);
	  JPanel panel = new JPanel();
	  int want =-1;
	  panel.add(new JLabel("want:"));
	  panel.add(Int);
	  int result = JOptionPane.showConfirmDialog(null, panel, 
              "Please Enter "+valName+" Value", JOptionPane.OK_CANCEL_OPTION);
     if (result == JOptionPane.OK_OPTION) {
    	 try {
	  want = Integer.parseInt(Int.getText());
    	 }
     catch(NumberFormatException e){
	  JOptionPane.showMessageDialog(null, "Wrong input\n\nTry again");
	  intInput(valName);
     }
    	 if (want==-1) {
    		  JOptionPane.showMessageDialog(null, "Wrong have or want input\n\n try Again");	
    		  intInput(valName);
    		  }
  }
  
  return want;
  }
  public static String[] stringAndIntInput(String valName) {
	  String[] inputs = new String[2];
	  JTextField string = new JTextField (10);
	  JTextField Int = new JTextField(5);
	  JPanel panel = new JPanel();
	  panel.add(new JLabel("title:"));
	  panel.add(string);
	  panel.add(Box.createHorizontalStrut(10));
	  panel.add(new JLabel(valName));
	  panel.add(Int);
	  String title=null;
	  String want=null;
	  int result = JOptionPane.showConfirmDialog(null, panel, 
              "Please Enter Title and wants Values", JOptionPane.OK_CANCEL_OPTION);
     if (result == JOptionPane.OK_OPTION) {
   	  title = string.getText();
   	  want=Int.getText(); 
   	  System.out.println(title+" W: "+want);
   	  	if (title=="" || want==""){
   	  		JOptionPane.showMessageDialog(null, "Wrong input\n\nTry again");
   	  		stringAndIntInput(valName);
   	  	} 
   	  	try {
  		  Integer.parseInt(want);
   	  	}
   	  	catch(NumberFormatException e1) {
   	  		JOptionPane.showMessageDialog(null, "Wrong input\n\nTry again");
   	  		stringAndIntInput(valName);
   	  	}
   	  	inputs[0]=title;inputs[1]=want;
     	}
   
     return inputs;
  }
  
  
  public static void main(String s[]) throws IOException {
	  Inventory_System_GUI example = new Inventory_System_GUI();
    
  }
}
