package mt.spring.remote.execute.tool;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import mt.spring.remote.execute.core.MethodService;
import mt.utils.common.Assert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

/**
 * @Author Martin
 * @Date 2021/3/12
 */
@SpringBootApplication
@Slf4j
public class Entrance implements InitializingBean {
	@Autowired
	private MethodService methodService;
	@Autowired
	private ToolProperties toolProperties;
	
	public static void main(String[] args) {
		SpringApplication.run(Entrance.class, args);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		String methodName = toolProperties.getMethodName();
		Assert.notNull(methodName, "tool.methodName不能为空");
		Map<String, Object> map = toolProperties.getParams();
		JSONObject params = new JSONObject();
		if (map != null) {
			params.putAll(map);
		}
		Object execute = methodService.execute(methodName, params);
		if (execute != null) {
			log.info("执行结果：{}", execute);
		}
		System.exit(0);
	}
}
