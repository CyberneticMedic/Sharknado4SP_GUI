package model;

/**
 * facade for project management system
 * @author Tristan
 * @author Gavin Humphries
 * @author Ryan Caudill
 * Tester: Tristan Shillingford
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.Locale;

public class ProjectSystemFACADE {
    private User user;
    private Project project;
    private static ProjectSystemFACADE facade;
    private UserList UL = UserList.getInstance();
    private ProjectList PL = ProjectList.getInstance();
    // UI vars
    private Scanner kb = new Scanner(System.in);
    private StringBuffer sb = new StringBuffer();
    private boolean exitUI = false;
    private Column column;
    private Task task = null;
    private String email = null;
    private String password = null;
    private Comment comment;

    public static ProjectSystemFACADE getInstance() {
        if (facade == null) {
            facade = new ProjectSystemFACADE();
        }
        return facade;
    }

    /**
     * 
     * @param email
     * @param password
     * @return a User object
     */
    public User login(String email, String password) {
        user = UserList.getInstance().login(password, email);
        return user;
    }

    /**
     * Signs the user out.
     */
    public void signOut() {
        user = null;
    }

    /**
     * 
     * @param email
     * @param firstName
     * @param lastName
     * @param isMaster
     * @param password
     * @return a User object
     */
    public User signUp(String email, String firstName, String lastName, boolean isMaster, String password) {
        User newUser = UserList.getInstance().addUser(email, firstName, lastName, isMaster, password);
        user = newUser;
        return newUser;
    }

    /**
     * 
     * @param email
     * @return a User object
     */
    public User getUser(String email) {
        return UserList.getInstance().getUserByEmail(email);
    }

    /**
     * Makes the requested project the current project.
     * 
     * @param name
     */
    public void openProject(String name) {
        project = PL.getProject(name);
    }

    /**
     * Method to access all Users in the system
     * 
     * @return ArrayList<User>
     */
    public ArrayList<User> getAllUsers() {
        return UserList.getInstance().getUsers();
    }

    /**
     * Method to access all Projects in the system
     * 
     * @return ArrayList<Project>
     */
    public ArrayList<Project> getAllProjects() {
        return ProjectList.getInstance().getProjects();
    }

    /**
     * Creates a Project
     * 
     * @param name
     * @param SMEmail
     * @param email
     * @param columns
     * @param devs
     * @return a Project object
     */
    public Project makeProject(String name, String SMEmail, String email, ArrayList<Column> columns,
            ArrayList<User> devs) {
        project = ProjectList.getInstance().addProject(name, getUser(SMEmail), columns, devs);
        return project;
    }

    /**
     * exits program
     */
    public void exitProgram() {
        UL.saveUsers();
        PL.saveProjects();
        System.exit(0);
    }

    /**
     * prints scrumview of current open project
     */
    public void scrumBoardView() {
        System.out.println("Your projects: ");
        ArrayList<Project> projects = PL.getProjects();
        // System.out.println("\u001b[H\u001b[2J");
        int projectNum = 1;
        for (Project p : projects) {
            System.out.println(projectNum + ". " + p.getName());
            projectNum++;
            ArrayList<Column> columns = p.getColumns();
            for (Column c : columns) {
                System.out.println("\t- " + c.getName() + ":");
                ArrayList<Task> tasks = c.getTasks();
                int taskNum = 1;
                for (Task t : tasks) {
                    System.out.println("\t\t" + taskNum + ". " + t.getTitle());
                    taskNum++;
                    ArrayList<Comment> comments = t.getComments();
                    if (!comments.isEmpty()) {
                        System.out.println("\t\t\tComments: ");
                    }
                    printComments(comments, 1);
                }
            }
        }

    }

    /**
     * helper function to recursively print nested comments
     */
    private void printComments(ArrayList<Comment> comments, int i) {
        int commentNum = 1;
        for (Comment c : comments) {
            for (int x = 0; x < i; x++) {
                System.out.print("\t");
            }
            System.out.println("\t\t" + commentNum + ". " + c.getBody() + " - " + c.getUser().getName());
            commentNum++;
            if (c.getComments() != null) {
                printComments(c.getComments(), i + 1);
            }
        }
    }

    public StringBuffer helperFunction(ArrayList<Comment> comments, int i, StringBuffer sb) {
        int commentNum = 1;
        for (Comment c : comments) {
            for (int x = 0; x < i; x++) {
                sb.append("\t");//////// no escape char
            }
            sb.append("\t\t" + commentNum + ". " + c.getBody() + " - " + c.getUser().getName() + "\n");
            commentNum++;
            if (c.getComments() != null) {
                sb = helperFunction(c.getComments(), i + 1, sb);
            }
        }
        return sb;
    }

    public void printBoardToFile() throws IOException {
        sb.append("Your projects: \n");
        ArrayList<Project> projects = PL.getProjects();
        // System.out.println("\u001b[H\u001b[2J");
        int projectNum = 1;
        for (Project p : projects) {
            sb.append(projectNum + ". " + p.getName() + "\n");
            projectNum++;
            ArrayList<Column> columns = p.getColumns();
            for (Column c : columns) {
                sb.append("\t- " + c.getName() + ":\n");
                ArrayList<Task> tasks = c.getTasks();
                int taskNum = 1;
                for (Task t : tasks) {
                    sb.append("\t\t" + taskNum + ". " + t.getTitle() + "\n");
                    taskNum++;
                    ArrayList<Comment> comments = t.getComments();
                    if (!comments.isEmpty()) {
                        sb.append("\t\t\tComments: \n");
                    }
                    sb = helperFunction(comments, 1, sb);

                }
            }
        }
        File file = new File("BoardOutput.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(sb.toString());
        writer.close();

    }

    public void openColumn(String name) {
        column = project.getColumn(name);
    }

    public boolean addTask(String title, int priority, Date dueDate, int leaderboardPoints, String email) {
        if (title == null || dueDate == null ||  email == null) {
            return false;
        } else if (priority < 0 || leaderboardPoints < 0) {
            return false;
        }
        System.out.println("Which column would you like to add the task to?");
        // show column options
        System.out.println(column.getName());
        System.out.println("Enter the name of the task:");
        System.out.println(title);
        System.out.println("Enter the priority of the task (1-3):");
        System.out.println(priority);
        System.out.println("Enter the due date for the task(YYYY-MM-DD): ");
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        System.out.println(formatter.format(dueDate));
        System.out.println("Enter the points gained from finishing the task:");
        System.out.println(leaderboardPoints);
        System.out.println("Enter the user email that should complete this task:");
        System.out.println(email);
        Task newTask = new Task(title);
        newTask.setDueDate(dueDate);
        newTask.addPoints(leaderboardPoints);
        newTask.assignUser(UL.getUserByEmail(email));
        newTask.setPriority(priority);
        if (column.addTask(newTask)) {
            // System.out.println("A task by that name already exists!");
            return false;
        }
        return true;
    }

    public void makeComment(Task aTask, String body) {
        if (aTask == null) {
            return;
        }
        aTask.makeComment(body, user);
    }

    public void makeComment(String body) {
        comment.makeComment(body, user);
    }

    public void replyToComment() {

    }

    public boolean selectComment(int num) {
        if (task.getComments().size() < num || num < 1) {
            return false;
        }
        if (task.getComments().get(num - 1) == null) {
            return false;
        }
        this.comment = task.getComments().get(num - 1);
        return true;
    }

    public boolean selectTask(int num) {
        if (num > column.getTasks().size() || num < 1) {
            return false;
        }
        this.task = column.getTasks().get(num - 1);
        return true;
    }

    public Task getTask() {
        return task;
    }

    public boolean moveTask() {
        return project.moveTask("Project Backlog", "Curve the metal to make a cylindrical shape", "Doing");
    }

    public boolean moveTask(int num) {
        return project.moveTask("Project Backlog", "Make impossible burger possible", "Abandoned");
    }

    public boolean addColumn(String title) {
        return project.addColumn(title);
    }

}