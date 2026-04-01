package model.task.quiz;

public abstract class QuestionBody {
    private final QuestionType type;
    
    protected QuestionBody(QuestionType type) {
        this.type = type;
    }
    
    public QuestionType getType() {
        return type;
    }
    
    public abstract boolean checkAnswer(String userAnswer);
    
    public abstract String getCorrectAnswerText();
    
    public abstract String getDisplayText();
}
