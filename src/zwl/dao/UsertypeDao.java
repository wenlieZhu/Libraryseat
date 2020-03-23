package zwl.dao;

import java.util.List;
import java.util.Map;

import zwl.bean.Usertype;

public interface UsertypeDao {
	public List<Usertype> findByCondition(Map<String, Object> map);
}
