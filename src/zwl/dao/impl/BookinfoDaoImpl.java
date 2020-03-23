package zwl.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zwl.bean.Bookinfo;
import zwl.dao.BaseDao;
import zwl.dao.BookinfoDao;

public class BookinfoDaoImpl extends BaseDao<Bookinfo> implements BookinfoDao {

	@Override
	public List<Bookinfo> findByCondition(Map<String, Object> map) {
		List<Bookinfo> list=new ArrayList<Bookinfo>();
		List<Object> paramList=new ArrayList<Object>();
		StringBuffer sf=new StringBuffer();
		sf.append(" select * from bookinfo where 1=1 ");
		if(map!=null && map.size()>0){
			if(map.get("uid")!=null){
				sf.append(" and uid=? order by bid DESC");
				paramList.add(map.get("uid"));
			}
			if(map.get("okuid")!=null){
				sf.append(" and uid=? ");
				paramList.add(map.get("okuid"));
			}
			if(map.get("bid")!=null){
				sf.append(" and bid=? ");
				paramList.add(map.get("bid"));
			}
			if(map.get("pid")!=null){
				sf.append(" and pid=? ");
				paramList.add(map.get("pid"));
			}
		}else{
			sf.append("order by bid DESC");
		}
		return this.executeQuery2(sf.toString(), paramList.toArray());
	}

	@Override
	public Bookinfo findBybid(Integer bid) {
		String sql=" select * from bookinfo where bid=? ";
		List<Bookinfo> list=this.executeQuery2(sql, bid);
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	/*
	 *  `uid` int(5) `bdate` date `btime` varchar(20) `pid` int(5) `sid` int(5) 
   `bremark` varchar(50)
	 */
	@Override
	public int saveBookinfo(Bookinfo entity) {
		String sql="insert into bookinfo (uid, bdate, btime, pid, sid, bremark) "
				+ "values(?,?,?,?,?,?)";
		Object[] paramsObjs={
				entity.getUid(),
				entity.getBdate(),
				entity.getBtime(),
				entity.getPid(),
				entity.getSid(),
				entity.getBremark()
		};
		return this.executeUpdate(sql, paramsObjs);
	}
	
	@Override
	public int updateBookinfo(Bookinfo entity) {
		String sql="update bookinfo set btid=? where bid=?";
		List<Object> paramList=new ArrayList<Object>();
		paramList.add(entity.getBtid());
		paramList.add(entity.getBid());
		return this.executeUpdate(sql, paramList.toArray());
	}
	/*
	 * `bid` int(5) `uid` int(5) `bdate` date `btime` varchar(20) `pid` int(5) `sid` int(5) 
  `bday` timestamp `btid` int(5) `bremark` varchar(50)
	 */
	@Override
	public Bookinfo getEntity(ResultSet rs) {
		Bookinfo entity=new Bookinfo();
		try {
			entity.setBid(rs.getInt("bid"));
			entity.setUid(rs.getInt("uid"));
			entity.setBdate(rs.getDate("bdate"));
			entity.setBtime(rs.getString("btime"));
			entity.setPid(rs.getInt("pid"));
			entity.setSid(rs.getInt("sid"));
			entity.setBday(rs.getTimestamp("bday"));
			entity.setBtid(rs.getInt("btid"));
			entity.setBremark(rs.getString("bremark"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	//select count(*) c from bookinfo where pid=4003 and (btid=6002 or btid=6003 or btid=6004);
	@Override
	public int whenseat(Integer pid) {
		String sql="bookinfo where pid=? and btid=6001 ";
		int seatnum=this.getCount(sql, pid);
		return seatnum;
	}


}
