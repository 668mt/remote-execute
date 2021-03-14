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
import mt.spring.remote.execute.core.method.DownloadFileMethod;
import mt.spring.remote.execute.core.method.ScriptMethod;
import mt.spring.remote.execute.core.method.UploadFileMethod;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import java.io.PrintStream;

public class DownloadFileBuilder extends Builder implements SimpleBuildStep {
	
	private final String host;
	private final String bucket;
	private final Long openId;
	private final String secretKey;
	private final String desFile;
	private String pathname;
	
	@DataBoundConstructor
	public DownloadFileBuilder(String host, String bucket, Long openId, String secretKey, String desFile) {
		this.host = host;
		this.bucket = bucket;
		this.openId = openId;
		this.secretKey = secretKey;
		this.desFile = desFile;
	}
	
	@DataBoundSetter
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	
	@Override
	public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) {
		PrintStream logger = listener.getLogger();
		MosSdk mosSdk = new MosSdk(host, openId, bucket, secretKey);
		ScriptMethod<?> method = new DownloadFileMethod(mosSdk);
		JSONObject params = new JSONObject();
		params.put("desFile", desFile);
		params.put("pathname", pathname);
		logger.println("下载文件：" + pathname + " 到 " + desFile);
		try {
			method.execute(params);
		} catch (Exception e) {
			logger.println("execute error:" + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			mosSdk.shutdown();
		}
	}
	
	@Symbol("jDownloadFile")
	@Extension
	public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
		
		public FormValidation doCheckName(@QueryParameter String desFile) throws Exception {
			return FormValidation.ok();
		}
		
		@Override
		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}
		
		@Override
		public String getDisplayName() {
			return "DownloadFile";
		}
		
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
	
	public String getDesFile() {
		return desFile;
	}
	
	public String getPathname() {
		return pathname;
	}
}
