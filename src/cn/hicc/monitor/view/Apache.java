package cn.hicc.monitor.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hicc.monitor.monitor.Monitor;

public class Apache extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String status = request.getParameter("status");
		if ("start".equals(status)) {
			Monitor.startApache(cn.hicc.monitor.service.Apache.getService());
		}
		if ("stop".equals(status)) {
			Monitor.stopApache(cn.hicc.monitor.service.Apache.getService());
		}
		response.sendRedirect(request.getContextPath() + "halberd.jsp");
	}

}
