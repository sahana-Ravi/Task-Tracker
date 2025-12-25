import java.util.Scanner;
import java.util.UUID;

public class Options {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskServices taskService = new TaskServices();
        boolean running = true;

        while (running) {
            System.out.println("Available Options:");
            System.out.println("1. Add Task");
            System.out.println("2. Update Task");
            System.out.println("3. Delete Task");
            System.out.println("4. View All Tasks");
            System.out.println("5. View Completed Tasks");
            System.out.println("6. View TODO Tasks");
            System.out.println("7. View In-Progress Tasks");
            System.out.println("8. Mark Task as Completed");
            System.out.println("9. Mark Task as In-Progress");
            System.out.println("10. Exit");
            System.out.println("Please select an option by entering the corresponding number:");
            System.out.print(">");
            System.out.flush();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left by nextInt()

            // Handle user choice here
            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    taskService.addTask(description);
                    break;
                case 2:
                    System.out.print("Enter task ID: ");
                    UUID idToUpdate = UUID.fromString(scanner.nextLine());
                    System.out.print("Enter new description: ");
                    String newDescription = scanner.nextLine();
                    taskService.updateTask(idToUpdate, newDescription);
                    break;
                case 3:
                    System.out.print("Enter task ID: ");
                    UUID id = UUID.fromString(scanner.nextLine());
                    taskService.deleteTask(id);
                    break;
                case 4:
                    taskService.viewAllTasks();
                    break;
                case 5:
                    taskService.viewCompletedTasks();
                    break;
                case 6:
                    taskService.viewTodoTasks();
                    break;
                case 7:
                    taskService.viewInProgressTasks();
                    break;
                case 8:
                    System.out.print("Enter task ID: ");
                    UUID idToComplete = UUID.fromString(scanner.nextLine());
                    taskService.markTaskAsDone(idToComplete);   
                    break;
                case 9: 
                    System.out.print("Enter task ID: ");
                    UUID idToInProgress = UUID.fromString(scanner.nextLine());
                    taskService.markTaskAsInProgress(idToInProgress);   
                    break; 
                case 10:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}