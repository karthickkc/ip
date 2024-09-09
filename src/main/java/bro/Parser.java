package bro;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private final Ui ui;

    public Parser(Ui ui) {
        this.ui = ui;
    }

    /**
     * Parses a date and time from a string using the format "yyyy-MM-dd HHmm".
     *
     * @param s The string representation of the date and time.
     * @return A LocalDateTime object representing the parsed date and time, or null if parsing fails.
     */
    public LocalDateTime parseDate(String s) {
        try {
            return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (DateTimeParseException dte) {
            System.out.println("   Input date and time format at yyyy-mm-dd tttt");
        }
        return null;
    }

    /**
     * Parses a deadline string, creates a {@code Deadline} task, and adds it to the provided {@code TaskList}.
     * The input string should contain a description of the task followed by the date, separated by " /by ".
     * Example input: "Submit report /by 2024-09-01T23:59"
     *
     * @param s      The input string containing the task description and deadline date.
     * @param tasks  The {@code TaskList} to which the newly created {@code Deadline} task will be added.
     * @return       A status message indicating that the task has been added, or an empty string if the date parsing failed.
     *               The status message is generated by {@code ui.printStatus(curr, tasks.size())}.
     *               If the date could not be parsed, the method returns an empty string.
     */
    public String parseDeadline(String s, TaskList tasks) {
        assert (s.contains("/by")) : "input string must contain this";
        String[] info = s.split(" /by ", 2);
        LocalDateTime by = this.parseDate(info[1]);
        if (by != null) {
            Task curr = new Deadline(info[0], by);
            tasks.add(curr);
            return ui.printStatus(curr, tasks.size());
        }
        return "";
    }

    /**
     * Parses a string to create an Event task with a specified start and end time.
     * The input string should be in the format: "description /from start_date /to end_date".
     * If the input string is valid, it creates an Event task, adds it to the provided TaskList,
     * and returns a status message. If the input string is invalid, it returns an empty string.
     *
     * @param s      The string containing the event description and time period.
     *               Expected format: "description /from start_date /to end_date".
     * @param tasks  The TaskList to which the new Event task will be added.
     * @return       A string representing the status after adding the Event task to the TaskList,
     *               or an empty string if the input is invalid.
     */
    public String parseEvent(String s, TaskList tasks) {
        assert (s.contains("/from") && s.contains("/to")) : "input string must contain this";
        String[] info = s.split(" /from | /to ", 3);
        LocalDateTime from = this.parseDate(info[1]);
        if (from != null) {
            LocalDateTime to = this.parseDate(info[2]);
            if (to != null) {
                Task curr = new Event(info[0], from, to);
                tasks.add(curr);
                return ui.printStatus(curr, tasks.size());
            }
        }
        return "";
    }
}
