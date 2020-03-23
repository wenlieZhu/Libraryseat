package zwl.demo;

import java.io.Writer;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import zwl.bean.Bookinfo;
import zwl.bean.Departinfo;
import zwl.bean.Placeinfo;
import zwl.bean.Seatinfo;
import zwl.bean.Userinfo;
import zwl.dao.BookinfoDao;
import zwl.dao.DepartinfoDao;
import zwl.dao.PlaceinfoDao;
import zwl.dao.SeatinfoDao;
import zwl.dao.UserinfoDao;
import zwl.dao.impl.BookinfoDaoImpl;
import zwl.dao.impl.DepartinfoDaoImpl;
import zwl.dao.impl.PlaceinfoDaoImpl;
import zwl.dao.impl.SeatinfoDaoImpl;
import zwl.dao.impl.UserinfoDaoImpl;
import zwl.utils.JsonDateValueProcessor;
import zwl.utils.StringUtils;

public class Demo {
	
	/*@Test
	public void test01(){
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		List<Userinfo> list=userinfoDao.findByCondition(new HashMap<String, Object>());
		if(list!=null && list.size()>0){
			for(Userinfo userinfo:list){
				System.out.println(userinfo.getUid()+userinfo.getUname());
			}
		}
		
	}
	
	@Test
	public void test02(){
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		List<Userinfo> list=userinfoDao.findByCondition(new HashMap<String, Object>());
		JSONArray json = JSONArray.fromObject(list);
		System.out.println(json);
//		String json_str = json.toString();
//		System.out.println(json_str);
	}
	
	@Test
	public void testlogin(){
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		String unumber="1500000000";
		String upasswd="1001";
		boolean suc=userinfoDao.login(unumber, upasswd);
		if(suc==true){
			System.out.println("登录成功");
		} else {
			System.out.println("用户名或密码错误！");
		}
		
	}
	
	@Test
	public void upuser(){
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		Integer uid=1001;
		Userinfo oldUserinfo=userinfoDao.findById(uid);
		System.out.println(oldUserinfo.getUname());
	}
	
	@Test
	public void allListjson(){
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		Integer unumber=1500000000;
		DepartinfoDao departinfoDao=new DepartinfoDaoImpl();
		if(unumber!=null){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("unumber", unumber);
			List<Userinfo> ulist=userinfoDao.findByCondition(map);
			Userinfo userinfo=(ulist!=null && ulist.size()>0)?ulist.get(0):null;
			
			List<Departinfo> dlist=departinfoDao.findByCondition(null);
			ArrayList list=new ArrayList();
			list.addAll(ulist);
			list.addAll(dlist);		
			JSONArray json = JSONArray.fromObject(list);
			String json_str = json.toString();
			System.out.println(json_str);
		}
	}*/
	
	@Test
	public void suiji(){
		Integer spid=4001;
		SeatinfoDao seatinfoDao=new SeatinfoDaoImpl();
		Map<String,Object> seatmap=new HashMap<String, Object>();
		seatmap.put("spid", spid);
		List<Seatinfo> seatlist=seatinfoDao.findByCondition(seatmap);
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		List<Bookinfo> booklist=bookinfoDao.findByCondition(null);
		Random random=new Random();
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		for(Seatinfo seatinfo:seatlist){
			//System.out.println(seatinfo.getSid());
			list1.add(seatinfo.getSid());
		}
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		for(Bookinfo bookinfo:booklist){
			list2.add(bookinfo.getSid());
		}
		System.out.println(list2.size());
		for(int i=0;;i++){
			Integer seat=list1.get(random.nextInt(list1.size()));
			if(list2.size()==27){
				System.out.println("此需求已满，请重新选择！");break;
			} else {
				if(list2.contains(seat)==false){
					System.out.println(seat);break;
				} 
				
			}
			
		}
		/*for(int i=0;;i++){
			
			Seatinfo seat=seatlist.get(random.nextInt(seatlist.size()));
			System.out.println(seatlist.size());
			//无任何需求（不靠窗、没有插座）
			if(booklist.contains(seat.getSid())==false){
				System.out.println("随机分配的座位号为："+seat.getSid());
				
			}
			//仅仅靠窗
			
			//仅仅有插座
			
			//靠窗、有插座
		}*/
	}
	
	@Test
	public void jdeji(){
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		
		Map<String,Object> umap=new HashMap<String, Object>();
		umap.put("uid", 1001);
		List<Bookinfo> ublist=bookinfoDao.findByCondition(umap);
//		System.out.println(ublist.get(ublist.size() - 1));
		
//		String jsonStr = Util.beanToJson(ublist,"yyyy-MM-dd HH:mm:ss");
//        System.out.println(jsonStr);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray ubjson = JSONArray.fromObject(ublist.get(ublist.size() - 1),jsonConfig);
		//System.out.println(json.toString());
		
		
		
//		JsonConfig jsonConfig = new JsonConfig();                                                          
//		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());               
//		JSONArray array = JSONArray.fromObject(ublist,jsonConfig);
//		System.out.println(array.toString());
		
		SeatinfoDao seatinfoDao=new SeatinfoDaoImpl();
		Map<String,Object> smap=new HashMap<String, Object>();
		smap.put("sid", 3393);
		List<Seatinfo> slist=seatinfoDao.findByCondition(smap);
		
		PlaceinfoDao placeinfoDao=new PlaceinfoDaoImpl();
		Map<String,Object> pmap=new HashMap<String, Object>();
		for(Seatinfo splist:slist){
			pmap.put("pid", splist.getSpid());
		}
		List<Placeinfo> plist=placeinfoDao.findByCondition(pmap);
		JSONArray pjson=JSONArray.fromObject(plist);
		
	//	String ubjson_str = ubjson.toString();
		
		JSONArray ssjson=JSONArray.fromObject(slist);
		String ssjson_str = ssjson.toString();
		System.out.println("{\"ubjson\":"+ubjson+",\"ssjson\":"+ssjson_str+",\"pjson\":"+pjson+"}");
		
	}
	
	@Test
	public void colcount(){
		SeatinfoDao seatinfoDao=new SeatinfoDaoImpl();
		int a=seatinfoDao.colcount(4003);
		System.out.println(a);
	}
	
	@Test
	public void colseatnum(){
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		int b=bookinfoDao.whenseat(4002);
		System.out.println(b);
	}
	
}
