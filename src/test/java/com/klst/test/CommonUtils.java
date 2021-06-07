package com.klst.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class CommonUtils {

	private static final Logger LOG = Logger.getLogger(CommonUtils.class.getName());

	static final String XML_SUFFIX = ".xml";

	public static File getTestFile(String uri) {
		File file = new File(uri);
		LOG.info("test file "+file.getAbsolutePath() + " canRead:"+file.canRead());
		return file;
	}

	public static File xmlToTempFile(String filename, byte[] xml) {
		try {
			Path temp = Files.createTempFile(filename, XML_SUFFIX); // throws IOException
			Files.write(temp, xml); // throws IOException
			LOG.info("written to "+temp);
			return getTestFile(temp.toString());
		} catch (IOException e) {
			e.printStackTrace();
			LOG.warning("write to "+filename + " : "+e);
		}
		return null;
	}

}
