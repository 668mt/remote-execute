package mt.spring.remote.execute.core;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

/**
 * @Author Martin
 * @Date 2021/3/13
 */
@Slf4j
public abstract class AbstractFileMethod implements ScriptMethod<String> {
	
	@Override
	public String execute(JSONObject params) throws Exception {
		String src = getParam(params, "src", String.class);
		String des = getParam(params, "des", String.class);
		boolean cover = getParam(params, "cover", Boolean.class, false);
		Assert.notNull(src, "src不能为空");
		Assert.notNull(des, "des不能为空");
		File srcFile = new File(src);
		File[] srcFiles = srcFile.getParentFile().listFiles((dir, name) -> match(name, srcFile.getName()));
		Assert.notNull(srcFiles, "源文件不存在");
		File desFile = new File(des);
		for (File source : srcFiles) {
			File target;
			if (desFile.isDirectory()) {
				target = new File(desFile, source.getName());
			} else {
				if (desFile.exists() && cover) {
					desFile.delete();
				}
				target = desFile;
			}
			if (!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
			}
			log.info("source:{},target:{}", source, target);
			modifyFile(source, target);
		}
		return null;
	}
	
	abstract void modifyFile(File source, File target) throws IOException;
	
	public boolean match(String name, String pattern) {
		pattern = "^" + pattern.replaceAll("\\*+", "(.+?)") + "$";
		return name.matches(pattern);
	}
}
