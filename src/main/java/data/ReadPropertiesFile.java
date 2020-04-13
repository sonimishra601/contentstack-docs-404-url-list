package data;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertiesFile {

	static Properties prop;

	public void listFilesAndFolders(String directoryName) {
		File directory = new File(directoryName);
		
		File[] fList = directory.listFiles();
		for (File file : fList) {
			System.out.println(file.getName());
		}
	}

	public ReadPropertiesFile(String path) throws IOException {

		prop = new Properties();
		InputStream input = null;

		input = new FileInputStream(path);

		prop.load(input);

	}

	public static String getProperty(String key) throws IOException {

		String value = null;
		try {
			value = prop.getProperty(key);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return value;

	}
}
