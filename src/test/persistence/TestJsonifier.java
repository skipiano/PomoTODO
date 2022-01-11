package persistence;

import model.DueDate;
import model.Status;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestJsonifier {
    private Jsonifier j;
    private Task t;
    private JSONObject jo;

    @BeforeEach
    public void doBefore() {
        j = new Jsonifier();
        t = new Task("hi");
        jo = new JSONObject();
    }

    @Test
    public void testConstructor() {
        j = new Jsonifier();
    }

    @Test
    public void testTaskToJson() {
        t.addTag("CPSC");
        t.addTag("210c");
        jo.put("description", "hi");
        JSONArray tagList = new JSONArray();
        JSONObject j1 = new JSONObject();
        j1.put("name", "CPSC");
        JSONObject j2 = new JSONObject();
        j2.put("name", "210c");
        tagList.put(j1);
        tagList.put(j2);
        jo.put("tags", tagList);
        jo.put("due-date", JSONObject.NULL);
        JSONObject priority = new JSONObject();
        priority.put("important", false);
        priority.put("urgent", false);
        jo.put("priority", priority);
        jo.put("status", "TODO");
        assertTrue(jo.similar(j.taskToJson(t)));
    }

    @Test
    public void testTaskListToJson() {
        t.setStatus(Status.UP_NEXT);
        JSONObject jo1 = new JSONObject();
        jo.put("description", "hi");
        JSONArray tagList = new JSONArray();
        jo.put("tags", tagList);
        jo.put("due-date", JSONObject.NULL);
        JSONObject priority = new JSONObject();
        priority.put("important", false);
        priority.put("urgent", false);
        jo.put("priority", priority);
        jo.put("status", "UP_NEXT");
        Task t1 = new Task("hello");
        Calendar cur = Calendar.getInstance();
        cur.set(Calendar.DAY_OF_MONTH, 5);
        cur.set(Calendar.MONTH, Calendar.FEBRUARY);
        cur.set(Calendar.YEAR, 2019);
        Date current = new Date(cur.getTimeInMillis());
        DueDate dd = new DueDate(current);
        dd.setDueTime(3, 10);
        t1.setDueDate(dd);
        t1.setStatus(Status.DONE);
        jo1.put("description", "hello");
        tagList = new JSONArray();
        jo1.put("tags", tagList);
        JSONObject dueDate = new JSONObject();
        dueDate.put("year", 2019);
        dueDate.put("month", 1);
        dueDate.put("day", 5);
        dueDate.put("hour", 3);
        dueDate.put("minute", 10);
        jo1.put("due-date", dueDate);
        priority = new JSONObject();
        priority.put("important", false);
        priority.put("urgent", false);
        jo1.put("priority", priority);
        jo1.put("status", "DONE");
        assertTrue(jo.similar(j.taskToJson(t)));
        assertTrue(jo1.similar(j.taskToJson(t1)));
        JSONArray ja = new JSONArray();
        List<Task> taskList = new ArrayList<>();
        ja.put(jo);
        ja.put(jo1);
        taskList.add(t);
        taskList.add(t1);
        assertTrue(ja.similar(j.taskListToJson(taskList)));
    }

    @Test
    public void testTaskToJsonNoNullDueDate() {
        Calendar cur = Calendar.getInstance();
        cur.set(Calendar.DAY_OF_MONTH, 5);
        cur.set(Calendar.MONTH, Calendar.FEBRUARY);
        cur.set(Calendar.YEAR, 2019);
        Date current = new Date(cur.getTimeInMillis());
        DueDate dd = new DueDate(current);
        dd.setDueTime(3, 10);
        t.setDueDate(dd);
        t.setStatus(Status.IN_PROGRESS);
        jo.put("description", "hi");
        JSONArray tagList = new JSONArray();
        jo.put("tags", tagList);
        JSONObject dueDate = new JSONObject();
        dueDate.put("year", 2019);
        dueDate.put("month", 1);
        dueDate.put("day", 5);
        dueDate.put("hour", 3);
        dueDate.put("minute", 10);
        jo.put("due-date", dueDate);
        JSONObject priority = new JSONObject();
        priority.put("important", false);
        priority.put("urgent", false);
        jo.put("priority", priority);
        jo.put("status", "IN_PROGRESS");
        assertTrue(jo.similar(j.taskToJson(t)));
    }
}
