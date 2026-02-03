package Friday;

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

    /**
     * Finds all tasks whose string representation contains
     * the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return A list of matching {@link Task} objects.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> results = new ArrayList<>();

        for (Task task : list) {
            if (task.toString().contains(keyword)) {
                results.add(task);
            }
        }
        return results;
    }
}
