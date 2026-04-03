package controller;

import java.util.List;

import java.util.stream.Collectors;

import model.task.Task;
import model.task.algorithmic.AlgorithmicTask;
import model.task.algorithmic.ProgrammingLang;
import model.task.quiz.Question;
import model.task.quiz.QuizTask;
import model.task.with_repository.TaskWithRepository;
import model.topic.Topic;
import service.TaskService;
import service.TopicService;
import view.ConsoleView;
import java.util.ArrayList;

public class TaskController {
    private final ConsoleView view;
    private final TaskService taskService;
    private final TopicService topicService;

    public TaskController(ConsoleView view, TaskService taskService, TopicService topicService) {
        this.view = view;
        this.taskService = taskService;
        this.topicService = topicService;
    }

    public void showTaskMenu() {
        while (true) {
            view.clear();
            view.printHeader("Управление тасками");

            printAllTasks();

            List<String> options = List.of(
                "Создать задачу с репозиторием",
                "Создать алгоритмическую задачу",
                "Создать викторину",
                "Переместить задачу в тему",
                "Редактировать задачу",
                "Управление вопросами QuizService",
                "Удалить задачу"
            );
            view.printOptions(options);

            int choice = view.readInt("");

            switch (choice) {
                case 1 -> createTaskWithRepo();
                case 2 -> createAlgorithmicTask();
                case 3 -> createQuizTask();
                case 4 -> moveTaskToTopic();
                case 5 -> editTask();
                case 6 -> manageQuizQuestions();
                case 7 -> deleteTask();
                case 0 -> { return; }
                default -> view.printError("Неправильно ввеел");
            }
            view.awaitContinue();
        }
    }

    private void printAllTasks() {
        List<Task> allTasks = taskService.getAllTasks();
        if (allTasks.isEmpty()) {
            view.printInfo("Нету задач еще");
            return;
        }

        view.println("\nВсе задачи:");

        view.printList(allTasks, (task) -> {
            String typeSymb = switch (task) {
                case QuizTask q -> "Q";
                case AlgorithmicTask a -> "A";
                case TaskWithRepository r -> "R";
                default -> "U";
            };
            
            String location = task.getGodTopic() != null 
                ? "в темkе: " + task.getGodTopic().getName() 
                + (task.getGodTopic().getGodCourse() != null 
                    ? " в курсе: " + task.getGodTopic().getGodCourse().getName() 
                    : "")
                : "висит одна";
            
             return String.format("%s %s %s", 
                typeSymb, task.getName(), location);
        });

        view.println("");
    }

