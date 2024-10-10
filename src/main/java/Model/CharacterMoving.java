package Model;

import static Model.Direction.DOWN;


/**
 * Represents an abstract character that can move in the game.
 */
public abstract class CharacterMoving extends Character {
    private Vector2D velocity;
    private Vector2D lastPosition;

    private Direction direction;
    private transient Animation currentAnimation;
    private transient Animation upAnimation;
    private transient Animation downAnimation;
    private transient Animation leftAnimation;
    private transient Animation rightAnimation;
    private transient Animation damageAnimation;

    /**
     * Constructs a CharacterMoving object with specified hit points, position, velocity, and animation path.
     *
     * @param hp            The hit points of the character.
     * @param currentPosition The current position of the character.
     * @param velocity      The velocity of the character.
     * @param animationPath The path to the folder containing animations for the character.
     */
    public CharacterMoving(int hp, Vector2D currentPosition, Vector2D velocity, String animationPath) {
        super(hp, currentPosition);
        this.velocity = velocity;
        this.direction = DOWN;
        this.lastPosition = new Vector2D(0,0);
        initAnimations(animationPath);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public void setUpAnimation(Animation upAnimation) {
        this.upAnimation = upAnimation;
    }

    public void setDownAnimation(Animation downAnimation) {
        this.downAnimation = downAnimation;
    }

    public void setLeftAnimation(Animation leftAnimation) {
        this.leftAnimation = leftAnimation;
    }

    public void setRightAnimation(Animation rightAnimation) {
        this.rightAnimation = rightAnimation;
    }

    public Animation getUpAnimation() {
        return upAnimation;
    }

    public Animation getDownAnimation() {
        return downAnimation;
    }

    public Animation getLeftAnimation() {
        return leftAnimation;
    }

    public Animation getRightAnimation() {
        return rightAnimation;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    /**
     * Initializes animations for the character.
     *
     * @param animationPath The path to the folder containing animations for the character.
     */
    private void initAnimations(String animationPath) {
        upAnimation = new Animation( animationPath + "/up", 3, Constants.ANIMATION_FRAME_DELAY);
        downAnimation = new Animation(animationPath + "/down", 3, Constants.ANIMATION_FRAME_DELAY);
        leftAnimation = new Animation(animationPath + "/left", 3, Constants.ANIMATION_FRAME_DELAY);
        rightAnimation = new Animation(animationPath + "/right", 3, Constants.ANIMATION_FRAME_DELAY);
//        damageAnimation = new Animation(animationPath + "/damage", 9, 6);
        currentAnimation = downAnimation; // По умолчанию начинаем с анимации вверх
        playCurrentAnimation();
    }


    /**
     * Plays the current animation of the character.
     */
    public void playCurrentAnimation() {
        if (currentAnimation != null) {
            currentAnimation.play();
        }
    }
    /**
     * Stops the current animation of the character.
     */
    public void stopCurrentAnimation() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
    }

    /**
     * Moves the character in the specified direction.
     *
     * @param direction The direction in which the character should move.
     */
    public void move(Vector2D direction){
        Vector2D displacement = direction.multiply(this.velocity);
        setCurrentPosition(getCurrentPosition().add(displacement));
        this.setDirection(Direction.fromVector(direction));
        switch (this.getDirection()) {
            case UP:
                setCurrentAnimation(getUpAnimation());
                break;
            case DOWN:
                setCurrentAnimation(getDownAnimation());
                break;
            case LEFT:
                setCurrentAnimation(getLeftAnimation());
                break;
            case RIGHT:
                setCurrentAnimation(getRightAnimation());
                break;
        }
        getCurrentAnimation().play();
        getCurrentAnimation().update();
    }


    public Vector2D getLastPosition() {
        return lastPosition;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public boolean isStopped() {
        return this.lastPosition.equals(this.getCurrentPosition());
    }
}
