package Catman2D.Toolbox;

public class Color3 {
    private float r = 0, g = 0, b = 0;

    public Color3(float r, float g, float b){
        setRGB(r, g, b);
    }

    public Color3(){

    }

    public void setRGB(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }
}
