import java.util.Scanner;

import controller.CourseController;
import controller.MenuConroller;
import repository.CourseRepository;
import repository.TaskRepository;
import repository.TopicRepository;
import service.CourseService;
import service.TaskService;
import service.TopicService;
import view.ConsoleView;

public class App {
    public static void main(String[] args) {
        CourseRepository courseRepository = new CourseRepository();
        TopicRepository topicRepository = new TopicRepository();
        TaskRepository taskRepository = new TaskRepository();

        CourseService courseService = new CourseService(courseRepository, topicRepository);
        TopicService topicService = new TopicService(topicRepository, taskRepository);
        TaskService taskService = new TaskService(taskRepository, topicRepository);

        Scanner scanner = new Scanner(System.in, "cp866");
        ConsoleView view = new ConsoleView(scanner);
        
        CourseController courseController = new CourseController(view, courseService);


        MenuConroller menuConroller = new MenuConroller(view, courseController, null, null);

        menuConroller.run();
    }
}
