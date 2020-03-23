package zwl.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zwl.bean.Userstate;
import zwl.dao.BaseDao;
import zwl.dao.UserstateDao;

public class UserstateDaoImpl extends BaseDao<Userstate> implements UserstateDao {

	@Override
	public List<Userstate> findByCondition(Map<String, Object> map) {
		List<Userstate> list=new ArrayList<Userstate>();
		List<Object> paramList=new ArrayList<Object>();
		StringBuffer sf=new StringBuffer();
		sf.append(" select * from userstate where 1=1 ");
		if(map!=null && map.size()>0){
			if(map.get("usid")!=null){
				sf.append(" and usid=? ");
				paramList.add(map.get("usid"));
			}
		}
		return this.executeQuery2(sf.toString(), paramList.toArray());
	}

	@Override
	public Userstate getEntity(ResultSet rs) {
		Userstate entity=new Userstate();
		try {
			entity.setUsid(rs.getInt("usid"));
			entity.setUstate(rs.getString("ustate"));
			entity.setUsreason(rs.getString("usreason"));
			entity.setUsremark(rs.getString("usremark"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

}
