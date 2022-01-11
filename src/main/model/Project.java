package model;

import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    private List<Todo> tasks;
    
    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        tasks = new ArrayList<>();
    }
    
    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it and it is not itself)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (!contains(task) && task != this) {
            tasks.add(task);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }
    
    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    @Override
    public int getEstimatedTimeToComplete() {
        int sum = 0;
        for (Todo t: tasks) {
            sum += t.getEstimatedTimeToComplete();
        }
        return sum;
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the nearest integer).
    //     the value returned is the average of the percentage of completion of
    //     all the tasks and sub-projects in this project.
    public int getProgress() {
        int sum = 0;
        for (Todo t: tasks) {
            sum += t.getProgress();
        }
        if (getNumberOfTasks() == 0) {
            return 0;
        } else {
            return sum / getNumberOfTasks();
        }
    }
    
    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
//     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }
    
    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }

    private class ProjectIterator implements Iterator<Todo> {
        private int importantUrgent = 0;
        private int important = 0;
        private int urgent = 0;
        private int none = 0;
        private int cursorImportantUrgent = 0;
        private int cursorImportant = 0;
        private int cursorUrgent = 0;
        private int cursorNone = 0;
        private Todo todo;

        public ProjectIterator() {
            for (Todo t: tasks) {
                if (t.getPriority().equals(new Priority(1))) {
                    importantUrgent++;
                } else if (t.getPriority().equals(new Priority(2))) {
                    important++;
                } else if (t.getPriority().equals(new Priority(3))) {
                    urgent++;
                } else {
                    none++;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return (cursorImportantUrgent < importantUrgent)
                    || (cursorImportant < important)
                    || (cursorUrgent < urgent)
                    || (cursorNone < none);
        }

        @Override
        public Todo next() {
            if (!hasNext() && !tasks.isEmpty()) {
                throw new NoSuchElementException();
            }
            if (!getImportantUrgent()) {
                if (!getImportant()) {
                    if (!getUrgent()) {
                        getNone();
                    }
                }
            }
            if (tasks.isEmpty()) {
                throw new NoSuchElementException();
            }
            return todo;
        }

        // MODIFIES: this
        // EFFECTS: returns false if there are no more important and urgent tasks;
        // else, sets the next important and urgent task and returns true
        private boolean getImportantUrgent() {
            if (cursorImportantUrgent >= importantUrgent && !tasks.isEmpty()) {
                return false;
            }
            int cursor = 0;
            for (Todo t: tasks) {
                if (t.getPriority().equals(new Priority(1))) {
                    if (cursor == cursorImportantUrgent) {
                        todo = t;
                        break;
                    }
                    cursor++;
                }
            }
            cursorImportantUrgent++;
            return !tasks.isEmpty();
        }

        // MODIFIES: this
        // EFFECTS: returns false if there are no more important tasks;
        // else, sets the next important task and returns true
        private boolean getImportant() {
            if (cursorImportant >= important && !tasks.isEmpty()) {
                return false;
            }
            int cursor = 0;
            for (Todo t: tasks) {
                if (t.getPriority().equals(new Priority(2))) {
                    if (cursor == cursorImportant) {
                        todo = t;
                        break;
                    }
                    cursor++;
                }
            }
            cursorImportant++;
            return !tasks.isEmpty();
        }

        // MODIFIES: this
        // EFFECTS: returns false if there are no more urgent tasks;
        // else, sets the next urgent task and returns true
        private boolean getUrgent() {
            if (cursorUrgent >= urgent && !tasks.isEmpty()) {
                return false;
            }
            int cursor = 0;
            for (Todo t: tasks) {
                if (t.getPriority().equals(new Priority(3))) {
                    if (cursor == cursorUrgent) {
                        todo = t;
                        break;
                    }
                    cursor++;
                }
            }
            cursorUrgent++;
            return !tasks.isEmpty();
        }

        // REQUIRES: !hasNext
        // MODIFIES: this
        // EFFECTS: returns false if there are no more default tasks;
        // else, sets the next default task
        private void getNone() {
            int cursor = 0;
            for (Todo t: tasks) {
                if (t.getPriority().equals(new Priority(4))) {
                    if (cursor == cursorNone) {
                        todo = t;
                        break;
                    }
                    cursor++;
                }
            }
            cursorNone++;
        }
    }
}