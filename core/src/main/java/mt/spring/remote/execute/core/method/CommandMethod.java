package mt.spring.remote.execute.core.method;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import mt.utils.common.Assert;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author Martin
 * @Date 2021/3/12
 */
@Slf4j
public class CommandMethod implements ScriptMethod<String> {
	@Override
	public String getName() {
		return "command";
	}
	
	@Override
	public String execute(JSONObject params) throws Exception {
		String command = getParam(params, "command", String.class);
		Assert.notNull(command, "command不能为空");
		String dir = getParam(params, "dir", String.class);
		File dirFile = null;
		if (dir != null) {
			dirFile = new File(dir);
		}
		log.info("开始执行命令：{}", command);
		List<String> lines = new ArrayList<>();
		for (String com : command.split("&&")) {
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			try {
				ProcessBuilder processBuilder = new ProcessBuilder();
				processBuilder.directory(dirFile);
				String[] commands = com.trim().split("\\s+");
				processBuilder.command(commands);
				Process process = processBuilder.start();
				executorService.submit(() -> {
					try {
						InputStream inputStream = process.getInputStream();
						InputStream errorStream = process.getErrorStream();
						String s = readInputStream(inputStream);
						if (StringUtils.isNotBlank(s)) {
							lines.add(s);
						}
						String s1 = readInputStream(errorStream);
						if (StringUtils.isNotBlank(s1)) {
							lines.add(s1);
						}
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				});
				process.waitFor(10, TimeUnit.MINUTES);
			} finally {
				executorService.shutdownNow();
			}
		}
		String content = StringUtils.join(lines, "\n");
		log.info("命令执行结果：{}", content);
		return content;
	}
	
	private String readInputStream(InputStream inputStream) throws IOException {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
			String line;
			List<String> lines = new ArrayList<>();
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
			return StringUtils.join(lines, "\n");
		} finally {
			IOUtils.close(inputStream);
		}
	}
	
	public static void main(String[] args) throws Exception {
		JSONObject params = new JSONObject();
		params.put("command", "git && java -version");
		new CommandMethod().execute(params);
	}
}
