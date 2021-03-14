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
import mt.spring.remote.execute.core.method.ScriptMethod;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import java.io.PrintStream;

public class HttpBuilder extends Builder implements SimpleBuildStep {
	
	private final String url;
	private String method;
	private String headers;
	private String body;
	
	@DataBoundConstructor
	public HttpBuilder(String url) {
		this.url = url;
	}
	
	@DataBoundSetter
	public void setMethod(String method) {
		this.method = method;
	}
	
	@DataBoundSetter
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	
	@DataBoundSetter
	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) {
		PrintStream logger = listener.getLogger();
		HttpRequestMethod scriptMethod = new HttpRequestMethod();
		JSONObject params = new JSONObject();
		params.put("url", url);
		params.put("method", method);
		params.put("headers", headers);
		params.put("body", body);
		logger.println("执行http请求：" + params.toJSONString());
		try {
			String execute = scriptMethod.execute(params);
			logger.println("请求结果：" + execute);
		} catch (Exception e) {
			logger.println("execute error:" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	@Symbol("jHttp")
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
			return "Http";
		}
		
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getHeaders() {
		return headers;
	}
	
	public String getBody() {
		return body;
	}
}
