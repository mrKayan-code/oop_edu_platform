package model.task.quiz;

import java.util.UUID;

import interfaces.Identifiable;

public class Question implements Identifiable {
    private final UUID id;
    private final QuestionType questionType;    
    private final QuizTask godQuizTask;
    private QuestionBody questionBody;
    private String questionText;
    
    public Question(QuizTask godQuizTask, QuestionType questionType) {
        this.godQuizTask = godQuizTask;
        this.questionType = questionType;
        id = UUID.randomUUID();
    }
    
    public QuestionType getQuestionType() {
        return questionType;
    }

    public String getQuestionText() {
        return questionText;
    }

    public QuizTask getGodQuizTask() {
        return godQuizTask;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setQuestionBody(QuestionBody questionBody) {
        if (questionBody != null && questionBody.getType() == this.questionType) {
            this.questionBody = questionBody;
        } else if (questionBody != null) {
            throw new IllegalArgumentException("Тип предложенного qustionBody не корелирует с имеющимся типом");
        }
    }

    @Override
    public UUID getId() {
        return id;
    }
}
