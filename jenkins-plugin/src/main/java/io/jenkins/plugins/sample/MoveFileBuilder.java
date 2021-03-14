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
import mt.spring.remote.execute.core.method.MoveFileMethod;
import mt.spring.remote.execute.core.method.ScriptMethod;
import org.apache.commons.lang3.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import java.io.PrintStream;

public class MoveFileBuilder extends Builder implements SimpleBuildStep {
	
	private final String src;
	private final String des;
	private boolean cover = true;
	
	@DataBoundConstructor
	public MoveFileBuilder(String src, String des) {
		this.src = src;
		this.des = des;
	}
	
	@DataBoundSetter
	public void setCover(boolean cover) {
		this.cover = cover;
	}
	
	@Override
	public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) {
		PrintStream logger = listener.getLogger();
		ScriptMethod<?> method = new MoveFileMethod();
		JSONObject params = new JSONObject();
		params.put("src", src);
		params.put("des", des);
		params.put("cover", cover);
		logger.println("移动文件: src=" + src + ",des=" + des);
		try {
			method.execute(params);
		} catch (Exception e) {
			logger.println("execute error:" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	@Symbol("jMoveFile")
	@Extension
	public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
		
		public FormValidation doCheckName(@QueryParameter String src, @QueryParameter String des) throws Exception {
			if (StringUtils.isBlank(src)) {
				return FormValidation.error("src不能为空");
			}
			if (StringUtils.isBlank(des)) {
				return FormValidation.error("des不能为空");
			}
			return FormValidation.ok();
		}
		
		@Override
		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}
		
		@Override
		public String getDisplayName() {
			return "moveFile";
		}
		
	}
	
	public String getSrc() {
		return src;
	}
	
	public String getDes() {
		return des;
	}
	
	public boolean isCover() {
		return cover;
	}
}
