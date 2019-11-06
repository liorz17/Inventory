import java.io.*;
import java.util.Scanner;
public class Customers implements Serializable{
	private String firstName;
	private String lastName;
	private String phoneNumber;
	public Customers(String firstName,String lastName,String phoneNumber) throws PhoneNumberNotValidException {
		this.firstName=firstName;
		this.lastName=lastName;
		TelephoneNumber phone = new TelephoneNumber(phoneNumber);
		this.phoneNumber = phone.toString();
		}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String toString() {
		return firstName+" "+lastName+": "+phoneNumber;
	}
}
