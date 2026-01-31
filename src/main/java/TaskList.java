import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.list.add(task);
    }

    public void deleteTask(int index) {
        this.list.remove(index - 1);
    }

    public void markTask(int index) {
        Task task = this.list.get(index - 1);
        task.mark();
    }

    public void unmarkTask(int index) {
        Task task = this.list.get(index - 1);
        task.unmark();
    }

    public int size() {
        return this.list.size();
    }

    public Task get(int index) {
        return this.list.get(index);
    }
}
