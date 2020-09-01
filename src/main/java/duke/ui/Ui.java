package duke.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import duke.tasklist.TaskList;
import duke.tasks.Task;

public class Ui {
    public Ui() {
    }

    //    public String readCommand() {
    //        return sc.nextLine();
    //    }

    //    public void showLine() {
    //        System.out.println("____________________________________________________________");
    //    }

    public String displayWelcome() {
        return "Hello! I am YURINA Chan.\nWhat can I do for you? ᕕ( ᐛ )ᕗ";
    }

    public String displayBye() {
        return "Bye~ Hope to see you again soon! ∠( ᐛ 」∠)＿";
    }

    public String showLoadingError() {
        return "There is no data file found. "
                + "A new file will be created at the end of the session. ⊂(￣▽￣)⊃";
    }

    public String displayAddTaskMessage(Task task) {
        return task.addMessage();
    }

    public String displayDeleteMessage(Task task) {
        return task.deleteMessage();
    }

    public String displayMarkAsDoneMessage(Task task) {
        return task.markAsDoneMessage();
    }

    public String displayErrorMessage(Exception ex) {
        return ex.getMessage();
    }

    /**
     * Shows all the tasks that are still in the list
     * @param taskList
     */

    public String showTask(TaskList taskList) {
        if (taskList.getTasks().size() == 0) {
            return "This is no task in your task list yet. Add one now! (/^▽^)/";
        } else {
            int no = 1;
            String output = "";
            for (Task task : taskList.getTasks()) {
                String prefix = task.toString().substring(0, 7);
                String end = task.toString().substring(7);
                String appended = prefix + no + ". " + end + "\n";
                output = output + appended;
                no++;
            }
            return output;
        }
    }

    /**
     * Shows the tasks on certain day
     * @param taskList
     */

    public String showTask(TaskList taskList, LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String dateString = date.format(formatter);
        ArrayList<Task> tasks = taskList.getTasks();
        ArrayList<Task> filtered = tasks.stream().filter(Task::getHasTime).filter(task -> task.getTime().equals(date))
                .collect(Collectors.toCollection(ArrayList::new));
        if (filtered.size() == 0) {
            return "No tasks on this day! Chill Chill~ ٩(˘◡˘)۶";
        } else {
            String output = "On " + dateString + ", you have the following tasks: \n";
            for (Task task : filtered) {
                output = output + task.toString() + "\n";
            }
            return output;
        }
    }

    /**
     * Shows the tasks containing certain keywords
     * @param taskList
     * @param keyword
     * @return
     */
    public String showTask(TaskList taskList, String keyword) {
        ArrayList<Task> tasks = taskList.getTasks();
        ArrayList<Task> filtered = tasks.stream().filter(task -> task.getDescription().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
        if (filtered.size() == 0) {
            return "Yurina can't find any relevant tasks. (T▽T)";
        } else {
            String output = "";
            for (Task task : filtered) {
                output = output + task.toString() + "\n";
            }
            return output;
        }
    }

}
