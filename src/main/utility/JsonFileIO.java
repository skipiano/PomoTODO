package utility;

import model.Task;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");
    
    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        TaskParser tp = new TaskParser();
        try {
            String fileContent = new String(Files.readAllBytes(jsonDataFile.toPath()));
            return tp.parse(fileContent);
        } catch (IOException e) {
            return new LinkedList<Task>();
        }
    }
    
    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        try {
            Jsonifier j = new Jsonifier();
            FileWriter fw = new FileWriter(jsonDataFile);
            fw.write(j.taskListToJson(tasks).toString());
            fw.close();
        } catch (IOException e) {
            return;
        }
    }
}
