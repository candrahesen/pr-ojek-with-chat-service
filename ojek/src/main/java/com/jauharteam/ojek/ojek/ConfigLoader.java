package com.jauharteam.ojek.ojek;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by dery on 11/5/17.
 */
public class ConfigLoader {

    private ObjectMapper objectMapper = new ObjectMapper();
    private String configJSON;
    private static Config config = null;

    public Config getConfig() {
        if(config!=null) return config;
        configJSON = getConfigFile("config.json");
        try {
            config = objectMapper.readValue(configJSON, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    private String getConfigFile(String fileName){

        StringBuilder result = new StringBuilder("");

        ClassLoader classLoader = getClass().getClassLoader();
        System.out.println(classLoader.getResource(fileName).getFile());
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
