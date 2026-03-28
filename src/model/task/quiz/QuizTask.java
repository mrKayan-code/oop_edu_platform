package model.task.quiz;

import java.util.ArrayList;
import java.util.List;

import model.task.Task;

public class QuizTask extends Task {
    private final List<Question> questions;

    public QuizTask(String name) {
        super(name);

        questions = new ArrayList<>();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }

    @Override
    public void mergeFrom(Task other) {
        super.mergeFrom(other);
        
        if (!(other instanceof QuizTask)) {
            return;
        }

        // QuizTask quiz = (QuizTask) other;

    }

}
