import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class person {
	
	private String sin;
	private String name;
	private String height;
	private String weight;
	private String eyeColor;
	private String hairColor;
	private String address;
	private String gender;
	private String birthday;
	private Connection con;
	
	public person(Connection con, String sin){
		this.con = con;
		this.sin = sin;
	}
	
	public void newPersonInfo(){
		Scanner in = new Scanner(System.in);
		Boolean enteringPerson = true;
		
		while(enteringPerson){
			System.out.format("New Person\n\n");
			System.out.format("Enter name: ");
			this.setName(in.nextLine());
			System.out.format("Enter height: ");
			this.setHeight(in.nextLine());
			System.out.format("Enter weight: ");
			this.setWeight(in.nextLine());
			System.out.format("Enter eye color: ");
			this.setEyeColor(in.nextLine());
			System.out.format("Enter hair color: ");
			this.setHairColor(in.nextLine());
			System.out.format("Enter address: ");
			this.setAddress(in.nextLine());
			System.out.format("Enter gender (F/M): ");
			this.setGender(in.nextLine().toLowerCase());
			System.out.format("Enter birthday (YYYYMMDD): ");
			this.setBirthday(in.nextLine());
			try{
				this.newPerson();
				enteringPerson = false;
			} catch (Exception e){
				System.out.println(e);
			}
		}
	}
	
	public void newPerson() throws Exception{
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(this.toString());
			stmt.close();
		} catch (Exception e){
			throw e;
		}
	}
	
	public static void checkPerson(String id, Connection con) throws Exception{
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select sin from people where sin = '" + id + "'");
			if (!rs.next()){
				person p = new person(con,id);
				p.newPersonInfo();
			}
			stmt.close();
		} catch (Exception e){
			throw e;
		}
	}
	
	
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getEyeColor() {
		return eyeColor;
	}
	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}
	public String getHairColor() {
		return hairColor;
	}
	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "insert into people values('" + getSin() + "', '" + getName() + "', " + getHeight()
				+ ", " + getWeight() + ", '" + getEyeColor() + "', '"
				+ getHairColor() + "', '" + getAddress() + "', '" + getGender()
				+ "', to_date('" + getBirthday() + "','YYYYMMDD'))";
	}
}
