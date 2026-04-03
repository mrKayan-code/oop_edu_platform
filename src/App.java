import java.util.List;
import java.util.Scanner;

import controller.CourseController;
import controller.MenuConroller;
import controller.SolutionController;
import controller.TaskController;
import controller.TopicController;
import model.course.Course;
import model.solution.Solution;
import model.task.algorithmic.AlgorithmicTask;
import model.task.quiz.QuizTask;
import model.task.with_repository.TaskWithRepository;
import model.topic.Section;
import repository.CourseRepository;
import repository.SolutionRepository;
import repository.TaskRepository;
import repository.TopicRepository;
import service.CourseService;
import service.SolutionService;
import service.TaskService;
import service.TopicService;
import view.ConsoleView;
import model.topic.Module;

public class App {
    public static void main(String[] args) {
        CourseRepository courseRepository = new CourseRepository();
        TopicRepository topicRepository = new TopicRepository();
        TaskRepository taskRepository = new TaskRepository();
        SolutionRepository solutionRepository = new SolutionRepository();

        CourseService courseService = new CourseService(courseRepository, topicRepository);
        TopicService topicService = new TopicService(topicRepository, taskRepository);
        TaskService taskService = new TaskService(taskRepository, topicRepository);
        SolutionService solutionService = new SolutionService(solutionRepository);

        Scanner scanner = new Scanner(System.in, "cp866");
        ConsoleView view = new ConsoleView(scanner);
        
        CourseController courseController = new CourseController(view, courseService);
        TopicController topicController = new TopicController(view, topicService, courseService);
        TaskController taskController = new TaskController(view, taskService, topicService);
        SolutionController solutionController = new SolutionController(view, solutionService, courseService);


        MenuConroller menuConroller = new MenuConroller(view, courseController, topicController, taskController, solutionController);

        // initDemo(courseService, topicService, taskService, solutionService, topicRepository);
        
        menuConroller.run();
    }

    // private static void initDemo(
    //     CourseService courseService,
    //     TopicService topicService, 
    //     TaskService taskService,
    //     SolutionService solutionService,
    //     TopicRepository topicRepository
    // ) {
    //     System.out.println("демка");

    //     Course jacksonCourse = courseService.createCourse("Jackson");

    //     Module basicsModule = topicService.createModule("Основы jackson");
    //     courseService.addTopicToCourse(jacksonCourse.getId(), basicsModule.getId());

    //     Section variablesSection = topicService.createSection("Переменные и типы");
    //     basicsModule.addTopic(variablesSection);
    //     topicRepository.update(basicsModule.getId(), basicsModule);

    //     AlgorithmicTask jacksTask = taskService.createAlgorithmicTask(
    //         "Объявление переменных",
    //         "Объявите переменные int age = 25; и String name = \"Student\";",
    //         "int age = 25;\nString name = \"Student\";",
    //         List.of()
    //     );
    //     variablesSection.addTask(jacksTask);
    //     topicRepository.update(variablesSection.getId(), variablesSection);

    //     TaskWithRepository gitTask = taskService.createTaskWithRepository(
    //         "Первый коммит",
    //         "Создайте репозиторий, добавьте файл Hello.java и сделайте push",
    //         "git init\ngit add .\ngit commit -m \"init\"\ngit push",
    //         "https:github.com/example/java-hello"
    //     );
    //     variablesSection.addTask(gitTask);
    //     topicRepository.update(variablesSection.getId(), variablesSection);

    //     Section controlSection = topicService.createSection("Управляющие конструкции");
    //     basicsModule.addTopic(controlSection);
    //     topicRepository.update(basicsModule.getId(), basicsModule);

    //     QuizTask controlQuiz = taskService.createQuizTask("Тест: Условия и циклы");
        
    //     controlQuiz.createBinaryQuestion(
    //         "Может ли цикл for быть бесконечным?", 
    //         true
    //     );
        
    //     controlQuiz.createMultipleChoiceQuestion(
    //         "Какой оператор используется для сравнения на равенство в Java?",
    //         List.of("=", "==", "===", "equals()"),
    //         1
    //     );
        
    //     controlQuiz.createFreeAnswerQuestion(
    //         "Какое ключевое слово используется для выхода из цикла?",
    //         "break"
    //     );
        
    //     controlSection.addTask(controlQuiz);
    //     topicRepository.update(controlSection.getId(), controlSection);

    //     Module oopModule = topicService.createModule("Объектно-ориентированное программирование");
    //     courseService.addTopicToCourse(jacksonCourse.getId(), oopModule.getId());

    //     Section classesSection = topicService.createSection("Классы и объекты");
    //     oopModule.addTopic(classesSection);
    //     topicRepository.update(oopModule.getId(), oopModule);

    //     AlgorithmicTask classTask = taskService.createAlgorithmicTask(
    //         "Создание класса Person",
    //         "Создайте класс Person с полями name, age и конструктором",
    //         "public class Person {\n  private String name;\n  private int age;\n   конструктор...\n}",
    //         List.of()
    //     );
    //     classesSection.addTask(classTask);
    //     topicRepository.update(classesSection.getId(), classesSection);

