package model;

import java.util.ArrayList;

/**
 * @author Ryan Caudill
 */
public class Project {
  private ArrayList<Column> columns;
  private String name;
  private User scrumMaster;
  private ArrayList<User> developers;

  public Project(){
    this.name = "Untitled";
    this.columns = new ArrayList<Column>();
    this.scrumMaster = null;
    this.developers = new ArrayList<User>();
  }

  public Project(String name){
    this.name = name;
    this.columns = new ArrayList<Column>();
    this.scrumMaster = null;
    this.developers = new ArrayList<User>();
  }

  public Project(ArrayList<Column> columns, String name, User scrumMaster, ArrayList<User> developers) {
    this.columns = columns;
    this.name = name;
    this.scrumMaster = scrumMaster;
    this.developers = developers;
  }
  
  public boolean addColumn(String column) {//finish??
    if (column == null || columnExists(column)) {
      return false;
    }
    columns.add(new Column(column));
    return true;
  }
  public boolean addColumn(Column column) {
    if (column == null || columnExists(column.getName())) {
      return false;
    }
    columns.add(column);
    return true;
  }
  private boolean columnExists(String columnName) {
    if (columnName == "" || columnName == null) {
      return false;
    }
    for (Column c : columns) {
      if (c.getName().equalsIgnoreCase(columnName)) {
        return true;
      }
    }
    return false;
  }

  public boolean removeColumn(String column) {
    for (Column c : columns) {
      if (c.getName().equalsIgnoreCase(column));
      {
        return columns.remove(c);
      }
    }
    return false;
  }

  public boolean renameColumn(String column, String rename) {
    if (column == null || rename == null || column == "" || rename == "") {
      return false;
    }
    for (Column c : columns) {
      if (c.getName().equals(column)) {
        c.setName(rename);
        return true;
      }
    }
    return false;
  }

  public boolean moveTask(String taskColumn, String task, String destination) {
    Column destColumn = null;
    for (Column c : columns) {
      if (c.getName().equalsIgnoreCase(destination)) {
        destColumn = c;//here
      }
    }
    if (destColumn == null) {
      return false;
    }
    Task temp = null;
    boolean exists = false;
    for (Column c : columns) {
      if (c.getName().equalsIgnoreCase(taskColumn)) {
        exists = true;
        for (Task t : c.getTasks()) {
          if (t.getTitle().equalsIgnoreCase(task)) {
            temp = t;
            break;
            //and here
          }
        }
        c.getTasks().remove(temp);
        destColumn.addTask(temp);
      }
    }

    if (temp == null || !exists) {
      return false;
    }
    return true;
  }

  public Column getColumn(String name){
    for(int i = 0; i < columns.size(); i++){
      if(columns.get(i).getName().toLowerCase().equals(name.toLowerCase())){
        return columns.get(i);
      }
    }
    return null;
  }

  public ArrayList<Column> getColumns() {
    return this.columns;
  }

  public String getName() {
    return this.name;
  }

  public User getMaster() {
    return this.scrumMaster;
  }

  public ArrayList<User> getDevelopers() {
    return this.developers;
  }

  
}
