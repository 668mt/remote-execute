package mt.spring.remote.execute.server.entity;

import lombok.Data;

import java.util.Map;

/**
 * @Author Martin
 * @Date 2021/3/12
 */
@Data
public class ExecuteDTO {
	private String methodName;
	private Map<String, Object> params;
	private String token;
}
