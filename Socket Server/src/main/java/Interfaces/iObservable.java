package Interfaces;

public interface iObservable {
    public void addObserver(String name, iObserver observer);
    public void removeObserver(String name);
    public void notifyAllObservers(String message) throws Exception;
    public void notifyObserver(String name, String message) throws Exception;
}
