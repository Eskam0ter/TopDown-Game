package Model;


/**
 * Represents a quest in the game.
 */
public class Quest {
    private String description;
    private boolean completed;

    public Quest(String description) {
        this.description = description;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void complete() {
        completed = true;
    }
}

