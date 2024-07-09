package lu.barrea.minesweeper.model;

import javafx.application.Platform;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Chrono{
    private Runnable chronoMethod;
    private Thread chronoThread;
    private int seconds;
    private boolean running;
    private PropertyChangeSupport changeSupport;

    public Chrono(){
        chronoMethod = ()->{
            int loopPerSecond = 4;
            int sleepTime = 996 / loopPerSecond;
            while(running){
                try{
                    Thread.sleep(sleepTime);
                }catch(InterruptedException e){
                    seconds = 0;
                }
                loopPerSecond--;
                if(loopPerSecond == 0){
                    seconds++;
                    loopPerSecond = 4;
                    Platform.runLater(()-> this.changeSupport.firePropertyChange("seconds", null, this.seconds));
                }
            }
        };

        seconds = 0;
        running = false;
        changeSupport = new PropertyChangeSupport(this);
    }

    public int getValue(){
        return seconds;
    }

    public void start() {
        this.running = true;
        chronoThread = new Thread(chronoMethod);
        chronoThread.setDaemon(true);
        this.chronoThread.start();
    }

    public void stop(){
        this.running = false;
    }

    public void reset(){
        this.stop();
        try{
            if(this.chronoThread != null) this.chronoThread.join();
        }catch(InterruptedException e){};
        this.seconds = 0;
        this.changeSupport.firePropertyChange("seconds", null, this.seconds);
    }

    public boolean isRunning(){
        return this.running;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        this.changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        this.changeSupport.removePropertyChangeListener(listener);
    }

    public String toString(){
        return seconds+"sec";
    }
}
