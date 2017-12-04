package com.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailAddressReader {
	public String[] getNamesFromFile(String filePath) {
		String names[] = null;
		int i = 0;
		BufferedReader reader;
		List nameList = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = reader.readLine()) != null) {
				// String line = reader.readLine();
				//System.out.println(line);
				// read next line
				// names[i++] = line;
				nameList.add(line);
			}
			reader.close();
			names = (String[]) nameList.toArray(new String[nameList.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return names;
	}
}
