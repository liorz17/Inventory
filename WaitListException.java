
public class WaitListException	extends Error {
	public WaitListException(String message) {
		System.out.println(message);
		System.err.println("continue?");
	}
}
