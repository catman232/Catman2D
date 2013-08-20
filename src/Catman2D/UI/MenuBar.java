package Catman2D.UI;

import Catman2D.Events.MouseListener;
import Catman2D.Events.MouseManager;
import Catman2D.Objects.Entity;
import Catman2D.Toolbox.CMConfig;
import Catman2D.Toolbox.Toolbox;

public class MenuBar extends UIObject{

    private String[] items = new String[3];
    private Label[] labels = new Label[3];
    private Label[] itemLabels = new Label[3];
    private String[][] menus = new String[3][3];
    private Entity bg, hbg;
    private Entity mbg;
    private MouseListener ml;
    private Boolean menuVisible = false;

    public MenuBar(){
        items[0] = "File";
        items[1] = "Edit";
        items[2] = "View";

        menus[0][0] = "Open";
        menus[0][1] = "Load Script";
        menus[0][2] = "Exit";

        menus[1][0] = "Copy";
        menus[1][1] = "Paste";
        menus[1][2] = "Undo";

        menus[2][0] = "Properties";
        menus[2][1] = "Toolbox";
        menus[2][2] = "Browser";

        bg = new Entity("MenuBackground");
        bg.getColor().setRGB(0.2f, 0.2f, 0.2f);
        bg.setX(0);
        bg.setY(0);
        bg.setWidth(CMConfig.WINDOW_WIDTH);
        bg.setHeight(20);

        mbg = new Entity("ItemBackground");
        mbg.setWidth(100);
        mbg.setY(20);
        mbg.getColor().setRGB(0.3f, 0.3f, 0.3f);

        hbg = new Entity("Highlight");
        hbg.setHeight(20);
        hbg.setY(0);
        hbg.setX(0);
        hbg.getColor().setRGB(0.3f, 0.3f, 0.3f);

        for(int x = 0; x < items.length; x++){
            labels[x] = new Label();
            labels[x].setText(items[x]);

            if(x > 0){
                labels[x].setX(labels[x - 1].getX() + labels[x - 1].getWidth() + 10);
            }else{
                labels[x].setX(5);
            }

            labels[x].setY(5);
            labels[x].setFont("data/roboto_small.fnt", "data/roboto_small_0.png");
        }

        for(int x = 0; x < itemLabels.length; x++){
            itemLabels[x] = new Label();
            if(x == 0){
                itemLabels[x].setY(25);
            }else{
                itemLabels[x].setY((x + 1) * 25);
            }
            itemLabels[x].setFont("data/roboto_small.fnt", "data/roboto_small_0.png");
            itemLabels[x].setText("");
        }

        ml = new MouseListener(){

            @Override
            public void onButtonPress(int button, int x, int y) {
                boolean hasPressed = false;
                for(int i = 0; i < labels.length; i++){
                    Label l = labels[i];
                    int tempX = l.getX() + l.getWidth();
                    int tempY = l.getY() + l.getHeight();
                    if(x > l.getX() && x < l.getX() + l.getWidth()){
                        if(y > l.getY() && y < 20){
                            mbg.setX(l.getX() - 2);
                            hasPressed = true;
                            showItems(i, l.getX() + 5, l);
                            menuVisible = true;
                            break;
                        }
                    }
                }

                if(hasPressed == true){
                    mbg.setHeight(100);
                }else{
                    mbg.setHeight(0);
                    menuVisible = false;
                }
            }

            @Override
            public void onButtonRelease(int button, int x, int y) {

            }
        };

        MouseManager.addListener(ml);

    }

    private void showItems(int menu, int x, Label l){
        String[] tempItems = menus[menu].clone();
        for(int i = 0; i < tempItems.length; i++){
            itemLabels[i].setText(tempItems[i]);
            itemLabels[i].setX(x);
        }
        hbg.setX(x - 7);
        hbg.setWidth(l.getWidth() + 4);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onRender() {
        bg.onRender();
        mbg.setHeight(itemLabels.length * 25);
        if(menuVisible == true){
            hbg.onRender();
            mbg.onRender();
            for(Label l : itemLabels){
                if(l.getText() == null){
                    l.setText("");
                }
                l.onRender();
            }
        }

        for(Label l : labels){
            l.onRender();
        }
    }
}
