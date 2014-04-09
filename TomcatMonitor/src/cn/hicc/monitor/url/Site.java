package cn.hicc.monitor.url;

public class Site {
	// 现在这里是写死的，等时间充裕了全部解析到xml或者是properties里面
	private static String url[] = { "http://www.baidu.com",
			"http://www.google.com" };

	public static String[] getUrl() {
		return url;
	}

}
