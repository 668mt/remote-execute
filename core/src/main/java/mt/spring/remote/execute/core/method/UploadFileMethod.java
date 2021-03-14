package mt.spring.remote.execute.core.method;

import com.alibaba.fastjson.JSONObject;
import mt.spring.mos.sdk.MosSdk;
import mt.spring.mos.sdk.entity.upload.UploadInfo;
import mt.spring.remote.execute.core.method.common.AbstractMosMethod;
import mt.spring.remote.execute.core.utils.FileListUtils;
import mt.utils.common.Assert;

import java.io.File;

/**
 * @Author Martin
 * @Date 2021/3/12
 */
public class UploadFileMethod extends AbstractMosMethod {
	
	public UploadFileMethod(MosSdk mosSdk) {
		super(mosSdk);
	}
	
	@Override
	public String getName() {
		return "uploadFile";
	}
	
	@Override
	public void execute(JSONObject params, MosSdk mosSdk) throws Exception {
		String srcFile = getParam(params, "srcFile", String.class);
		Assert.notNull(srcFile, "srcFile不能为空");
		File[] files = FileListUtils.listFiles(new File(srcFile));
		Assert.notNull(files, "源文件不存在");
		String pathname = getParam(params, "pathname", String.class);
		String path = getParam(params, "path", String.class, "/");
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		for (File file : files) {
			String uploadPathname;
			if (pathname != null) {
				uploadPathname = pathname;
			} else {
				uploadPathname = path + "/" + file.getName();
			}
			mosSdk.uploadFile(file, new UploadInfo(uploadPathname, true));
		}
	}
}
