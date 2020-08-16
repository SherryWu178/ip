public class Event extends Task {
    protected String time;

    public Event(String description, String time) {
        super(description);
        this.time = time;
    }

    @Override
    public String toString() {
        return super.toString().replace("[\u2718]", "[E][\u2718]") + " (at: " + time + ")";
    }
}