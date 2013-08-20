package Catman2D.UI;

import Catman2D.Events.MouseListener;
import Catman2D.Events.MouseManager;
import Catman2D.Logic.LogicListener;
import Catman2D.Logic.LogicManager;
import Catman2D.Objects.Entity;
import Catman2D.Toolbox.CMConfig;
import org.lwjgl.input.Mouse;

import java.util.HashMap;

public class Window extends UIObject{

    private Entity titleBar, body, resize, child;
    private Label close;
    private Label titleLabel;
    private MouseListener ml;
    private boolean isDragging = false, isResizing = false;
    private int offsetX = 0, offsetY = 0, resX = 0, resY = 0;
    private LogicListener ll;
    private String title = "Window";
    private boolean isSnapped = false;
    private HashMap<Integer, Entity> children = new HashMap<Integer, Entity>();
    private int nextKey = 0;
    private TextBox tb;

    public Window(){
        titleBar = new Entity("TitleBar");
        titleBar.setWidth(this.getWidth());
        titleBar.setHeight(20);
        titleBar.getColor().setRGB(0.2f, 0.2f, 0.2f);
        titleBar.setX(this.getX());
        titleBar.setY(this.getY());

        body = new Entity("Body");
        body.setWidth(this.getWidth());
        body.setHeight(this.getHeight());
        body.setX(this.getX());
        body.setY(this.getY());
        body.getColor().setRGB(0.8f ,0.8f, 0.8f);

        resize = new Entity("Resize");
        resize.setTexture("data/ui_resize.png");
        resize.setX(titleBar.getX() + titleBar.getWidth() - 10);
        resize.setY(body.getY() - body.getHeight() + 10);
        resize.setWidth(10);
        resize.setHeight(10);

        close = new Label();
        close.setX(titleBar.getX() + titleBar.getWidth() - 10);
        close.setY(titleBar.getY() - 5);
        close.setText("X");
        close.setFont("data/roboto_small.fnt", "data/roboto_small_0.png");
        close.getColor().setRGB(1.0f, 0.0f, 0.0f);

        child = new Entity("Child");
        child.setWidth(100);
        child.setHeight(100);
        child.setX(10);
        child.setY(10);
        child.getColor().setRGB(0.0f, 0.0f, 1.0f);

        titleLabel = new Label();
        titleLabel.setText(title);
        titleLabel.setFont("data/roboto_small.fnt", "data/roboto_small_0.png");

        tb = new TextBox();
        tb.setHintText("Test");
        tb.setX(100);
        tb.setY(100);
        tb.getLabel().setFont("data/roboto_small.fnt", "data/roboto_small_0.png");

        addEntity(tb);
        //addEntity(child);

        ml = new MouseListener(){

            @Override
            public void onButtonPress(int button, int x, int y) {
                if(titleBar.isInBounds(x, y)){
                        isDragging = true;
                        offsetX = titleBar.getX() - x;
                        offsetY = titleBar.getY() - y;
                        updatePositions();
                }
                if(resize.isInBounds(x, y)){
                        resX = x;
                        resY = y;
                        isResizing = true;
                }
            }

            @Override
            public void onButtonRelease(int button, int x, int y) {
                if(isDragging){
                    if(isSnapped == false){
                        titleBar.setX(x + offsetX);
                        titleBar.setY(y + offsetY);
                    }else{
                        isSnapped = false;
                    }
                    updatePositions();
                    isDragging = false;
                }

                if(isResizing){
                    if(resX > x){
                        if(titleBar.getWidth() - (resX - x) >= titleLabel.getWidth()){
                            titleBar.setWidth(titleBar.getWidth() - (resX - x));
                        }
                    }else{
                        titleBar.setWidth(titleBar.getWidth() + (x - resX));
                    }

                    if(resY > y){
                        if(body.getHeight() - (resY - y) >= 30){
                            body.setHeight(body.getHeight() - (resY - y));
                        }
                    }else{
                        body.setHeight(body.getHeight() + (y - resY));
                    }

                    body.setWidth(titleBar.getWidth());
                    updatePositions();
                    isResizing = false;
                }
            }
        };

        ll = new LogicListener() {
            @Override
            public void onUpdate() {
                if(isDragging){
                    if(MouseManager.getX() + offsetX < 20){
                        titleBar.setX(0);
                        isSnapped = true;
                    }else if(MouseManager.getX() + offsetX + titleBar.getWidth() > CMConfig.WINDOW_WIDTH - 20){
                        titleBar.setX(CMConfig.WINDOW_WIDTH - titleBar.getWidth());
                        isSnapped = true;
                    }else{
                        titleBar.setX(MouseManager.getX() + offsetX);
                    }

                    if(MouseManager.getY() + offsetY < 40){
                        titleBar.setY(20);
                        isSnapped = true;
                    }else if(MouseManager.getY() + (offsetY + body.getHeight() + titleBar.getHeight()) > CMConfig.WINDOW_HEIGHT - 20){
                        titleBar.setY(CMConfig.WINDOW_HEIGHT - (body.getHeight() + titleBar.getHeight()));
                        isSnapped = true;
                    }else{
                        titleBar.setY(MouseManager.getY() + offsetY);
                    }

                    updatePositions();
                }

                int x = Mouse.getX();
                int y = MouseManager.getY();

                if(isResizing){
                    if(resX > x){
                        if(titleBar.getWidth() - (resX - x) >= titleLabel.getWidth()){
                            titleBar.setWidth(titleBar.getWidth() - (resX - x));
                            resX = x;
                        }
                    }else{
                        titleBar.setWidth(titleBar.getWidth() + (x - resX));
                        resX = x;
                    }

                    if(resY > y){
                        if(body.getHeight() - (resY - y) >= 30){
                            body.setHeight(body.getHeight() - (resY - y));
                            resY = y;
                        }
                    }else{
                        body.setHeight(body.getHeight() + (y - resY));
                        resY = y;
                    }

                    body.setWidth(titleBar.getWidth());
                    updatePositions();
                }
            }
        };

        MouseManager.addListener(ml);
        LogicManager.addListener(ll);
    }

