import greenfoot.*;

public class Snake extends Actor {
    public Snake(boolean head) {
        if (head)
            setImage("snakehead.png");
        else
            setImage("snakebody.png");
    }
}

