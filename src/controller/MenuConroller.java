package controller;

import java.util.List;

import view.ConsoleView;

public class MenuConroller {
    private final ConsoleView view;
    private final CourseController courseController;
    private final TopicController topicController;
    private final TaskController taskController;
    private final SolutionController solutionController;

    private boolean running = true;

    public MenuConroller(ConsoleView view, CourseController courseController, TopicController topicController, TaskController taskController, SolutionController solutionController) {
        this.view = view;
        this.courseController = courseController;
        this.topicController = topicController;
        this.taskController = taskController;
        this.solutionController = solutionController;
    }

    public void run() {
        while (running) {
            showMenu();
        }
        view.print("ОТГУЛ");
    }

    private void showMenu() {
        view.clear();

        view.printHeader("МЕНЮ");

        List<String> options = List.of(
            "Управление курсами по воздуханству",
            "Управление всякими темками",
            "Управление задачами",
            "Управление решениями"
        );

        view.printOptions(options);

        int choice = view.readInt("");

        switch (choice) {
            case 1 -> courseController.showCourseMenu();
            case 2 -> topicController.showTopicMenu();
            case 3 -> taskController.showTaskMenu();
            case 4 -> solutionController.showSolutionMenu();
            case 0 -> running = false;
            default -> view.printError("Неправильно вводишь");
        }

        view.awaitContinue();
    }
}
