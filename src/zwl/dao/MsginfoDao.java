package zwl.dao;

import java.util.List;
import java.util.Map;

import zwl.bean.Msginfo;

public interface MsginfoDao {
	public List<Msginfo> findByCondition(Map<String, Object> map);
	
	public int saveMsginfo(Msginfo entity);
	
	//修改mtype为off（隐藏）
	public int updateMsginfo(Msginfo entity);
	
	public Msginfo findById(Integer msgid);
}
