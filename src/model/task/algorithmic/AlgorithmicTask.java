package model.task.algorithmic;

import model.task.Task;
import model.task.algorithmic.ProgrammingLang;

import java.util.List;

public class AlgorithmicTask extends Task {
    private List<ProgrammingLang> programmingLangs;

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