    public void addEntity(Entity e){
        e.setParX(body.getX());
        e.setParY(body.getY());
        children.put(nextKey, e);
        nextKey++;
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onRender() {
        titleBar.onRender();
        titleLabel.onRender();
        body.onRender();
        resize.onRender();
        close.onRender();

        for(Integer key : children.keySet()){
            children.get(key).onRender();
        }
    }

    @Override
    public void setX(int x){
        super.setX(x);
        titleBar.setX(x);
        updatePositions();
    }

    @Override
    public void setY(int y){
        super.setY(y);
        titleBar.setY(y);
        updatePositions();
    }

    @Override
    public void setWidth(int width){
        super.setWidth(width);
        titleBar.setWidth(width);
        body.setWidth(width);
        updatePositions();
    }

    @Override
    public void setHeight(int height){
        super.setHeight(height);
        body.setHeight(height);
        updatePositions();
    }

    private void updatePositions(){
        titleLabel.setY(titleBar.getY() + ((titleBar.getHeight() / 2) - (titleLabel.getHeight() / 2)));
        titleLabel.setX(titleBar.getX() + ((titleBar.getWidth() / 2) - (titleLabel.getWidth() / 2)));

        body.setY(titleBar.getY() + titleBar.getHeight());
        body.setX(titleBar.getX());

        resize.setX(titleBar.getX() + titleBar.getWidth() - 10);
        resize.setY(body.getY() + body.getHeight() - 10);

        close.setX(titleBar.getX() + titleBar.getWidth() - 10);
        close.setY(titleBar.getY() + 3);

        for(Integer key : children.keySet()){
            children.get(key).setParX(body.getX());
            children.get(key).setParY(body.getY());
        }
    }
}
