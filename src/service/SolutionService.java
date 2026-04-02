package service;

import java.util.Optional;
import java.util.UUID;

import model.solution.Solution;
import repository.SolutionRepository;
import model.task.Task;
import model.task.quiz.QuizTask;

import java.util.Collections;
import java.util.List;

public class SolutionService {
    private final SolutionRepository solutionRepository;

    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    public Solution submitSolution(Task task, String solutionText) {
        Solution solution = new Solution(task);

        solution.setSolutionText(solutionText);

        return solutionRepository.create(solution);
    }

    public Solution submitSolution(UUID existingSolutionId, String solutionText) {
        Solution solution = solutionRepository.findById(existingSolutionId).orElseThrow(
            () -> new IllegalArgumentException("Не удалось найти существующее решение")
        );

        solution.setSolutionText(solutionText);

        return solution;
    }

    public Solution submitQuizTaskAnswers(QuizTask quiz, List<String> answers) {
        StringBuilder sb = new StringBuilder();

        for (String answer : answers) {
            sb.append(answer);
        }

        return submitSolution(quiz, sb.toString());
    }

    public Solution submitQuizTaskAnswers(UUID existingSolutionId, List<String> answers) {
        StringBuilder sb = new StringBuilder();

        for (String answer : answers) {
            sb.append(answer);
        }

        return submitSolution(existingSolutionId, sb.toString());
    }

    public List<Solution> getAllSolutions() {
        return Collections.unmodifiableList(solutionRepository.findAll());
    }

    public Optional<Solution> getSolutionForTask(UUID taskId) {
        return solutionRepository.findAll().stream()
            .filter(s -> s.getTask().getId().equals(taskId))
            .findFirst();
    }

    public boolean deleteSolution(UUID solutionId) {
        return solutionRepository.delete(solutionId);
    }
}
