package mt.spring.remote.execute.core;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author Martin
 * @Date 2021/3/10
 */
public interface ScriptMethod<T1> {
	/**
	 * 获取方法名
	 *
	 * @return 方法名
	 */
	String getName();
	
	/**
	 * 执行
	 *
	 * @param params 参数
	 */
	T1 execute(JSONObject params) throws Exception;
	
	default <T> T getParam(JSONObject params, String name, Class<T> type, T defaultValue) {
		T value = params.getObject(name, type);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}
	
	default <T> T getParam(JSONObject params, String name, Class<T> type) {
		return getParam(params, name, type, null);
	}
}
