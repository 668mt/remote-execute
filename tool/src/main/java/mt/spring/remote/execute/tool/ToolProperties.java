package mt.spring.remote.execute.tool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Martin
 * @Date 2021/3/12
 */
@Data
@Component
@ConfigurationProperties(prefix = "tool")
public class ToolProperties {
	private String methodName;
	private Map<String, Object> params;
}
