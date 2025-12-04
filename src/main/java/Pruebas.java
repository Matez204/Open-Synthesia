
import audio.*;
import reading.Reader;

import javax.sound.midi.MidiUnavailableException;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Pruebas {
    public static void main(String [] asdfa) throws MidiUnavailableException, Exception {
        Scanner sc = new Scanner(System.in);
        Metronome metro = new Metronome();
        Synthe sin = new Synthe(0);
        Synthe sin1 = new Synthe(0);
        Reader r = new Reader();
//        List<List<Double>>[] cancion = r.read("data/partituras/partitura1.txt");
//        sin.stab(cancion[0]);
//        sin1.stab(cancion[1]);
//        new Thread(sin).start();
//        new Thread(sin1).start();
        File carpeta = new File("data/partituras");
        if (carpeta.listFiles() != null){
            for (File i : carpeta.listFiles() ){
                System.out.println(i.getName() + "  " + i.getPath());
            }
        }
//        System.out.println(aa.length);
//        System.out.println(bb.length);

//        new Thread(metro).start();
//        System.out.println("Escriba la nueva velocidad a la que quiere el metronomo");
//        metro.stab(sc.nextInt());

    }
}
