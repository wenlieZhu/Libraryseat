package zwl.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zwl.bean.Usertype;
import zwl.dao.BaseDao;
import zwl.dao.UsertypeDao;

public class UsertypeDaoImpl extends BaseDao<Usertype> implements UsertypeDao {

	@Override
	public List<Usertype> findByCondition(Map<String, Object> map) {
		List<Usertype> list=new ArrayList<Usertype>();
		List<Object> paramList=new ArrayList<Object>();
		StringBuffer sf=new StringBuffer();
		sf.append(" select * from usertype where 1=1 ");
		if(map!=null && map.size()>0){
			if(map.get("utid")!=null){
				sf.append(" and utid=? ");
				paramList.add(map.get("utid"));
			}
		}
		return this.executeQuery2(sf.toString(), paramList.toArray());
	}

	@Override
	public Usertype getEntity(ResultSet rs) {
		Usertype entity=new Usertype();
		try {
			entity.setUtid(rs.getInt("utid"));
			entity.setUtname(rs.getString("utname"));
			entity.setUtremark(rs.getString("utremark"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}

}
