package io.jenkins.plugins.sample;

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
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;
import java.io.PrintStream;

public class HelloWorldBuilder extends Builder implements SimpleBuildStep {
	
	private final String name;
	private String path;
	private boolean useFrench;
	
	@DataBoundConstructor
	public HelloWorldBuilder(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
	
	@DataBoundSetter
	public void setPath(String path) {
		this.path = path;
	}
	
	public boolean isUseFrench() {
		return useFrench;
	}
	
	@DataBoundSetter
	public void setUseFrench(boolean useFrench) {
		this.useFrench = useFrench;
	}
	
	@Override
	public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
		PrintStream logger = listener.getLogger();
		logger.println("name:" + name);
		logger.println("path:" + path);
		logger.println("useFrench:" + useFrench);
		if (useFrench) {
			logger.println("Bonjour, " + name + "!");
		} else {
			logger.println("Hello, " + name + "!");
		}
	}
	
	@Symbol("greet")
	@Extension
	public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
		
		public FormValidation doCheckName(@QueryParameter String name, @QueryParameter String path) throws Exception {
			System.out.println("1111111111:" + name);
			System.out.println("2222222222:" + path);
			if (name.length() == 0) {
				return FormValidation.error("不能为空");
			}
			if (name.length() < 4) {
				return FormValidation.warning("太短了");
			}
			return FormValidation.ok();
		}
		
		@Override
		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}
		
		@Override
		public String getDisplayName() {
			return "test";
		}
		
	}
	
	
}
