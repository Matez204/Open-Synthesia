package visual;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;

public class PianoView extends Pane {
    private List<KeyView> keys = new ArrayList<>();
    public PianoView() {
        buildKeyBoard();
    }
    public void buildKeyBoard(){
        int startMidi = 48;
        int finalMidi = 84;
        double whiteWidth = 40;
        double whiteHeidth = 200;
        double blackWidth = 25;
        double blackHeidth = 120;

        double x = 0;
        for (int midi = startMidi ; midi <= finalMidi; midi ++){
            int semiTono = midi%12;
            boolean isBlack = semiTono == 1 || semiTono == 3 || semiTono == 6 || semiTono == 8 || semiTono == 10;
            KeyView tecla = new KeyView(midi,isBlack);
            if (!isBlack){
                tecla.shape.setWidth(whiteWidth);
                tecla.shape.setHeight(whiteHeidth);
                tecla.shape.setFill(Color.WHITE);
                tecla.shape.setStroke(Color.BLACK);
                tecla.shape.setX(x);
                tecla.shape.setY(0);

                getChildren().add(tecla.shape);
                keys.add(tecla);
                x+=whiteWidth;
            } else {
                tecla.shape.setWidth(blackWidth);
                tecla.shape.setHeight(blackHeidth);
                tecla.shape.setFill(Color.BLACK);
                tecla.shape.setWidth(x-(whiteWidth/2));
                tecla.shape.setHeight(0);
                getChildren().add(tecla.shape);
                keys.add(tecla);
            }
        }
    }
    public KeyView getKey(int midi){
        return keys.stream()
                .filter(k -> k.midi == midi)
                .findFirst()
                .orElse(null);
    }
    public void press(int midi){
        KeyView tecla = getKey(midi);
        if (tecla!=null){
            tecla.shape.setFill(tecla.isBlack ? Color.DARKBLUE : Color.LIGHTBLUE);
        }
    }
    public void release(int midi){
        KeyView tecla = getKey(midi);
        if (tecla != null){
            tecla.shape.setFill(tecla.isBlack ? Color.BLACK : Color.WHITE);
        }
    }
}
