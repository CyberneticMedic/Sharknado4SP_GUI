package model;
/*
 * @author Ashiya Branch 
*/
import java.util.ArrayList;
/*
 * The Column class gives the user the ability manage the tasks within columns.
*/
public class Column {
    private String name;
    private ArrayList<Task> tasks;

    /* 
     * Creates a an new column with a certain name.
     * @param name of column.
     */
    public Column(String name) 
    {
        this.name = name;
        this.tasks = new ArrayList<Task>();
    }
    /**
     * Constructor for dataloader to utilize
     * @param name string name
     * @param tasks arraylist of tasks
     */
    public Column(String name, ArrayList<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }
    /*
     * Adds a task to the column.
     * @param task is the added task.
     * @return true if the task was added successfully and false if not. 
     */
    public boolean addTask(Task task) 
    {
        if (task !=null){
            return tasks.add(task);
        }
        return false;
    }
    public boolean taskExists(Task task) {
        if(task == null){
            return false;
        }
        ProjectList pl = ProjectList.getInstance();
        ArrayList<Project> projects = pl.getProjects();
        for (Project p : projects) {
            ArrayList<Column> columns = p.getColumns();
            for(Column c : columns){
                ArrayList<Task> tasks = c.getTasks();
                for (Task t : tasks) {
                    if (t.getTitle().equalsIgnoreCase(task.getTitle())) {
                        return true;
                    }
                }
            } 
        }
        return false;
    }
    /*
     * Allows user to view the tasks in the column.
     * @returns an arraylist of tasks in the column.
     */
    public ArrayList<Task> TaskListView() 
    {
        return new ArrayList<>(tasks); 
    }

    /*
     * Removes task from column.
     * @param task as the task to remove. 
     * @returm true if task was removed successfull and false if not.
     */
    public boolean removeTask(Task task) 
    {
        if(task !=null){
            return tasks.remove(task);
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getTask(String name){
        for(int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).getTitle().equals(name)){
                return tasks.get(i);
            }
        }
        return null;
    }

    public boolean setName(String rename) {
        if (rename == "" || rename == null) {
            return false;
        }
        this.name = rename;
        return true;
    }
}
