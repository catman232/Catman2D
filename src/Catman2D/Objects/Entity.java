package Catman2D.Objects;

import Catman2D.Packaging.PackageManager;
import Catman2D.Toolbox.Color3;
import Catman2D.Toolbox.Toolbox;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.FileInputStream;
import java.io.IOException;

public class Entity {

    /**
     * Entity Class
     *
     * Represents all objects in the game.
     */

    private static final String TAG = "Entity";

    private int x = 0, y = 0, width = 0, height = 0;
    private float opacity = 1.0f;
    private String name = "", texPath = "";
    private Color3 color = new Color3();
    private Texture tex = null;
    private int parX = 0, parY = 0;

    public Entity(){

    }

    public Entity(String name){
        init(name, 0, 0, 0, 0, 0.0f);
    }

    public Entity(String name, int x, int y){
        init(name, x, y, 0, 0, 0.0f);
    }

    public Entity(String name, int x, int y, int width, int height){
        init(name, x, y, width, height, 0.0f);
    }

    public Entity(String name, int x, int y, int width, int height, float opacity){
        init(name, x, y, width, height, opacity);
    }

    private void init(String name, int x, int y, int width, int height, float opacity){
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.opacity = opacity;
    }

    public void onUpdate(){

    }

    public void onRender(){
        if(tex != null){
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            tex.bind();
        }else{
            if(texPath.equals("")){
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glColor3f(color.getR(), color.getG(), color.getB());
            }else{
                try {
                    this.tex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(texPath));
                    this.setWidth(tex.getTextureWidth());
                    this.setHeight(tex.getTextureHeight());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                texPath = "";
            }
        }

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(x + parX, y + parY);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(x + parX + width, y + parY);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(x + parX + width, y + height + parY);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(x + parX, y + height + parY);
        GL11.glEnd();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public boolean isInBounds(int x, int y){
        if(x > getX() && x < getX() + getWidth()){
            if(y > getY() && y < getY() + getHeight()){
                return true;
            }
        }
        return false;
    }

    public void setTexture(String path){
        texPath = path;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color3 getColor() {
        return color;
    }

    public void setColor(Color3 color) {
        this.color = color;
    }

    public int getParY() {
        return parY;
    }

    public void setParY(int parY) {
        this.parY = parY;
    }

    public int getParX() {
        return parX;
    }

    public void setParX(int parX) {
        this.parX = parX;
    }
}
