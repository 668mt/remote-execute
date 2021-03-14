package mt.spring.remote.execute.core.method;

import com.alibaba.fastjson.JSONObject;
import mt.spring.remote.execute.core.method.common.AbstractFileMethod;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Author Martin
 * @Date 2021/3/10
 */
public class CopyFileMethod extends AbstractFileMethod {
	@Override
	public String getName() {
		return "copyFile";
	}
	
	@Override
	protected void modifyFile(File source, File target) throws IOException {
		if (source.isDirectory()) {
			FileUtils.copyDirectory(source, target);
		} else {
			FileUtils.copyFile(source, target);
		}
	}
	
	public static void main(String[] args) throws Exception {
		CopyFileMethod copyFileMethod = new CopyFileMethod();
		JSONObject params = new JSONObject();
		params.put("src", "G:\\work\\idea_workspace\\remote-execute\\tool\\target\\mos-*.jar");
		params.put("des", "H:/test/11/test.jar");
		copyFileMethod.execute(params);
	}
}
