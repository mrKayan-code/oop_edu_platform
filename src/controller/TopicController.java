package controller;

import java.util.List;

import model.course.Course;
import model.topic.Section;
import model.topic.Topic;
import service.CourseService;
import service.TopicService;
import view.ConsoleView;
import model.topic.Module;

public class TopicController {
    private final ConsoleView view;
    private final TopicService topicService;
    private final CourseService courseService;

    public TopicController(ConsoleView view, TopicService topicService, CourseService courseService) {
        this.view = view;
        this.courseService = courseService;
        this.topicService = topicService;
    }

    public void showTopicMenu() {
        while (true) {
            view.clear();
            view.printHeader("Темы");

            List<Course> courses = courseService.getAllCourses();
            if (courses.isEmpty()) {
                view.printError("Сначала создайте курс, темы не могут существовать без курсов");
                view.awaitContinue();
                return;
            }

            view.print("все курсы:");
            view.printList(courses, Course::getName);

            List<Topic> topics = topicService.getAllTopics();

            view.print("все темы:");
            view.printList(topics, t -> {
                if (t.getGodModule() == null) return t.getName() + " из курса: " + t.getGodCourse().getName();
                else return t.getName() + " из курса: " + t.getGodCourse().getName() + " из: " + t.getGodModule().getName();
            });

            List<String> options = List.of(
                "Работать с темами в определенном курсе",
                "Создать тему",
                "Редактировать тему",
                "Удалить тему"
            );

            view.printOptions(options);

            int choice = view.readInt("");
            
            switch (choice) {
                case 1 -> { };
                case 2 -> { };
                case 3 -> editTopic();
                case 4 -> { };
                case 0 -> { return; }
                default -> view.printError("Неправильно вводишь");
            }

            view.awaitContinue();
        }
    }

    private void editTopic() {
        view.printSubHeader("Редактирование темы");
        
        List<Topic> topics = topicService.getAllTopics();
        
        if (topics.isEmpty()) {
            view.printInfo("Нет тем");
            return;
        }
        
        view.printList(topics, Topic::getName);

        int index = view.readInt("Выберите тему") - 1;
        
        if (index < 0 || index >= topics.size()) {
            view.printError("Неправильно ввел");
            return;
        }
        
        Topic topic = topics.get(index);

        if (topic instanceof Section) {
            editSection((Section) topic);
        } else if (topic instanceof Module) {
            editModule((Module) topic);
        }
    }

    private void editSection(Section section) {
        view.printSubHeader("Редактирование " + section.getName());

        String newName = view.readRequired("Новое название");

        try {
            topicService.updateTopicName(section.getId(), newName);
            view.printSuccess("Секция обновлёна");
        } catch (Exception e) {
            view.printError(e.getMessage());
        }

    }

    private void editModule(Module module) {
        view.printSubHeader("Редактирование " + module.getName());

        List<String> options = List.of(
            "Поменять имя",
            "Перетащить его в другой курс",
            "Перетащить его в другой модуль этого курса"
        );

        view.printOptions(options);

        int choice = view.readInt("");
        
        switch (choice) {
            case 1 -> {
                String newName = view.readRequired("Новое название");

                try {
                    topicService.updateTopicName(module.getId(), newName);
                    view.printSuccess("Модуль обновлён");
                } catch (Exception e) {
                    view.printError(e.getMessage());
                }
            }
            case 2 -> { };
            case 3 -> editTopic();
            case 0 -> { return; }
            default -> view.printError("Неправильно вводишь");
        }

        view.awaitContinue();
    }

}
