package io.jenkins.plugins.sample;

import com.alibaba.fastjson.JSONObject;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import jenkins.tasks.SimpleBuildStep;
import mt.spring.remote.execute.core.method.HttpRequestMethod;
import mt.utils.common.Assert;
import org.apache.commons.lang3.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.io.PrintStream;

public class RemoteExecuteBuilder extends Builder implements SimpleBuildStep {
	
	private final String host;
	private final String token;
	private final String method;
	private final String params;
	
	@DataBoundConstructor
	public RemoteExecuteBuilder(String host, String token, String method, String params) {
		this.host = host;
		this.token = token;
		this.method = method;
		this.params = params;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getToken() {
		return token;
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getParams() {
		return params;
	}
	
	@Override
	public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) {
		Assert.notNull(host, "host不能为空");
		Assert.notNull(params, "params不能为空");
		Assert.notNull(token, "token不能为空");
		Assert.notNull(method, "method不能为空");
		PrintStream logger = listener.getLogger();
		HttpRequestMethod httpRequestMethod = new HttpRequestMethod();
		JSONObject params = new JSONObject();
		String fixHost;
		if (host.endsWith("/")) {
			fixHost = host.substring(host.length() - 1);
		} else {
			fixHost = host;
		}
		JSONObject methodParams = new JSONObject();
		methodParams.put("methodName", method);
		methodParams.put("params", JSONObject.parseObject(this.params));
		methodParams.put("token", token);
		
		String url = fixHost + "/execute";
		params.put("url", url);
		params.put("method", "post");
		params.put("headers", "content-type=application/json");
		params.put("body", methodParams.toJSONString());
		logger.println("执行远程请求: " + methodParams.toJSONString());
		try {
			String execute = httpRequestMethod.execute(params);
			logger.println("请求结果：" + execute + "");
		} catch (Exception e) {
			logger.println("execute error:" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	@Symbol("jRemoteExecute")
	@Extension
	public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
		
		public FormValidation doCheckName(@QueryParameter String url) throws Exception {
			return FormValidation.ok();
		}
		
		@Override
		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}
		
		@Override
		public String getDisplayName() {
			return "RemoteExecute";
		}
		
	}
	
}
