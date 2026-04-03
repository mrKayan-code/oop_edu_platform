package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.course.Course;
import model.solution.Solution;
import model.task.Task;
import model.task.algorithmic.AlgorithmicTask;
import model.task.quiz.Question;
import model.task.quiz.QuizTask;
import model.task.with_repository.TaskWithRepository;
import model.topic.Section;
import model.topic.Topic;
import service.CourseService;
import service.SolutionService;
import view.ConsoleView;
import model.topic.Module;
import java.util.Map;

public class SolutionController {
    private final ConsoleView view;
    private final SolutionService solutionService;
    private final CourseService courseService;

     public SolutionController(ConsoleView view, SolutionService solutionService, CourseService courseService) {
        this.view = view;
        this.solutionService = solutionService;
        this.courseService = courseService;
    }

    public void showSolutionMenu() {
        while (true) {
            view.clear();
            view.printHeader("Выбор задачи для решения");

            List<String> options = List.of(
                "Отправить решение задачи",
                "Посмотреть все решения по темам",
                "Посмотреть все видимые решения по темам",
                "Удалить решение"
            );
            view.printOptions(options);

            int choice = view.readInt("");

            switch (choice) {
                case 1 -> submitSolution();
                case 2 -> viewAllSolutionsByTopics();
                case 3 -> viewAllVisibleSolutionsByTopics();
                case 4 -> deleteSolution();
                case 0 -> { return; }
                default -> view.printError("Неправильно вводишь");
            }
            view.awaitContinue();
        }
    }

    private void submitSolution() {
        view.printSubHeader("Выбор задачи для решения");

        Course course = chooseCourse();
        if (course == null) return;

        Topic topic = chooseVisibleTopic(course);
        if (topic == null) return;

        Task task = chooseTaskFromTopic(topic);
        if (task == null) return;


        if (task instanceof QuizTask quiz) {
            submitQuizSolution(quiz);
        } else if (task instanceof AlgorithmicTask algo){
            submitAlgorithmicTasksSolution(algo);
        } else if (task instanceof TaskWithRepository repoTask) {
            submitTaskTaskWithRepositorySolution(repoTask);
        }
    }
    
    private Course chooseCourse() {
        view.printSubHeader("Выбор курса");

        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            view.printInfo("Нет курсов");
            return null;
        }
        
        view.printList(courses, (c) -> "++>" + c.getName());

        int index = view.readInt("Выберите курс") - 1;
        if (index < 0 || index >= courses.size()) return null;

