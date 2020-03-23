package zwl.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zwl.bean.Msginfo;
import zwl.dao.BaseDao;
import zwl.dao.MsginfoDao;

public class MsginfoDaoImpl extends BaseDao<Msginfo> implements MsginfoDao {

	@Override
	public List<Msginfo> findByCondition(Map<String, Object> map) {
		List<Msginfo> list=new ArrayList<Msginfo>();
		List<Object> paramList=new ArrayList<Object>();
		StringBuffer sf=new StringBuffer();
		sf.append(" select * from msginfo where 1=1 ");
		if(map!=null && map.size()>0){
			if(map.get("msgid")!=null){
				sf.append(" and msgid=? ");
				paramList.add(map.get("msgid"));
			}
			if(map.get("mtype")!=null){
				sf.append(" and mtype=? ");
				paramList.add(map.get("mtype"));
			}
		}else{
			sf.append("order by msgid DESC");
		}
		return this.executeQuery2(sf.toString(), paramList.toArray());
	}

	@Override
	public int saveMsginfo(Msginfo entity) {
		String sql="insert into msginfo(mtitle, mdetail) values(?,?)";
		Object[] paramsObjs={
				entity.getMtitle(),
				entity.getMdetail()
		};
		return this.executeUpdate(sql, paramsObjs);
	}

	@Override
	public int updateMsginfo(Msginfo entity) {
		String sql="update emp set mtype=? where msgid=?";
		List<Object> paramList=new ArrayList<Object>();
		paramList.add(entity.getMtype());
		paramList.add(entity.getMsgid());
		return this.executeUpdate(sql, paramList.toArray());
	}

	@Override
	public Msginfo findById(Integer msgid) {
		String sql=" select * from msginfo where msgid=? ";
		List<Msginfo> list=this.executeQuery2(sql, msgid);
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	@Override
	public Msginfo getEntity(ResultSet rs) {
		Msginfo entity=new Msginfo();
		try {
			entity.setMsgid(rs.getInt("msgid"));
			entity.setMtitle(rs.getString("mtitle"));
			entity.setMdetail(rs.getString("mdetail"));
			entity.setMtime(rs.getTimestamp("mtime"));
			entity.setMtype(rs.getString("mtype"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

}
