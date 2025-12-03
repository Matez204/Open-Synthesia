package audio;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Synthe implements Runnable{
    public Synthesizer sin = MidiSystem.getSynthesizer();
    public MidiChannel channel;
    private int note;
    private int time;

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

    public void sonar() throws InterruptedException {
        this.channel.noteOn(note, 1000);
        Thread.sleep(time);
        this.channel.noteOff(note);
    }

    public void stab(int note, int time){
        this.time = time;
        this.note = note;
    }

    public void run(){
        try {
            this.sonar();
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
