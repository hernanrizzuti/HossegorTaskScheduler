package com.rizzutih.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class IOHandler  {
	private List<TreeMap<Date,Map<String,List<String>>>> list;

	private static String absolutePath;
	private Pattern pattern;
	private Matcher matcher;
	private static final String DATE_PATTERN = 
			"(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

	public IOHandler() {
		list = new ArrayList<TreeMap<Date,Map<String,List<String>>>>();
	}

	public List<TreeMap<Date,Map<String,List<String>>>> reader(String path) throws ObjectSplitterException{
		absolutePath = path;
		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) parser.parse(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		for (Object o : jsonArray) {
			JSONObject holidays = (JSONObject) o;
			String task =  holidays.get("Tasks").toString();
			String people = holidays.get("People").toString();
			list.add(new ObjectSplitter().handler(task,people));
		}
		return list;
	}

	public String printSchedule(List<TreeMap<Date,Map<String,List<String>>>> list){
		StringBuilder sb = new StringBuilder();
		for(int i =0;i<=list.size()-1;i++){
			for(Entry<Date, Map<String, List<String>>> entry :list.get(i).entrySet()){
				sb.append("-------------------------------");
				sb.append("\n");
				sb.append(entry.getKey());
				sb.append("\n");
				sb.append("-------------------------------");
				sb.append("\n");
				for(Entry<String, List<String>> entry2 :entry.getValue().entrySet()){
					sb.append(entry2.getKey().trim()+": ");
					for(int j =0;j <= entry2.getValue().size()-1;j++){
						if(entry2.getValue().size()==1){
							sb.append(entry2.getValue().get(j));
							sb.append("\n");
						}else{
							if(j==0){
								sb.append(entry2.getValue().get(j)+", ");
							}else if(j==entry2.getValue().size()-1){
								sb.append(entry2.getValue().get(j));
								sb.append("\n");
							}
						}
					}
				}
			}
		}
		return sb.toString();
	}

	public String writer(String text, Extention ext){
		File file = getFile(ext);
		try {
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter bw = getBufferedWriter(file);
			bw.write(text);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.toString();
	}

	public File getFile(Extention ext) {
		String baseUrl = null;
		File file = null;
		switch (ext) {
		case JSON:
			baseUrl = System.getProperty("user.home");
			file = new File(baseUrl +"/"+"Hossegor.json");
			break;
		case TXT:
			baseUrl = FilenameUtils.getFullPath(absolutePath);
			file = new File(baseUrl +"Hossegor-Calendar.txt");
			break;
		}
		return file;
	}

	public BufferedWriter getBufferedWriter(File file) throws IOException {
		return new BufferedWriter(new FileWriter(file));
	}

	public void validate(final String textpanel) throws IOHandlerException{
		pattern = Pattern.compile(DATE_PATTERN);
		matcher = pattern.matcher(textpanel);
		if (".".equals(textpanel.trim())){
			throw new IOHandlerException("ERROR: Empty text - Nothing to schedule.");
		}else if(matcher.matches()){
			throw new IOHandlerException("ERROR: Dates cannot be found");
		}else if (!textpanel.toUpperCase().contains("TASKS:")){
			throw new IOHandlerException("ERROR: Tasks cannot be found");
		}else if (!textpanel.toUpperCase().contains("PEOPLE:")){
			throw new IOHandlerException("ERROR: People cannot be found");
		}
		//check for full stop at the end of each line.
		//check for Tasks:dates until dates; [A-Z][a-z],[0-9]\.
		//check for People:dates until dates; [A-Z][a-z],[A-Z][a-z]\.
	}

	public String getJson(String textpanel) throws IOHandlerException {
		StringBuilder sb = null;
		validate(textpanel);
		String[] splittextpanel= textpanel.trim().split("\\.");
		String noLineBreak;
		sb = new StringBuilder();
		int i;
		int length = splittextpanel.length-1;
		sb.append("[\n");
		for(i = 0; i<=length;i++){
			String[] splitarray = splittextpanel[i].split(":");
			if(i%2==0){
				sb.append("	{");
			}
			for(int j=0 ; j<=splitarray.length-1; j++){
				if(i%2==0){
					if(j==0){
						noLineBreak = splitarray[j].trim().replace("\n", "");
						sb.append("\"" + noLineBreak + "\":");
					} else{
						noLineBreak = splitarray[j].trim().replace("\n", "");
						sb.append("\""+noLineBreak+";\",\n");
					}
				}else{
					if(j==0){
						noLineBreak = splitarray[j].trim().replace("\n", "");
						sb.append("	\""+noLineBreak+"\":");
					} else{
						sb.append("\""+splitarray[j].trim()+";\"\n");
					}
				}
			}
			if((i%2)-1==0 && i!=length){
				sb.append("	},\n");		
			}else if (i==length){
				sb.append("	}\n");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}