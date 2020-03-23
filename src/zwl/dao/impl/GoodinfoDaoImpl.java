package zwl.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zwl.bean.Goodinfo;
import zwl.dao.BaseDao;
import zwl.dao.GoodinfoDao;

public class GoodinfoDaoImpl extends BaseDao<Goodinfo> implements GoodinfoDao {

	@Override
	public List<Goodinfo> findByCondition(Map<String, Object> map) {
		List<Goodinfo> list=new ArrayList<Goodinfo>();
		List<Object> paramList=new ArrayList<Object>();
		StringBuffer sf=new StringBuffer();
		sf.append(" select * from goodinfo where 1=1 ");
		if(map!=null && map.size()>0){
			if(map.get("goodid")!=null){
				sf.append(" and goodid=? ");
				paramList.add(map.get("goodid"));
			}
			if(map.get("gtype")!=null){
				sf.append(" and gtype=? ");
				paramList.add(map.get("gtype"));
			}
		}else {
			sf.append("order by goodid DESC");
		}
		return this.executeQuery2(sf.toString(), paramList.toArray());
	}

	@Override
	public int saveGoodinfo(Goodinfo entity) {
		String sql="insert into goodinfo(gname, gdetail, gphone) values(?,?,?)";
		Object[] paramsObjs={
				entity.getGname(),
				entity.getGdetail(),
				entity.getGphone()
		};
		return this.executeUpdate(sql, paramsObjs);
	}

	@Override
	public int updateGoodinfo(Goodinfo entity) {
		String sql="update goodinfo set gtype=? where goodid=?";
		List<Object> paramList=new ArrayList<Object>();
		paramList.add(entity.getGtype());
		paramList.add(entity.getGoodid());
		return this.executeUpdate(sql, paramList.toArray());
	}

	@Override
	public Goodinfo findById(Integer goodid) {
		String sql=" select * from goodinfo where goodid=? ";
		List<Goodinfo> list=this.executeQuery2(sql, goodid);
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	@Override
	public Goodinfo getEntity(ResultSet rs) {
		Goodinfo entity=new Goodinfo();
		try {
			entity.setGoodid(rs.getInt("goodid"));
			entity.setGname(rs.getString("gname"));
			entity.setGdetail(rs.getString("gdetail"));
			entity.setGphone(rs.getString("gphone"));
			entity.setGtime(rs.getTimestamp("gtime"));
			entity.setGtype(rs.getString("gtype"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

}
