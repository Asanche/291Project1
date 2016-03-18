import java.util.*;
import java.sql.*;


public class menu {
	
	
	public static userInfo getUserInfo(){
		Scanner in = new Scanner(System.in);
		userInfo info = new userInfo();
	
		System.out.format("Enter url: ");
		info.setM_url(in.nextLine());
		System.out.format("Enter username: ");
		info.setM_username(in.nextLine());
		System.out.format("Enter password: ");
		info.setM_password(in.nextLine());
		return(info);
		
	}
	
	public static String applicationChoose(){
		Scanner in = new Scanner(System.in);
		String choice;
		System.out.print("\nPlease input 1-5 or print exit then hit enter.\n1 - New Vehicle Registration\n2 - Auto Transaction\n3 - Driver Licence Registration\n4 - Violation Record\n5 - Search Engine\n");
		choice = in.nextLine();
		return(choice);
	}

	
}