        return courses.get(index);
    }

    private Topic chooseVisibleTopic(Course course) {
        List<Topic> rootTopics = course.getTopics().stream()
            .filter(t -> t.getGodModule() == null && t.getVisibility())
            .collect(Collectors.toList());

        if (rootTopics.isEmpty()) {
            view.printInfo("Нет доступных тем");
            return null;
        }

        List<Topic> collected = new ArrayList<>();

        for (Topic topic : rootTopics) {
            printVisibleTopicTree(collected, topic, 0);
        }

        int index = view.readInt("Выберите тему") - 1;
        if (index < 0 || index >= collected.size()) return null;

        return collected.get(index);
    }

    private void printVisibleTopicTree(List<Topic> collected, Topic topic, int depth) {
        collected.add(topic);
        String num = String.valueOf(collected.size()) + ". ";
        String indent = "  ".repeat(depth);
        String typeSymb = topic instanceof Module ? "m" : topic instanceof Section ? "s" : "-";
        String tasksInfo = topic.getTasks().isEmpty() ? "" : " [" + topic.getTasks().size() + " задач внутри]";

        view.println(indent + num + typeSymb + " " + topic.getName() + tasksInfo);
        
        if (topic instanceof Module module) {
            for (Topic sub : module.getTopics()) {
                if (sub.getVisibility()) {
                    printVisibleTopicTree(collected, sub, depth + 1);
                }
            }
        }
    }

    private Task chooseTaskFromTopic(Topic topic) {
        List<Task> tasks = topic.getTasks();
        
        if (tasks.isEmpty()) {
            view.printError("В этой теме нет задач");
            return null;
        }

        view.printSubHeader("Задачи в " + topic.getName());
        view.printList(tasks, t -> {
            String type = switch (t) {
                case QuizTask q -> "Викторина ";
                case AlgorithmicTask a -> "Алгоритмическая ";
                case TaskWithRepository r -> "С репозиторием ";
                default -> "";
            };
            return type + t.getName();
        });

        int index = view.readInt("Выберите задачу") - 1;
        if (index < 0 || index >= tasks.size()) return null;

        return tasks.get(index);
    }

    // private void submitSimpleSolution(Task task) {
    //     view.printSubHeader(task.getName());
    //     view.println("Текст задачи: " + task.getTaskText());
        
    //     if (task.getExample() != null && !task.getExample().isBlank()) {
    //         view.printInfo("Пример: " + task.getExample());
    //     }

    //     String solutionText;
    //     solutionText = view.readRequired("Ваш ответ");

    //     try {
    //         solutionService.submitSolution(task, solutionText);
    //         view.printSuccess("Решение отправлено");
    //     } catch (Exception e) {
    //         view.printError(e.getMessage());
    //     }
    // }

    private void submitAlgorithmicTasksSolution(AlgorithmicTask task) {
        view.printSubHeader(task.getName());
        view.println("Текст задачи: " + task.getTaskText());
        view.println("Доступные языки");
        view.printList(task.getProgrammingLangs(), (l) -> l.toString());
        
        if (task.getExample() != null && !task.getExample().isBlank()) {
            view.printInfo("Пример: " + task.getExample());
        }

        String solutionText;
        solutionText = view.readRequired("Ваш ответ");

        try {
            solutionService.submitSolution(task, solutionText);
            view.printSuccess("Решение отправлено");
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private void submitTaskTaskWithRepositorySolution(TaskWithRepository task) {
        view.printSubHeader(task.getName());
        view.println("Текст задачи: " + task.getTaskText());
        view.println("Repository: " + task.getRepositoryLink());
        
        if (task.getExample() != null && !task.getExample().isBlank()) {
            view.printInfo("Пример: " + task.getExample());
        }

        String solutionText;
        solutionText = view.readRequired("Ваш ответ");

        try {
            solutionService.submitSolution(task, solutionText);
            view.printSuccess("Решение отправлено");
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private void submitQuizSolution(QuizTask quiz) {
        view.printSubHeader(quiz.getName());

        List<Question> questions = quiz.getQuestions();
        if (questions.isEmpty()) {
            view.printError("В викторине нет вопросов");
            return;
        }

        List<String> answers = new ArrayList<>();
        int correctCount = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);

            view.clear();
            view.printSubHeader("Вопрос " + (i + 1) + "/" + questions.size());
            view.println(q.getQuestionText());

            String answer = view.readRequired("Ваш ответ");
            answers.add(answer);

            if (q.checkAnswer(answer)) {
                view.printSuccess("Верно");
                correctCount++;
            } else {
                view.printError("Неверно правильный ответ: " + q.getCorrectAnswerText());
            }

            view.awaitContinue();
        }

        try {
            solutionService.submitQuizTaskAnswers(quiz, answers);

            view.clear();
            view.printHeader("Результат викторины " + quiz.getName());
            view.println("Правильно: " + correctCount + " из " + questions.size());
        } catch (Exception e) {
            view.printError(e.getMessage());
        }
    }

    private void viewAllSolutionsByTopics() {
        view.printSubHeader("Все решения");

        List<Solution> allSolutions = solutionService.getAllSolutions();
        if (allSolutions.isEmpty()) {
            view.printInfo("Нет решений");
            return;
        }

        Map<Topic, List<Solution>> byTopic = allSolutions.stream()
            .collect(Collectors.groupingBy(s -> s.getTask().getGodTopic()));

        for (Map.Entry<Topic, List<Solution>> entry : byTopic.entrySet()) {
            Topic topic = entry.getKey();
            List<Solution> topicSolutions = entry.getValue();

            String visibilitySymb = topic.getVisibility() ? " <O>" : " <->";

            view.println(topic.getName() + visibilitySymb + " (" + topicSolutions.size() + " решений)");
            
            for (Solution s : topicSolutions) {
                view.println("  >" + s.getTask().getName() + ": " +  
                    (s.getSolutionText().length() > 10 
                        ? s.getSolutionText().substring(0, 10) + "..." 
                        : s.getSolutionText()));
            }
        }
    }

    private void viewAllVisibleSolutionsByTopics() {
        view.printSubHeader("Все видимые решения");

        List<Solution> allSolutions = solutionService.getAllSolutions().stream()
        .filter(s -> s.getTask().getGodTopic().getVisibility())
        .collect(Collectors.toList());

        if (allSolutions.isEmpty()) {
            view.printInfo("Нет видимых решений");
            return;
        }

        Map<Topic, List<Solution>> byTopic = allSolutions.stream()
            .collect(Collectors.groupingBy(s -> s.getTask().getGodTopic()));

        for (Map.Entry<Topic, List<Solution>> entry : byTopic.entrySet()) {
            Topic topic = entry.getKey();
            List<Solution> topicSolutions = entry.getValue();

            view.println(topic.getName() + " (" + topicSolutions.size() + " решений)");
            
            for (Solution s : topicSolutions) {
                view.println("  >" + s.getTask().getName() + ": " +  
                    (s.getSolutionText().length() > 10 
                        ? s.getSolutionText().substring(0, 10) + "..." 
                        : s.getSolutionText()));
            }
        }
    }

    private void deleteSolution() {
        view.printSubHeader("Удаление решения");
        
        List<Solution> solutions = solutionService.getAllSolutions();
        if (solutions.isEmpty()) {
            view.printInfo("Нет решений для удаления");
            return;
        }

        view.printSubHeader("Решения:");
        view.printList(solutions, s -> s.getTask().getName());

        int idx = view.readInt("Номер решения для удаления") - 1;
        if (idx < 0 || idx >= solutions.size()) return;

        Solution solution = solutions.get(idx);

        if (view.readBoolean("Удалить решение для задачи '" + solution.getTask().getName() + "'?")) {
            solutionService.deleteSolution(solution.getId());
            view.printSuccess("Решение удалено");
        }
    }
}
