package friday;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of {@link Task} objects.
 * Provides operations to add, remove, retrieve, and update tasks in the list.
 */
public class TaskList {
    private static final int USER_INDEX_OFFSET = 1;
    private List<Task> list;

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
     * The index is 0-based.
     *
     * @param index The 0-based index of the task to remove.
     * @throws FridayException If the index is invalid.
     */
    public void deleteTask(int index) throws FridayException {
        if (index < 0 || index >= list.size()) {
            throw new FridayException("Invalid index for deleteTask: " + index);
        }
        this.list.remove(index);
    }

    /**
     * Marks the task at the specified position as completed.
     * The index is 0-based.
     *
     * @param index The 0-based index of the task to mark.
     * @throws FridayException If the index is invalid.
     */
    public void markTask(int index) throws FridayException {
        if (index < 0 || index >= list.size()) {
            throw new FridayException("Invalid index for markTask: " + index);
        }
        Task task = this.list.get(index);
        task.mark();
    }

    /**
     * Marks the task at the specified position as not completed.
     * The index is 0-based.
     *
     * @param index The 0-based index of the task to unmark.
     * @throws FridayException If the index is invalid.
     */
    public void unmarkTask(int index) throws FridayException {
        if (index < 0 || index >= list.size()) {
            throw new FridayException("Invalid index for unmarkTask: " + index);
        }
        Task task = this.list.get(index);
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
     * @throws FridayException If the index is invalid.
     */
    public Task get(int index) throws FridayException {
        if (index < 0 || index >= list.size()) {
            throw new FridayException("Invalid index in getTask: " + index);
        }
        return this.list.get(index);
    }

    /**
     * Finds all tasks whose string representation contains
     * the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return An unmodifiable list of matching {@link Task} objects.
     */
    public List<Task> findTasks(String keyword) {
        List<Task> results = new ArrayList<>();
        for (Task task : list) {
            if (task.toString().contains(keyword)) {
                results.add(task);
            }
        }
        return java.util.Collections.unmodifiableList(results);
    }

    /**
     * Returns a list of tasks occurring within the specified number of days
     * starting from the given date (inclusive).
     * <p>
     * Only {@link Deadline} and {@link Event} tasks are considered.
     *
     * @param today The reference date to calculate the reminder window.
     * @param days  The number of days ahead to include.
     * @return An unmodifiable list of tasks occurring within the specified range.
     */
    public List<Task> getUpcomingTasks(LocalDate today, int days) {
        LocalDate endDate = today.plusDays(days);
        List<Task> upcoming = new ArrayList<>();
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
        return java.util.Collections.unmodifiableList(upcoming);
    }
}
