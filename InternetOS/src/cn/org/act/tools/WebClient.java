package cn.org.act.tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebClient {
	private IHttpModify modifier;
	
	public WebClient(){
		this.modifier = new IHttpModify(){

			@Override
			public void handle(HttpURLConnection conn) {
				// TODO Auto-generated method stub
				
			}};
	}
	public WebClient(IHttpModify modifier){
		this.modifier = modifier;
	}
	
	public void externalForward(String url,HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		if(res != null)
			res.getWriter().write("Forwarded");
	}

	public String getWebContentByGet(
			String urlString, 
			final String charset,
			int timeout) throws IOException {
		if (urlString == null || urlString.length() == 0) {
			return null;
		}
		urlString = (urlString.startsWith("http://") || urlString
				.startsWith("https://")) ? urlString : ("http://" + urlString)
				.intern();
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		// ���ӱ�ͷ��ģ�����������ֹ����
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
		// ֻ����text/html���ͣ���ȻҲ���Խ���ͼƬ,pdf,*/*���⣬����tomcat/conf/web���涨����Щ
		conn.setRequestProperty("Accept", "*/*");
		conn.setConnectTimeout(timeout);
		
		this.modifier.handle(conn);
		
		try {
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		InputStream input = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input,
				charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		if (reader != null) {
			reader.close();
		}
		if (conn != null) {
			conn.disconnect();
		}
		return sb.toString();

	}

	public String getWebContentByGet(String urlString) throws IOException {
		return getWebContentByGet(urlString, "iso-8859-1", 5000);
	}

	public String getWebContentByPost(String urlString, String data,
			final String charset, int timeout) throws IOException {
		if (urlString == null || urlString.length() == 0) {
			return null;
		}
		urlString = (urlString.startsWith("http://") || urlString
				.startsWith("https://")) ? urlString : ("http://" + urlString)
				.intern();
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// �����Ƿ���connection�������Ϊ�����post���󣬲���Ҫ���� http�����ڣ������Ҫ��Ϊtrue
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		// Post ������ʹ�û���
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		// connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		// ���ӱ�ͷ��ģ�����������ֹ����
		connection.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows vista)");
		// ֻ����text/html���ͣ���ȻҲ���Խ���ͼƬ,pdf,*/*����
		connection.setRequestProperty("Accept", "text/xml");// text/html
		connection.setConnectTimeout(timeout);
		
		
		this.modifier.handle(connection);
		connection.connect();
		
		
		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());
		// String content = data;//+URLEncoder.encode("���� ", "utf-8");
		// out.writeBytes(content);
		byte[] content = data.getBytes("UTF-8");// +URLEncoder.encode("���� ",
												// "utf-8");
		out.write(content);
		out.flush();
		out.close();
		try {
			// ����д�ڷ������ݵĺ���
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), charset));
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		if (reader != null) {
			reader.close();
		}
		if (connection != null) {
			connection.disconnect();
		}
		return sb.toString();
	}

	public String getWebContentByPost(String urlString, String data)
			throws IOException {
		return getWebContentByPost(urlString, data, "UTF-8", 5000);// iso-8859-1
	}
}