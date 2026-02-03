package Friday;

import java.util.ArrayList;

/**
 * Represents a list of {@link Task} objects.
 * Provides operations to add, remove, retrieve, and update tasks in the list.
 */
public class TaskList {
    private ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The {@code Task} to be added.
     */
    public void addTask(Task task) {
        this.list.add(task);
    }

    /**
     * Deletes the task at the specified position in the list.
     * The index is 1-based.
     *
     * @param index The 1-based index of the task to remove.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    public void deleteTask(int index) {
        this.list.remove(index - 1);
    }

    /**
     * Marks the task at the specified position as completed.
     * The index is 1-based.
     *
     * @param index The 1-based index of the task to mark.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    public void markTask(int index) {
        Task task = this.list.get(index - 1);
        task.mark();
    }

    /**
     * Marks the task at the specified position as not completed.
     * The index is 1-based.
     *
     * @param index The 1-based index of the task to unmark.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    public void unmarkTask(int index) {
        Task task = this.list.get(index - 1);
        task.unmark();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The total number of tasks.
     */
    public int size() {
        return this.list.size();
    }

    /**
     * Returns the task at the specified 0-based index.
     *
     * @param index The 0-based index of the task to retrieve.
     * @return The {@code Task} at the specified index.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    public Task get(int index) {
        return this.list.get(index);
    }
}
