package mt.spring.remote.execute.core;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @Author Martin
 * @Date 2021/3/10
 */
@Component
public class MoveFileMethod extends AbstractFileMethod {
	@Override
	public String getName() {
		return "moveFile";
	}
	
	@Override
	void modifyFile(File source, File target) throws IOException {
		if (source.isDirectory()) {
			FileUtils.moveDirectory(source, target);
		} else {
			FileUtils.moveFile(source, target);
		}
	}
}
