package cn.org.act.demo;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.tools.IHttpModify;
import cn.org.act.tools.WebClient;

/**
 * Servlet implementation class signaltest
 */
@WebServlet("/signaltest")
public class signaltest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signaltest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accesstoken = (String)request.getSession().getAttribute(Setting.TOKEN);
		
		//test synchronous
		//response.getWriter().write(new WebClient().getWebContentByGet(Setting.INTERNETOS+"/signal/send?token="+accesstoken));
		
		//test asynchronous
		new WebClient(new IHttpModify(){

			@Override
			public void handle(HttpURLConnection conn) {
				conn.addRequestProperty("client-signal", "");
				conn.addRequestProperty("clienttype","cn.org.act.internetos.clientsignal.alert");
				conn.addRequestProperty("async", "true");
				conn.addRequestProperty("callback", "http://localhost:8080/DemoApp/callback");
			}}).getWebContentByPost(Setting.INTERNETOS+"/signal/send?callback=http://localhost:8080/DemoApp/callback&token="+accesstoken,"event");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
