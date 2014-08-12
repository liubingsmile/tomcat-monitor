package cn.hicc.monitor.monitor;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import cn.hicc.monitor.service.Apache;
import cn.hicc.monitor.service.Tomcat;
import cn.hicc.monitor.url.Site;

public class Monitor {

	private static Logger logger = Logger.getLogger(Monitor.class);

	public static void keepTomcatAlive() throws NullPointerException {
		String status[] = new Tomcat().getStatus();
		String service[] = new Tomcat().getService();
		String apacheStatus = Apache.getStatus();
		String apacheService = Apache.getService();
		String url[] = Site.getUrl();
		java.io.BufferedReader in;
		String s;
		boolean isTomcatAlive[] = { false, false, false, false, false };
		boolean isApacheAlive = false;

		// ��һ���裺���tomcat�Ƿ���������
		try {
			java.lang.Process p = java.lang.Runtime.getRuntime().exec(
					"tasklist");
			in = new java.io.BufferedReader(new java.io.InputStreamReader(p
					.getInputStream()));
			out: while ((s = in.readLine()) != null) {
				for (int i = 0; i < status.length; i++) {
					if (s.startsWith(status[i])) {
						logger.info(service[i] + " ����������");
						isTomcatAlive[i] = true;
						continue out;
					}
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// �ڶ����裺��ֹͣ��tomcat�����������Ҽ��������ȫ�������ˣ�����������apache
		for (int i = 0; i < isTomcatAlive.length; i++) {
			int j = 0;
			if (!isTomcatAlive[i]) {
				logger.error(service[i] + " ֹͣ����");
				stopApache(service[i]);
				startTomcat(service[i]);
				logger.info("��Ϊ" + service[i] + "tomcat���ˣ����������tomcat");
				j++;
				if (j == 5) {
					stopApache(apacheService);
					startApache(apacheService);
					logger.info("��Ϊ5��tomcat�����˶�����apache");
				}
			}
		}

		// �������裺���Apacheֹͣ�ˣ���������

		try {
			java.lang.Process p = java.lang.Runtime.getRuntime().exec(
					"tasklist");
			in = new java.io.BufferedReader(new java.io.InputStreamReader(p
					.getInputStream()));
			while ((s = in.readLine()) != null) {
				if (s.startsWith(apacheStatus)) {
					logger.info(apacheStatus + " ����������");
					isApacheAlive = true;
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!isApacheAlive) {
			startApache(apacheService);
		}

		// ���Ĳ��裺ͨ��url������ҳ�Ƿ��ܹ�����

		for (int i = 0; i < url.length; i++) {
			try {
				URL site = new URL(url[i]);
				URLConnection con = site.openConnection();
				in = new java.io.BufferedReader(new java.io.InputStreamReader(
						con.getInputStream()));
				con.setConnectTimeout(1000);
				con.setReadTimeout(4000);
				while ((s = in.readLine()) != null) {
					if (s.length() > 0) {
						logger.info(url[i] + " ����������");
						return;
					}
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// ���岽�裺ͨ���ж�tomcat�Ƿ񶼻������ж�Ҫ��Ҫ��������Ϊ����������˵����ҳ���ܷ����ˣ����tomcatû�ж�����������������apache
		if (isTomcatAlive[0] || isTomcatAlive[1] || isTomcatAlive[2]
				|| isTomcatAlive[3] || isTomcatAlive[4]) {
			logger.error("Tomcat��Ȼ���ţ�������վ�Ѿ�����");
			// ��ô������Tomcat��֮������Apache��
			for (int i = 0; i < service.length; i++) {
				stopTomcat(service[i]);
				startTomcat(service[i]);
			}
			stopApache(apacheService);
			startApache(apacheService);
			logger.info("ȫ���������");

		}
	}

	public static void stopTomcat(String service) {
		try {

			java.lang.Process p = java.lang.Runtime.getRuntime().exec(
					"net stop " + service);
			String s;
			String t = "�ɹ�ֹͣ";
			boolean restart = false;
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.InputStreamReader(p.getInputStream()));
			while ((s = in.readLine()) != null) {
				if (s.indexOf(t) != -1) {
					restart = true;
					break;
				}
			}
			logger.info(service + " is stop " + (restart ? "OK" : "ERROR"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void startTomcat(String service) {
		try {
			java.lang.Process p = java.lang.Runtime.getRuntime().exec(
					"net stop " + service);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			java.lang.Process p = java.lang.Runtime.getRuntime().exec(
					"net start " + service);
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.InputStreamReader(p.getInputStream()));
			String s;
			String t = "�����ɹ�";
			boolean restart = false;
			while ((s = in.readLine()) != null) {
				if (s.indexOf(t) != -1) {
					restart = true;
					break;
				}
			}
			logger.info(service + " is start " + (restart ? "OK" : "ERROR"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stopApache(String service) {
		try {

			java.lang.Process p = java.lang.Runtime.getRuntime().exec(
					"net stop " + service);
			String s;
			String t = "�ɹ�ֹͣ";
			boolean restart = false;
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.InputStreamReader(p.getInputStream()));
			while ((s = in.readLine()) != null) {
				if (s.indexOf(t) != -1) {
					restart = true;
					break;
				}
			}
			logger.info(service + " is stop " + (restart ? "OK" : "ERROR"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void startApache(String service) {
		try {
			java.lang.Process p = java.lang.Runtime.getRuntime().exec(
					"net stop " + service);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			java.lang.Process p = java.lang.Runtime.getRuntime().exec(
					"net start " + service);
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.InputStreamReader(p.getInputStream()));
			String s;
			String t = "�����ɹ�";
			boolean restart = false;
			while ((s = in.readLine()) != null) {
				if (s.indexOf(t) != -1) {
					restart = true;
					break;
				}
			}
			logger.info(service + " is start " + (restart ? "OK" : "ERROR"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		while (true) {
			try {
				Monitor.keepTomcatAlive();
				Thread.sleep(300000000);
			} catch (Exception ex) {

			}
		}
	}
}
