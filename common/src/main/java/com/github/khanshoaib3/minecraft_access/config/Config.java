package com.github.khanshoaib3.minecraft_access.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.khanshoaib3.minecraft_access.MainClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Config {
    private static final String CONFIG_PATH = Paths.get("config", "minecraft-access", "config.json").toString();

    public void loadConfig() {
        createEmptyConfigFileIfNotExist();

        try {
            Student student = new Student();
            student.setAge(10);
            student.setName("Mahesh");
            writeJSON(student);

            Student student1 = readJSON();
            System.out.println(student1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void createEmptyConfigFileIfNotExist() {
        File configFile = new File(CONFIG_PATH);
        if (!configFile.exists()) {
            MainClass.infoLog(configFile.getAbsolutePath());
            try {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeJSON(Student student) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONFIG_PATH), student);
    }

    private Student readJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(CONFIG_PATH), Student.class);
    }
}

class Student {
    private String name;
    private int age;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "Student [ name: " + getName() + ", age: " + getAge() + " ]";
    }
}