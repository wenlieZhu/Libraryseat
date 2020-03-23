package zwl.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zwl.bean.Departinfo;
import zwl.dao.BaseDao;
import zwl.dao.DepartinfoDao;

public class DepartinfoDaoImpl extends BaseDao<Departinfo> implements DepartinfoDao {

	@Override
	public List<Departinfo> findByCondition(Map<String, Object> map) {
		List<Departinfo> list=new ArrayList<Departinfo>();
		List<Object> paramList=new ArrayList<Object>();
		StringBuffer sf=new StringBuffer();
		sf.append(" select * from departinfo where 1=1 ");
		if(map!=null && map.size()>0){
			if(map.get("did")!=null){
				sf.append(" and did=? ");
				paramList.add(map.get("did"));
			}
		}
		return this.executeQuery2(sf.toString(), paramList.toArray());
	}

	@Override
	public Departinfo getEntity(ResultSet rs) {
		Departinfo entity=new Departinfo();
		try {
			entity.setDid(rs.getInt("did"));
			entity.setDname(rs.getString("dname"));
			entity.setDremark(rs.getString("dremark"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

}
