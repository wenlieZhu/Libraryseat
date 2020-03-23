package zwl.web.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import zwl.bean.Bookinfo;
import zwl.bean.Departinfo;
import zwl.bean.Goodinfo;
import zwl.bean.Msginfo;
import zwl.bean.Userinfo;
import zwl.dao.BookinfoDao;
import zwl.dao.DepartinfoDao;
import zwl.dao.GoodinfoDao;
import zwl.dao.MsginfoDao;
import zwl.dao.UserinfoDao;
import zwl.dao.impl.BookinfoDaoImpl;
import zwl.dao.impl.DepartinfoDaoImpl;
import zwl.dao.impl.GoodinfoDaoImpl;
import zwl.dao.impl.MsginfoDaoImpl;
import zwl.dao.impl.UserinfoDaoImpl;
import zwl.utils.BeanUtils;
import zwl.utils.JsonDateValueProcessor;
import zwl.utils.StringUtils;

public class AdminServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/x-www-form-urlencoded;charset=utf-8");
        String opr=request.getParameter("opr");
        if("good_add".equals(opr)){                //失物招领 
        	good_add(request, resp);
		}else if("good_up".equals(opr)){
			good_up(request, resp);
		}else if("good_select".equals(opr)){
			good_select(request, resp);
		}else if("msg_add".equals(opr)){            //留言管理
			msg_add(request, resp);
		}else if("msg_up".equals(opr)){
			msg_up(request, resp);
		}else if("userinfo_list".equals(opr)){      //用户管理
			userinfo_list(request, resp);
		}else if("userinfo_add".equals(opr)){
			userinfo_add(request, resp);
		}else if("userinfo_loadedit".equals(opr)){
			userinfo_loadedit(request, resp);
		}else if("userinfo_edit".equals(opr)){
			userinfo_edit(request, resp);
		}else if("book_list".equals(opr)){        //订单管理
			book_list(request, resp);
		}else if("book_loadedit".equals(opr)){        //订单管理
			book_loadedit(request, resp);
		}else if("book_edit".equals(opr)){        //订单管理
			book_edit(request, resp);
		}else{
			msg_select(request, resp);
		}
	}
	
	private void book_edit(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		int rows=0;
		String msg="未知错误";
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		try {
			Bookinfo newbtid=getBookEntity(request);
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
	
	private Bookinfo getBookEntity(HttpServletRequest request) {
		Bookinfo newBtype=new Bookinfo();
		newBtype.setBid(StringUtils.str2Integer(request.getParameter("bid"), false));
		newBtype.setBtid(StringUtils.str2Integer(request.getParameter("btid"), false));
		return newBtype;
	}

	private void book_loadedit(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		Integer bid=StringUtils.str2Integer(request.getParameter("bid"), false, null);
		Map<String,Object> bmap=new HashMap<String, Object>();
		bmap.put("bid", bid);
		List<Bookinfo> ublist=bookinfoDao.findByCondition(bmap);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		
		JSONArray ubjson=JSONArray.fromObject(ublist,jsonConfig);
		Writer out = resp.getWriter();
		out.write("{\"ublist\":"+ubjson+"}");
		out.flush();
		
	}

	private void book_list(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		BookinfoDao bookinfoDao=new BookinfoDaoImpl();
		List<Bookinfo> blist=bookinfoDao.findByCondition(new HashMap<String, Object>());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray bjson=JSONArray.fromObject(blist,jsonConfig);
		String bjson_str = bjson.toString();
		Writer out = resp.getWriter();
		out.write(bjson_str);
        out.flush();
		
	}

	private void userinfo_edit(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		int rows=0;
		String msg="";
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		try {
			Userinfo newUserinfo=getEntity(request);
			if(newUserinfo.getUid()==null){
				throw new RuntimeException("没有登录");
			}
			if(newUserinfo.getUname()==null){
				throw new RuntimeException("姓名不能为空");
			}
			Userinfo oldUserinfo=userinfoDao.findById(newUserinfo.getUid());
			BeanUtils.copyProperties(oldUserinfo, newUserinfo);
			rows=userinfoDao.updateUserinfo(oldUserinfo);
			
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

	private Userinfo getEntity(HttpServletRequest request) {
		Userinfo newuserinfo=new Userinfo();
		newuserinfo.setUid(StringUtils.str2Integer(request.getParameter("uid"), false));
		newuserinfo.setUname(request.getParameter("uname"));
		newuserinfo.setUnumber(request.getParameter("unumber"));
		newuserinfo.setUpasswd(request.getParameter("upasswd"));
	//	newuserinfo.setUrex(request.getParameter("urex"));
		newuserinfo.setUphone(request.getParameter("uphone"));
		newuserinfo.setDid(StringUtils.str2Integer(request.getParameter("did"), false));
		newuserinfo.setUemail(request.getParameter("uemail"));
		newuserinfo.setUcard(request.getParameter("ucard"));
	//	newuserinfo.setUtid(StringUtils.str2Integer(request.getParameter("utid"), false));
		newuserinfo.setUtphone(request.getParameter("utphone"));
		newuserinfo.setUqq(request.getParameter("uqq"));
		newuserinfo.setUwechat(request.getParameter("uwechat"));
	//	newuserinfo.setUsid(StringUtils.str2Integer(request.getParameter("usid"), false));
		newuserinfo.setUremark(request.getParameter("uremark"));
		return newuserinfo;
	}

	private void userinfo_loadedit(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		Integer uid=StringUtils.str2Integer(request.getParameter("uid"), false);
		DepartinfoDao departinfoDao=new DepartinfoDaoImpl();
		if(uid!=null){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("uid", uid);
			List<Userinfo> ulist=userinfoDao.findByCondition(map);
			Userinfo userinfo=(ulist!=null && ulist.size()>0)?ulist.get(0):null;
			List<Departinfo> dlist=departinfoDao.findByCondition(null);
			/*ArrayList list=new ArrayList();
			list.addAll(ulist);
			list.addAll(dlist);*/
			JSONArray ujson = JSONArray.fromObject(ulist);
			String ujson_str = ujson.toString();
			JSONArray djson = JSONArray.fromObject(dlist);
			String djson_str = djson.toString();
			Writer out = resp.getWriter();
			out.write("{\"ujson\":"+ujson_str+",\"djson\":"+djson_str+"}");
			out.flush();
		}
		
	}

	private void userinfo_add(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		int rows=0;
		String msg="未知错误";
		try {
			Userinfo entity=new Userinfo();
			entity.setUname(request.getParameter("uname"));
			entity.setUnumber(request.getParameter("unumber"));
			entity.setUpasswd(request.getParameter("upasswd"));
			entity.setUrex(request.getParameter("urex"));
			entity.setUphone(request.getParameter("uphone"));
			entity.setDid(StringUtils.str2Integer(request.getParameter("did"), false));
			entity.setUemail(request.getParameter("uemail"));
			entity.setUcard(request.getParameter("ucard"));
			entity.setUtid(StringUtils.str2Integer(request.getParameter("utid"), false));
			entity.setUtphone(request.getParameter("utphone"));
			entity.setUqq(request.getParameter("uqq"));
			entity.setUwechat(request.getParameter("uwechat"));
		//	entity.setUsid(StringUtils.str2Integer(request.getParameter("usid"), false));
			entity.setUremark(request.getParameter("uremark"));
			if(StringUtils.strIsNull(entity.getUphone())){
				throw new RuntimeException("手机号不能为空");
			}
			UserinfoDao userinfoDao=new UserinfoDaoImpl();
			rows=userinfoDao.saveUserinfo(entity);
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

	private void userinfo_list(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		List<Userinfo> list=userinfoDao.findByCondition(new HashMap<String, Object>());
		JSONArray json = JSONArray.fromObject(list);
		String json_str = json.toString();
		Writer out = resp.getWriter();
		out.write(json_str);
        out.flush();
		
	}

	private void msg_select(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		MsginfoDao msginfoDao=new MsginfoDaoImpl();
		List<Msginfo> list=msginfoDao.findByCondition(new HashMap<String, Object>());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray msg_json=JSONArray.fromObject(list,jsonConfig);
		Writer out = resp.getWriter();
		out.write("{\"msg_json\":"+msg_json+"}");
		out.flush();
	}

	private void msg_up(HttpServletRequest request, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	private void msg_add(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		int rows=0;
		String msg="未知错误";
		try {
			Msginfo entity=new Msginfo();
			entity.setMtitle(request.getParameter("mtitle"));
			entity.setMdetail(request.getParameter("mdetail"));
			if(StringUtils.strIsNull(entity.getMtitle())){
				throw new RuntimeException("主题不能为空");
			}
			MsginfoDao msginfoDao=new MsginfoDaoImpl();
			rows=msginfoDao.saveMsginfo(entity);
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

	private void good_select(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		GoodinfoDao goodinfoDao=new GoodinfoDaoImpl();
		List<Goodinfo> list=goodinfoDao.findByCondition(new HashMap<String, Object>());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray good_json=JSONArray.fromObject(list,jsonConfig);
		Writer out = resp.getWriter();
		out.write("{\"good_json\":"+good_json+"}");
		out.flush();
		
	}

	private void good_up(HttpServletRequest request, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	private void good_add(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		int rows=0;
		String msg="未知错误";
		try {
			Goodinfo entity=new Goodinfo();
			entity.setGname(request.getParameter("gname"));
			entity.setGdetail(request.getParameter("gdetail"));
			entity.setGphone(request.getParameter("gphone"));
			if(StringUtils.strIsNull(entity.getGphone())){
				throw new RuntimeException("联系方式不能为空");
			}
			GoodinfoDao goodinfoDao=new GoodinfoDaoImpl();
			rows=goodinfoDao.saveGoodinfo(entity);
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
