import java.util.*;
import java.sql.*;



public class vehicleRegistration {

	private String primary_owner_sin;
	private String is_primary_owner;
	private LinkedList<String> secondary_owner_sin = new LinkedList<String>();
	private Integer secondary_owner_number;
	private String serial_no;
	private Connection con;
	
	public vehicleRegistration(Connection con){
		this.con = con;
		this.is_primary_owner = "y";
	}
	
	public void newVehicleRegistrationInfo(){
		Scanner in = new Scanner(System.in);
		Boolean enteringVehicleRegistration = true;
		
		System.out.format("New Vehicle Registration\n\n");
		try{
			while(enteringVehicleRegistration){
				if(this.primary_owner_sin==null){
					System.out.format("Enter primary owner's SIN: ");
					this.setPrimary_owner_sin(in.nextLine());
					try {
						person.checkPerson(this.getPrimary_owner_sin(), con);;
					} catch (Exception e){
						System.err.println(e);
						this.primary_owner_sin=null;
						continue;
					}
				}
				if(this.secondary_owner_number==null){
					System.out.format("How many secondary owners: ");
					this.secondary_owner_number=Integer.valueOf(in.nextLine());
					
				}
				while(this.secondary_owner_sin.size()<this.secondary_owner_number){
					System.out.format("Enter secondary owner # %d SIN: ",this.secondary_owner_sin.size()+1);
					this.secondary_owner_sin.add(in.nextLine());
					try {
						person.checkPerson(this.secondary_owner_sin.getLast(), con);
					} catch (Exception e) {
						System.err.println(e);
						this.secondary_owner_sin.removeLast();
						continue;
					}
				}
				if(this.serial_no==null){
					System.out.format("Please enter vehicle serial number: ");
					this.setSerial_no(in.nextLine());
					try {
						vehicle.checkVehicleMakeNew(this.getSerial_no(), con);
					} catch (IllegalArgumentException iae) {
						System.err.println("Vehicle already in database\n");
						this.serial_no=null;
						continue;
					} catch (IllegalStateException ise){
						throw ise;
					} catch (Exception e){
						System.err.println(e);
						this.serial_no=null;
						continue;
					}
				}
				try{
					this.newVehicleRegistration();
					enteringVehicleRegistration = false;
				} catch (Exception e){
					System.err.println(e);
				}
			}
		}catch(IllegalStateException ise){
			
		}
	}
	

	public void newVehicleRegistration() throws Exception{
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(this.toString());
			
			for(int i=0;i<this.secondary_owner_number;i++){
				stmt.executeUpdate("insert into owner values('" + this.secondary_owner_sin.get(i) + "', '" + this.serial_no + "', 'n')");
			}
			stmt.close();
		} catch (Exception e){
			throw e;
		}
		
	}
	
	
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getIs_primary_owner() {
		return is_primary_owner;
	}
	public void setIs_primary_owner(String is_primary_owner) {
		this.is_primary_owner = is_primary_owner;
	}

	
	
	public String toString() {
		return ("insert into owner values('" + this.getPrimary_owner_sin() + "', '" + this.getSerial_no() + "', '" + this.getIs_primary_owner() + "')");	
	}

	public String getPrimary_owner_sin() {
		return primary_owner_sin;
	}

	public void setPrimary_owner_sin(String primary_owner_sin) {
		this.primary_owner_sin = primary_owner_sin;
	}
}

