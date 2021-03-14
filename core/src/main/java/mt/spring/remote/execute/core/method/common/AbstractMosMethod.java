package mt.spring.remote.execute.core.method.common;

import com.alibaba.fastjson.JSONObject;
import mt.spring.mos.sdk.MosSdk;
import mt.spring.remote.execute.core.method.ScriptMethod;

/**
 * @Author Martin
 * @Date 2021/3/13
 */
public abstract class AbstractMosMethod implements ScriptMethod<String> {
	protected final MosSdk mosSdk;
	
	public AbstractMosMethod(MosSdk mosSdk) {
		this.mosSdk = mosSdk;
	}
	
	@Override
	public String execute(JSONObject params) throws Exception {
//		String bucket = getParam(params, "mos.bucket", String.class);
//		String host = getParam(params, "mos.host", String.class);
//		String secret = getParam(params, "mos.secret", String.class);
//		Long openId = getParam(params, "mos.openId", Long.class);
//		Assert.notNull(bucket, "mos.bucket不能为空");
//		Assert.notNull(host, "mos.host不能为空");
//		Assert.notNull(secret, "mos.secret不能为空");
//		Assert.notNull(openId, "mos.openId不能为空");
//		MosSdk mosSdk = new MosSdk(host, openId, bucket, secret);
		execute(params, mosSdk);
		return null;
	}
	
	protected abstract void execute(JSONObject params, MosSdk mosSdk) throws Exception;
}
