package model.task.quiz;

public class FreeAnswerBody extends QuestionBody {
    private final String correctAnswer;
    private final boolean caseSensitive;
    private final boolean exactMatch;
    
    public FreeAnswerBody(String correctAnswer) {
        this(correctAnswer, false, false);
    }
    
    public FreeAnswerBody(String correctAnswer, boolean caseSensitive, boolean exactMatch) {
        super(QuestionType.FREE_ANSWER);
        
        if (correctAnswer == null || correctAnswer.isBlank()) {
            throw new IllegalArgumentException("Правильно не может быть пусто");
        }
        
        this.correctAnswer = correctAnswer.trim();
        this.caseSensitive = caseSensitive;
        this.exactMatch = exactMatch;
    }
    
    @Override
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null || userAnswer.isBlank()) {
            return false;
        }
        
        String user = userAnswer.trim();
        String correct = correctAnswer;
        
        if (!caseSensitive) {
            user = user.toLowerCase();
            correct = correct.toLowerCase();
        }
        
        if (exactMatch) {
            return user.equals(correct);
        } else {
            return user.contains(correct);
        }
    }
    
    @Override
    public String getCorrectAnswerText() {
        return correctAnswer;
    }
    
    @Override
    public String getDisplayText() {
        return "[введи сюда текстовый ответ]";
    }
    
    public String getCorrectAnswer() { return correctAnswer; }
    public boolean isCaseSensitive() { return caseSensitive; }
    public boolean isExactMatch() { return exactMatch; }
}
