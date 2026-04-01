package model.task.quiz;

public class BinaryQuestionBody extends QuestionBody {
    private final boolean correctAnswer;
    private final String correctText;
    private final String falschText;
    
    public BinaryQuestionBody(boolean correctAnswer) {
        this(correctAnswer, "Да", "Нет");
    }
    
    public BinaryQuestionBody(boolean correctAnswer, String correctText, String falschText) {
        super(QuestionType.BINARY);
        this.correctAnswer = correctAnswer;
        this.correctText = correctText;
        this.falschText = falschText;
    }
    
    @Override
    public boolean checkAnswer(String userAnswer) {
        String normalized = userAnswer.trim().toLowerCase();
        
        boolean userSaidTrue = normalized.equals("да") || 
                               normalized.equals("д") || 
                               normalized.equals("yes") || 
                               normalized.equals("y") || 
                               normalized.equals("1") ||
                               normalized.equals("true");
        
        return userSaidTrue == correctAnswer;
    }
    
    @Override
    public String getCorrectAnswerText() {
        return correctAnswer ? correctText : falschText;
    }
    
    @Override
    public String getDisplayText() {
        return String.format("[%s / %s]", correctText, falschText);
    }
    
    public boolean getCorrectAnswer() { return correctAnswer; }
    public String getCorrectText() { return correctText; }
    public String getFalschText() { return falschText; }
}
