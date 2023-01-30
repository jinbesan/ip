package duke;
import duke.task.*;
import duke.command.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public Parser() {

    }

    public static Command parse(String cmd) throws DukeException{
        String[] tokens = cmd.split(" ", 2);
        String command = tokens[0];

        // List Tasks
        if (command.equals("list")) {
            return new ListCommand();
        }

        // Exit Duke
        if (command.equals("exit")) {
            return new ExitCommand();
        }

        // Remove task
        if (command.equals("remove")) {
            if (tokens.length != 2) {
                throw new DukeException("Please enter an index to remove!");
            }
            try {
                int index = Integer.parseInt(tokens[1]);
                return new DeleteCommand(index);
            }
            catch (NumberFormatException nfe) {
                throw new DukeException("Index must be an integer!");
            }
        }

        // Mark/Unmark tasks
        if (command.equals("mark")) {
            if (tokens.length != 2) {
                throw new DukeException("Please enter an index to mark!");
            }
            try {
                int index = Integer.parseInt(tokens[1]);
                return new MarkCommand(index);
            }
            catch (NumberFormatException nfe) {
                throw new DukeException("Index must be an integer!");
            }
        }
        if (command.equals("unmark")) {
            if (tokens.length != 2) {
                throw new DukeException("Please enter an index to unmark!");
            }
            try {
                int index = Integer.parseInt(tokens[1]);
                return new UnmarkCommand(index);
            }
            catch (NumberFormatException nfe) {
                throw new DukeException("Index must be an integer!");
            }
        }

        // Adding Tasks
        if (command.equals("todo")) {
            if (tokens.length != 2) {
                throw new DukeException("Please enter a todo task!");
            }
            return new AddCommand(new Todo(tokens[1]));
        }
        if (command.equals("event")) {
            if (tokens.length != 2) {
                throw new DukeException("Please enter a event task!");
            }
            String[] taskTime = tokens[1].split("/", 2);
            if (taskTime.length != 2) {
                throw new DukeException("Please enter a time for the event!");
            }
            return new AddCommand(new Event(taskTime[0], taskTime[1]));
        }
        if (command.equals("deadline")) {
            if (tokens.length != 2) {
                throw new DukeException("Please enter a deadline task!");
            }
            String[] taskTime = tokens[1].split("/", 2);
            if (taskTime.length != 2) {
                throw new DukeException("Please enter a time for the deadline!");
            }
            try {
                Deadline deadline = new Deadline(taskTime[0], LocalDate.parse(taskTime[1]));
                return new AddCommand(deadline);
            } catch (DateTimeParseException e) {
                throw new DukeException("Please enter a valid date in the format [yyyy-mm-dd]");
            }
        }

        throw new DukeException("Not a valid command.");
    }
}
