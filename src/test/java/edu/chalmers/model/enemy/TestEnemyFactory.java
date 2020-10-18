package edu.chalmers.model.enemy;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.test.RunWithFX;
import edu.chalmers.model.SetupWorld;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static edu.chalmers.FXGLTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RunWithFX.class)
public class TestEnemyFactory {

    Entity tempPlayer;

    @BeforeAll
    public static void initApp() throws InterruptedException {
        initialize();
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        deInitialize();
    }

    private void init() throws InterruptedException {
        waitForRunLater(() -> {
            tempPlayer = spawn("player", 0, 0);
        });
    }

    @Test
    public void testCreateZombie() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            Entity zombie1 = EnemyFactory.getInstance().createEnemy("zombie", 0, 0, tempPlayer, new StatMultiplier());
            Entity zombie2 = EnemyFactory.getInstance().createEnemy("ZOMBIE", 0, 0, tempPlayer, new StatMultiplier());
            Entity zombie3 = EnemyFactory.getInstance().createEnemy("ZoMbIe", 0, 0, tempPlayer, new StatMultiplier());

            assertEquals(EnemyTypes.getZombieClass(), zombie1.getComponent(EnemyComponent.class).getEnemyType().getClass());
            assertEquals(EnemyTypes.getZombieClass(), zombie2.getComponent(EnemyComponent.class).getEnemyType().getClass());
            assertEquals(EnemyTypes.getZombieClass(), zombie3.getComponent(EnemyComponent.class).getEnemyType().getClass());
        });
    }

    @Test
    public void testCreateRex() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            Entity rex1 = EnemyFactory.getInstance().createEnemy("rex", 0, 0, tempPlayer, new StatMultiplier());
            Entity rex2 = EnemyFactory.getInstance().createEnemy("REX", 0, 0, tempPlayer, new StatMultiplier());
            Entity rex3 = EnemyFactory.getInstance().createEnemy("ReX", 0, 0, tempPlayer, new StatMultiplier());

            assertEquals(EnemyTypes.getRexClass(), rex1.getComponent(EnemyComponent.class).getEnemyType().getClass());
            assertEquals(EnemyTypes.getRexClass(), rex2.getComponent(EnemyComponent.class).getEnemyType().getClass());
            assertEquals(EnemyTypes.getRexClass(), rex3.getComponent(EnemyComponent.class).getEnemyType().getClass());
        });
    }

    @Test
    public void testCreateBlob() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            Entity blob1 = EnemyFactory.getInstance().createEnemy("blob", 0, 0, tempPlayer, new StatMultiplier());
            Entity blob2 = EnemyFactory.getInstance().createEnemy("BLOB", 0, 0, tempPlayer, new StatMultiplier());
            Entity blob3 = EnemyFactory.getInstance().createEnemy("BlOb", 0, 0, tempPlayer, new StatMultiplier());

            assertEquals(EnemyTypes.getBlobClass(), blob1.getComponent(EnemyComponent.class).getEnemyType().getClass());
            assertEquals(EnemyTypes.getBlobClass(), blob2.getComponent(EnemyComponent.class).getEnemyType().getClass());
            assertEquals(EnemyTypes.getBlobClass(), blob3.getComponent(EnemyComponent.class).getEnemyType().getClass());
        });
    }

    @Test
    public void testCreateNull() throws InterruptedException {
        init();
        waitForRunLater(() -> {
            Entity _null = EnemyFactory.getInstance().createEnemy("", 0, 0, tempPlayer, new StatMultiplier());
            Entity _null2 = EnemyFactory.getInstance().createEnemy("   ", 0, 0, tempPlayer, new StatMultiplier());
            Entity _null3 = EnemyFactory.getInstance().createEnemy("Hej", 0, 0, tempPlayer, new StatMultiplier());
            Entity _null4 = EnemyFactory.getInstance().createEnemy(null, 0, 0, tempPlayer, new StatMultiplier());

            assertEquals(null, _null);
            assertEquals(null, _null2);
            assertEquals(null, _null3);
            assertEquals(null, _null4);
        });
    }
}

