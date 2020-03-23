package zwl.dao;

import java.util.List;
import java.util.Map;

import zwl.bean.Goodinfo;

public interface GoodinfoDao {
	public List<Goodinfo> findByCondition(Map<String, Object> map);
	
	public int saveGoodinfo(Goodinfo entity);
	
	//修改gtype为off（隐藏）
	public int updateGoodinfo(Goodinfo entity);
	
	public Goodinfo findById(Integer goodid);
}
