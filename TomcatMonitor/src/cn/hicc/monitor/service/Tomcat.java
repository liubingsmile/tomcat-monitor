package cn.hicc.monitor.service;

public class Tomcat {
	private String service[] = { "\"Apache Tomcat 6.0 Tomcat6.1\"",
			"\"Apache Tomcat 6.0 Tomcat6.2\"", "\"Apache Tomcat 6.0 Tomcat6.3\"",
			"\"Apache Tomcat 6.0 Tomcat6.4\"", "\"Apache Tomcat 6.0 Tomcat6.5\"" };

	private String status[] = { "Tomcat6.1.exe", "Tomcat6.2.exe",
			"Tomcat6.3.exe", "Tomcat6.4.exe", "Tomcat6.5.exe" };

	public String[] getService() {
		return service;
	}

	public void setService(String[] service) {
		this.service = service;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

}
