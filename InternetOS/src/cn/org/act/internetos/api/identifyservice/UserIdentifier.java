package cn.org.act.internetos.api.identifyservice;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import cn.org.act.internetos.Settings;

/**
 * Servlet Filter implementation class UserIdentifier
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
//				DispatcherType.FORWARD, 
//				DispatcherType.INCLUDE, 
//				DispatcherType.ERROR
		}
					, urlPatterns = { "/*" })
public class UserIdentifier implements Filter {

    /**
     * Default constructor. 
     */
    public UserIdentifier() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		String token = request.getParameter(Settings.TOKEN);
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
