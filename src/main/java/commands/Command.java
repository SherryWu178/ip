package java.commands;

import java.tasklist.TaskList;
import java.storage.Storage;
import java.ui.Ui;

/**
 * A command object containing information parsed from an Ui object
 * When the command is executed, the data structure will be modified and relevant message will be displayed.
 */
public abstract class Command {
    public Command(){
    }

    public boolean isExit() {
        return false;
    }

    public abstract String execute(TaskList tasks, Ui ui, Storage storage);

}
