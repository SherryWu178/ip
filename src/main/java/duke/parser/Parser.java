package duke.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.commands.*;
import duke.exceptions.*;

public class Parser {
    public Parser() {
    }

    /**
     * Cut an user-input String to smaller piece of information
     * Returns different command constructed with the info pieces
     * @param fullCommand
     * @return Command
     * @throws EmptyTaskException
     * @throws EmptyTimeException
     * @throws CommandNotFoundException
     * @throws WrongDateFormatException
     */
    public static Command parse(String fullCommand) throws EmptyTaskException, EmptyTimeException,
            CommandNotFoundException, WrongDateFormatException, IncompleteMessageException {

        String[] parseArray = fullCommand.trim().split(" ", 2);
        String type = parseArray[0];
        Command command = null;

        switch(type) {
        case "bye":
            command = new ExitCommand();
            break;
        case "list":
            if (parseArray.length == 1) {
                command = new ListCommand(null);
            } else {
                String time = parseArray[1];
                try {
                    LocalDate date = LocalDate.parse(time);
                    command = new ListCommand(date);
                } catch(DateTimeParseException ex) {
                    throw new WrongDateFormatException();
                }
            }
            break;
        case "done":
            if (parseArray.length == 1) {
                throw new EmptyTaskException();
            } else {
                String time = parseArray[1];
                try {
                    int index = Integer.parseInt(time) - 1;
                    command = new DoneCommand(index);
                } catch (NumberFormatException ex) {
                    throw new EmptyTaskException();
                }
            }
            break;
        case "find":
            if (parseArray.length == 1) {
                throw new IncompleteMessageException("Please specify keyword. (´∀`)");
            } else {
                String keyword = parseArray[1];
                command = new FindCommand(keyword);
            }
            break;
        case "delete":
            if (parseArray.length == 1) {
                throw new EmptyTaskException();
            } else {
                String rest = parseArray[1];
                int index = Integer.parseInt(rest) - 1;
                command = new DeleteCommand(index);
            }
            break;
        case "todo":
            // Fallthrough
        case "deadline":
            // Fallthrough
        case "event":
            if (parseArray.length == 1) {
                throw new EmptyTaskException();
            } else {
                String rest = parseArray[1];
                switch(type) {
                case "todo":
                    command = new AddCommand(type, rest, null);
                    break;
                case "deadline":
                    if (rest.split("/").length == 1) {
                        throw new EmptyTimeException("Please specify deadline using \"/by\". (´∀`)");
                    } else {
                        String description = rest.split(" /")[0];
                        try {
                            String time = rest.split(" /")[1].split(" ", 2)[1];
                            LocalDate date = LocalDate.parse(time);
                            command = new AddCommand(type, description, date);
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            throw new EmptyTimeException("Please don't leave the deadline blank~ (´∀`)");
                        } catch (DateTimeParseException ex) {
                            throw new WrongDateFormatException();
                        }
                    }
                    break;
                case "event":
                    if (rest.split("/").length == 1) {
                        throw new EmptyTimeException("Please specify event time using \"/at\". (´∀`)");
                    } else {
                        String description = rest.split(" /")[0];
                        try {
                            String time = rest.split(" /")[1].split(" ", 2)[1];
                            LocalDate date = LocalDate.parse(time);
                            command = new AddCommand(type, description, date);
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            throw new EmptyTimeException("Please don't leave the event time blank~ (´∀`)");
                        } catch (DateTimeParseException ex) {
                            throw new WrongDateFormatException();
                        }
                    }
                    break;
                default:
                    throw new AssertionError(type);
                }
                break;
            }
        default:
            throw new CommandNotFoundException();
        }
        return command;
    }
}
