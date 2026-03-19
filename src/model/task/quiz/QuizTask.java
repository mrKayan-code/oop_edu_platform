package model.task.quiz;

import java.util.List;

import model.task.Task;

public class QuizTask extends Task {
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }

}
