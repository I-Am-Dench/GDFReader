package gdf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GDFReader {
	private static String path = "";
	
	private static String standardizeName(String fileName) {
		if (fileName.charAt(fileName.length()-1) != '/')
			fileName += "/";
		if (fileName.charAt(0) != '!')
			return path + fileName;
		return fileName.substring(1);
	}
	
	private static Scanner openFile(String fileName) {
		fileName = standardizeName(fileName);
		try {
			return new Scanner(new File(fileName));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.printf("[GDF-ERROR]: File '%s' could not be opened.", fileName);
			return null;
		}
	}
	
	private static boolean isTag(String line) {
		return line.charAt(0) == '<' && line.charAt(line.length()-1) == '>';
	}
	
	private static boolean isCommand(String line, String command) {
		if (line.matches("^#command\\(.+\\)$")) {
			String sub = line.substring(line.indexOf('(')+1, line.indexOf(')'));
			return sub.equals(command);
		}
		return false;
	}
	
	private static String getValue(String line) {
		String[] lineArr = line.split(",");
		String[] value = new String[lineArr.length]; // Final value as a string array
		
		for (int i=0; i < lineArr.length; i++) {
			switch(lineArr[i]) {
			case "+":
				value[i] = "true";
				break;
			case "-":
				value[i] = "false";
				break;
			case "!":
				value[i] = "";
				break;
			default:
				value[i] = lineArr[i];
				break;
			}
		}
		
		if (value.length == 1)
			return value[0];
		else {
			String str = "";
			for (String s : value) str += s + ",";
			str = str.substring(0, str.length()-1); // Remove last comma
			return str;
		}
	}
	
	public static GDFData getData(String fileName, String target) {
		Scanner file = openFile(fileName);
		if (file == null)
			return new GDFData();
		else {
			GDFData newData = new GDFData();
			boolean foundTarget = false;
			while (file.hasNext()) {
				String line = file.nextLine();
				
				if (isCommand(line, "stop")) break;
				
				if (!line.isEmpty()) {
					if (isTag(line) && !foundTarget)
						foundTarget = line.substring(1, line.indexOf('>')).equals(target);
					else if (isTag(line) && foundTarget)
						break;
					else if (line.charAt(0) == ':' && foundTarget) {
						// Get attributes
						line = line.replaceAll("\\s", ""); // Remove whitespace
						String valueLine = line.substring(line.indexOf('=')+1);
						
						String keyName = line.substring(1, line.indexOf('='));
						String keyValue = getValue(valueLine);
						newData.add(keyName, keyValue);
					}
				}
			}
			
			file.close();
			return newData;
		}
	}
	
	public static List<String> getTags(String fileName) {
		Scanner file = openFile(fileName);
		boolean skipTag = false;
		if (file == null)
			return new ArrayList<String>();
		else {
			List<String> newList = new ArrayList<>();
			while (file.hasNext()) {
				String line = file.nextLine();
				if (isCommand(line, "skip")) skipTag = true;
				if (isCommand(line, "stop")) break;
				
				if (!line.isEmpty() && isTag(line)) {
					if (skipTag)
						skipTag = false;
					else
						newList.add(line.substring(1, line.indexOf('>')));
				}
			}
			
			file.close();
			return newList;
		}
	}
	
	public static void setPath(String p) {
		String newPath = (p.charAt(0) == '/') ? p.substring(1) : p;
		path = standardizeName(newPath);
	}
	
	public static String getPath() {
		return path;
	}
}





















