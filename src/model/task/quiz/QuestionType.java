package model.task.quiz;

public enum QuestionType {
    BINARY("Да/Нет"),
    MULTIPLE_CHOICE("Выбор из нескольких"),
    FREE_ANSWER("Свободный ответ");
    
    private final String displayName;
    
    QuestionType(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
