package mt.spring.remote.execute.core.method;

import mt.spring.remote.execute.core.method.common.AbstractFileMethod;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Author Martin
 * @Date 2021/3/10
 */
public class MoveFileMethod extends AbstractFileMethod {
	@Override
	public String getName() {
		return "moveFile";
	}
	
	@Override
	protected void modifyFile(File source, File target) throws IOException {
		if (source.isDirectory()) {
			FileUtils.moveDirectory(source, target);
		} else {
			FileUtils.moveFile(source, target);
		}
	}
}
