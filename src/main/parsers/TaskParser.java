package parsers;

import model.DueDate;
import model.Priority;
import model.Status;
import model.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

// Represents Task parser
public class TaskParser {

    private JSONArray jsonArray;
    private Task task;
    private JSONObject js;
    private List<Task> taskList;

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        jsonArray = new JSONArray(input);
        taskList = new LinkedList<>();
        for (Object j: jsonArray) {
            js = (JSONObject) j;
            if (correctKeySet(js)) {
                task = new Task(js.getString("description"));
                try {
                    update();
                } catch (JSONException e) {
                    continue;
                }
            }
        }
        return taskList;
    }

    // EFFECTS: returns true if j has correct names for the key set
    private boolean correctKeySet(JSONObject j) {
        Set<String> keySet = j.keySet();
        return j.length() == 5
                && keySet.contains("description")
                && keySet.contains("tags")
                && keySet.contains("due-date")
                && keySet.contains("priority")
                && keySet.contains("status");
    }

    // EFFECTS: updates tags if Json is valid, if not, throws JsonException
    private void updateTags() throws JSONException {
        JSONArray jsArray = js.getJSONArray("tags");
        for (Object t: jsArray) {
            JSONObject ts = (JSONObject) t;
            task.addTag(ts.getString("name"));
        }
    }

    // EFFECTS: updates due date if Json is valid, if not, throws JsonException
    private void updateDueDate() throws JSONException {
        JSONObject dueDate = js.getJSONObject("due-date");
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, dueDate.getInt("year"));
        date.set(Calendar.MONTH, dueDate.getInt("month"));
        date.set(Calendar.DAY_OF_MONTH, dueDate.getInt("day"));
        date.set(Calendar.HOUR_OF_DAY, dueDate.getInt("hour"));
        date.set(Calendar.MINUTE, dueDate.getInt("minute"));
        DueDate cur = new DueDate(new Date(date.getTimeInMillis()));
        task.setDueDate(cur);
    }

    // EFFECTS: updates priority if Json is valid, if not, throws JsonException
    private void updatePriority() throws JSONException {
        JSONObject priority =  js.getJSONObject("priority");
        Priority p = new Priority();
//        if (priority.length() != 2) {
//            throw new JSONException("priority is incorrectly represented");
//        }
        p.setUrgent(priority.getBoolean("urgent"));
        p.setImportant(priority.getBoolean("important"));
        task.setPriority(p);
    }

    // EFFECTS: updates status if Json is valid, if not, throws JsonException
    private void updateStatus() throws JSONException {
        if (js.getString("status").equals("TODO")) {
            task.setStatus(Status.TODO);
        } else if (js.getString("status").equals("IN_PROGRESS")) {
            task.setStatus(Status.IN_PROGRESS);
        } else if (js.getString("status").equals("DONE")) {
            task.setStatus(Status.DONE);
        } else if (js.getString("status").equals("UP_NEXT")) {
            task.setStatus(Status.UP_NEXT);
        } else {
            throw new JSONException("status is incorrectly represented");
        }
    }

    // EFFECTS: updates fields of task
    private void update() throws JSONException {
        updateTags();
        if (js.get("due-date") != JSONObject.NULL) {
            updateDueDate();
        }
        updatePriority();
        updateStatus();
        taskList.add(task);
    }
}
