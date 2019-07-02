package sample.tianye.metrics.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

public class Telnets {

	private String ip; // 瑕乼elnet鐨処P鍦板潃
	private String port; // 绔彛鍙凤紝榛樿23
	private String user;// 鐢ㄦ埛鍚�
	private String pwd; // 鐢ㄦ埛瀵嗙爜
	private String osTag = "#";// 绯荤粺琛ㄧず绗﹀彿

	private final TelnetClient tc = new TelnetClient(); // commons-net.jar
	private InputStream in; // 杈撳叆娴侊紝鎺ュ彈杩斿洖淇℃伅
	private PrintStream out; // 鍚戞湇鍔″櫒鍐欏叆鍛戒护

	public Telnets(String ip, String port, String user, String pwd) {

		this.ip = ip;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
		
		connection();
	}

	public void connection() {

		try {
			tc.connect(ip, Integer.parseInt(port));
			in = tc.getInputStream();
			out = new PrintStream(tc.getOutputStream());

			if (findStr("login"))
				sendTelnetMsg(user + "\n");
			if (findStr("password"))
				sendTelnetMsg(pwd + "\n");

		} catch (Exception e) {
			System.out.println("connect error");
		}
	}

	public String execute(String command) {

		sendTelnetMsg(command + "\n");

		// if (findStr(">"))
		// sendTelnetMsg(command + "\n");
		// if (findStr(">"))
		// close();

		return returnMessage;
	}

	private String returnMessage;

	public boolean findStr(String str) {
		for (;;) {
			this.returnMessage = readTelnetMsg();
			if (this.returnMessage.indexOf(str) != -1)
				return true;
		}
	}

	public void sendTelnetMsg(String msg) {
		byte[] b = msg.getBytes();
		try {
			out.write(b, 0, b.length);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readTelnetMsg() {
		StringBuffer sb = new StringBuffer();

		try {
			char ch = (char) in.read();
			while (true) {
				sb.append(ch);
				if (ch == osTag.charAt(osTag.length() - 1)) {
					if (sb.toString().endsWith(osTag))
						return sb.toString();
				}
				ch = (char) in.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "execute error";
	}

	public void close() {
		if (tc != null) {
			try {
				in.close();
				out.close();
				tc.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// public String readTelnetMsg() {
	// try {
	// int len = 0;
	// byte[] b = new byte[1024];
	// do {
	// len = in.read(b);
	// if (len == 0)
	// return new String(b, 0, len);
	// } while (len >= 0);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

}