<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>keep Tomcat Alive ---power by halberd</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<center>
			<h1>
				手动重启终端
			</h1>
			<table border="1">
				<tr>
					<td>
						ApacheTomcat6.1
					</td>
					<td>
						<a href="tomcat?status=start1">启动</a>
					</td>
					<td>
						<a href="tomcat?status=stop1">停止</a>
					</td>
				</tr>
				<tr>
					<td>
						ApacheTomcat6.2
					</td>
					<td>
						<a href="tomcat?status=start2">启动</a>
					</td>
					<td>
						<a href="tomcat?status=stop2">停止</a>
					</td>
				</tr>
				<tr>
					<td>
						ApacheTomcat6.3
					</td>
					<td>
						<a href="tomcat?status=start3">启动</a>
					</td>
					<td>
						<a href="tomcat?status=stop3">停止</a>
					</td>
				</tr>
				<tr>
					<td>
						ApacheTomcat6.4
					</td>
					<td>
						<a href="tomcat?status=start4">启动</a>
					</td>
					<td>
						<a href="tomcat?status=stop4">停止</a>
					</td>
				</tr>
				<tr>
					<td>
						ApacheTomcat6.5
					</td>
					<td>
						<a href="tomcat?status=start5">启动</a>
					</td>
					<td>
						<a href="tomcat?status=stop5">停止</a>
					</td>
				</tr>
				<tr>
					<td>
						Apache2.2
					</td>
					<td>
						<a href="apache?status=start">启动</a>
					</td>
					<td>
						<a href="apache?status=stop">停止</a>
					</td>
				</tr>
			</table>
		</center>
	</body>
</html>
