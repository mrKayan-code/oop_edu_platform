package model.topic;


public class Section extends Topic {
    
    public Section(String name) {
        super("Секция " + name);
    }

    @Override
    public void setName(String name) {
        super.setName("Секция " + name);
    }
}
