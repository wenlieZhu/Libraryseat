package zwl.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zwl.bean.Placeinfo;
import zwl.dao.BaseDao;
import zwl.dao.PlaceinfoDao;

public class PlaceinfoDaoImpl extends BaseDao<Placeinfo> implements PlaceinfoDao {

	@Override
	public List<Placeinfo> findByCondition(Map<String, Object> map) {
		List<Placeinfo> list=new ArrayList<Placeinfo>();
		List<Object> paramList=new ArrayList<Object>();
		StringBuffer sf=new StringBuffer();
		sf.append(" select * from placeinfo where 1=1 ");
		if(map!=null && map.size()>0){
			if(map.get("pid")!=null){
				sf.append(" and pid=? ");
				paramList.add(map.get("pid"));
			}
			if(map.get("pnumber")!=null){
				sf.append(" and pnumber=? ");
				paramList.add(map.get("pnumber"));
			}
		}
		return this.executeQuery2(sf.toString(), paramList.toArray());
	}

	@Override
	public Placeinfo getEntity(ResultSet rs) {
		Placeinfo entity=new Placeinfo();
		try {
			entity.setPid(rs.getInt("pid"));
			entity.setPname(rs.getString("pname"));
			entity.setPnumber(rs.getString("pnumber"));
			entity.setPremark(rs.getString("premark"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

}
