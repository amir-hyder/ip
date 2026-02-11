package friday;

import java.time.LocalDate;
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
        assert index >= 0 && index < list.size()
                : "Index out of bounds in deleteTask";
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
        assert index >= 0 && index < list.size()
                : "Invalid index in getTask";
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

    /**
     * Returns a list of tasks occurring within the specified number of days
     * starting from the given date (inclusive).
     * <p>
     * Only {@link Deadline} and {@link Event} tasks are considered.
     *
     * @param today The reference date to calculate the reminder window.
     * @param days  The number of days ahead to include.
     * @return A list of tasks occurring within the specified range.
     */
    public ArrayList<Task> getUpcomingTasks(LocalDate today, int days) {
        LocalDate endDate = today.plusDays(days);
        ArrayList<Task> upcoming = new ArrayList<>();

        for (Task t : list) {
            if (t instanceof Deadline d) {
                LocalDate due = d.getDate();
                if (!due.isBefore(today) && !due.isAfter(endDate)) {
                    upcoming.add(t);
                }
            } else if (t instanceof Event e) {
                LocalDate eventDate = e.getDate();
                if (!eventDate.isBefore(today) && !eventDate.isAfter(endDate)) {
                    upcoming.add(t);
                }
            }
        }
        return upcoming;
    }
}
