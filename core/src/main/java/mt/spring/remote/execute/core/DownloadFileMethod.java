package mt.spring.remote.execute.core;

import com.alibaba.fastjson.JSONObject;
import mt.spring.mos.sdk.MosSdk;
import mt.spring.mos.sdk.entity.upload.UploadInfo;
import mt.utils.common.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author Martin
 * @Date 2021/3/12
 */
@Component
public class DownloadFileMethod implements ScriptMethod<String> {
	@Autowired
	private MosSdk mosSdk;
	
	@Override
	public String getName() {
		return "uploadFile";
	}
	
	@Override
	public String execute(JSONObject params) throws Exception {
		String desFile = getParam(params, "desFile", String.class);
		String pathname = getParam(params, "pathname", String.class);
		Assert.notNull(desFile, "desFile不能为空");
		Assert.notNull(pathname, "pathname不能为空");
		mosSdk.downloadFile(pathname, new File(desFile), true);
		return null;
	}
}
