package audio;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.List;

public class Synthe implements Runnable{
    public Synthesizer sin = MidiSystem.getSynthesizer();
    public MidiChannel channel;
    private List<List<Double>> cancion;

    public Synthe(int instrument) throws MidiUnavailableException {
        this.sin.open();
        this.channel = this.sin.getChannels()[0];
        this.channel.programChange(instrument);
    }

    public void sonar(int note, double time) throws InterruptedException {
        this.channel.noteOn(note, 1000);
        Thread.sleep((long) time);
        this.channel.noteOff(note);
    }

    public void stab(List<List<Double>> cancion){
        this.cancion = cancion;
    }

    public void run(){
        try {
            for(List<Double> tiempo : cancion){
                Double duracion = tiempo.getLast();
                tiempo.removeLast();
                for( Double nota : tiempo){
                    channel.noteOn(nota.intValue(), 1000);
                }
                double dur = Metronome.beat*duracion;
                Thread.sleep((long)dur);
                for( Double nota : tiempo){
                    channel.noteOff(nota.intValue());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getInstruments() {
        int n = 0;

        for(Instrument i : this.sin.getAvailableInstruments()) {
            System.out.print(n + " -- ");
            System.out.println(i.getName());
            ++n;
        }

    }

    public void close() {
        this.sin.close();
    }
}
