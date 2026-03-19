package model.task.quiz;

public class Question {
    private QuestionType questionType;    
    private QuizTask godQuiz;
    private String questionText;
    
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
