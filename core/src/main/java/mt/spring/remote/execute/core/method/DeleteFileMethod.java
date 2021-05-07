package mt.spring.remote.execute.core.method;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import mt.spring.remote.execute.core.utils.FileListUtils;
import mt.utils.common.Assert;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @Author Martin
 * @Date 2021/3/19
 */
@Slf4j
public class DeleteFileMethod implements ScriptMethod<String> {
	@Override
	public String getName() {
		return "deleteFile";
	}
	
	@Override
	public String execute(JSONObject params) throws Exception {
		String src = getParam(params, "src", String.class);
		Assert.notNull(src, "src不能为空");
		File[] files = FileListUtils.listFiles(new File(src));
		if (files != null) {
			for (File file : files) {
				log.info("删除文件：{}", file);
				FileUtils.deleteQuietly(file);
			}
		}
		return null;
	}
}
