package controller;

import java.util.List;

import model.course.Course;
import service.CourseService;
import view.ConsoleView;

public class CourseController {
    private final ConsoleView view;
    private final CourseService courseService;

    public CourseController(ConsoleView view, CourseService courseService) {
        this.view = view;
        this.courseService = courseService;
    }

    public void showCourseMenu() {
        while (true) {
            view.clear();
            view.printHeader("ИНФОЦЫГАНСКИЕ КУРСЫ");

            List<Course> courses = courseService.getAllCourses();

            view.printList(courses, Course::getName);

            List<String> options = List.of(
                "Создать курс",
                "Редактировать курс",
                "Удалить курс"
            );

            view.printOptions(options);

            int choice = view.readInt("");
            
            switch (choice) {
                case 1 -> createCourse();
                case 2 -> editCourse();
                case 3 -> deleteCourse();
                case 0 -> { return; }
                default -> view.printError("Неправильно вводишь");
            }

            view.awaitContinue();
        }
    }
        

    private void createCourse() {
        view.printSubHeader("Создание курса");

        String name = view.readRequired("Название курса");
        
        try {
            Course course = courseService.createCourse(name);
            view.printSuccess("Курс создан: " + course.getName());
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private void editCourse() {
        view.printSubHeader("Редактирование курса");
        
        List<Course> courses = courseService.getAllCourses();
        
        if (courses.isEmpty()) {
            view.printInfo("Нет курсов");
            return;
        }
        
        view.printList(courses, Course::getName);

        int index = view.readInt("Выберите курс") - 1;
        
        if (index < 0 || index >= courses.size()) {
            view.printError("Неправильно ввел");
            return;
        }
        
        Course course = courses.get(index);
        String newName = view.readRequired("Новое название");
        
        try {
            courseService.updateCourseName(course.getId(), newName);
            view.printSuccess("Курс обновлён");
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private void deleteCourse() {
        view.printSubHeader("Удаление курса");

        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            view.printInfo("Нет курсов");
            return;
        }
        
        view.printList(courses, Course::getName);
        int index = view.readInt("Выберите курс") - 1;
        
        if (index < 0 || index >= courses.size()) {
            view.printError("Неверно ввел");
            return;
        }
        
        Course course = courses.get(index);
        
        view.printInfo(String.format("Удаление этого курса удалит еще столько: %d тем внутри него", course.getTopics().size()));
        if (view.readBoolean("Удалить курс '" + course.getName() + "'")) {
            try {
                courseService.deleteCourseWithTopics(course.getId());
                view.printSuccess("Курс удалён");
            } catch (Exception e) {
                view.printError(e.getMessage());
            }
        }
    }

}
