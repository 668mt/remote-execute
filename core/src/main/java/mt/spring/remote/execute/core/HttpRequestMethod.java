package mt.spring.remote.execute.core;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import mt.utils.httpclient.Request;
import mt.utils.httpclient.RequestBuilder;
import mt.utils.httpclient.ServiceClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author Martin
 * @Date 2021/3/10
 */
@Component
@Slf4j
public class HttpRequestMethod implements ScriptMethod<String> {
	@Override
	public String getName() {
		return "httpRequest";
	}
	
	@Override
	public String execute(JSONObject params) throws Exception {
		String url = getParam(params, "url", String.class);
		String method = getParam(params, "method", String.class, "get");
		//content-type=application/json;charset=utf-8&aa=cc
		String headers = getParam(params, "headers", String.class);
		//a=1&b=2&c=3
		String body = getParam(params, "body", String.class);
		Assert.notNull(url, "url不能为空");
		Assert.notNull(method, "method不能为空");
		RequestBuilder requestBuilder = RequestBuilder.create();
		requestBuilder.setUrl(url);
		requestBuilder.setMethod(Request.HttpMethod.valueOf(method.toUpperCase()));
		if (StringUtils.isNotBlank(headers)) {
			Map<String, String> headerParams = parse(headers);
			for (Map.Entry<String, String> stringStringEntry : headerParams.entrySet()) {
				requestBuilder.addHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
			}
		}
		if (StringUtils.isNotBlank(body)) {
			requestBuilder.setBody(body);
		}
		Request request = requestBuilder.build();
		ServiceClient serviceClient = new ServiceClient();
		try {
			CloseableHttpResponse response = serviceClient.execute(request);
			String s = EntityUtils.toString(response.getEntity());
			log.info("请求内容：{}", s);
			return s;
		}finally {
			serviceClient.shutdown();
		}
	}
	
	private Map<String, String> parse(String content) {
		Map<String, String> params = new LinkedHashMap<>();
		String[] split = content.split("&");
		for (String s : split) {
			String[] arr = s.split("=");
			String name = arr[0];
			String value = "";
			if (arr.length > 1) {
				value = arr[1];
			}
			params.put(name, value);
		}
		return params;
	}
	
	public static void main(String[] args) throws Exception {
		HttpRequestMethod httpRequestMethod = new HttpRequestMethod();
		JSONObject params = new JSONObject();
		params.put("url", "http://localhost:9095/execute");
		params.put("method", "post");
		params.put("headers", "content-type=application/json");
		params.put("body", "{\n" +
				"    \"methodName\":\"command\",\n" +
				"    \"params\":{\n" +
				"        \"command\":\"java -version\"\n" +
				"    },\n" +
				"    \"token\":\"-PzrgXeWNX7IA6tOjTCXOA==\"\n" +
				"}");
		httpRequestMethod.execute(params);
	}
}
