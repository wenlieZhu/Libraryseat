package zwl.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zwl.bean.Seatinfo;
import zwl.dao.BaseDao;
import zwl.dao.SeatinfoDao;

public class SeatinfoDaoImpl extends BaseDao<Seatinfo> implements SeatinfoDao {

	@Override
	public List<Seatinfo> findByCondition(Map<String, Object> map) {
		List<Seatinfo> list=new ArrayList<Seatinfo>();
		List<Object> paramList=new ArrayList<Object>();
		StringBuffer sf=new StringBuffer();
		sf.append(" select * from seatinfo where 1=1 ");
		if(map!=null && map.size()>0){
			if(map.get("sid")!=null){
				sf.append(" and sid=? ");
				paramList.add(map.get("sid"));
			}
			if(map.get("snumber")!=null){
				sf.append(" and snumber=? ");
				paramList.add(map.get("snumber"));
			}
			if(map.get("spid")!=null){
				sf.append(" and spid=? ");
				paramList.add(map.get("spid"));
			}
			if(map.get("swindow")!=null){
				sf.append(" and swindow=? ");
				paramList.add(map.get("swindow"));
			}
			if(map.get("spower")!=null){
				sf.append(" and spower=? ");
				paramList.add(map.get("spower"));
			}
		}
		return this.executeQuery2(sf.toString(), paramList.toArray());
	}
	/*
	 * `sid` int(5) NOT NULL,
  `snumber` varchar(6) COLLATE utf8_bin NOT NULL,
  `sname` varchar(15) COLLATE utf8_bin NOT NULL,
  `spid` int(5) NOT NULL,
  `swindow` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `spower` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `sremark` varchar(50) COLLATE utf8_bin DEFAULT NULL,
	 */
	@Override
	public Seatinfo getEntity(ResultSet rs) {
		Seatinfo entity=new Seatinfo();
		try {
			entity.setSid(rs.getInt("sid"));
			entity.setSnumber(rs.getString("snumber"));
			entity.setSname(rs.getString("sname"));
			entity.setSpid(rs.getInt("spid"));
			entity.setSwindow(rs.getString("swindow"));
			entity.setSpower(rs.getString("spower"));
			entity.setSremark(rs.getString("sremark"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@Override
	public Seatinfo findById(Integer sid) {
		String sql=" select * from seatinfo where sid=? ";
		List<Seatinfo> list=this.executeQuery2(sql, sid);
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	@Override
	public int colcount(Integer spid) {
		String sql=" seatinfo where spid=? ";
		int thecount=this.getCount(sql, spid);
		return thecount;
	}

}
