import audio.Metronome;
import audio.Synthe;
import javafx.application.Application;
import javafx.geometry.Insets;
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
    @Override
    public void start(Stage stage) throws Exception {
        PianoView piano = new PianoView();
        Metronome metro = new Metronome();
        Synthe sintR = new Synthe(0);
        sintR.setPiano(piano);
        Synthe sintL = new Synthe(0);
        sintL.setPiano(piano);
        Reader read = new Reader();
        List<List<Double>> cancionSelesccionada = new ArrayList<>();
        stage.setScene(primeraEscena());
        stage.show();
    }

    public static void main(String[] args) throws MidiUnavailableException, InterruptedException {
        launch();
    }

    public static void initChoice(){
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

    public static Scene primeraEscena(){
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
//            try {
//                List<List<Double>>[] cancion = read.read(pathCancion.get());
//                sintR.stab(cancion[0],true);
//                sintL.stab(cancion[1],false);
//                new Thread(sintR).start();
//                new Thread(sintL).start();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        });
        VBox primeraRaiz = new VBox(titulo1,pianoBoton,eleccionCancion);
        primeraRaiz.setSpacing(10.0);
        primeraRaiz.setAlignment(Pos.CENTER);
        return new Scene(primeraRaiz,500,500);
    }

    public static Scene segundaEscena(){
        
    }
}
