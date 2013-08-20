package Catman2D.UI;


import org.lwjgl.opengl.GL11;
import org.newdawn.slick.*;
import org.newdawn.slick.Image;

public class Label extends UIObject{

    private AngelCodeFont font;
    private String text = "";

    public Label(){
        this.getColor().setRGB(1.0f, 1.0f, 1.0f);
        try {
            font = new AngelCodeFont("data/roboto.fnt", new Image("data/roboto_0.png"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onRender() {
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        font.drawString(this.getX() + this.getParX(), this.getY() + this.getParY(),
                this.getText(), new Color(getColor().getR(), getColor().getG(), getColor().getB()));
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFont(String fnt, String img){
        try {
            font = new AngelCodeFont(fnt, new Image(img));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public int getWidth(){
        return font.getWidth(this.getText());
    }

    public int getHeight(){
        return font.getHeight(this.getText());
    }

    public AngelCodeFont getFont(){
        return this.font;
    }
}
