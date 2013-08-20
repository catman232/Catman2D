package Catman2D.UI;

import Catman2D.Events.*;
import Catman2D.Objects.Entity;
import Catman2D.Toolbox.Color3;
import org.lwjgl.input.Keyboard;

public class TextBox extends UIObject{

    public static final String TAG = "TextBox";

    private Label label;
    private KeyboardListener kl;
    private String hintText = "";
    private boolean hasTyped = false;
    private GenericListener gListener = null;
    private boolean focused = true;
    private Entity bg;
    private MouseListener ml;

    public TextBox(){

        label = new Label();
        label.setX(this.getX());
        label.setY(this.getY());
        label.setText(hintText);
        label.getColor().setRGB(0.0f, 0.0f, 0.0f);

        bg = new Entity();
        bg.setX(label.getX() - 5);
        bg.setY(label.getY() - 2);
        bg.setWidth(label.getWidth() + 5);
        bg.setHeight(label.getHeight() + 4);
        bg.getColor().setRGB(1.0f, 1.0f, 1.0f);

        kl = new KeyboardListener() {
            @Override
            public void onKeyPress(int key) {
                if(focused){
                    switch(key){
                        case Keyboard.KEY_BACK:
                            if(label.getText().length() >= 2){
                                label.setText(label.getText().substring(0, label.getText().length() - 1));
                            }else{
                                label.setText("");
                            }
                            break;

                        default:
                            if(!Character.isIdentifierIgnorable(Keyboard.getEventCharacter())
                                    && Keyboard.getEventKey() != Keyboard.KEY_RETURN
                                    && Keyboard.getEventKey() != Keyboard.KEY_GRAVE){
                                if(hasTyped == false){
                                    label.setText("");
                                    hasTyped = true;
                                }
                                label.setText(label.getText() + Keyboard.getEventCharacter());
                                reloadBG();
                            }
                            break;
                    }

                    if(gListener != null){
                        gListener.onEvent(Keyboard.getEventKey());
                    }
                }
            }

            @Override
            public void onKeyRelease(int key) {

            }
        };

        ml = new MouseListener() {
            @Override
            public void onButtonPress(int button, int x, int y) {
                if(bg.isInBounds(x + getParX(), y + getParY())){
                    focused = true;
                }else{
                    focused = false;
                }
            }

            @Override
            public void onButtonRelease(int button, int x, int y) {

            }
        };

        KeyboardManager.addListener(kl);
        MouseManager.addListener(ml);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onRender() {
        label.setX(this.getX());
        label.setY(this.getY());
        reloadBG();
        bg.onRender();
        label.onRender();
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
        label.setText(hintText);
        reloadBG();
    }

    public Label getLabel(){
        return this.label;
    }

    public void setListener(GenericListener<Integer> listener){
        this.gListener = listener;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    private void reloadBG(){
        bg.setX(label.getX() - 25);
        bg.setY(label.getY() - 2);
        bg.setWidth(label.getWidth() + 50);
        bg.setHeight(label.getHeight() + 4);
    }

    @Override
    public void setParX(int x){
        super.setParX(x);
        bg.setParX(x);
        label.setParX(x);
    }

    @Override
    public void setParY(int y){
        super.setParY(y);
        bg.setParY(y);
        label.setParY(y);
    }
}
