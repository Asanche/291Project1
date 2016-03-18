import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class vehicle {

	private String serial_no;
	private String maker;
	private String model;
	private String year;
	private String color;
	private String type;
	private String type_id;
	private Connection con;
	
	public vehicle(Connection con, String serial_no){
		this.con = con;
		this.serial_no = serial_no;
	}
	
	public void newVehicleInfo(){
		Scanner in = new Scanner(System.in);
		Boolean enteringVehicle = true;
		
		try{
			while(enteringVehicle){
				System.out.format("New Vehicle\n\n");
				if(this.maker==null){
					System.out.format("Enter vehicle maker: ");
					this.setMaker(in.nextLine());
					this.exit(this.maker);
				}
				if(this.model==null){
					System.out.format("Enter vehicle model: ");
					this.setModel(in.nextLine());
					this.exit(model);
				}
				if(this.year==null){
					System.out.format("Enter vehicle year: ");
					this.setYear(in.nextLine());
					this.exit(year);
				}
				if(this.color==null){
					System.out.format("Enter vehicle color: ");
					this.setColor(in.nextLine());
					this.exit(color);
				}
				if(this.type_id==null){
					System.out.format("Enter vehicle type: ");
					try{
						this.type=in.nextLine();
						this.exit(type);
						this.setType_id(this.type);
					} catch (IllegalArgumentException iae){
						System.err.println("Type not valid");
						continue;
					} catch (Exception e){
						System.err.println(e);
						continue;
					}
				}
				try{
					this.newVehicle();
					enteringVehicle = false;
				} catch (Exception e){
					this.clear();
					System.err.println(e);
				}
			}
		} catch (IllegalStateException ise){
			throw ise;
		}
	}
	
	public void newVehicle() throws Exception{
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(this.toString());
			stmt.close();
		} catch (Exception e){
			throw e;
		}
	}
	
	public static void checkVehicleMakeNew(String id, Connection con) throws Exception{
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select serial_no from vehicle where serial_no = '" + id + "'");
			if (!rs.next()){
				vehicle v = new vehicle(con,id);
				v.newVehicleInfo();
			}
			else{
				throw new IllegalArgumentException();
			}
			stmt.close();
		} catch (Exception e){
			throw e;
		}
	}
	
	public static void checkVehicle(String id, Connection con) throws Exception{
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select serial_no from vehicle where serial_no = '" + id + "'");
			if (!rs.next()){
				throw new IllegalArgumentException();
			}
			stmt.close();
		} catch (Exception e){
			throw e;
		}
	}
	
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type) throws Exception {
		
		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("select type_id from vehicle_type where type = '" + type + "'");
			if(rs.next()){
				this.type_id = rs.getString(1);
			}
			else{
				throw new IllegalArgumentException();
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
	
	public void clear(){
		this.color=null;
		this.maker=null;
		this.model=null;
		this.serial_no=null;
		this.type_id=null;
		this.year=null;
	}
	
	public void exit(String anything){
		
		if (anything.toLowerCase().equals("exit")){
			throw new IllegalStateException();
		}
	}

	@Override
	public String toString() {
		return "insert into vehicle values('" + getSerial_no() + "', '" + getMaker() + "', '" + getModel() + "', " + getYear() 
		+ ", '" + getColor() + "', " + getType_id() + ")";
	}
	
}
