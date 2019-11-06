import java.util.LinkedList;
import java.util.Queue;
import java.io.Serializable;
import java.util.ArrayList;

public class InventoryItem implements Serializable{
	private int have,want;
	private String title;
	private waitingList waitList;
	public InventoryItem() {
		waitList = new waitingList();
		have = 0;
		want=0;
		title="";
	}
	public InventoryItem(String title,int have, int want) {
		waitList = new waitingList();
		this.have =have;
		this.want=want;
		this.title=title;
	}
	public InventoryItem(String title) {
		this.title=title;
		
	}
	public void addToWaitList(Customers r) {
		waitList.add(r);
	}
	public void removeFromWaitList() {
		if (!waitList.isEmpty())
		waitList.remove();
		else System.out.println("list is Empty");
	}
	public Customers get() throws WaitListException {
		if (!waitList.isEmpty()) return waitList.get();
		else throw new WaitListException ("List is empty");
	}
	public  void setHaveValue(int have) {
		this.have=have;
	}
	public void setWantValue(int want) {
		this.want=want;
	}
	public int getWantValue() {
		return want;
	}
	public int getHaveValue() {
		return have;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public int WaitListSize() {
		return this.waitList.size();
	}
	public String toString() {
		return "Title name: "+this.title+" Have:"+ this.have+" Want: "+this.want;
	}

	
}
