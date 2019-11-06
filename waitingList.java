import java.util.Queue;
import java.io.Serializable;
import java.util.LinkedList;
public class waitingList implements Serializable {
	private Queue<Customers> list;
	public waitingList() {
		 list= new LinkedList<Customers>();
	}
	
	public void add(Customers r) {
		list.add(r);
	}
	public void remove() {
		list.remove();
	}
	public void removeAll() {
		list.clear();
		
	}
	public Customers get() {
		return list.peek();
	}
	
	public int size() {
		return list.size();
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}

}