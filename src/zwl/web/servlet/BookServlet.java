package zwl.web.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import zwl.bean.Bookinfo;
import zwl.bean.Placeinfo;
import zwl.bean.Seatinfo;
import zwl.dao.BookinfoDao;
import zwl.dao.PlaceinfoDao;
import zwl.dao.SeatinfoDao;
import zwl.dao.impl.BookinfoDaoImpl;
import zwl.dao.impl.PlaceinfoDaoImpl;
import zwl.dao.impl.SeatinfoDaoImpl;
import zwl.utils.BeanUtils;
import zwl.utils.JsonDateValueProcessor;
import zwl.utils.StringUtils;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
	Integer thissid;
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/x-www-form-urlencoded;charset=utf-8");
        String opr=request.getParameter("opr");
        if("add".equals(opr)){
			add(request, resp);
		}else if("loadByuid".equals(opr)){
			loadByuid(request, resp);
		}else if("loadlistinfo".equals(opr)){
			loadlistinfo(request, resp);
		}else if("cancelbook".equals(opr)){
			cancelbook(request, resp);
		}else if("countseat".equals(opr)){
			countseat(request, resp);
		}else if("test".equals(opr)){
			test(request, resp);
		}else if("oklist".equals(opr)){
			oklist(request, resp);
		}else{
			list(request, resp);
		}
	}

	private void oklist(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		Integer uid=StringUtils.str2Integer(request.getParameter("uid"), false, null);
		Map<String,Object> umap=new HashMap<String, Object>();
		umap.put("okuid", uid);
		List<Bookinfo> ublist=bookinfoDao.findByCondition(umap);
		
		SeatinfoDao seatinfoDao=new SeatinfoDaoImpl();
		Map<String,Object> smap=new HashMap<String, Object>();
		smap.put("sid", thissid);
		List<Seatinfo> slist=seatinfoDao.findByCondition(smap);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		
		
		PlaceinfoDao placeinfoDao=new PlaceinfoDaoImpl();
		Map<String,Object> pmap=new HashMap<String, Object>();
		for(Seatinfo splist:slist){
			pmap.put("pid", splist.getSpid());
		}
		List<Placeinfo> plist=placeinfoDao.findByCondition(pmap);
		JSONArray pjson=JSONArray.fromObject(plist);
		
		JSONArray ubjson=JSONArray.fromObject(ublist.get(ublist.size() - 1),jsonConfig);
		String ubjson_str = ubjson.toString();
		
		JSONArray ssjson=JSONArray.fromObject(slist);
		String ssjson_str = ssjson.toString();
		
		Writer out = resp.getWriter();
		out.write("{\"ubjson\":"+ubjson+",\"ssjson\":"+ssjson_str+",\"pjson\":"+pjson+"}");
		out.flush();
		
	}

	//统计座位数
	private void countseat(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		//总座位数
		SeatinfoDao seatinfoDao=new SeatinfoDaoImpl();
		int zixiu_total=seatinfoDao.colcount(4001);
		int yuelan_total=seatinfoDao.colcount(4002);
		int dier_total=seatinfoDao.colcount(4003);
		
		//已被预约座位
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		int zixiu_used=bookinfoDao.whenseat(4001);
		int yuelan_used=bookinfoDao.whenseat(4002);
		int dier_used=bookinfoDao.whenseat(4003);
		
		//剩余座位数
		int zixiu_sur=zixiu_total-zixiu_used;
		int yuelan_sur=yuelan_total-yuelan_used;
		int dier_sur=dier_total-dier_used;
		
		JSONArray zixiu_js=JSONArray.fromObject(zixiu_sur);
		String zixiu_str = zixiu_js.toString();
		JSONArray yuelan_js=JSONArray.fromObject(yuelan_sur);
		String yuelan_str = yuelan_js.toString();
		JSONArray dier_js=JSONArray.fromObject(dier_sur);
		String dier_str = dier_js.toString();
		Writer out = resp.getWriter();
		out.write("{\"zixiu\":"+zixiu_str+",\"yuelan\":"+yuelan_str+",\"dier\":"+dier_str+"}");
		out.flush();
	}

	//取消订单，在未使用前（修改状态为6003不删除）
	private void cancelbook(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		int rows=0;
		String msg="未知错误";
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		try {
			Bookinfo newbtid=getEntity(request);
			if(newbtid.getBid()==null){
				throw new RuntimeException("未找到订单id");
			}
			if(newbtid.getBtid()==null){
				throw new RuntimeException("未找到状态id");
			}
			Bookinfo oldbtid=bookinfoDao.findBybid(newbtid.getBid());
			oldbtid.setBtid(newbtid.getBtid());
			BeanUtils.copyProperties(oldbtid, newbtid);
			rows=bookinfoDao.updateBookinfo(oldbtid);
		} catch (Exception e) {
			e.printStackTrace();
			msg="保存失败："+e.getMessage();
		}
		Writer out = resp.getWriter();
		if(rows>0){
			out.write("true");
		}else {
			out.write("false");
		}
	}

	private Bookinfo getEntity(HttpServletRequest request) {
		Bookinfo newBtype=new Bookinfo();
		newBtype.setBid(StringUtils.str2Integer(request.getParameter("bid"), false));
		newBtype.setBtid(StringUtils.str2Integer(request.getParameter("btid"), false));
		return newBtype;
	}

	private void loadlistinfo(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		Integer bid=StringUtils.str2Integer(request.getParameter("bid"), false, null);
		if(bid!=null){
			Map<String,Object> bmap=new HashMap<String, Object>();
			bmap.put("bid", bid);
			List<Bookinfo> blist=bookinfoDao.findByCondition(bmap);
			
			SeatinfoDao seatinfoDao=new SeatinfoDaoImpl();
			Integer sid=StringUtils.str2Integer(request.getParameter("sid"), false, null);
			Map<String,Object> smap=new HashMap<String, Object>();
			smap.put("sid", sid);
			List<Seatinfo> slist=seatinfoDao.findByCondition(smap);
			
			PlaceinfoDao placeinfoDao=new PlaceinfoDaoImpl();
			Integer pid=StringUtils.str2Integer(request.getParameter("pid"), false, null);
			Map<String,Object> pmap=new HashMap<String, Object>();
			pmap.put("pid", pid);
			List<Placeinfo> plist=placeinfoDao.findByCondition(pmap);
			
			//订单信息
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
			JSONArray bjson=JSONArray.fromObject(blist,jsonConfig);
			//座位信息
			JSONArray ssjson=JSONArray.fromObject(slist);
			String sjson = ssjson.toString();
			//场地信息
			JSONArray ppjson=JSONArray.fromObject(plist);
			String pjson = ppjson.toString();
			
			Writer out = resp.getWriter();
			out.write("{\"bjson\":"+bjson+",\"sjson\":"+sjson+",\"pjson\":"+pjson+"}");
			out.flush();
		}
		
	}

	private void test(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		Integer uid=StringUtils.str2Integer(request.getParameter("uid"), false, null);
		Map<String,Object> umap=new HashMap<String, Object>();
		umap.put("uid", 1001);
		List<Bookinfo> ublist=bookinfoDao.findByCondition(umap);
		
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		
		JSONArray ubjson=JSONArray.fromObject(ublist,jsonConfig);
		Writer out = resp.getWriter();
		out.write("{\"ubjson\":"+ubjson+"}");
		out.flush();
	}

	private void list(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		Integer uid=StringUtils.str2Integer(request.getParameter("uid"), false, null);
		Map<String,Object> umap=new HashMap<String, Object>();
		umap.put("uid", uid);
		List<Bookinfo> ublist=bookinfoDao.findByCondition(umap);
		
		SeatinfoDao seatinfoDao=new SeatinfoDaoImpl();
		Map<String,Object> smap=new HashMap<String, Object>();
		smap.put("sid", thissid);
		List<Seatinfo> slist=seatinfoDao.findByCondition(smap);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		
		
		PlaceinfoDao placeinfoDao=new PlaceinfoDaoImpl();
		Map<String,Object> pmap=new HashMap<String, Object>();
		for(Seatinfo splist:slist){
			pmap.put("pid", splist.getSpid());
		}
		List<Placeinfo> plist=placeinfoDao.findByCondition(pmap);
		JSONArray pjson=JSONArray.fromObject(plist);
		
		JSONArray ubjson=JSONArray.fromObject(ublist.get(ublist.size() - 1),jsonConfig);
		String ubjson_str = ubjson.toString();
		
		JSONArray ssjson=JSONArray.fromObject(slist);
		String ssjson_str = ssjson.toString();
		
		Writer out = resp.getWriter();
		out.write("{\"ubjson\":"+ubjson+",\"ssjson\":"+ssjson_str+",\"pjson\":"+pjson+"}");
		out.flush();
	}

	private void loadByuid(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		Integer uid=StringUtils.str2Integer(request.getParameter("uid"), false, null);
		Map<String,Object> umap=new HashMap<String, Object>();
		umap.put("uid", uid);
		List<Bookinfo> ublist=bookinfoDao.findByCondition(umap);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		
		JSONArray ubjson=JSONArray.fromObject(ublist,jsonConfig);
		Writer out = resp.getWriter();
		out.write("{\"ubjson\":"+ubjson+"}");
		out.flush();
		
	}
	/*
	 *  insert into bookinfo (uid, bdate, btime, pid, sid, bremark)
	 */
	private void add(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		int rows=0;
		String msg="未知错误";
		try {
			Bookinfo entity=new Bookinfo();
			entity.setUid(StringUtils.str2Integer(request.getParameter("uid"), false));
			entity.setBdate(StringUtils.str2DateBySql(request.getParameter("bdate"), false, "yyyy-MM-dd"));
			entity.setBtime(request.getParameter("btime"));
			entity.setPid(StringUtils.str2Integer(request.getParameter("pid"), false));
			
			//随机分配座位
			//entity.setSid(StringUtils.str2Integer(request.getParameter("sid"), false));
			Integer spid=StringUtils.str2Integer(request.getParameter("pid"), false, null);
			SeatinfoDao seatinfoDao=new SeatinfoDaoImpl();
			Map<String,Object> seatmap=new HashMap<String, Object>();
			seatmap.put("spid", spid);
			List<Seatinfo> seatlist=seatinfoDao.findByCondition(seatmap);
			
			BookinfoDao bookinfoDao=new BookinfoDaoImpl();
			Map<String,Object> bookmap=new HashMap<String, Object>();
			bookmap.put("pid", spid);
			List<Bookinfo> booklist=bookinfoDao.findByCondition(bookmap);
			
			Random random=new Random();
			ArrayList<Integer> list_seatinfosid = new ArrayList<Integer>();
			for(Seatinfo seatinfo:seatlist){
				list_seatinfosid.add(seatinfo.getSid());
			}
			ArrayList<Integer> list_bookinfosid = new ArrayList<Integer>();
			for(Bookinfo bookinfo:booklist){
				list_bookinfosid.add(bookinfo.getSid());
			}
			
			OUT:
			for(int i=0;;i++){
				Integer seat=list_seatinfosid.get(random.nextInt(list_seatinfosid.size()));
				Map<String,Object> seattype=new HashMap<String, Object>();
				seattype.put("sid", seat);
				List<Seatinfo> seatwp=seatinfoDao.findByCondition(seattype);
//				for(Seatinfo seatwplist:seatwp){
//					String theswindow=seatwplist.getSwindow();
//					String thespower=seatwplist.getSpower();
//				}
				
				if(list_bookinfosid.size()==24){
//					Writer sout = resp.getWriter();
//					sout.write("此需求已满，请重新选择！");break;
					break;
				} else {
					for(Seatinfo seatwplist:seatwp){
						if(list_bookinfosid.contains(seat)==false){
//							if(request.getParameter("swindow").equals(seatwplist.getSwindow()) && request.getParameter("spower").equals("false")) {
//								entity.setSid(seat);break OUT;
//							} else if(request.getParameter("spower").equals(seatwplist.getSpower()) && request.getParameter("swindow").equals("false")) {
//								entity.setSid(seat);break OUT;
//							} else if(request.getParameter("spower").equals(seatwplist.getSpower()) && request.getParameter("swindow").equals(seatwplist.getSwindow())) {
//								entity.setSid(seat);break OUT;
//							} else {
//								entity.setSid(seat);break OUT;
//							}
							if(request.getParameter("spower").equals(seatwplist.getSpower()) || request.getParameter("swindow").equals(seatwplist.getSwindow())){
								thissid=seat;
								entity.setSid(seat);break OUT;
							}
							
						} 
					}
				}
				
			}
			
			
			
			
			entity.setBremark(request.getParameter("bremark"));
			if(StringUtils.str2Integer(request.getParameter("uid"), false, null)==null){
				throw new RuntimeException("您没有登录");
			}
			if(entity.getSid()==null){
				throw new RuntimeException("座位分配失败");
			}
			
			rows=bookinfoDao.saveBookinfo(entity);
		} catch (Exception e) {
			e.printStackTrace();
			msg="保存失败："+e.getMessage();
		}
		Writer out = resp.getWriter();
		if(rows>0){
			out.write("true");
		}else{
			out.write("false"+msg);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
