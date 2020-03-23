package zwl.dao;

import java.util.List;
import java.util.Map;

import zwl.bean.Userinfo;

public interface UserinfoDao {
	public List<Userinfo> findByCondition(Map<String, Object> map);
	
	public Userinfo findById(Integer uid);
	
	public int saveUserinfo(Userinfo entity);
	
	public int updateUserinfo(Userinfo entity);
	
	public boolean login(String unumber,String upasswd);
}
