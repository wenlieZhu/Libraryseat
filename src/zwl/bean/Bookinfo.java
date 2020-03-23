package zwl.bean;


import java.security.Timestamp;
import java.util.Date;

public class Bookinfo {
	private Integer bid;
	private Integer uid;
	private Date bdate;
	private String btime;
	private Integer pid;
	private Integer sid;
	private java.sql.Timestamp bday;
	private Integer btid;
	private String bremark;
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Date getBdate() {
		return bdate;
	}
	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}
	public String getBtime() {
		return btime;
	}
	public void setBtime(String btime) {
		this.btime = btime;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
	public java.sql.Timestamp getBday() {
		return bday;
	}
	public void setBday(java.sql.Timestamp timestamp) {
		this.bday = timestamp;
	}
	public Integer getBtid() {
		return btid;
	}
	public void setBtid(Integer btid) {
		this.btid = btid;
	}
	public String getBremark() {
		return bremark;
	}
	public void setBremark(String bremark) {
		this.bremark = bremark;
	}
}
