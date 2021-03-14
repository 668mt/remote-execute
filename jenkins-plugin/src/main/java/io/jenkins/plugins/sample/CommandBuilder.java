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
import mt.spring.remote.execute.core.method.CommandMethod;
import mt.spring.remote.execute.core.method.ScriptMethod;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import java.io.PrintStream;

public class CommandBuilder extends Builder implements SimpleBuildStep {
	
	private final String command;
	private String dir;
	
	@DataBoundConstructor
	public CommandBuilder(String command) {
		this.command = command;
	}
	
	@DataBoundSetter
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	@Override
	public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) {
		PrintStream logger = listener.getLogger();
		CommandMethod scriptMethod = new CommandMethod();
		JSONObject params = new JSONObject();
		params.put("command", command);
		params.put("dir", dir);
		logger.println("执行命令：" + command);
		try {
			String execute = scriptMethod.execute(params);
			logger.println("执行结果：" + execute);
		} catch (Exception e) {
			logger.println("execute error:" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	@Symbol("jCommand")
	@Extension
	public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
		
		public FormValidation doCheckName(@QueryParameter String command) throws Exception {
			return FormValidation.ok();
		}
		
		@Override
		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}
		
		@Override
		public String getDisplayName() {
			return "Command";
		}
		
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getDir() {
		return dir;
	}
}
