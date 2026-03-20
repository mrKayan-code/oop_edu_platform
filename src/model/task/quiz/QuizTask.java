package model.task.quiz;

import java.util.List;

import model.task.Task;
import model.topic.Topic;

public class QuizTask extends Task {
    private List<Question> questions;

    public QuizTask(Topic godTopic) {
        super(godTopic);
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

}
