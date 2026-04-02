package model.task.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.task.Task;

public class QuizTask extends Task {
    private final List<Question> questions;

    public QuizTask(String name) {
        super(name);

        this.questions = new ArrayList<>();
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

    @Override
    public void mergeFrom(Task other) {
        super.mergeFrom(other);
        
        if (!(other instanceof QuizTask)) {
            return;
        }

        // QuizTask quiz = (QuizTask) other;

    }

    public Question createBinaryQuestion(String questionText, boolean correctAnswer) {
        Question q = new Question(this, QuestionType.BINARY);
        q.setQuestionText(questionText);
        q.setQuestionBody(new BinaryQuestionBody(correctAnswer));
        addQuestion(q);
        return q;
    }

    public Question createMultipleChoiceQuestion(String questionText, List<String> options, int correctOptionIndex) {
        Question q = new Question(this, QuestionType.MULTIPLE_CHOICE);
        q.setQuestionText(questionText);
        q.setQuestionBody(new MultipleChoiceBody(options, correctOptionIndex));
        addQuestion(q);
        return q;
    }

    public Question createFreeAnswerQuestion(String questionText, String correctAnswer) {
        Question q = new Question(this, QuestionType.FREE_ANSWER);
        q.setQuestionText(questionText);
        q.setQuestionBody(new FreeAnswerBody(correctAnswer));
        addQuestion(q);
        return q;
    }

    public Question removeQuestionById(UUID questionId) {
        Question found = questions.stream()
            .filter(q -> q.getId().equals(questionId))
            .findFirst()
            .orElse(null);
        if (found != null) {
            questions.remove(found);
        }
        return found;
    }
}
