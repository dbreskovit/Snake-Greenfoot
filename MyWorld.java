import greenfoot.*;

public class MyWorld extends World {
    private Snake snakeBody[];
    private Score score;
    private Food food;
    private static Music m = new Music();
    private int direction;
    private int state;
    private static int BLOCK = 15;

    public MyWorld() {
        super(20 * BLOCK, 30 * BLOCK, 1);
        setBackground("menu.png");
        direction = 0;
        state = 0;
    }

    public void act() {
        if (state == 0) {
            setBackground("menu.png");
            if (Greenfoot.isKeyDown("space")) {
                state = 1;
                m.musicStarted();
            }
            return;
        } else if (state == 1) {
            setBackground("background.png");
            food = new Food();
            addObject(food, 0, 0);
            score = new Score("Pontos: ");
            addObject(score, 15 * BLOCK, getHeight() - 10);
            snakeBody = new Snake[4];
            for (int i = 0; i < snakeBody.length; i++) {
                snakeBody[i] = new Snake(i == 0);
                addObject(snakeBody[i], 6 * BLOCK + (snakeBody.length - i) * BLOCK, 2 * BLOCK);
            }
            updateFoodLocation();
            state = 666;
            return;
        }

        else if (state == -1) {
            m.musicStopped();
            removeObjects(getObjects(null));
            setBackground("gameover.png");
            if (Greenfoot.isKeyDown("space")) {
                Greenfoot.setWorld(new MyWorld());
                state = 1;

            }
            return;
        }
        gameSnake();
    }

    public void gameSnake() {
        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {
            if (direction == 1 || direction == 3) direction = 0;
        } else if (Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("s")) {
            if (direction == 0 || direction == 2) direction = 1;
        } else if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {
            if (direction == 1 || direction == 3) direction = 2;
        } else if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("w")) {
            if (direction == 0 || direction == 2) direction = 3;
        }

        int rotation = snakeBody[0].getRotation();
        int previousLocationX = snakeBody[0].getX();
        int previousLocationY = snakeBody[0].getY();
        snakeBody[0].setRotation(direction * 90);
        snakeBody[0].move(BLOCK);

        if (snakeBody[0].getX() != previousLocationX || snakeBody[0].getY() != previousLocationY) {
            for (int i = 1; i < snakeBody.length; i++) {
                int tempRotation = snakeBody[i].getRotation();
                snakeBody[i].setRotation(rotation);
                previousLocationX = snakeBody[i].getX();
                previousLocationY = snakeBody[i].getY();
                snakeBody[i].move(BLOCK);
                rotation = tempRotation;
            }
            if (snakeBody[0].getX() == food.getX() && snakeBody[0].getY() == food.getY()) {
                growSnake(previousLocationX, previousLocationY, rotation);
                updateFoodLocation();
                score.add(1);
            }
            for (int i = 1; i < snakeBody.length; i++) {
                if (snakeBody[0].getX() == snakeBody[i].getX() && snakeBody[0].getY() == snakeBody[i].getY()) state = -1;
            }
        } else {
            direction = rotation / 90;
            snakeBody[0].setRotation(direction * 90);
            state = -1;
        }
    }

    private void growSnake(int x, int y, int rotation) {
        Snake s = new Snake(false);
        Snake oldSnake[] = new Snake[snakeBody.length];
        for (int i = 0; i < snakeBody.length; i++) {
            oldSnake[i] = snakeBody[i];
        }
        snakeBody = new Snake[snakeBody.length + 1];
        for (int i = 0; i < snakeBody.length - 1; i++) {
            snakeBody[i] = oldSnake[i];
        }
        snakeBody[snakeBody.length - 1] = s;
        snakeBody[snakeBody.length - 1].setRotation(rotation);
        addObject(snakeBody[snakeBody.length - 1], x, y);
    }

    private void updateFoodLocation() {
        int x = 0, y = 0;
        boolean overlap = true;

        while (overlap) {
            x = Greenfoot.getRandomNumber(getWidth() / BLOCK);
            y = Greenfoot.getRandomNumber(getHeight() / BLOCK);

            for (int i = 0; i < snakeBody.length; i++) {
                if (x != snakeBody[i].getX() || y != snakeBody[i].getY()) {
                    overlap = false;
                    break;
                }
            }
        }
        food.setLocation(x * BLOCK, y * BLOCK);
    }
}

