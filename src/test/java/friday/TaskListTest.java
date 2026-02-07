package friday;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void addTask_increasesSize() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());

        list.addTask(new ToDo("read book"));
        assertEquals(1, list.size());
    }

    @Test
    public void deleteTask_removesCorrectTask() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("a"));
        list.addTask(new ToDo("b"));

        // your deleteTask expects 1-based index
        list.deleteTask(1);

        assertEquals(1, list.size());
        assertTrue(list.get(0).toString().contains("b"));
    }
}

