package cn.hicc.monitor.service;

public class Apache {
	private static String status = "httpd.exe";
	private static String service = "\"apache2.2\"";

	public static String getStatus() {
		return status;
	}

	public static String getService() {
		return service;
	}

}
