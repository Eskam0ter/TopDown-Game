package Model;

/**
 * An interface representing objects that can be animated.
 */
public interface Animatable {
    /**
     * Initializes the animations for the object from the specified animation path.
     *
     * @param animationPath The path to the animation resources.
     */
    void initAnimations(String animationPath);

    /**
     * Plays the current animation of the object.
     */
    void playCurrentAnimation();
}
