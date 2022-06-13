import greenfoot.*;

public class Music extends Actor {
    GreenfootSound bgMusic = new GreenfootSound("music.mp3");

    public void musicStarted() {
        bgMusic.playLoop();
    }

    public void musicStopped() {
        bgMusic.stop();
    }
}




