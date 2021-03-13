package mt.spring.remote.execute.server.controller;

import com.alibaba.fastjson.JSONObject;
import mt.spring.remote.execute.core.MethodService;
import mt.spring.remote.execute.server.entity.ExecuteDTO;
import mt.spring.remote.execute.server.entity.ServerProperties;
import mt.utils.common.Assert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author Martin
 * @Date 2021/3/12
 */
@RestController
public class ApiController {
	@Autowired
	private ServerProperties serverProperties;
	@Autowired
	private MethodService methodService;
	
	@PostMapping("/execute")
	public Object execute(@RequestBody ExecuteDTO executeDTO) throws Exception {
		if (StringUtils.isNotBlank(serverProperties.getToken())) {
			Assert.state(serverProperties.getToken().equals(executeDTO.getToken()), "token验证不正确");
		}
		String methodName = executeDTO.getMethodName();
		Map<String, Object> params = executeDTO.getParams();
		Assert.notNull(methodName, "methodName不能为空");
		JSONObject jsonObject = new JSONObject();
		if (params != null) {
			jsonObject.putAll(params);
		}
		return methodService.execute(methodName, jsonObject);
	}
}
