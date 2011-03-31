package cn.org.act.internetos;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.tools.IHttpModify;
import cn.org.act.tools.WebClient;

public class SignalDispatcher {

	public void sendSignal(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String token = request.getParameter(Settings.TOKEN);
		//final String data = request.getReader().readLine();
		
		if(request.getHeader("async") != null)
		{
			//new WebClient().externalForward(urlStrings, req, res)
//			final String callback = request.getHeader("callback");
//			final HashMap<String,String> headers = new HashMap<String,String>();
//			Enumeration<String> enumeration = request.getHeaderNames();
//			while(enumeration.hasMoreElements()){
//				String k = enumeration.nextElement();
//				headers.put(k, request.getHeader(k));
//			}
//			
//			new Thread(new Runnable(){
//
//				@Override
//				public void run() {
//
//					String data2=null;
//					try {
//						data2 = new WebClient(new IHttpModify(){
//
//							@Override
//							public void handle(HttpURLConnection conn) {
//							    for(String k: headers.keySet()){
//							    	conn.addRequestProperty(k, headers.get(k));
//							    }
//							}}).getWebContentByPost("http://localhost:8080/DemoApp/listener", data);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					try {
//						new WebClient().getWebContentByPost(callback,data2 );
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}}).start();
			
		}else{
			new WebClient().externalForward("http://localhost:8080/DemoApp/listener",request, response);
		}
		
	}
}
