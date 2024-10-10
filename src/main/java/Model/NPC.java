package Model;

import java.util.ArrayList;
import java.util.List;

public class NPC extends Character{
    private String name;
    private boolean hasQuest;
    private Item questItem;
    private boolean questCompleted;
    private List<Dialog> dialogs;
    private Quest quest;

    public NPC(String name, Item questItem, Vector2D position, Quest quest) {
        super(10000, position);
        this.name = name;
        this.questItem = questItem;
        this.hasQuest = true;
        this.questCompleted = false;
        this.dialogs = new ArrayList<Dialog>();
        this.quest = quest;
        this.dialogs.add(new Dialog(quest.getDescription()));
        this.dialogs.add(new Dialog("Thanks! See ya"));

    }

    public Dialog getCurrentDialog() {
        if (!questCompleted) {
            return dialogs.getFirst();
        }
        return dialogs.getLast();
    }


    public Item getQuestItem() {
        return questItem;
    }

    // Метод для завершения квеста
    public void completeQuest(Player player) {
        this.questCompleted = true;
        this.hasQuest = false;
        player.getInventory().addItem(questItem);
    }

    public boolean hasQuest() {
        return hasQuest;
    }

    public boolean isQuestCompleted() {
        return questCompleted;
    }

    public String getName() {
        return name;
    }
}

