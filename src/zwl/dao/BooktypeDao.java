package zwl.dao;

import java.util.List;
import java.util.Map;

import zwl.bean.Booktype;

public interface BooktypeDao {
	public List<Booktype> findByCondition(Map<String, Object> map);
}
