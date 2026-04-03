package model.task.algorithmic;

public enum ProgrammingLang {
    CPP("C++"),
    CSHARP("C#"),
    JAVA("Java"),
    PYTHON("Python");

    private final String displayName;

    ProgrammingLang(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
