package Catman2D.UI;

import Catman2D.Objects.Entity;

public abstract class UIObject extends Entity{
    private int x = 0, y = 0, width = 0, height = 0;

    public abstract void onUpdate();
    public abstract void onRender();

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
}
