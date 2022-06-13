import greenfoot.*;
import java.awt.Graphics;

public class Score extends Actor {
    private static final Color textColor = new Color(1, 1, 1);
    private int value = 0;
    private int target = 0;
    private String text;
    private int stringLength;

    public Score(String prefix) {
        text = prefix;
        stringLength = (text.length() + 2) * 20;
        setImage(new GreenfootImage(stringLength, 16));
        GreenfootImage image = getImage();
        image.setColor(textColor);
        reload();
    }

    public void act() {
        if (value < target) {
            value++;
            reload();
        } else if (value > target) {
            value--;
            reload();
        }
    }

    public void add(int score) {
        target += score;
    }

    private void reload() {
        GreenfootImage image = getImage();
        image.clear();
        image.drawString(text + value, 1, 12);
    }
}




