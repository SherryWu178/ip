package duke.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.commands.*;
import duke.exceptions.*;

public class Parser {
    public Parser() {
    }


    private static boolean isOneWordCommand(String[] parseArray) {
        return parseArray.length == 1;
    }

    private static int getArrayIndex(String[] parseArray) {
        String index = parseArray[1];
        return Integer.parseInt(index) - 1;
    }


    /**
     * Cut an user-input String to smaller piece of information
     *
     * @param fullCommand String of user input.
     * @return Command A command object constructed with the info pieces.
     * @throws EmptyTaskException If no index is specified.
     * @throws EmptyTimeException If no date is specified.
     * @throws CommandNotFoundException If a unrecognisable command is used.
     * @throws WrongDateFormatException If the date cannot be parsed.
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
            if (isOneWordCommand(parseArray)) {
                command = new ListCommand(null);
            } else {
                String time = parseArray[1];
                LocalDate date = LocalDate.parse(time);
                command = new ListCommand(date);
            }
            break;
        case "done":
            if (isOneWordCommand(parseArray)) {
                throw new EmptyTaskException();
            } else {
                int arrayIndex = getArrayIndex(parseArray);
                command = new DoneCommand(arrayIndex);
            }
            break;
        case "find":
            if (isOneWordCommand(parseArray)) {
                throw new IncompleteMessageException("Please specify keyword. (´∀`)");
            } else {
                String keyword = parseArray[1];
                command = new FindCommand(keyword);
            }
            break;
        case "delete":
            if (isOneWordCommand(parseArray)) {
                throw new EmptyTaskException();
            } else {
                int arrayIndex = getArrayIndex(parseArray);
                command = new DeleteCommand(arrayIndex);
            }
            break;
        case "todo":
            // Fallthrough
        case "deadline":
            // Fallthrough
        case "event":
            if (isOneWordCommand(parseArray)) {
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

                }
                break;
            }
        default:
            throw new CommandNotFoundException();
        }

        return command;
    }


}
