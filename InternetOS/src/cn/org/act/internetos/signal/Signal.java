package cn.org.act.internetos.signal;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cn.org.act.tools.StreamHelper;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;

public abstract class Signal {
	
	@XStreamOmitField
	private int id;
	
	private String url;
	
	private String method;
	

	public static class HeadersConvertor implements Converter{

		@Override
		public boolean canConvert(Class arg0) {
			
			return HashMap.class.equals(arg0);
		}

		@Override
		public void marshal(Object value, HierarchicalStreamWriter writer,
				MarshallingContext context) {
			HashMap<String,String> headers = (HashMap<String,String>) value;
			for(Entry<String,String> entry: headers.entrySet()){
				
				writer.startNode(entry.getKey());
				writer.setValue(entry.getValue());
				writer.endNode();
			}
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader arg0,
				UnmarshallingContext arg1) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	@XStreamConverter(HeadersConvertor.class)
	private HashMap<String,String> headers;
	
	public static class StreamConvertor implements Converter {

		@Override
		public boolean canConvert(Class clazz) {
			return InputStream.class.isAssignableFrom(clazz);
		}

		@Override
		public void marshal(Object value, HierarchicalStreamWriter writer,
				MarshallingContext context) {
			InputStream stream = (InputStream) value;
			writer.setValue(StreamHelper.readStream(stream));
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
				UnmarshallingContext context) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@XStreamConverter(StreamConvertor.class)
	//@XStreamAlias("data")
	private InputStream data;
	
	public void setHeaders(HashMap<String,String> headers) {
		this.headers = headers;
	}
	
	public HashMap<String,String> getHeaders() {
		return headers;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethod() {
		return method;
	}
	public void setData(InputStream data) throws IOException {
		this.data = data;
	}
	public InputStream getData() {
		return data;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public abstract void sendTo(List<SignalListener> listener,OutputStream result) throws IOException;
	
	public String toString(){
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.autodetectAnnotations(true);
		xstream.aliasSystemAttribute(null, "class"); // Remove xml
		return xstream.toXML(this);
	}
	
	public static void main(String[] args){
		Signal s = new AsyncSignal("callback", "usertoken");
		s.setUrl("url");
		try {
			s.setData(new ByteArrayInputStream("he\nllo".getBytes("UTF-8")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		s.setMethod("get");
		HashMap<String,String> headers = new HashMap<String,String>();
		headers.put("type", "cnorgactinternetosalert");
		headers.put("async", "true");
		s.setHeaders(headers);
		
		System.out.println(s.toString());
	}
}



