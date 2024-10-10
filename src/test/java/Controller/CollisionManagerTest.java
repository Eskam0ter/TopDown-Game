package Controller;

import Model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CollisionManagerTest {

    private Level mockLevel;
    private Player mockPlayer;
    private CollisionManager collisionManager;

    @Before
    public void setUp() {
        mockLevel = mock(Level.class);
        mockPlayer = mock(Player.class);
        collisionManager = new CollisionManager(mockLevel, mockPlayer);
    }

    @Test
    public void testIsPlayerNearNpc_PlayerNearNpc() {
        NPC mockNpc = mock(NPC.class);
        when(mockLevel.getNpcs()).thenReturn(Collections.singletonList(mockNpc));
        when(mockPlayer.isNear(mockNpc)).thenReturn(true);

        NPC result = collisionManager.isPlayerNearNpc();

        assertEquals(mockNpc, result);
    }

    @Test
    public void testIsPlayerNearNpc_PlayerNotNearNpc() {
        NPC mockNpc = mock(NPC.class);
        when(mockLevel.getNpcs()).thenReturn(Collections.singletonList(mockNpc));
        when(mockPlayer.isNear(mockNpc)).thenReturn(false);

        NPC result = collisionManager.isPlayerNearNpc();

        assertNull(result);
    }

    @Test
    public void testCheckPlayerCollision_NoCollision() {
        Vector2D direction = new Vector2D(1, 0);
        Vector2D newPlayerPosition = new Vector2D(50, 50);
        when(mockPlayer.getVelocity()).thenReturn(new Vector2D(10, 10));
        when(mockPlayer.getCurrentPosition()).thenReturn(new Vector2D(40, 50));
        when(mockLevel.getLevelWidth()).thenReturn(100);
        when(mockLevel.getLevelHeight()).thenReturn(100);
        when(mockLevel.getWalls()).thenReturn(Collections.emptyList());

        boolean result = collisionManager.checkPlayerCollision(direction);

        assertFalse(result);
    }

    @Test
    public void testCheckPlayerCollision_CollisionWithWall() {
        Vector2D direction = new Vector2D(1, 0);
        Vector2D newPlayerPosition = new Vector2D(50, 50);
        Rectangle wallBoundingBox = new Rectangle(50, 50, 10, 10);

        when(mockPlayer.getVelocity()).thenReturn(new Vector2D(10, 10));
        when(mockPlayer.getCurrentPosition()).thenReturn(new Vector2D(40, 50));
        when(mockLevel.getLevelWidth()).thenReturn(100);
        when(mockLevel.getLevelHeight()).thenReturn(100);

        Tile mockWall = mock(Tile.class);
        when(mockWall.getBoundingBox()).thenReturn(wallBoundingBox);
        when(mockLevel.getWalls()).thenReturn(Collections.singletonList(mockWall));

        boolean result = collisionManager.checkPlayerCollision(direction);

        assertTrue(result);
    }

    @Test
    public void testCheckBulletCollision_CollisionWithZombie() {
        Bullet mockBullet = mock(Bullet.class);
        Rectangle bulletBoundingBox = new Rectangle(50, 50, 5, 5);
        Zombie mockZombie = mock(Zombie.class);
        Rectangle zombieBoundingBox = new Rectangle(50, 50, 10, 10);

        when(mockBullet.getBoundingBox()).thenReturn(bulletBoundingBox);
        when(mockZombie.getBoundingBox()).thenReturn(zombieBoundingBox);
        when(mockLevel.getZombies()).thenReturn(Collections.singletonList(mockZombie));

        boolean result = collisionManager.checkBulletCollision(mockBullet);

        assertTrue(result);
        verify(mockZombie).takeDamage(Constants.BULLET_DAMAGE);
        verify(mockZombie).setDamaged(true);
    }

    @Test
    public void testCheckPlayerCollisionWithItem_NoCollision() {
        Rectangle playerBoundingBox = new Rectangle(10, 10, 10, 10);
        Item mockItem = mock(Item.class);

        when(mockPlayer.getBoundingBox()).thenReturn(playerBoundingBox);
        when(mockLevel.getItems()).thenReturn(Collections.emptyList());

        Item result = collisionManager.checkPlayerCollisionWithItem(mockPlayer, mockItem);

        assertEquals(mockItem, result);
    }

    @Test
    public void testCheckPlayerCollisionWithItem_CollisionWithItem() {
        Rectangle playerBoundingBox = new Rectangle(10, 10, 10, 10);
        Item mockItem = mock(Item.class);
        Item mockItemToCheck = mock(Item.class);
        Rectangle itemBoundingBox = new Rectangle(10, 10, 10, 10);

        when(mockPlayer.getBoundingBox()).thenReturn(playerBoundingBox);
        when(mockItem.getBoundingBox()).thenReturn(itemBoundingBox);
        when(mockLevel.getItems()).thenReturn(Collections.singletonList(mockItem));

        Item result = collisionManager.checkPlayerCollisionWithItem(mockPlayer, mockItemToCheck);

        assertEquals(mockItem, result);
    }
}
