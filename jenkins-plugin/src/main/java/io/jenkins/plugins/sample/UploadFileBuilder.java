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
import mt.spring.mos.sdk.MosSdk;
import mt.spring.remote.execute.core.method.ScriptMethod;
import mt.spring.remote.execute.core.method.UploadFileMethod;
import org.apache.commons.lang3.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import java.io.PrintStream;

public class UploadFileBuilder extends Builder implements SimpleBuildStep {
	
	private final String host;
	private final String bucket;
	private final Long openId;
	private final String secretKey;
	private final String srcFile;
	private String pathname;
	private String path;
	
	@DataBoundConstructor
	public UploadFileBuilder(String host, String bucket, Long openId, String secretKey, String srcFile) {
		this.host = host;
		this.bucket = bucket;
		this.openId = openId;
		this.secretKey = secretKey;
		this.srcFile = srcFile;
	}
	
	@DataBoundSetter
	public void setPath(String path) {
		this.path = path;
	}
	
	@DataBoundSetter
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getBucket() {
		return bucket;
	}
	
	public Long getOpenId() {
		return openId;
	}
	
	public String getSecretKey() {
		return secretKey;
	}
	
	public String getSrcFile() {
		return srcFile;
	}
	
	public String getPathname() {
		return pathname;
	}
	
	public String getPath() {
		return path;
	}
	
	@Override
	public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) {
		PrintStream logger = listener.getLogger();
		MosSdk mosSdk = new MosSdk(host, openId, bucket, secretKey);
		ScriptMethod<?> method = new UploadFileMethod(mosSdk);
		JSONObject params = new JSONObject();
		params.put("srcFile", srcFile);
		params.put("pathname", pathname);
		params.put("path", path);
		logger.println("上传文件：" + srcFile);
		try {
			method.execute(params);
		} catch (Exception e) {
			logger.println("execute error:" + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			mosSdk.shutdown();
		}
	}
	
	@Symbol("jUploadFile")
	@Extension
	public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
		
		public FormValidation doCheckName(@QueryParameter String srcFile) throws Exception {
			return FormValidation.ok();
		}
		
		@Override
		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}
		
		@Override
		public String getDisplayName() {
			return "uploadFile";
		}
		
	}
	
}
