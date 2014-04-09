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

		// 第一步骤：检测tomcat是否在运行中
		try {
			java.lang.Process p = java.lang.Runtime.getRuntime().exec(
					"tasklist");
			in = new java.io.BufferedReader(new java.io.InputStreamReader(p
					.getInputStream()));
			out: while ((s = in.readLine()) != null) {
				for (int i = 0; i < status.length; i++) {
					if (s.startsWith(status[i])) {
						logger.info(service[i] + " 正常启动中");
						isTomcatAlive[i] = true;
						continue out;
					}
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 第二步骤：对停止的tomcat进行启动并且计数，如果全部死掉了，就马上重启apache
		for (int i = 0; i < isTomcatAlive.length; i++) {
			int j = 0;
			if (!isTomcatAlive[i]) {
				logger.error(service[i] + " 停止运行");
				stopApache(service[i]);
				startTomcat(service[i]);
				logger.info("因为" + service[i] + "tomcat死了，重启了这个tomcat");
				j++;
				if (j == 5) {
					stopApache(apacheService);
					startApache(apacheService);
					logger.info("因为5个tomcat都死了而重启apache");
				}
			}
		}

		// 第三步骤：如果Apache停止了，马上重启

		try {
			java.lang.Process p = java.lang.Runtime.getRuntime().exec(
					"tasklist");
			in = new java.io.BufferedReader(new java.io.InputStreamReader(p
					.getInputStream()));
			while ((s = in.readLine()) != null) {
				if (s.startsWith(apacheStatus)) {
					logger.info(apacheStatus + " 正常启动中");
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

		// 第四步骤：通过url解析网页是否能够访问

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
						logger.info(url[i] + " 正常访问中");
						return;
					}
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 第五步骤：通过判断tomcat是否都活着来判断要不要重启，因为如果到这里就说明网页不能访问了，如果tomcat没有都死，就重启在重启apache
		if (isTomcatAlive[0] || isTomcatAlive[1] || isTomcatAlive[2]
				|| isTomcatAlive[3] || isTomcatAlive[4]) {
			logger.error("Tomcat虽然活着，但是网站已经死了");
			// 那么就重启Tomcat的之后重启Apache吧
			for (int i = 0; i < service.length; i++) {
				stopTomcat(service[i]);
				startTomcat(service[i]);
			}
			stopApache(apacheService);
			startApache(apacheService);
			logger.info("全部重启完毕");

		}
	}

	public static void stopTomcat(String service) {
		try {

			java.lang.Process p = java.lang.Runtime.getRuntime().exec(
					"net stop " + service);
			String s;
			String t = "成功停止";
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
			String t = "启动成功";
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
			String t = "成功停止";
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
			String t = "启动成功";
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
