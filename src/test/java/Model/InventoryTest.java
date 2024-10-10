package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class InventoryTest {

    private Inventory inventory;
    private Item mockItem1;
    private Item mockItem2;
    private Item mockItem3;

    @Before
    public void setUp() {
    inventory = new Inventory(2);
    mockItem1 = mock(Item.class);
    mockItem2 = mock(Item.class);
    mockItem3 = mock(Item.class);

    // Настройка mock объектов (при необходимости)
    when(mockItem1.getName()).thenReturn("Item 1");
    when(mockItem1.getDescription()).thenReturn("Description 1");
    when(mockItem1.getItemType()).thenReturn(ObjectType.ITEM_KEY);

    when(mockItem2.getName()).thenReturn("Item 2");
    when(mockItem2.getDescription()).thenReturn("Description 2");
    when(mockItem2.getItemType()).thenReturn(ObjectType.ITEM_AMMO);

    when(mockItem3.getName()).thenReturn("Item 3");
    when(mockItem3.getDescription()).thenReturn("Description 3");
    when(mockItem3.getItemType()).thenReturn(ObjectType.ITEM_MEDICINE);
    }

    @Test
    public void testAddItem_Success() {
        assertTrue(inventory.addItem(mockItem1));
        assertEquals(1, inventory.getItemCount());
    }

    @Test
    public void testAddItem_FailWhenFull() {
        inventory.addItem(mockItem1);
        inventory.addItem(mockItem2);
        assertFalse(inventory.addItem(mockItem3)); // Инвентарь полон
        assertEquals(2, inventory.getItemCount());
    }

    @Test
    public void testAddItemAtSpecificPlace_Success() {
        assertTrue(inventory.addItem(mockItem1, 0));
        assertEquals(mockItem1, inventory.getItems().get(0));
    }

    @Test
    public void testAddItemAtSpecificPlace_FailWhenFull() {
        inventory.addItem(mockItem1);
        inventory.addItem(mockItem2);
        assertFalse(inventory.addItem(mockItem3, 1)); // Инвентарь полон
    }

    @Test
    public void testRemoveItem() {
        inventory.addItem(mockItem1);
        inventory.removeItem(0);
        assertEquals(0, inventory.getItemCount());
        assertNull(inventory.getItems().get(0));
    }

    @Test
    public void testGetItems() {
        inventory.addItem(mockItem1);
        inventory.addItem(mockItem2);
        Map<Integer, Item> items = inventory.getItems();
        assertEquals(2, items.size());
        assertEquals(mockItem1, items.get(0));
        assertEquals(mockItem2, items.get(1));
    }

    @Test
    public void testGetCapacity() {
        assertEquals(2, inventory.getCapacity());
    }

    @Test
    public void testGetItemCount() {
        inventory.addItem(mockItem1);
        assertEquals(1, inventory.getItemCount());
    }

    @Test
    public void testSetItems() {
        Map<Integer, Item> newItems = new HashMap<>();
        newItems.put(0, mockItem1);
        newItems.put(1, mockItem2);
        inventory.setItems(newItems);
        assertEquals(2, inventory.getItemCount());
        assertEquals(mockItem1, inventory.getItems().get(0));
        assertEquals(mockItem2, inventory.getItems().get(1));
    }
}
