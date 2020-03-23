package zwl.dao;

import java.util.List;
import java.util.Map;

import zwl.bean.Userstate;

public interface UserstateDao {
	public List<Userstate> findByCondition(Map<String, Object> map);
}
