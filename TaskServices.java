
import java.io.File;
import java.io.FileWriter;  
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path; 
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskServices {
    private List<Task> tasks;
    File file;
    
    public TaskServices() {
        this.tasks = loadTasks();
        String filePath = "data.json";
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                // Initialize with empty array
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while creating the data file.");
            }
    }
    }
    private List<Task> loadTasks(){
        List<Task> stored_tasks = new ArrayList<>();

        if (!Files.exists(Path.of("data.json"))){
            return new ArrayList<>();
        }

        try {
            String jsonContent = Files.readString(Path.of("data.json"));
            String[] taskList = jsonContent.replace("[", "")
                                            .replace("]", "")
                                            .split("},");
            for (String taskJson : taskList){
                if (!taskJson.endsWith("}")){
                    taskJson = taskJson + "}";
                    stored_tasks.add(Task.fromJson(taskJson));
                } else {
                    stored_tasks.add(Task.fromJson(taskJson));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return stored_tasks;
    }

    public Task getTaskById(UUID id){
        // for (Task task : tasks) {
        //     if (task.getId().equals(id)) {
        //         return task;
        //     }
        // }.orElse(IllegalArgumentException("Task not found: " + id));
        return tasks.stream().filter(task -> task.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
    }

    public void addTaskstoJson() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("[");
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).toJson());
                if (i < tasks.size() - 1) {
                    writer.write(", ");
                }
            }
            writer.write("]");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    public void addTask(String description) {
        Task newTask = new Task(description);
        tasks.add(newTask);
        addTaskstoJson();
       System.out.println("Task added: " + description);
    }

    public void updateTask(UUID id, String newDescription) throws IllegalArgumentException {
        // for (Task task : tasks) {
        //     if (task.getId().equals(id)) {
        //         task.setDescription(newDescription);
        //         System.out.println("Task updated: " + id);
        //         return;
        //     }
        // }
        tasks.stream().filter(task->task.getId().equals(id))
            .findFirst()
            .ifPresentOrElse(task -> {
                task.setDescription(newDescription);
                System.out.println("Task updated: " + id);
            }, () -> {
                System.out.println("Task not found: " + id);
            });
        addTaskstoJson();
        System.out.println("Task not found: " + id);
    }

    public void deleteTask(UUID id){
        try{
        if (tasks.removeIf(task -> task.getId().equals(id))) {
            System.out.println("Task deleted: " + id);
        } else {
            System.out.println("Task not found: " + id);
        }
        addTaskstoJson();
        } catch (IllegalArgumentException e){
            System.out.println("An error occurred while deleting the task: " + e.getMessage());
        }
    }

    public void viewAllTasks() {
        for (Task task : tasks) {
            System.out.println(task.getId() + ": " + task.getDescription() + " [" + task.getStatus() + "]");
        }
    }

    public void viewCompletedTasks() {
        for (Task task : tasks) {
            if (task.getStatus() == Task.Status.DONE) {
                System.out.println(task.getId() + ": " + task.getDescription());
            }
        }
    }

    public void viewTodoTasks() {
        for (Task task : tasks) {
            if (task.getStatus() == Task.Status.TODO) {
                System.out.println(task.getId() + ": " + task.getDescription());
            }
        }
    }

    public void viewInProgressTasks() {
        for (Task task : tasks) {
            if (task.getStatus() == Task.Status.IN_PROGRESS) {
                System.out.println(task.getId() + ": " + task.getDescription());
            }
        }
    }

    public void markTaskAsDone(UUID id){
        try{
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setStatus("DONE");
                System.out.println("Task marked as done: " + id);
                return;
            }
        }
        addTaskstoJson();
        } catch (IllegalArgumentException e){
        System.out.println("Task not found: " + id);
    }
    }
    public void markTaskAsInProgress(UUID id){
        try{
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setStatus("IN_PROGRESS");
                System.out.println("Task marked as in-progress: " + id);
                return;
            }
        }
        addTaskstoJson();
        } catch (IllegalArgumentException e){
            System.out.println("Task not found: " + id);
    }
    }
}