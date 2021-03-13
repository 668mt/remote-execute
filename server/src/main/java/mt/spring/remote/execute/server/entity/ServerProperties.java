package mt.spring.remote.execute.server.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author Martin
 * @Date 2021/3/12
 */
@Data
@ConfigurationProperties(prefix = "remote.execute")
@Component
public class ServerProperties {
	private String token;
	
}
