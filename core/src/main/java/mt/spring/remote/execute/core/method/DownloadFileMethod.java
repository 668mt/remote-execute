package mt.spring.remote.execute.core.method;

import com.alibaba.fastjson.JSONObject;
import mt.spring.mos.sdk.MosSdk;
import mt.spring.remote.execute.core.method.common.AbstractMosMethod;
import mt.utils.common.Assert;

import java.io.File;

/**
 * @Author Martin
 * @Date 2021/3/12
 */
public class DownloadFileMethod extends AbstractMosMethod {
	
	public DownloadFileMethod(MosSdk mosSdk) {
		super(mosSdk);
	}
	
	@Override
	public String getName() {
		return "downloadFile";
	}
	
	@Override
	protected void execute(JSONObject params, MosSdk mosSdk) throws Exception {
		String desFile = getParam(params, "desFile", String.class);
		String pathname = getParam(params, "pathname", String.class);
		Assert.notNull(desFile, "desFile不能为空");
		Assert.notNull(pathname, "pathname不能为空");
		mosSdk.downloadFile(pathname, new File(desFile), true);
	}
}
