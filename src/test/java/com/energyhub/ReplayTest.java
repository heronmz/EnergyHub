package com.energyhub;

import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class ReplayTest{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void notEnoughArguments() {
        exception.expect(IllegalArgumentException.class);
        Replay.main("22");
    }

    // Only for test with files
    private String fileName= "/Users/user/Downloads/thermostat-data.jsonl";
    private String gzFileName= "/Users/user/Downloads/thermostat-data.jsonl.gz";

    //@Test
    public void happyPathNormalFile() {
        DataProcessor dataProcessor = new DataProcessor();
        String result = dataProcessor.process("ambientTemp", fileName, "2016-01-01T03:18:30.001950");
        assertEquals("82",result);
    }

    //@Test
    public void NormalFileNotFound() {
        DataProcessor dataProcessor = new DataProcessor();
        exception.expect(IllegalStateException.class);
        String result = dataProcessor.process("ambientTemp", "fileName", "2016-01-01T03:18:30.001950");
    }

    //@Test
    public void happyPathGzFile() {
        DataProcessor dataProcessor = new DataProcessor();
        String result = dataProcessor.process("ambientTemp", gzFileName, "2016-01-01T03:18:30.001950");
        assertEquals("82",result);
    }
    // Only for test with files   END

    @Test
    public void notValueFound() throws IOException, ParseException {
        DataProcessor dataProcessor = new DataProcessor();
        BufferedReader reader = new BufferedReader(new StringReader(fileContent));
        String result = dataProcessor.processContent("notValue", "2016-01-01T03:18:30.001950", reader);
        assertEquals("Value not found",result);
    }

    @Test
    public void happyPathContentAmbientTemp() throws IOException, ParseException {
        DataProcessor dataProcessor = new DataProcessor();
        BufferedReader reader = new BufferedReader(new StringReader(fileContent));
        String result = dataProcessor.processContent("ambientTemp", "2016-01-01T03:18:30.001950", reader);
        assertEquals("82",result);
    }

    @Test
    public void happyPathContentSchedule() throws IOException, ParseException {
        DataProcessor dataProcessor = new DataProcessor();
        BufferedReader reader = new BufferedReader(new StringReader(fileContent));
        String result = dataProcessor.processContent("schedule", "2016-01-01T03:18:30.001950", reader);
        assertEquals("true",result);
    }

    @Test
    public void happyPathContentLastAlertTs() throws IOException, ParseException {
        DataProcessor dataProcessor = new DataProcessor();
        BufferedReader reader = new BufferedReader(new StringReader(fileContent));
        String result = dataProcessor.processContent("lastAlertTs", "2016-01-08T12:27:30.001131", reader);
        assertEquals("2016-01-08T02:13:30.003741",result);
    }


    private String fileContent = "{\"updateTime\": \"2016-01-01T00:43:00.001064\", \"update\": {\"ambientTemp\": 80}}\n" +
            "{\"updateTime\": \"2016-01-01T01:38:00.001038\", \"update\": {\"ambientTemp\": 82}}\n" +
            "{\"updateTime\": \"2016-01-01T03:18:30.001950\", \"update\": {\"schedule\": true}}\n" +
            "{\"updateTime\": \"2016-01-02T01:32:00.002066\", \"update\": {\"schedule\": false}}\n" +
            "{\"updateTime\": \"2016-01-02T02:35:00.001013\", \"update\": {\"ambientTemp\": 82}}\n" +
            "{\"updateTime\": \"2016-01-02T04:35:00.001204\", \"update\": {\"ambientTemp\": 73}}\n" +
            "{\"updateTime\": \"2016-01-03T00:32:30.001083\", \"update\": {\"heatTemp\": 68}}\n" +
            "{\"updateTime\": \"2016-01-03T00:46:30.005581\", \"update\": {\"ambientTemp\": 69}}\n" +
            "{\"updateTime\": \"2016-01-03T03:14:00.020107\", \"update\": {\"ambientTemp\": 63}}\n" +
            "{\"updateTime\": \"2016-01-04T01:29:30.001738\", \"update\": {\"ambientTemp\": 71}}\n" +
            "{\"updateTime\": \"2016-01-04T02:35:00.068493\", \"update\": {\"ambientTemp\": 75}}\n" +
            "{\"updateTime\": \"2016-01-04T08:54:30.002086\", \"update\": {\"ambientTemp\": 77}}\n" +
            "{\"updateTime\": \"2016-01-06T00:12:30.001252\", \"update\": {\"ambientTemp\": 68}}\n" +
            "{\"updateTime\": \"2016-01-06T01:13:00.001923\", \"update\": {\"coolTemp\": 69}}\n" +
            "{\"updateTime\": \"2016-01-06T04:09:00.001258\", \"update\": {\"ambientTemp\": 69}}\n" +
            "{\"updateTime\": \"2016-01-07T01:20:00.001124\", \"update\": {\"coolTemp\": 66}}\n" +
            "{\"updateTime\": \"2016-01-07T02:27:30.001061\", \"update\": {\"ambientTemp\": 68}}\n" +
            "{\"updateTime\": \"2016-01-07T05:21:30.001974\", \"update\": {\"ambientTemp\": 72}}\n" +
            "{\"updateTime\": \"2016-01-08T02:13:30.003741\", \"update\": {\"lastAlertTs\": \"2016-01-08T02:13:30.003741\"}}\n" +
            "{\"updateTime\": \"2016-01-08T04:28:00.002167\", \"update\": {\"ambientTemp\": 76}}\n" +
            "{\"updateTime\": \"2016-01-08T12:27:30.001131\", \"update\": {\"heatTemp\": 73}}";
}
