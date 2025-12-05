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


import audio.Metronome;
import audio.Synthe;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import reading.Reader;
import visual.PianoView;

import javax.sound.midi.MidiUnavailableException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class Main extends Application{
    private static ChoiceBox<Pair<String,String>> canciones = new ChoiceBox<>();
    private static Pair<String,String> EMPTY_PAIR = new Pair<>("","");
    private static AtomicReference<String> pathCancion = new AtomicReference<>();
    private static PianoView piano = new PianoView();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(primeraEscena(stage));
        stage.show();
    }

    public static void main(String[] args) throws MidiUnavailableException, InterruptedException {
        launch();
    }

    public static void initChoice(){
        canciones.getItems().clear();
        File partituras = new File("data/partituras");
        List<Pair<String,String>> nombresCanciones = new ArrayList<>();
        if (partituras.listFiles() != null){
            for(File i : partituras.listFiles()){
                nombresCanciones.add(new Pair<>(i.getName().replace(".txt",""),i.getPath()));
            }
        }
        canciones.setConverter(new StringConverter<Pair<String, String>>() {
            @Override
            public String toString(Pair<String, String> pair) {
                return pair.getKey();
            }
            @Override
            public Pair<String, String> fromString(String s) {
                return null;
            }
        });
        canciones.getItems().add(EMPTY_PAIR);
        canciones.getItems().addAll(nombresCanciones);
        canciones.setValue(EMPTY_PAIR);
    }

    public static Scene primeraEscena(Stage stage){
        Label titulo1 = new Label("Open Synthesia");
        titulo1.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        Button pianoBoton = new Button("Piano");
        pianoBoton.setStyle("-fx-font-size: 20px;");

        Label selCancion = new Label("Elegia una cancion: ");
        selCancion.setStyle("-fx-font-size: 20px;");
        Button seleccionCancion = new Button("Seleccionar");
        seleccionCancion.setStyle("-fx-font-size: 20px;");
        canciones.setPrefWidth(200);
        canciones.setStyle("-fx-font-size: 20px;");

        HBox eleccionCancion = new HBox(
                selCancion,
                canciones,
                seleccionCancion
        );
        eleccionCancion.setSpacing(5);
        eleccionCancion.setAlignment(Pos.CENTER);
        eleccionCancion.setPadding(new Insets(30));
        initChoice();

        seleccionCancion.setOnAction(event -> {
            pathCancion.set(canciones.getValue().getValue());
            System.out.println(canciones.getValue().getValue());
        });

        pianoBoton.setOnAction(event ->{
            try {
                stage.setScene(segundaEscena(stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        VBox primeraRaiz = new VBox(titulo1,pianoBoton,eleccionCancion);
        primeraRaiz.setSpacing(10.0);
        primeraRaiz.setAlignment(Pos.CENTER);
        return new Scene(primeraRaiz,500,500);
    }

    public static Scene segundaEscena(Stage stage) throws Exception {
        Metronome metro = new Metronome();
        Synthe sintR = new Synthe(0);
        sintR.setPiano(piano);
        Synthe sintL = new Synthe(0);
        sintL.setPiano(piano);
        Reader read = new Reader();

        Button volver = new Button("Volver");
        volver.setOnAction(event -> {
            stage.setScene(primeraEscena(stage));
        });
        Button repetir = new Button("Iniciar");
        repetir.setOnAction(event -> {
            repetir.setText("Repetir");
            List<List<Double>>[] cancion;
            try {
                cancion = read.read(pathCancion.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sintR.stab(cancion[0], true);
            sintL.stab(cancion[1],false);
            new Thread(sintR).start();
            new Thread(sintL).start();
        });

        AnchorPane botonesSuperiores = new AnchorPane();

        AnchorPane.setTopAnchor(volver,10.0);
        AnchorPane.setLeftAnchor(volver,10.0);

        AnchorPane.setTopAnchor(repetir,10.0);
        AnchorPane.setRightAnchor(repetir,10.0);
        TextField metronomo = new TextField();
        metronomo.setPromptText("Velocidad del metronomo");
        metronomo.setPrefWidth(200.0);
        Button seleccionMetro = new Button("Seleccionar");
        HBox cajaMetronomo = new HBox(metronomo,seleccionMetro);
        cajaMetronomo.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(cajaMetronomo,10.0);
        AnchorPane.setRightAnchor(cajaMetronomo,0.0);
        AnchorPane.setLeftAnchor(cajaMetronomo,0.0);
        botonesSuperiores.getChildren().addAll(volver,cajaMetronomo,repetir);
        seleccionMetro.setOnAction(event -> {
            String velocidad = metronomo.getText();
            int vel;
            if(velocidad != null){
                try{
                    vel = Integer.parseInt(velocidad);
                    Metronome.stab(vel);
                } catch (NumberFormatException e) {
                    System.out.println("No es un numero");
                }
            }
        });
        BorderPane root = new BorderPane();
        root.setTop(botonesSuperiores);
        HBox cajaPiano = new HBox(piano);
        cajaPiano.setAlignment(Pos.CENTER);
        root.setBottom(cajaPiano);
        return new Scene(root, 500, 500);
    }
}
