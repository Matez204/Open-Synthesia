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
