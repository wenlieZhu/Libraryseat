package zwl.web.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.groovy.GJson;
import zwl.bean.Departinfo;
import zwl.bean.Userinfo;
import zwl.bean.Usertype;
import zwl.dao.DepartinfoDao;
import zwl.dao.UserinfoDao;
import zwl.dao.UsertypeDao;
import zwl.dao.impl.DepartinfoDaoImpl;
import zwl.dao.impl.UserinfoDaoImpl;
import zwl.dao.impl.UsertypeDaoImpl;
import zwl.utils.BeanUtils;
import zwl.utils.StringUtils;

/**
 * Servlet implementation class UserinfoServlet
 */
@WebServlet("/UserinfoServlet")
public class UserinfoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/x-www-form-urlencoded;charset=utf-8");
        String opr=request.getParameter("opr");
		if("add".equals(opr)){
			add(request, resp);
		}else if("loadByadd".equals(opr)){
			loadByadd(request, resp);
		}else if("loadByUpdate".equals(opr)){
			loadByUpdate(request, resp);
		}else if("update".equals(opr)){
			update(request, resp);
		}else if("login".equals(opr)){
			login(request, resp);
		}else{
			list(request, resp);
		}
		
	}

	

	private void loadByadd(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		DepartinfoDao departinfoDao=new DepartinfoDaoImpl();
		List<Departinfo> dlist=departinfoDao.findByCondition(null);
		UsertypeDao usertypeDao=new UsertypeDaoImpl();
		List<Usertype> utlist=usertypeDao.findByCondition(null);
		JSONArray utjson = JSONArray.fromObject(utlist);
		String utjson_str = utjson.toString();
		JSONArray djson = JSONArray.fromObject(dlist);
		String djson_str = djson.toString();
		Writer out = resp.getWriter();
		out.write("{\"utjson\":"+utjson_str+",\"djson\":"+djson_str+"}");
		out.flush();
	}



	private void loadByUpdate(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		Integer unumber=StringUtils.str2Integer(request.getParameter("unumber"), false);
		DepartinfoDao departinfoDao=new DepartinfoDaoImpl();
		if(unumber!=null){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("unumber", unumber);
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

	private void list(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		List<Userinfo> list=userinfoDao.findByCondition(new HashMap<String, Object>());
		JSONArray json = JSONArray.fromObject(list);
		String json_str = json.toString();
		Writer out = resp.getWriter();
		out.write(json_str);
        out.flush();
	}


	private void login(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		UserinfoDao userinfoDao=new UserinfoDaoImpl();
		String unumber=request.getParameter("unumber");
		String upasswd=request.getParameter("upasswd");
		
		boolean flag=userinfoDao.login(unumber, upasswd);
		if(flag==true){
			Writer out = resp.getWriter();
			out.write("True");
		}
		
	}


	private void update(HttpServletRequest request, HttpServletResponse resp) throws IOException {
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
			/*oldUserinfo.setUid(newUserinfo.getUid());
			oldUserinfo.setUname(newUserinfo.getUname());
			oldUserinfo.setUnumber(newUserinfo.getUnumber());
			oldUserinfo.setUpasswd(newUserinfo.getUpasswd());
			oldUserinfo.setUrex(newUserinfo.getUrex());
			oldUserinfo.setUphone(newUserinfo.getUphone());
			oldUserinfo.setDid(newUserinfo.getDid());
			oldUserinfo.setUemail(newUserinfo.getUemail());
			oldUserinfo.setUcard(newUserinfo.getUcard());
			oldUserinfo.setUtid(newUserinfo.getUtid());
			oldUserinfo.setUtphone(newUserinfo.getUtphone());
			oldUserinfo.setUqq(newUserinfo.getUqq());
			oldUserinfo.setUwechat(newUserinfo.getUwechat());
			oldUserinfo.setUsid(newUserinfo.getUsid());
			oldUserinfo.setUremark(newUserinfo.getUremark());*/
			
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



	private void add(HttpServletRequest request, HttpServletResponse resp) throws IOException {
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