    private void createTaskWithRepo() {
        view.printSubHeader("Таска с репозиторием");
        String name = view.readRequired("Название");
        String text = view.readRequired("Текст задания");
        String example = view.readLine("Пример");
        String repoLink = view.readRequired("Ссылка на репозиторий");
        
        try {
            TaskWithRepository task = taskService.createTaskWithRepository(name, text, example, repoLink);
            view.printSuccess("Таска создана: " + task.getName());
            
            if (view.readBoolean("Назначить задачу в темку?")) {
                assignTaskToTopic(task);
            }
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private void createAlgorithmicTask() {
        view.printSubHeader("Алгоритмическая таска");
        String name = view.readRequired("Название");
        String text = view.readRequired("Текст задания");
        String example = view.readLine("Пример");

        List<ProgrammingLang> selectedLanguages = selectProgrammingLanguages();
        
        try {
            AlgorithmicTask task = taskService.createAlgorithmicTask(name, text, example, selectedLanguages);
            view.printSuccess("Задача создана: " + task.getName());
            
            if (view.readBoolean("Назначить задачу в темку?")) {
                assignTaskToTopic(task);
            }
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private List<ProgrammingLang> selectProgrammingLanguages() {
        view.printSubHeader("Выбери языки");
        
        List<ProgrammingLang> allLangs = List.of(ProgrammingLang.values());
        
        view.printList(allLangs, (l) -> l.toString());
        view.println("  0. все");
        
        String input = view.readLine("Номера");
        
        
        if (input.isBlank() || input.trim().equals("0")) {
            return List.copyOf(allLangs);
        }

        List<ProgrammingLang> selected = new ArrayList<>();
        String[] parts = input.trim().split("\\s+");
        
        for (String part : parts) {
            try {
                int idx = Integer.parseInt(part) - 1;
                if (idx >= 0 && idx < allLangs.size()) {
                    ProgrammingLang lang = allLangs.get(idx);
                    if (!selected.contains(lang)) {
                        selected.add(lang);
                    }
                }
            } catch (NumberFormatException e) { }
        }
        
        return selected;
    }

    private void createQuizTask() {
        view.printSubHeader("Викторина ирина");
        String name = view.readRequired("Название");
        
        try {
            QuizTask quiz = taskService.createQuizTask(name);
            view.printSuccess("Викторина создана: " + quiz.getName());
            
            if (view.readBoolean("Назначить викторину в темку?")) {
                assignTaskToTopic(quiz);
            }
            
            if (view.readBoolean("Добавить вопросы сейчас?")) {
                manageQuizQuestions(quiz);
            }
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private void assignTaskToTopic(Task task) {
        List<Topic> topics = topicService.getAllTopics();
        if (topics.isEmpty()) {
            view.printError("Нету тем для назначения");
            return;
        }
        
        view.printSubHeader("Выбери тему:");
        view.printList(topics, t -> {
            String course = t.getGodCourse() != null ? t.getGodCourse().getName() : "висит одна";
            return t.getName() + " в курсе: " + course;
        });
        
        int idx = view.readInt("Номер темы") - 1;

        if (idx >= 0 && idx < topics.size()) {
            Topic topic = topics.get(idx);
            topicService.addTaskToTopic(topic.getId(), task.getId());
            view.printSuccess("Таска назначена в тему: " + topic.getName());
        }
    }

    private void moveTaskToTopic() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            view.printError("Нет задач для перемещения");
            return;
        }
        
        view.printSubHeader("Выбери задачу для перемещения:");
        
        view.printList(tasks, Task::getName);
        
        int taskIdx = view.readInt("Номер таски") - 1;
        
        if (taskIdx < 0 || taskIdx >= tasks.size()) return;
        
        Task task = tasks.get(taskIdx);
        
        List<Topic> allTopics = topicService.getAllTopics();

        if (allTopics.isEmpty()) {
            view.printError("Нет доступных тем");
            return;
        }
        
        view.printSubHeader("Выбери таргет тему:");

        view.printList(allTopics, Topic::getName);

        int topicIdx = view.readInt("Номер темы") - 1;
        
        if (topicIdx < 0 || topicIdx >= allTopics.size()) return;
        
        Topic targetTopic = allTopics.get(topicIdx);
        
        try {

            if (task.getGodTopic() != null) {
                topicService.removeTaskFromTopic(task.getGodTopic().getId(), task.getId());
            }

            topicService.addTaskToTopic(targetTopic.getId(), task.getId());
            
            view.printSuccess("Задача перемещена в: " + targetTopic.getName());
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private void editTask() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            view.printInfo("Нету задач");
            return;
        }
        
        view.printSubHeader("Выбери задачу для эдита:");
        
        view.printList(tasks, Task::getName);
        
        int idx = view.readInt("Номер") - 1;
        
        if (idx < 0 || idx >= tasks.size()) return;
        
        Task task = tasks.get(idx);
        
        view.printSubHeader("Редактирование: " + task.getName());
        
        String newName = view.readRequired("Новое название");
        String newText = view.readRequired("Новый текст задания");
        String newExample = view.readLine("Новый пример");
        
        try {
            if (!newText.isBlank()) {
                taskService.updateTaskText(task.getId(), newText);
            }

            if (!newExample.isBlank()) {
                taskService.updateTaskExample(null, newExample);
            }
            
            taskService.updateTaskName(task.getId(), newName);
            view.printSuccess("Задача обновлена");
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private void manageQuizQuestions() {
        List<Task> quizTasks = taskService.getAllTasks().stream()
            .filter(t -> t instanceof QuizTask)
            .collect(Collectors.toList());
            
        if (quizTasks.isEmpty()) {
            view.printError("Нет виктоирин");
            return;
        }
        
        view.printSubHeader("Выберите викторину:");
        
        view.printList(quizTasks, Task::getName);
        
        int idx = view.readInt("Номер") - 1;
        if (idx < 0 || idx >= quizTasks.size()) return;
        
        QuizTask quiz = (QuizTask) quizTasks.get(idx);
        manageQuizQuestions(quiz);
    }

    private void manageQuizQuestions(QuizTask quiz) {
        while (true) {
            view.clear();
            view.printHeader("Q " + quiz.getName());
            
            List<Question> questions = quiz.getQuestions();
            if (questions.isEmpty()) {
                view.printInfo("В викторине пока нет вопросов");
            } else {
                view.printList(questions, (q) -> {
                    return String.format("%s [%s]", 
                        q.getQuestionText(), q.getQuestionType());
                });
            }
            
            List<String> options = List.of(
                "Добавить вопрос 1 из 2",
                "Добавить вопрос 1 из нескольких",
                "Добавить вопрос со свободным ответом",
                "Удалить вопрос"
            );
            view.printOptions(options);
            
            int choice = view.readInt("");
            switch (choice) {
                case 1 -> addBinaryQuestion(quiz);
                case 2 -> addMultipleChoiceQuestion(quiz);
                case 3 -> addFreeAnswerQuestion(quiz);
                case 4 -> removeQuestion(quiz);
                case 0 -> { return; }
                default -> view.printError("Неправильно вводишь");
            }
            view.awaitContinue();
        }
    }

    private void addBinaryQuestion(QuizTask quiz) {
        view.printSubHeader("Вопрос 1 из 2");
        String text = view.readRequired("Текст вопроса");
        boolean correct = view.readBoolean("Правильный ответ: Да (y/n) | Нет (n/н)");
        
        quiz.createBinaryQuestion(text, correct);
        
        view.printSuccess("Вопрос добавлен");
    }

    private void addMultipleChoiceQuestion(QuizTask quiz) {
        view.printSubHeader("Вопрос один из многих");
        String text = view.readRequired("Текст вопроса");
        
        List<String> options = new ArrayList<>();
        view.println("Добавь варианты:");
        while (true) {
            String opt = view.readLine("Вариант " + (options.size() + 1));
            if (opt.isBlank()) {
                if (options.size() < 2) {
                    view.printError("Нужно минимум 2 варианта");
                    continue;
                }
                break;
            }
            options.add(opt);
        }

        view.println("получилось:");
        view.printList(options, (o) -> o);
        
        int correctIdx = view.readInt("Номер правильного варианта") - 1;
        if (correctIdx < 0 || correctIdx >= options.size()) {
            view.printError("Неверный номер");
            return;
        }
        
        quiz.createMultipleChoiceQuestion(text, options, correctIdx);
        
        view.printSuccess("Вопрос добавлен");
    }

    private void addFreeAnswerQuestion(QuizTask quiz) {
        view.printSubHeader("Вопрос со свободным ответом");
        String text = view.readRequired("Текст вопроса");
        String correct = view.readRequired("Правильный ответ");
        
        quiz.createFreeAnswerQuestion(text, correct);

        view.printSuccess("Вопрос добавлен");
    }

    private void removeQuestion(QuizTask quiz) {
        List<Question> questions = quiz.getQuestions();
        
        if (questions.isEmpty()) {
            view.printInfo("Нет вопросов для удаления");
            return;
        }
        
        view.printSubHeader("Выберите вопрос для удаления:");
        
        view.printList(questions, (q) -> q.getQuestionText());
        
        int idx = view.readInt("Номер вопроса") - 1;
        if (idx < 0 || idx >= questions.size()) return;
        
        Question toRemove = questions.get(idx);
        if (view.readBoolean("Удалить вопрос '" + toRemove.getQuestionText() + "'?")) {
            quiz.removeQuestion(toRemove);
            view.printSuccess("Вопрос удалён");
        }
    }

    private void deleteTask() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            view.printInfo("Нет задач");
            return;
        }
        
        view.printSubHeader("Выберите задачу для удаления:");
        
        view.printList(tasks, Task::getName);
        
        int idx = view.readInt("Номер") - 1;
        if (idx < 0 || idx >= tasks.size()) return;
        
        Task task = tasks.get(idx);
        
        String location = task.getGodTopic() != null 
                ? "в теме: " + task.getGodTopic().getName() 
                + (task.getGodTopic().getGodCourse() != null 
                    ? " в курсе: " + task.getGodTopic().getGodCourse().getName() 
                    : "")
                : "висит одна";
        view.printInfo(String.format("выбранная задача %s %s", task.getName(), location));
        
        if (view.readBoolean("Удалить задачу '" + task.getName() + "'?")) {
            if (task.getGodTopic() != null) {
                Topic topic = task.getGodTopic();
                topicService.removeTaskFromTopic(topic.getId(), task.getId());
            }
            taskService.deleteTask(task.getId());
            view.printSuccess("Задача удалена");
        }
    }
}
