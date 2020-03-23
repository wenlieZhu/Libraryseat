package zwl.dao;

import java.util.List;
import java.util.Map;

import zwl.bean.Placeinfo;

public interface PlaceinfoDao {
	public List<Placeinfo> findByCondition(Map<String, Object> map);
}
