package mt.spring.remote.execute.core;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author Martin
 * @Date 2021/3/12
 */
@Component
public class TestMethod implements InitializingBean {
	@Autowired
	private UploadFileMethod uploadFileMethod;
	@Autowired
	private DownloadFileMethod downloadFileMethod;
	
	@Override
	public void afterPropertiesSet() throws Exception {
//		JSONObject params = new JSONObject();
//		params.put("srcFile", "G:\\work\\idea_workspace\\mos\\mos-server\\target\\mos-server-2.1.2.jar");
//		params.put("desFile", "G:\\work\\idea_workspace\\mos\\mos-server\\target\\test.jar");
//		params.put("pathname", "mos-server.jar");
////		uploadFileMethod.execute(params);
//		downloadFileMethod.execute(params);
	}
}
