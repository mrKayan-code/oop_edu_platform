package interfaces;

public interface Updatable<T>{
    public void mergeFrom(T other);
}
