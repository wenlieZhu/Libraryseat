package zwl.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {
	private String encoding;
	@Override
	public void destroy() {
		System.out.println("web容器关闭时：销毁数据");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("EncodingFilter doFilter");
		//统一设置编码方式
		request.setCharacterEncoding(encoding);
		
		//计算当次请求的耗时
		long startTime=System.currentTimeMillis();//开始时间
		//编写过滤脚本的方法
		chain.doFilter(request, response);//继续执行请求的资源地址或下一个过滤器
		long endTime=System.currentTimeMillis();//开始时间
		System.out.println("当次请求的耗时为："+(endTime-startTime));
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("web容器启动时执行：初始化数据：也可以获取配置文件中的数据");
		encoding=config.getInitParameter("encoding");
	}

}
