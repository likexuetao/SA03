package Server;

import pojo.People;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * author:          ndg
 * date:            2023/11/3
 */
public class ServerData {
    private final String filename = "People";
    public  void writePeopleListToFile(List<People> peopleList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (People person : peopleList) {
                writer.write(person.getName() + "\t\t" + person.getAddress() + "\t\t" + person.getPhone());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  List<People> readPeopleListFromFile() {
        List<People> peopleList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t\t");
                if (parts.length == 3) {
                    peopleList.add(new People(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return peopleList;
    }
}
