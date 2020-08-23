import java.time.LocalDate;

public class Task {
    protected String description;
    protected boolean isDone;
    protected boolean hasTime;
    protected static int numberOfTasks = 0;


    public Task (String description) {
        this.description = description;
        this.isDone = false;
        numberOfTasks++;
    }

    public static void reduceOneTasks() {
        numberOfTasks--;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public boolean getHasTime(){
        return hasTime;
    }

    public LocalDate getTime(){
        return null;
    }

    public Task markAsDone() {
        this.isDone = true;
        System.out.println("Nice!(^∇^) I've marked this task as done:\n[\u2713] " + this.description );
        return this;
    }

    @Override
    public String toString() {
        return "Got it. I've added this task:\n" +
                "[\u2718] " + this.description;
    }

    public String deleteMessage() {
        return "Got it. I've deleted this task:\n" +
                "[\u2718] " + this.description;
    }
}