package model.task.quiz;

public class Question {
    private QuestionType questionType;    
    private QuizTask godQuizTask;
    private String questionText;
    
    public Question(QuizTask godQuizTask) {
        this.godQuizTask = godQuizTask;
    }
    
    public QuestionType getQuestionType() {
        return questionType;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    //TODO(questionBody в зависимости от )
}
