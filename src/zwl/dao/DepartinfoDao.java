package zwl.dao;

import java.util.List;
import java.util.Map;

import zwl.bean.Departinfo;

public interface DepartinfoDao {
	public List<Departinfo> findByCondition(Map<String, Object> map);
}
