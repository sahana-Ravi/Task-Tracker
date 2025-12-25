import java.time.LocalDateTime;
import java.util.UUID;


public class Task{
    private UUID id;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    
    public enum Status {TODO, IN_PROGRESS, DONE};

    public Task(String description) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = Status.TODO;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
        this.updatedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    public String toString() {
        return id + ": " + description + " [" + status + "]" + " (Created: " + createdAt + ", Updated: " + updatedAt + ")";
    }   
    public String toJson() {
        return "{"
            + "\"id\": " + id + ", "
            + "\"description\": \"" + description + "\", "
            + "\"status\": \"" + status + "\", "
            + "\"createdAt\": \"" + createdAt + "\", "
            + "\"updatedAt\": \"" + updatedAt + "\""
            + "}";
    }
    public static Task fromJson(String json) {
        json = json.replace("{", "").replace("}", "").replace("\"", "");
        String[] json1 = json.split(",");

        String id = json1[0].split(":")[1].strip();
        String description = json1[1].split(":")[1].strip();
        String statusString = json1[2].split(":")[1].strip();
        String createdAtStr = json1[3].split("[a-z]:")[1].strip();
        String updatedAtStr = json1[4].split("[a-z]:")[1].strip();

        Status status = Status.valueOf(statusString.toUpperCase().replace(" ", "_"));

        Task task = new Task(description);
        task.id = UUID.fromString(id);
        task.status = status;
        task.createdAt = LocalDateTime.parse(createdAtStr);
        task.updatedAt = LocalDateTime.parse(updatedAtStr);         
        return task;
    }
       
}