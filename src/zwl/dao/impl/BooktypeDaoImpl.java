package zwl.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zwl.bean.Booktype;
import zwl.dao.BaseDao;
import zwl.dao.BooktypeDao;

public class BooktypeDaoImpl extends BaseDao<Booktype> implements BooktypeDao {

	@Override
	public List<Booktype> findByCondition(Map<String, Object> map) {
		List<Booktype> list=new ArrayList<Booktype>();
		List<Object> paramList=new ArrayList<Object>();
		StringBuffer sf=new StringBuffer();
		sf.append(" select * from booktype where 1=1 ");
		if(map!=null && map.size()>0){
			if(map.get("btid")!=null){
				sf.append(" and btid=? ");
				paramList.add(map.get("btid"));
			}
			if(map.get("btype")!=null){
				sf.append(" and btype=? ");
				paramList.add(map.get("btype"));
			}
		}
		return this.executeQuery2(sf.toString(), paramList.toArray());
	}

	@Override
	public Booktype getEntity(ResultSet rs) {
		Booktype entity=new Booktype();
		try {
			entity.setBtid(rs.getInt("btid"));
			entity.setBtype(rs.getString("btype"));
			entity.setBtremark(rs.getString("btremark"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

}
