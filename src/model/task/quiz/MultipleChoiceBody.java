package model.task.quiz;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class MultipleChoiceBody extends QuestionBody {
    private final List<String> options;
    private final int correctOptionIndex;
    
    public MultipleChoiceBody(List<String> options, int correctOptionIndex) {
        super(QuestionType.MULTIPLE_CHOICE);
        
        if (options == null || options.size() < 2) {
            throw new IllegalArgumentException("Должно быть минимум 2 варика");
        }
        if (correctOptionIndex < 0 || correctOptionIndex >= options.size()) {
            throw new IllegalArgumentException("Неверный индекс правильного ответа");
        }
        
        this.options = new ArrayList<>(options);
        this.correctOptionIndex = correctOptionIndex;
    }
    
    @Override
    public boolean checkAnswer(String userAnswer) {
        try {
            int userChoice = Integer.parseInt(userAnswer.trim()) - 1;
            return userChoice == correctOptionIndex;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public String getCorrectAnswerText() {
        return (correctOptionIndex + 1) + ". " + options.get(correctOptionIndex);
    }
    
    @Override
    public String getDisplayText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            sb.append(String.format("\n  %d. %s", i + 1, options.get(i)));
        }
        return sb.toString();
    }
    
    public List<String> getOptions() {
        return Collections.unmodifiableList(options);
    }
    
    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }
    
    public String getOption(int index) {
        if (index < 0 || index >= options.size()) {
            throw new IllegalArgumentException("Неверный индекс варианта");
        }
        return options.get(index);
    }
}