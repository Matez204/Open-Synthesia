/*
 * This file is part of Open Synthesia.
 *
 * Copyright (C) 2025  Mathew Zahav Rodriguez Clavijo
 *
 * Open Synthesia is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Open Synthesia is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Open Synthesia.  If not, see <https://www.gnu.org/licenses/>.
 */


package audio;

import javafx.application.Platform;
import visual.PianoView;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.List;

public class Synthe implements Runnable{
    public Synthesizer sin = MidiSystem.getSynthesizer();
    public MidiChannel channel;
    private List<List<Double>> cancion;
    private PianoView piano;
    boolean rol;

    public Synthe(int instrument) throws MidiUnavailableException {
        this.sin.open();
        this.channel = this.sin.getChannels()[0];
        this.channel.programChange(instrument);
    }

    public void setPiano(PianoView piano){
        this.piano = piano;
    }

    public void sonar(int note, double time) throws InterruptedException {
        this.channel.noteOn(note, 1000);
        Thread.sleep((long) time);
        this.channel.noteOff(note);
    }

    public void stab(List<List<Double>> cancion, boolean rol){
        this.cancion = cancion;
        this.rol = rol;
    }

    public void run(){
        try {
            for(List<Double> tiempo : cancion){
                Double duracion = tiempo.getLast();
                tiempo.removeLast();
                for( Double nota : tiempo){
                    channel.noteOn(nota.intValue(), 1000);
                    Platform.runLater(() -> piano.press(nota.intValue(),rol));
                }
                double dur = Metronome.beat*duracion;
                Thread.sleep((long)dur);
                for( Double nota : tiempo){
                    channel.noteOff(nota.intValue());
                    Platform.runLater(() -> piano.release(nota.intValue()));
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
