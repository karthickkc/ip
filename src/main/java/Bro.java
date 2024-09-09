import bro.*;


public class Bro {

    /**
     * Processes a user command and returns a response based on the action specified.
     * Commands include listing tasks, marking/unmarking tasks as done, adding new tasks,
     * deleting tasks, finding tasks, and handling other task-related operations.
     * It also handles saving the current state of tasks to storage when necessary.
     *
     * @param word    The user input command. The first word indicates the action to perform,
     *                and the rest provides additional information if needed.
     * @param ui      An instance of Ui used for generating user interface messages.
     * @param tasks   The TaskList containing the current list of tasks.
     * @param storage An instance of Storage used for saving tasks to a file.
     * @return        A string response generated based on the input command. This could be a list of tasks,
     *                a confirmation message, or an error message depending on the command.
     * @throws BroException            If there is a specific error related to task operations.
     * @throws NumberFormatException   If the input for task indexing is not a valid number.
     * @throws IndexOutOfBoundsException If the task index specified is out of range.
     */
    public String getResponse(String word, Ui ui, TaskList tasks, Storage storage) {
        if (word.equalsIgnoreCase("list")) {
            return ui.printList(tasks);
        } else {
            String action = word.split(" ", 2)[0];
            String info;
            if (word.split(" ", 2).length == 1) {
                info = "";
            } else {
                info = word.split(" ", 2)[1];
            }
            try {
                switch (action.toLowerCase()) {
                    case "mark":
                        String mark = tasks.markTask(Integer.parseInt(info));
                        storage.saveToFile();
                        return mark;
                    case "unmark":
                        String unmark = tasks.unmarkTask(Integer.parseInt(info));
                        storage.saveToFile();
                        return unmark;
                    case "delete":
                        String delete = tasks.deleteTask(Integer.parseInt(info));
                        storage.saveToFile();
                        return delete;
                    case "find":
                        return tasks.findTasks(info);
                    case "todo":
                        String todo = tasks.addTodo(info);
                        storage.saveToFile();
                        return todo;
                    case "deadline":
                        String deadline = tasks.addDeadline(info);
                        storage.saveToFile();
                        return deadline;
                    case "event":
                        String event = tasks.addEvent(info);
                        storage.saveToFile();
                        return event;
                    default:
                        return ui.printDefault();
                }
            } catch (BroException be) {
                return be.getMessage();
            } catch (NumberFormatException nfe) {
                return ui.printErrorFormat();
            } catch (IndexOutOfBoundsException e) {
                return ui.printErrorSize(tasks.size());
            }
        }
    }

    public static void main(String[] args) {

    }
}