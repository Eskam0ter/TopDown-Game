package Model;

/**
 * Represents a zombie character in the game.
 */
public class Zombie extends CharacterMoving {
    private int damage;
    private int stepsCounter = 0;
    private final int maxSteps = 15;
    private boolean isDamaged = false;

    public Zombie(int hp, Vector2D position, Vector2D velocity, int damage, String animationPath) {
        super(hp, position, velocity,animationPath);
        this.damage = damage;
        setDirection(Direction.UP);
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public void attack(Player player) {
        player.takeDamage(damage);
    }

    /**
     * Moves the zombie if ready.
     * @param direction The direction in which the zombie moves.
     */
    public void moveIfReady(Vector2D direction) {
        stepsCounter++;
        if (stepsCounter >= maxSteps) {
            stepsCounter = 0; // Обнуляем счетчик
            move(direction); // Делаем шаг
        }
    }

    /**
     * Checks if the player character is visible to the zombie.
     * @param player The player character to check visibility.
     * @return True if the player character is visible, false otherwise.
     */
    public boolean isPlayerVisible(Player player) {
        int zombieX = this.getCurrentPosition().getX();
        int zombieY = this.getCurrentPosition().getY();
        int playerX = player.getCurrentPosition().getX();
        int playerY = player.getCurrentPosition().getY();

        // Определите радиус обзора зомби (например, в пикселях)
        int visibilityRadius = 100;

        // Проверяем, находится ли игрок в радиусе видимости зомби
        return Math.abs(playerX - zombieX) <= visibilityRadius && Math.abs(playerY - zombieY) <= visibilityRadius;
    }

    /**
     * Calculates the direction in which the zombie should move towards the player character.
     * @param player The player character to calculate direction towards.
     * @return The direction vector towards the player character.
     */
    public Vector2D calculatePlayerDirection(Player player) {
        // Получаем текущее положение игрока и зомби
        Vector2D zombiePosition = this.getCurrentPosition();
        Vector2D playerPosition = player.getCurrentPosition();

        // Вычисляем разницу координат между зомби и игроком
        int dx = playerPosition.getX() - zombiePosition.getX();
        int dy = playerPosition.getY() - zombiePosition.getY();

        // Определяем направление движения зомби к игроку
        if (Math.abs(dx) > Math.abs(dy)) {
            // Если разница по X больше, двигаемся вдоль оси X
            return new Vector2D(dx > 0 ? 1 : -1, 0);
        } else {
            // Иначе, двигаемся вдоль оси Y
            return new Vector2D(0, dy > 0 ? 1 : -1);
        }
    }


}
