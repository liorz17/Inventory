import java.io.*;
import java.util.Scanner;
public class TelephoneNumber implements Serializable{
	private String areaCode;
	private String exchangeCode;
	private String number;
	
	public TelephoneNumber(String aString) throws PhoneNumberNotValidException {

		try{
		String[] aStringCut=aString.split("-");
		
		if (aString.length()!=12 && aString.length()!=8){
			throw  new PhoneNumberNotValidException();
			}
		if (aString.length()==12 && aStringCut[0].length()==3 && aStringCut[1].length()==3 && aStringCut[2].length()==4){
			areaCode=aStringCut[0]; 
			exchangeCode=aStringCut[1];
			number=aStringCut[2];	
			}
		else if (aString.length()==8 && aStringCut[0].length()==3 && aStringCut[1].length()==4){
			exchangeCode=aStringCut[0];
			number=aStringCut[1];	
			}
		else{
			throw  new PhoneNumberNotValidException();
		}
		}
		catch (PhoneNumberNotValidException e){
			System.out.println(aString);
			System.out.println(e.getMessage());
		}
	
	
	}
	public String toString (){
		String toString;
		if (areaCode == null){
			toString= exchangeCode+"-"+number;
		}
		else{
			toString =areaCode+"-"+exchangeCode+"-"+number;
		}
		return toString;
	}
}