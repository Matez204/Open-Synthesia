package visual;

import javafx.scene.shape.Rectangle;

public class KeyView {
    public Rectangle shape;
    public int midi;
    public boolean isBlack;
    public KeyView (int midi, boolean isBlack){
        this.midi = midi;
        this.isBlack = isBlack;
        this.shape = new Rectangle();
    }
}
