package bro;

public class Task {
    String name;
    boolean done = false;

    public Task(String s) {
        name = s;
    }

    public void mark() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
    }

    public String toLoad() {
        return "";
    }

    @Override
    public String toString() {
        if (done) {
            return "[X] " + name;
        } else {
            return "[ ] " + name;
        }
    }
}