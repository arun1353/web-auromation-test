package com.goldclub.common;

import java.io.*;
import java.util.*;


public class GCPropertyLoader {
		
		public static Properties loadDefaultPropsFile() throws IOException {
			String propsPath = GCCommonTestCase.GENERIC_FILE_PATH + "common/defaultTest.properties";
			File propsFile = new File(propsPath);
			if (!propsFile.canRead()) System.out.println("Could not load properties file " + propsFile);
			Properties testProps = new Properties();
			InputStream inputStream = new FileInputStream(propsFile);
			testProps.load(inputStream);
			testProps.putAll(System.getProperties());
			inputStream.close();
			return testProps;
		}
	}


