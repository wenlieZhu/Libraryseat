package zwl.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zwl.bean.Userinfo;
import zwl.dao.BaseDao;
import zwl.dao.UserinfoDao;

public class UserinfoDaoImpl extends BaseDao<Userinfo> implements UserinfoDao {

	@Override
	public List<Userinfo> findByCondition(Map<String, Object> map) {
		List<Userinfo> list=new ArrayList<Userinfo>();
		List<Object> paramList=new ArrayList<Object>();
		StringBuffer sf=new StringBuffer();
		sf.append(" select * from userinfo where 1=1 ");
		if(map!=null && map.size()>0){
			if(map.get("uid")!=null){
				sf.append(" and uid=? ");
				paramList.add(map.get("uid"));
			}
			if(map.get("unumber")!=null){
				sf.append(" and unumber=? ");
				paramList.add(map.get("unumber"));
			}
		}else{
			sf.append("order by uid DESC");
		}
		return this.executeQuery2(sf.toString(), paramList.toArray());
	}

	@Override
	public Userinfo getEntity(ResultSet rs) {
		Userinfo entity=new Userinfo();
		try {
			entity.setUid(rs.getInt("uid"));
			entity.setUname(rs.getString("uname"));
			entity.setUnumber(rs.getString("unumber"));
			entity.setUpasswd(rs.getString("upasswd"));
			entity.setUrex(rs.getString("urex"));
			entity.setUphone(rs.getString("uphone"));
			entity.setDid(rs.getInt("did"));
			entity.setUemail(rs.getString("uemail"));
			entity.setUcard(rs.getString("ucard"));
			entity.setUtid(rs.getInt("utid"));
			entity.setUtphone(rs.getString("utphone"));
			entity.setUqq(rs.getString("uqq"));
			entity.setUwechat(rs.getString("uwechat"));
			entity.setUsid(rs.getInt("usid"));
			entity.setUremark(rs.getString("uremark"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public Userinfo findById(Integer uid) {
		String sql=" select * from userinfo where uid=? ";
		List<Userinfo> list=this.executeQuery2(sql, uid);
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	@Override
	public int saveUserinfo(Userinfo entity) {
		String sql="insert into userinfo(uname, unumber, upasswd, urex, uphone, did, uemail, "
				+ "ucard, utid, utphone, uqq, uwechat, uremark) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramsObjs={
			/*entity.getUid(),*/
			entity.getUname(),
			entity.getUnumber(),
			entity.getUpasswd(),
			entity.getUrex(),
			entity.getUphone(),
			entity.getDid(),
			entity.getUemail(),
			entity.getUcard(),
			entity.getUtid(),
			entity.getUtphone(),
			entity.getUqq(),
			entity.getUwechat(),
		//	entity.getUsid(),
			entity.getUremark()
		};
		return this.executeUpdate(sql, paramsObjs);
	}

	@Override
	public int updateUserinfo(Userinfo entity) {
		String sql="update userinfo set uname=?,unumber=?,upasswd=?,"
				+ "uphone=?,did=?,uemail=?,utphone=?,ucard=?,uqq=?,"
				+ "uwechat=?,uremark=? where uid=?";
		List<Object> paramList=new ArrayList<Object>();
		paramList.add(entity.getUname());
		paramList.add(entity.getUnumber());
		paramList.add(entity.getUpasswd());
		paramList.add(entity.getUphone());
		paramList.add(entity.getDid());
		paramList.add(entity.getUemail());
		paramList.add(entity.getUtphone());
		paramList.add(entity.getUcard());
		paramList.add(entity.getUqq());
		paramList.add(entity.getUwechat());
		paramList.add(entity.getUremark());
		paramList.add(entity.getUid());
		return this.executeUpdate(sql, paramList.toArray());
	}
	
	@Override
	public boolean login(String unumber, String upasswd) {
		boolean flag=false;
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		List<Userinfo> list=userinfoDao.findByCondition(new HashMap<String, Object>());
		for(Userinfo userinfo:list){
			if(userinfo.getUnumber().equals(unumber) && userinfo.getUpasswd().equals(upasswd)){
				flag=true;
			}
		}
		
		return flag;
	}

}
