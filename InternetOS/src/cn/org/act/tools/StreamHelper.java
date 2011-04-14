package cn.org.act.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamHelper {
	public static void copyStream(InputStream srcStream, OutputStream dstStream) throws IOException {
		byte[] buf = new byte[100];
		int i = -1;
		try {
			while ((i = srcStream.read(buf)) != -1) {
				dstStream.write(buf, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("The stream is not avaliable!");
		}
	}
}