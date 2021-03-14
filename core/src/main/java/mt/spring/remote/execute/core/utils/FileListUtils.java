package mt.spring.remote.execute.core.utils;

import java.io.File;

/**
 * @Author Martin
 * @Date 2021/3/13
 */
public class FileListUtils {
	public static File[] listFiles(File srcFile) {
		return srcFile.getParentFile().listFiles((dir, name) -> match(name, srcFile.getName()));
	}
	
	private static boolean match(String name, String pattern) {
		pattern = "^" + pattern.replaceAll("\\*+", "(.+?)") + "$";
		return name.matches(pattern);
	}
}
