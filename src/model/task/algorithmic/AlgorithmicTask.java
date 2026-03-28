package model.task.algorithmic;

import model.task.Task;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmicTask extends Task {
    private final List<ProgrammingLang> programmingLangs;

    public AlgorithmicTask(String name) {
        super(name);

        programmingLangs = new ArrayList<>();
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

    @Override
    public void mergeFrom(Task other) {
        super.mergeFrom(other);
        
        if (!(other instanceof AlgorithmicTask)) {
            return;
        }

        // AlgorithmicTask algo = (AlgorithmicTask) other;
        
        // this.programmingLangs = algo.programmingLangs;
    }
}
