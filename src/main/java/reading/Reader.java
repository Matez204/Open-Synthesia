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


package reading;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Reader {
    Map<String, Double> notesMIDI = new HashMap<>();
    String notes;
    public Reader() throws Exception{
        this.notes = Files.readString(Path.of("data/notas.txt"));
        String[] lines = this.notes.split("\n");
        for (String line : lines){
            String[] partes = line.split("-");
            Integer midi = Integer.parseInt(partes[0]);
            String nota = partes[1];
            notesMIDI.put(nota, midi.doubleValue());
        }
        notesMIDI.put("REDONDA", 4.0);
        notesMIDI.put("BLANCA",2.0);
        notesMIDI.put("NEGRA",1.0);
        notesMIDI.put("CORCHEA",0.5);
        notesMIDI.put("SEMICORCHEA",0.25);
        notesMIDI.put("SIL",0.0);
        notesMIDI.put("BLANCAP",this.notesMIDI.get("BLANCA")*1.5);
        notesMIDI.put("NEGRAP",1.5);
    }
    public List<List<Double>>[] read(String link) throws IOException {
        String partitura = Files.readString(Path.of(link));
        List<List<Double>> mano1 = new ArrayList<>();
        List<List<Double>> mano2 = new ArrayList<>();
        String[] lines = partitura.split("\n");
        for (String line : lines){
            String[] partes = line.split("\\s");
            String[] notas = partes[0].split("-");
            List<Double> can = new ArrayList<>();
            if(partes.length == 1){
                for(String i : notas){
                    can.add(notesMIDI.get(i));
                }
                mano1.add(can);
            } else {
                for(String i : notas){
                    can.add(notesMIDI.get(i));
                }
                mano1.add(can);
                notas = partes[1].split("-");
                can = new ArrayList<>();
                for(String i : notas){
                    can.add(notesMIDI.get(i));
                }
                mano2.add(can);
            }
        }
        return new List[]{mano1,mano2};
    }
}
