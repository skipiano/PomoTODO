package persistence;


import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();
        tagJson.put("name", tag.getName());
        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();
        priorityJson.put("important", priority.isImportant());
        priorityJson.put("urgent", priority.isUrgent());
        return priorityJson;
    }

    // EFFECTS: returns JSON representation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject dueDateJson = new JSONObject();
        if (dueDate == null) {
            return null;
        } else {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(dueDate.getDate().getTime());
            dueDateJson.put("year", date.get(Calendar.YEAR));
            dueDateJson.put("month", date.get(Calendar.MONTH));
            dueDateJson.put("day", date.get(Calendar.DAY_OF_MONTH));
            dueDateJson.put("hour", date.get(Calendar.HOUR_OF_DAY));
            dueDateJson.put("minute", date.get(Calendar.MINUTE));
        }
        return dueDateJson;
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();
        taskJson.put("description", task.getDescription());
        taskJson.put("tags", tagListToJson(task.getTags()));
        if (dueDateToJson(task.getDueDate()) == null) {
            taskJson.put("due-date", JSONObject.NULL);
        } else {
            taskJson.put("due-date", dueDateToJson(task.getDueDate()));
        }
        taskJson.put("priority", priorityToJson(task.getPriority()));
        taskJson.put("status", getStatus(task));
        return taskJson;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray taskList = new JSONArray();
        for (Task t: tasks) {
            taskList.put(taskToJson(t));
        }
        return taskList;
    }

    // EFFECTS: returns JSON array representing list of tags
    private static JSONArray tagListToJson(Set<Tag> tags) {
        JSONArray tagList =  new JSONArray();
        for (Tag t: tags) {
            tagList.put(tagToJson(t));
        }
        return tagList;
    }

    // EFFECTS: returns status of task
    private static String getStatus(Task task) {
        if (task.getStatus()  == Status.IN_PROGRESS) {
            return "IN_PROGRESS";
        } else if (task.getStatus() == Status.TODO) {
            return "TODO";
        } else if (task.getStatus() == Status.DONE) {
            return "DONE";
        } else {
            return "UP_NEXT";
        }
    }
}
