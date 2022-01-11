package parsers;

import model.DueDate;
import model.Status;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Jsonifier;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTaskParser {

    private TaskParser tp;
    private Jsonifier j;
    private Task t;
    private JSONArray jsonArray;
    private List<Task> taskList;

    @BeforeEach
    public void doBefore() {
        t = new Task("hi");
        tp = new TaskParser();
        j = new Jsonifier();
        jsonArray = new JSONArray();
        taskList = new LinkedList<>();
    }

    @Test
    public void testParseSingle() {
        taskList.add(t);
        jsonArray = j.taskListToJson(taskList);
        tp.parse(jsonArray.toString());
        assertEquals(1, tp.parse(jsonArray.toString()).size());
        assertTrue(tp.parse(jsonArray.toString()).contains(t));
    }

    @Test
    public void testMultipleAllGood() {
        Task t1 = new Task("hello");
        t.setStatus(Status.DONE);
        t1.setStatus(Status.IN_PROGRESS);
        DueDate dueDate = new DueDate();
        t1.setDueDate(dueDate);
        Task t2 = new Task("test");
        t2.setStatus(Status.UP_NEXT);
        taskList.add(t);
        taskList.add(t1);
        taskList.add(t2);
        jsonArray = j.taskListToJson(taskList);
        assertEquals(3, tp.parse(jsonArray.toString()).size());
        assertTrue(tp.parse(jsonArray.toString()).contains(t));
        assertTrue(tp.parse(jsonArray.toString()).contains(t1));
        assertTrue(tp.parse(jsonArray.toString()).contains(t2));
    }

    @Test
    public void testMultipleSomeBad() {
        Task t1 = new Task("hello");
        JSONObject jsonT1 = j.taskToJson(t1);
        jsonT1.remove("description");
        jsonT1.put("decryption", "hello");
        jsonArray = new JSONArray();
        jsonArray.put(jsonT1);
        jsonArray.put(j.taskToJson(t));
        assertEquals(1, tp.parse(jsonArray.toString()).size());
        assertTrue(tp.parse(jsonArray.toString()).contains(t));
    }

    @Test
    public void testParseBadNames() {
        Task t1 = new Task("1");
        JSONObject jsonT1 = j.taskToJson(t1);
        jsonT1.remove("tags");
        jsonT1.put("tag", "hi");
        Task t2 = new Task("2");
        JSONObject jsonT2 = j.taskToJson(t2);
        jsonT2.remove("priority");
        jsonT2.put("priorit", "hi");
        Task t3 = new Task("3");
        JSONObject jsonT3 = j.taskToJson(t3);
        jsonT3.remove("status");
        jsonT3.put("state", "hi");
        DueDate cur = new DueDate();
        Task t4 = new Task("4");
        t4.setDueDate(cur);
        JSONObject jsonT4 = j.taskToJson(t4);
        jsonT4.remove("description");
        jsonT4.put("decrypt", "hi");
        Task t5 = new Task("5");
        t5.setDueDate(cur);
        JSONObject jsonT5 = j.taskToJson(t5);
        jsonT5.remove("tags");
        jsonT5.put("tag", "hi");
        Task t6 = new Task("6");
        t6.setDueDate(cur);
        JSONObject jsonT6 = j.taskToJson(t6);
        jsonT6.remove("priority");
        jsonT6.put("prior", "hi");
        Task t7 = new Task("7");
        t7.setDueDate(cur);
        JSONObject jsonT7 = j.taskToJson(t7);
        jsonT7.remove("status");
        jsonT7.put("satan", "hi");
        Task t8 = new Task("8");
        t8.setDueDate(cur);
        JSONObject jsonT8 = j.taskToJson(t8);
        jsonT8.put("satan", "hi");
        Task t9 = new Task("9");
        t9.setDueDate(cur);
        JSONObject jsonT9 = j.taskToJson(t9);
        jsonT9.remove("due-date");
        jsonT9.put("satan", "hello");
        jsonArray = new JSONArray();
        jsonArray.put(j.taskToJson(t));
        jsonArray.put(jsonT1);
        jsonArray.put(jsonT2);
        jsonArray.put(jsonT3);
        jsonArray.put(jsonT4);
        jsonArray.put(jsonT5);
        jsonArray.put(jsonT6);
        jsonArray.put(jsonT7);
        jsonArray.put(jsonT8);
        jsonArray.put(jsonT9);
        assertEquals(1, tp.parse(jsonArray.toString()).size());
        assertTrue(tp.parse(jsonArray.toString()).contains(t));
    }

    @Test
    public void testParseTags() {
        t.addTag("hi");
        t.addTag("hello");
        taskList.add(t);
        jsonArray = j.taskListToJson(taskList);
        assertEquals(1, tp.parse(jsonArray.toString()).size());
        assertTrue(tp.parse(jsonArray.toString()).contains(t));
    }

    @Test
    public void testParseIncorrectStatus() {
        JSONObject jsonT = j.taskToJson(t);
        jsonT.remove("status");
        jsonT.put("status", "not_done_yet");
        jsonArray.put(jsonT);
        assertTrue(tp.parse(jsonArray.toString()).isEmpty());
    }
}