    //     Section inheritanceSection = topicService.createSection("Наследование и полиморфизм");
    //     oopModule.addTopic(inheritanceSection);
    //     topicRepository.update(oopModule.getId(), oopModule);

    //     QuizTask inheritanceQuiz = taskService.createQuizTask("Тест: Наследование");
    //     inheritanceQuiz.createBinaryQuestion("Может ли класс наследоваться от нескольких классов в Java?", false);
    //     inheritanceQuiz.createMultipleChoiceQuestion(
    //         "Какое ключевое слово используется для наследования?",
    //         List.of("extends", "implements", "inherits", "super"),
    //         0
    //     );
    //     inheritanceSection.addTask(inheritanceQuiz);
    //     topicRepository.update(inheritanceSection.getId(), inheritanceSection);

    //     System.out.println("Курс Java OOP: " + jacksonCourse.getTopics().size() + " тем, ~7 задач");


    //     Course pythonCourse = courseService.createCourse("Python для начинающих");
    //     System.out.println("Создан курс: " + pythonCourse.getName());

    //     Module syntaxModule = topicService.createModule("Основы синтаксиса");
    //     courseService.addTopicToCourse(pythonCourse.getId(), syntaxModule.getId());

    //     Section pythonVars = topicService.createSection("Переменные и типы данных");
    //     syntaxModule.addTopic(pythonVars);
    //     topicRepository.update(syntaxModule.getId(), syntaxModule);

    //     AlgorithmicTask pyVarTask = taskService.createAlgorithmicTask(
    //         "Работа со строками",
    //         "Напишите код, который создаёт строку и выводит её длину",
    //         "text = \"Hello\"\nprint(len(text))",
    //         List.of()
    //     );
    //     pythonVars.addTask(pyVarTask);
    //     topicRepository.update(pythonVars.getId(), pythonVars);

    //     Section pyFunctions = topicService.createSection("Функции");
    //     syntaxModule.addTopic(pyFunctions);
    //     topicRepository.update(syntaxModule.getId(), syntaxModule);

    //     TaskWithRepository pyGitTask = taskService.createTaskWithRepository(
    //         "GitHub + Python",
    //         "Загрузите ваш Python-скрипт в репозиторий",
    //         "Ссылка на файл .py в вашем репо",
    //         "https:github.com/example/python-demo"
    //     );
    //     pyFunctions.addTask(pyGitTask);
    //     topicRepository.update(pyFunctions.getId(), pyFunctions);

    //     System.out.println("Курс Python: " + pythonCourse.getTopics().size() + " тем, ~3 задачи");


    //     Course webCourse = courseService.createCourse("Веб-разработка: старт");
    //     System.out.println("✓ Создан курс: " + webCourse.getName());

    //     Module htmlModule = topicService.createModule("HTML & CSS");
    //     courseService.addTopicToCourse(webCourse.getId(), htmlModule.getId());

    //     Section htmlSection = topicService.createSection("Структура HTML");
    //     htmlModule.addTopic(htmlSection);
    //     topicRepository.update(htmlModule.getId(), htmlModule);

    //     TaskWithRepository htmlTask = taskService.createTaskWithRepository(
    //         "Первая страница",
    //         "Создайте index.html с заголовком и параграфом",
    //         "<!DOCTYPE html>\n<html>\n<head><title>My Page</title></head>\n<body><h1>Hello</h1></body>\n</html>",
    //         "https:github.com/example/my-page"
    //     );
    //     htmlSection.addTask(htmlTask);
    //     topicRepository.update(htmlSection.getId(), htmlSection);

    //     System.out.println("✓ Курс Веб: " + webCourse.getTopics().size() + " тем, ~1 задача");


    //     System.out.println("\nДобавляем демо-решения...");


    //     Solution sol1 = solutionService.submitSolution(jacksTask, "int age = 25;\nString name = \"Alex\";");
    //     Solution sol2 = solutionService.submitSolution(gitTask, "https:github.com/alexey/java-learn");
        
    //     solutionService.submitQuizTaskAnswers(controlQuiz, List.of("Да", "1", "break"));
        

    //     solutionService.submitSolution(jacksTask, "var age = 25;\nvar name = \"Maria\";");
    //     solutionService.submitQuizTaskAnswers(controlQuiz, List.of("Нет", "2", "return"));
        
    //     solutionService.submitSolution(pyVarTask, "text = 'Hello World'\nprint(len(text))");
    //     solutionService.submitSolution(pyGitTask, "https:github.com/dmitry/python-tasks");

    //     System.out.println("Добавлено ~7 демо-решений от 3 студентов");


    //     System.out.println("\nДемо-данные готовы:");
    //     System.out.println("   Курсов: 3");
    //     System.out.println("   Тем (модули/секции): ~10");
    //     System.out.println("   Задач: ~12 (включая 2 викторины с вопросами)");
    //     System.out.println("   Решений: ~7");
    // }
}
