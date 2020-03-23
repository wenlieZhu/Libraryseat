package zwl.bean;

public class Msginfo {
	private Integer msgid;
	private String mtitle;
	private String mdetail;
	private java.sql.Timestamp mtime;
	private String mtype;
	public Integer getMsgid() {
		return msgid;
	}
	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}
	public String getMtitle() {
		return mtitle;
	}
	public void setMtitle(String mtitle) {
		this.mtitle = mtitle;
	}
	public String getMdetail() {
		return mdetail;
	}
	public void setMdetail(String mdetail) {
		this.mdetail = mdetail;
	}
	public java.sql.Timestamp getMtime() {
		return mtime;
	}
	public void setMtime(java.sql.Timestamp mtime) {
		this.mtime = mtime;
	}
	public String getMtype() {
		return mtype;
	}
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
}
