package friday;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void addTask_increasesSize() throws FridayException {
        TaskList list = new TaskList();
        assertEquals(0, list.size());

        list.addTask(new ToDo("read book"));
        assertEquals(1, list.size());
    }

    @Test
    public void deleteTask_removesCorrectTask() throws FridayException {
        TaskList list = new TaskList();
        list.addTask(new ToDo("a"));
        list.addTask(new ToDo("b"));

        // delete first element (0-based index)
        list.deleteTask(0);

        assertEquals(1, list.size());
        assertTrue(list.get(0).toString().contains("b"));
    }
}

