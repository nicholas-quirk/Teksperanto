package com.teksperanto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * 
 * @author Nicholas Quirk
 * 
 */
public class EspdicLoader {

	HashMap<String, String> eoToEn = new HashMap<String, String>();

	public HashMap<String, String> createDictionary() {

		BufferedReader br = null;

		InputStream input = getClass().getResourceAsStream("espdic.txt");

		try {
			String line;
			br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			while ((line = br.readLine()) != null) {
				if (line.split(":").length > 1) {
					String[] dic = line.split(":");
					eoToEn.put(dic[0], dic[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return eoToEn;
	}
}
