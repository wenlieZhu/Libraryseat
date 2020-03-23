package zwl.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import zwl.bean.Bookinfo;

public interface BookinfoDao {
	
	public List<Bookinfo> findByCondition(Map<String, Object> map);
	
	public Bookinfo findBybid(Integer bid);
	
	//提交预约
	public int saveBookinfo(Bookinfo entity);
	
	//取消订单（修改状态）
	public int updateBookinfo(Bookinfo entity);
	
	//查询剩余座位
	//select count(*) c from bookinfo where pid=4003 and (btid=6002 or btid=6003 or btid=6004);
	public int whenseat (Integer pid);
	
	
}
