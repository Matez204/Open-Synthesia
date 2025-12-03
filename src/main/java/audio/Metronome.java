package audio;

import javax.sound.midi.MidiUnavailableException;

public class Metronome implements Runnable{
    private final Synthe channel = new Synthe(13);
    private volatile boolean running = true;
    private int beat;
    public Metronome(int time) throws MidiUnavailableException{
        this.beat = 60000/time;
    }
    public void detener(){
        this.running = false;
    }
    public void run() {
        try{
            int contador = 4;
            while(this.running){
                if(contador%4 == 0){
                    this.channel.sonar(62,beat);
                } else{
                    this.channel.sonar(60,beat);
                }
                contador++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
