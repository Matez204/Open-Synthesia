
import audio.*;
import reading.Reader;

import javax.sound.midi.MidiUnavailableException;
import java.util.Scanner;

public class Pruebas {
    public static void main(String [] asdfa) throws MidiUnavailableException, Exception {
        Scanner sc = new Scanner(System.in);
        Metronome metro = new Metronome();
        metro.stab(200);
        Synthe sin = new Synthe(0);
        Reader r = new Reader();
        sin.sonar(60,Metronome.beat);
        sin.sonar(60,Metronome.beat);
        sin.sonar(62,Metronome.beat*0.5);
        sin.sonar(60,Metronome.beat*0.5);
        sin.sonar(0,Metronome.beat);
        sin.sonar(60,Metronome.beat);
        sin.sonar(60,Metronome.beat);


//        System.out.println(aa.length);
//        System.out.println(bb.length);

//        new Thread(metro).start();
//        System.out.println("Escriba la nueva velocidad a la que quiere el metronomo");
//        metro.stab(sc.nextInt());

    }
}
