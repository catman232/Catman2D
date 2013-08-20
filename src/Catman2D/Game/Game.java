package Catman2D.Game;

public abstract class Game {

    /**
     * Game Class
     *
     * Used to create a new instance of the C2D Engine.
     * All logic is executed through this class.
     */

    public abstract void init(); //Called when game is created

    public abstract void onClose(); //Called when game is closed

    public abstract void onUpdate(); //Called when game was updated

    public abstract void onResize(); //Called when game window is resized

}
