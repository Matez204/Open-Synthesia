import audio.Metronome;
import audio.Synthe;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import reading.Reader;
import visual.PianoView;

import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        PianoView piano = new PianoView();
        Metronome metro = new Metronome();
        Synthe sintR = new Synthe(0);
        sintR.setPiano(piano);
        Synthe sintL = new Synthe(0);
        sintL.setPiano(piano);
        Reader read = new Reader();
        DoubleProperty dimenX = new SimpleDoubleProperty();
        DoubleProperty dimenY = new SimpleDoubleProperty();
        Label size = new Label("Dimenciones de la ventana");
        TextField showSize = new TextField();
        showSize.textProperty().bind(
                Bindings.format("(%.1f, %.1f)",dimenX,dimenY)
        );
        GridPane gp = new GridPane();
        gp.add(size,0,0);
        gp.add(showSize,0,1);
        gp.setHgap(10.0);
        HBox h = new HBox(gp);
        h.setAlignment(Pos.CENTER);
        Label l1 = new Label("Haz click en algun lado");
        Label l = new Label("Mira el rectangulo");
        Rectangle rectangulo = new Rectangle(100,50, Color.BLUE);
        Button boton = new Button("Hacer click");
        VBox raiz = new VBox(20);
        raiz.getChildren().addAll(h,l1,l,rectangulo,boton,piano);
        raiz.setAlignment(Pos.CENTER);
        raiz.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            double x = event.getX();
            double y = event.getY();
            l1.setText("Hiciste click en x: " + x + "y en y: " + y);
            dimenX.set(stage.getWidth());
            dimenY.set(stage.getHeight());
        });
        AtomicInteger contador = new AtomicInteger();
        boton.setOnAction(i -> {
            List<List<Double>>[] cancion;
            try {
                cancion = read.read("data/partituras/partitura1.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sintR.stab(cancion[0],true);
            sintL.stab(cancion[1],false);
            contador.getAndIncrement();
            l.setText("El rectangulo cambio " + contador );
            rectangulo.setScaleX(1.5);
            rectangulo.setScaleY(1.5);
            new Thread(sintR).start();
            new Thread(sintL).start();
        });
        Scene scene = new Scene(raiz, 640, 480);
        stage.setScene(scene);
        stage.setOnShown((event) -> {
            dimenX.set(stage.getWidth());
            dimenY.set(stage.getHeight());
        });
        stage.show();
    }

    public static void main(String[] args) throws MidiUnavailableException, InterruptedException {
        launch();
    }
}
