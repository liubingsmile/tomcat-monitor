package cn.hicc.monitor.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hicc.monitor.monitor.Monitor;

public class Tomcat extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String status = request.getParameter("status");
		String service[] = new cn.hicc.monitor.service.Tomcat().getService();
		if ("start1".equals(status)) {
			Monitor.startTomcat(service[0]);
		}
		if ("start2".equals(status)) {
			Monitor.startTomcat(service[1]);
		}
		if ("start3".equals(status)) {
			Monitor.startTomcat(service[2]);
		}
		if ("start4".equals(status)) {
			Monitor.startTomcat(service[3]);
		}
		if ("start5".equals(status)) {
			Monitor.startTomcat(service[4]);
		}
		if ("stop1".equals(status)) {
			Monitor.stopTomcat(service[0]);
		}
		if ("stop2".equals(status)) {
			Monitor.stopTomcat(service[1]);
		}
		if ("stop3".equals(status)) {
			Monitor.stopTomcat(service[2]);
		}
		if ("stop4".equals(status)) {
			Monitor.stopTomcat(service[3]);
		}
		if ("stop5".equals(status)) {
			Monitor.stopTomcat(service[4]);
		}
		response.sendRedirect(request.getContextPath() + "halberd.jsp");
	}
}
