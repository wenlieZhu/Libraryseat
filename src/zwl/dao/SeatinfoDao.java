package zwl.dao;

import java.util.List;
import java.util.Map;

import zwl.bean.Seatinfo;

public interface SeatinfoDao {
	public List<Seatinfo> findByCondition(Map<String, Object> map);
	
	public Seatinfo findById(Integer sid);
	
	public int colcount(Integer spid);
	
}
