import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class autoSale {
	
	private String transaction_id;
	private String buyer_id;
	private String seller_id;
	private String vehicle_id;
	private String sale_date;
	private String price;
	private Connection con;
	
	public autoSale(Connection con){
		this.con = con;
		try{
			this.setTransaction_id();
		}
		catch (Exception e){
			System.err.println(e);
		}
	}
	
	
	public void newAutoSaleInfo(){
		Scanner in = new Scanner(System.in);
		Boolean enteringSale = true;
		
		while(enteringSale){
			
			System.out.format("Auto Transaction\n\n");
			if (this.buyer_id==null){
				System.out.format("Enter buyers SIN: ");
				this.setBuyer_id(in.nextLine());
				try {
					person.checkPerson(this.buyer_id, con);
				} catch (Exception e){
					this.setBuyer_id(null);
					System.err.println(e);
					continue;
				}
			}

			if (this.seller_id==null){
				System.out.format("Enter sellers SIN: ");
				this.setSeller_id(in.nextLine());
				try {
					person.checkPerson(this.seller_id, con);

				} catch (Exception e){
					this.setSeller_id(null);
					System.err.println(e);
					continue;
				}
			}
			if (this.vehicle_id==null){
				System.out.format("Enter vehicle serial number: ");
				this.setVehicle_id(in.nextLine());
				try {
					vehicle.checkVehicle(this.vehicle_id, con);
				} catch (IllegalArgumentException iae){
					System.err.println("Vehicle not in database");
					this.setVehicle_id(null);
					continue;
				} catch (Exception e){
					System.err.println(e);
					this.setVehicle_id(null);
					continue;
				}
			}
			try {
				checkOwner();
			} catch (IllegalArgumentException iae){
				System.err.println("Seller is not the primary owner of this vehicle\n");
				this.clear();
				continue;
			} catch (Exception e) {
				System.err.println(e);
				this.clear();
				continue;
			}
			if (this.sale_date==null){
				System.out.format("Enter sale date (YYYYMMDD): ");
				this.setSale_date(in.nextLine());
			}
			if (this.price==null){
				System.out.format("Enter sale price $(XXXX.XX): ");
				this.setPrice(in.nextLine());
			}
			try{
				this.newAutoTransaction();
				enteringSale = false;
			} catch (Exception e){
				this.clear();
				System.out.println(e);
			}
		}
	}
	
	public void newAutoTransaction() throws Exception{
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(this.toString());
			stmt.close();
			Statement stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt2.executeQuery("select owner_id, vehicle_id, is_primary_owner from owner where vehicle_id = '" + this.vehicle_id + "'");
			while(rs.next()){
				rs.deleteRow();
			}
			stmt2.close();
			Statement stmt1 = con.createStatement();
			stmt1.executeUpdate("insert into owner values('" + this.buyer_id + "', '" + this.vehicle_id +  "', 'y')");
			stmt1.close();
		} catch (Exception e){
			throw e;
		} 
	}
	
	public void checkOwner() throws Exception{
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from owner where owner_id = '" + this.getSeller_id() + "' and vehicle_id = '" + this.getVehicle_id() + "' and is_primary_owner = 'y'");
			if (!rs.next()){
				throw new IllegalArgumentException();
			}
			stmt.close();
		} catch (Exception e){
			throw e;
		}
	}
	
	
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(String vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public String getSale_date() {
		return sale_date;
	}
	public void setSale_date(String sale_date) {
		this.sale_date = sale_date;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}


	public String getTransaction_id() {
		return transaction_id;
	}


	public void setTransaction_id() throws Exception {
		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for (int i=1;i<100000;i++){
				ResultSet rs = stmt.executeQuery("select * from auto_sale where transaction_id = '" + i + "'");
				if (!rs.next()){
					this.transaction_id = String.valueOf(i);
					break;
				}
			}
			stmt.close();
		} catch (Exception e){
			throw e;
		}
	}
    
	public String toString() {
		return ("insert into auto_sale values('" + this.getTransaction_id() + "', '" + this.getSeller_id() + "', '" + this.getBuyer_id() + "', '" + this.getVehicle_id() + "', to_date('" + this.getSale_date() + "','YYYYMMDD'), " + this.getPrice() +  ")");	
	}
	
	public void clear() {
		this.transaction_id = null;
		this.buyer_id = null;
		this.seller_id = null;
		this.vehicle_id = null;
		this.sale_date = null;
		this.price = null;
	}
	
}
