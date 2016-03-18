import java.sql.*;

//jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS

public class project1Main {
	


	public static void main(String[] args) throws Exception {
		
		Connection con = null;
		String m_driverName = "oracle.jdbc.driver.OracleDriver";
		while(con==null){
			userInfo info = menu.getUserInfo();
			System.out.format("url: %s\nusername: %s\npassword: %s\n\n",info.getM_url(),info.getM_username(),info.getM_password());
			try{
				con = sql.getConnected(m_driverName, info.getM_url(), info.getM_username(), info.getM_password());
				System.out.println(con);
			} catch (Exception e){
				System.out.println(e);
			}
		}
		
		Boolean programRun = true;
		while(programRun){
			String choice = menu.applicationChoose().toLowerCase();
			
			switch (choice) {
			case "1": 
				vehicleRegistration vR = new vehicleRegistration(con);
				vR.newVehicleRegistrationInfo();
				break;
			case "2":
				autoSale aS = new autoSale(con);
				aS.newAutoSaleInfo();
				break;
			case "3":
				System.out.println("Driver Licence Registration");
				break;
			case "4":
				System.out.println("Violation Record");
				break;
			case "5":
				System.out.println("Search Engine");
				break;
			case "exit":
				programRun = false;
				break;
			default:
				System.out.println("Incorrect entry");
				break;
			
			}
		}
		con.close();
		System.out.println("Program finished");
		

	}
	


}
