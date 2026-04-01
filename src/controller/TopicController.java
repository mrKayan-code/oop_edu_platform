package controller;

import java.util.List;

import java.util.stream.Collectors;

import interfaces.ContainerOfTopics;

import model.course.Course;
import model.topic.Module;
import model.topic.Section;
import model.topic.Topic;
import service.CourseService;
import service.TopicService;
import view.ConsoleView;
import java.util.ArrayList;

public class TopicController {
    private final ConsoleView view;
    private final TopicService topicService;
    private final CourseService courseService;

    public TopicController(ConsoleView view, TopicService topicService, CourseService courseService) {
        this.view = view;
        this.topicService = topicService;
        this.courseService = courseService;
    }

    public void showTopicMenu() {
        while (true) {
            view.clear();
            view.printHeader("Управление темками");

            printFullHierarchy();

            List<String> options = List.of(
                "Создать темkу",
                "Переместить тему в контейнер",
                "Редактировать тему",
                "Удалить тему"
            );
            view.printOptions(options);

            int choice = view.readInt("Выберите действие");

            switch (choice) {
                case 1 -> createTopic();
                case 2 -> moveTopic();
                case 3 -> editTopic();
                case 4 -> deleteTopic();
                case 0 -> { return; }
                default -> view.printError("Неверный выбор");
            }
            view.awaitContinue();
        }
    }

    private void printFullHierarchy() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            view.printInfo("Нет курсов");
            return;
        }

        for (Course course : courses) {
            view.println("\n+++>" + course.getName());
            view.println("─".repeat(40));
            
            List<Topic> rootTopics = course.getTopics().stream()
                .filter(t -> t.getGodModule() == null)
                .collect(Collectors.toList());
            
            for (Topic topic : rootTopics) {
                printTopicTree(topic, 1);
            }
        }
    }

    private void printTopicTree(Topic topic, int depth) {
        String indent = "  ".repeat(depth);
        String icon = topic instanceof Module ? "()" : topic instanceof Section ? "-" : "?";
        String tasksInfo = topic.getTasks().isEmpty() ? "" : " [" + topic.getTasks().size() + " задач]";
        
        view.println(indent + icon + topic.getName() + tasksInfo);
        
        if (topic instanceof Module module) {
            for (Topic sub : module.getTopics()) {
                printTopicTree(sub, depth + 1);
            }
        }
    }

    private void createTopic() {
        view.printSubHeader("Создание темы");

        printFullHierarchy();
        
        ContainerOfTopics container = selectContainer("Выбери, куда создать тему:");
        if (container == null) return;
        
        List<String> typeOptions = List.of(
            "Модуль", 
            "Секция"
        );
        
        view.printSubHeader("Тип темы:");
        view.printOptions(typeOptions);
        int typeChoice = view.readInt("Выберите тип");
        
        Topic newTopic = switch (typeChoice) {
            case 1 -> createModule(container);
            case 2 -> createSection(container);
            default -> null;
        };

        if (newTopic == null) {
            view.printError("неверный выбор");
            return;
        }
        
        try {
            if (container instanceof Course course) {
                courseService.addTopicToCourse(course.getId(), newTopic.getId());
            } else if (container instanceof Module module) {
                courseService.addTopicToCourse(module.getGodCourse().getId(), newTopic.getId());
                topicService.moveTopicToModule(module.getId(), newTopic.getId());
            }
            view.printSuccess("Тема создана: " + newTopic.getName());
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private void moveTopic() {
        view.printSubHeader("Перемещение темки");
        
        printFullHierarchy();
        
        List<Topic> allTopics = topicService.getAllTopics();
        if (allTopics.isEmpty()) {
            view.printError("Нет тем для перемещения");
            return;
        }

        view.printSubHeader("Выберите тему:");
        
        view.printList(allTopics, t -> {
            return t.getName() + " (" +  (t.getGodModule() != null ? ("в: " + t.getGodModule().getName()) : "" ) + (t.getGodCourse() != null ? (" в курсе: " + t.getGodCourse().getName()) : " ни в каком курсе") + ")";
        });

        int topicIdx = view.readInt("Номер темы") - 1;
        if (topicIdx < 0 || topicIdx >= allTopics.size()) return;
        Topic topicToMove = allTopics.get(topicIdx);
        
        ContainerOfTopics target = selectContainer("Выбери контейнер для перемещения:");
        if (target == null) return;
        if (target == topicToMove) {
            view.printError("Не надо перемещать темку саму в себя");
            return;
        }

        try {
            if (target instanceof Course) {
                if (topicToMove.getGodModule() != null) { 
                    topicService.removeTopicFromModule(topicToMove.getGodModule().getId(), topicToMove.getId());
                }
                courseService.moveTopicToCourse(target.getId(), topicToMove.getId());
            } else if (target instanceof Module moduleTarget) {
                if (topicToMove instanceof Module moduleToMove) {
                    if (topicService.isTopicInModuleChildren(moduleTarget.getId(), moduleToMove.getId())) {
                        view.printError("Не надо перемещать темку в своего ребенка");
                        return;
                    }                        
                }
                courseService.moveTopicToCourse(moduleTarget.getGodCourse().getId(), topicToMove.getId());
                topicService.moveTopicToModule(target.getId(), topicToMove.getId());
            }
            view.printSuccess("Тема перемещена в: " + target.getName());
        } catch (Exception e) {
            view.printError(e.getMessage());
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
        if (index < 0 || index >= topics.size()) return;
        
        Topic topic = topics.get(index);
        String newName = view.readRequired("Новое название");
        
        try {
            topicService.updateTopicName(topic.getId(), newName);
            view.printSuccess("Тема обновлена: " + topic.getName());
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private void deleteTopic() {
        view.printSubHeader("Удаление темы");
        List<Topic> topics = topicService.getAllTopics();
        if (topics.isEmpty()) {
            view.printInfo("Нет тем");
            return;
        }
        view.printList(topics, Topic::getName);
        int index = view.readInt("Выберите тему") - 1;
        if (index < 0 || index >= topics.size()) return;
        
        Topic topic = topics.get(index);
        if (view.readBoolean("Удалить '" + topic.getName() + "'?")) {
            topicService.deleteTopic(topic.getId());
            view.printSuccess("Тема удалена");
        }
    }

    private ContainerOfTopics selectContainer(String prompt) {
        view.printSubHeader(prompt);
        
        List<ContainerOfTopics> containers = new ArrayList<>();
        
        for (Course course : courseService.getAllCourses()) {
            
            containers.add(course);
        }
        
        for (Topic topic : topicService.getAllTopics()) {
            if (topic instanceof Module module && module.getGodCourse() != null) {
                containers.add(module);
            }
        }
        
        if (containers.isEmpty()) {
            view.printError("Нет доступных контейнеров");
            return null;
        }
        
        view.printList(containers, c -> {
            return c.getName();
        });
        
        int idx = view.readInt("Номер контейнера") - 1;
        if (idx < 0 || idx >= containers.size()) return null;
        return containers.get(idx);
    }

    private Module createModule(ContainerOfTopics container) {
        String name = view.readRequired("Название модуля");

        return topicService.createModule(name);
    }

    private Section createSection(ContainerOfTopics container) {
        String name = view.readRequired("Название секты");

        return topicService.createSection(name);
    }
}