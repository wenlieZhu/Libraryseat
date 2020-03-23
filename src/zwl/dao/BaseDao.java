package zwl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao<T> {
	private String driverClass="com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://localhost:3306/library?characterEncoding=utf-8";
	private String username="root";
	private String password="ZHUwenlie,2019";
	
	protected Connection conn=null;
	protected PreparedStatement pra=null;
	protected ResultSet rs=null;
	
	public Connection getConnection(){
		try {
			Class.forName(driverClass);
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void closeAll(ResultSet rs,Statement sta,Connection conn){
		try {
			if(rs!=null) rs.close();
			if(sta!=null) sta.close();
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int executeUpdate(String sql,Object... paramsObjs){
		Connection conn=this.getConnection();
		PreparedStatement pra=null;
		try {
			pra=conn.prepareStatement(sql);
			if(paramsObjs!=null && paramsObjs.length>0){
				for(int i=0;i<paramsObjs.length;i++){
					pra.setObject(i+1, paramsObjs[i]);
				}
			}
			return pra.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally{
			this.closeAll(null, pra, conn);
		}
	}
	
	public ResultSet executeQuery(String sql,Object... paramsObjs) throws Exception{
		conn=this.getConnection();
		pra=conn.prepareStatement(sql);
		if(paramsObjs!=null && paramsObjs.length>0){
			for(int i=0;i<paramsObjs.length;i++){
				pra.setObject(i+1, paramsObjs[i]);
			}
		}
		ResultSet rs=pra.executeQuery();
		return rs;
	}
	
	public List<T> executeQuery2(String sql,Object... paramsObjs){
		List<T> list=new ArrayList<T>();
		Connection conn=this.getConnection();
		PreparedStatement pra=null;
		ResultSet rs=null;
		try {
			pra=conn.prepareStatement(sql);
			if(paramsObjs!=null && paramsObjs.length>0){
				for(int i=0;i<paramsObjs.length;i++){
					pra.setObject(i+1, paramsObjs[i]);
				}
			}
			rs=pra.executeQuery();
			while(rs.next()){
				list.add(this.getEntity(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			this.closeAll(rs, pra, conn);
		}
		return list;
	}
	
	public abstract T getEntity(ResultSet rs);

	public int getCount(String sql,Object... paramsObjs){
		Connection conn=this.getConnection();
		PreparedStatement pra=null;
		ResultSet rs=null;
		try {
			String countSql="select count(*) c from "+sql+"";
			pra=conn.prepareStatement(countSql);
			if(paramsObjs!=null && paramsObjs.length>0){
				for(int i=0;i<paramsObjs.length;i++){
					pra.setObject(i+1, paramsObjs[i]);
				}
			}
			rs=pra.executeQuery();
			if(rs.next()){
				return rs.getInt("c");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			this.closeAll(rs, pra, conn);
		}
		return 0;
	}
}
