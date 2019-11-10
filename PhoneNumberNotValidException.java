
public class PhoneNumberNotValidException extends Exception {
	public PhoneNumberNotValidException(){
		super("not valid phone number\nCustomers is not saved to the file\nTry again");
	}
	public PhoneNumberNotValidException(String message){
		JOptionPane.showMessageDialog(null, message);
	}

}
