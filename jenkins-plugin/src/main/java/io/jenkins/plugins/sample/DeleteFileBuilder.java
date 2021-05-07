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
import mt.spring.remote.execute.core.method.CopyFileMethod;
import mt.spring.remote.execute.core.method.DeleteFileMethod;
import mt.spring.remote.execute.core.method.ScriptMethod;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import java.io.PrintStream;

public class DeleteFileBuilder extends Builder implements SimpleBuildStep {
	
	private final String src;
	
	@DataBoundConstructor
	public DeleteFileBuilder(String src) {
		this.src = src;
	}
	
	@Override
	public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) {
		PrintStream logger = listener.getLogger();
		ScriptMethod<?> method = new DeleteFileMethod();
		JSONObject params = new JSONObject();
		params.put("src", src);
		logger.println("删除文件: src=" + src);
		try {
			method.execute(params);
		} catch (Exception e) {
			logger.println("execute error:" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	@Symbol("jDeleteFile")
	@Extension
	public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
		
		public FormValidation doCheckName(@QueryParameter String src, @QueryParameter String des) throws Exception {
			return FormValidation.ok();
		}
		
		@Override
		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}
		
		@Override
		public String getDisplayName() {
			return "deleteFile";
		}
		
	}
	
	public String getSrc() {
		return src;
	}
	
}
