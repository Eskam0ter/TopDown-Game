package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Represents an animation composed of multiple frames.
 */
public class Animation  {
    private  BufferedImage[] frames;
    private int currentFrameIndex;
    private int frameDelay; // Задержка между кадрами в миллисекундах
    private long lastFrameTime; // Время последнего обновления кадра

    /**
     * Constructs an Animation object.
     *
     * @param folderPath  The path to the folder containing the animation frames.
     * @param frameCount  The number of frames in the animation.
     * @param frameDelay  The delay between frames in milliseconds.
     */
    public Animation(String folderPath, int frameCount, int frameDelay) {
        frames = new BufferedImage[frameCount];
        this.frameDelay = frameDelay;

        try {
            for (int i = 0; i < frameCount; i++) {
                InputStream is = getClass().getResourceAsStream(folderPath + "/" + i + ".png");
                frames[i] = ImageIO.read(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrameIndex];
    }

    /**
     * Updates the animation to the next frame.
     */
    public void update() {
        lastFrameTime = System.currentTimeMillis();
    }

    /**
     * Plays the animation by advancing to the next frame based on the frame delay.
     */
    public void play() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= frameDelay) {
            currentFrameIndex = (currentFrameIndex + 1) % frames.length;
            lastFrameTime = currentTime;
        }
    }

    /**
     * Stops the animation by resetting the current frame index to 0.
     */
    public void stop() {
        currentFrameIndex = 0;
    }
}




