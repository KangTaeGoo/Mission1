package wifi;

public class History {
	
	
	int id;
	double lat;				   	
	double lnt;					 
	String inquireTime;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLnt() {
		return lnt;
	}
	public void setLnt(double lnt) {
		this.lnt = lnt;
	}
	public String getInquireTime() {
		return inquireTime;
	}
	public void setInquireTime(String inquireTime) {
		this.inquireTime = inquireTime;
	}
	
}
