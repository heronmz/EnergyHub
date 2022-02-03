package com.energyhub;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.zip.GZIPInputStream;

public class DataProcessor {
    public String process(String variable, String fileName, String timeStampString) {
        try {
            if (fileName.endsWith("gz")) {
                return processGzFile(variable, fileName, timeStampString);
            } else {
                return processNormalFile(variable, fileName, timeStampString);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new IllegalStateException("File not found");
        } catch (IOException e) {
            System.out.println("File cannot be read");
            throw new IllegalStateException("File cannot be read");
        } catch (ParseException e) {
            System.out.println("Error parsing data");
            throw new IllegalStateException("Error parsing data");
        }
    }

    private String processGzFile(String variable,String fileName,String timeStampString) throws IOException, ParseException {
        try (
                InputStream fileStream = new FileInputStream(fileName);
                InputStream gzipStream = new GZIPInputStream(fileStream);
                Reader decoder = new InputStreamReader(gzipStream, "UTF-8");
                BufferedReader br = new BufferedReader(decoder)) {
            return processContent(variable, timeStampString, br);
        }
    }

    private String processNormalFile(String variable,String fileName,String timeStampString) throws IOException, ParseException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return processContent(variable, timeStampString, br);
        }
    }

    public String processContent(String variable,String timeStampString,BufferedReader br) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        LocalDateTime timestamp = LocalDateTime.parse(timeStampString);
        String lastValue = "";
        String line;

        while ((line = br.readLine()) != null) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
            LocalDateTime updateTime = LocalDateTime.parse((String) jsonObject.get("updateTime"));
            if (updateTime.isAfter(timestamp)) {
                break;
            }
            JSONObject updateObject = (JSONObject) jsonObject.get("update");
            Object value = updateObject.get(variable);
            if (value != null) {
                lastValue = value.toString();
            }
        }
        return lastValue==null||lastValue.equals("")?"Value not found":lastValue;
    }
}
