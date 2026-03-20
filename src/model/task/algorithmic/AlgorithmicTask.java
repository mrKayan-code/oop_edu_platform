package model.task.algorithmic;

import model.task.Task;
import model.topic.Topic;

import java.util.List;

public class AlgorithmicTask extends Task {
    private List<ProgrammingLang> programmingLangs;

    public AlgorithmicTask(Topic godTopic) {
        super(godTopic);
    }
    public List<ProgrammingLang> getProgrammingLangs() {
        return programmingLangs;
    }

    public void addProgrammingLang(ProgrammingLang programmingLang) {
        programmingLangs.add(programmingLang);
    }

    public void removeProgrammingLang(ProgrammingLang programmingLang) {
        programmingLangs.remove(programmingLang);
    }

}
