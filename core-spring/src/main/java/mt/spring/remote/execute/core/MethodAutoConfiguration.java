package mt.spring.remote.execute.core;

import mt.spring.mos.sdk.MosSdk;
import mt.spring.remote.execute.core.method.*;
import org.springframework.context.annotation.Bean;

/**
 * @Author Martin
 * @Date 2021/3/13
 */
public class MethodAutoConfiguration {
	@Bean
	public MethodService methodService() {
		return new MethodService();
	}
	
	@Bean
	public DeleteFileMethod deleteFileMethod() {
		return new DeleteFileMethod();
	}
	
	@Bean
	public CommandMethod commandMethod() {
		return new CommandMethod();
	}
	
	@Bean
	public CopyFileMethod copyFileMethod() {
		return new CopyFileMethod();
	}
	
	@Bean
	public MoveFileMethod moveFileMethod() {
		return new MoveFileMethod();
	}
	
	@Bean
	public UploadFileMethod uploadFileMethod(MosSdk mosSdk) {
		return new UploadFileMethod(mosSdk);
	}
	
	@Bean
	public DownloadFileMethod downloadFileMethod(MosSdk mosSdk) {
		return new DownloadFileMethod(mosSdk);
	}
	
	@Bean
	public HttpRequestMethod httpRequestMethod() {
		return new HttpRequestMethod();
	}
}
