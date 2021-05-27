package com.klst.test;

import java.io.File;
import java.util.logging.Logger;

public class CommonUtils {

	private static final Logger LOG = Logger.getLogger(CommonUtils.class.getName());

	public static File getTestFile(String uri) {
		File file = new File(uri);
		LOG.info("test file "+file.getAbsolutePath() + " canRead:"+file.canRead());
		return file;
	}

}
