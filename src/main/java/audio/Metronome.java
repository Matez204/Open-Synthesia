package audio;

import javax.sound.midi.MidiUnavailableException;

public class Metronome implements Runnable{
    private final Synthe channel = new Synthe(13);
    private volatile boolean running = true;
    public static Double beat;
    public Metronome() throws MidiUnavailableException{
        beat = (double) (60000/120);
    }
    public void stab(int time){
        beat = (double) (60000/time);
    }
    public void stop(){
        this.running = false;
    }
    public void run() {
        try{
            int contador = 4;
            while(this.running){
                if(contador%4 == 0){
                    this.channel.sonar(20,beat);
                } else{
                    this.channel.sonar(23,beat);
                }
                contador++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
