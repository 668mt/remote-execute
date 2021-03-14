package mt.spring.remote.execute.core;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import mt.spring.remote.execute.core.method.ScriptMethod;
import mt.utils.common.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Martin
 * @Date 2021/3/12
 */
@Slf4j
public class MethodService {
	@Autowired
	private List<ScriptMethod> methods = new ArrayList<>();
	
	public Object execute(String methodName, JSONObject params) throws Exception {
		ScriptMethod scriptMethod = methods.stream()
				.filter(s -> s.getName().equals(methodName))
				.findFirst().orElse(null);
		Assert.notNull(scriptMethod, "找不到这个方法：" + methodName);
		log.info("methodName:{}", methodName);
		log.info("params:{}", params);
		return scriptMethod.execute(params);
	}
}
