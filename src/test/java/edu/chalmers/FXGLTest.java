package edu.chalmers;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import edu.chalmers.main.Main;
import edu.chalmers.model.GameWorldFactory;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static com.almasb.fxgl.dsl.FXGL.getApp;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Helper class for all of the tests related to FXGL/JavaFX.
 */
public final class FXGLTest {
    /**
     * The maximum amount of time (in seconds) to wait for a condition to be true.
     */
    public static final int AWAIT_TIMEOUT_SEC = 10;

    private static Main mainInstance;

    /**
     * Initialize this helper class.
     * @throws InterruptedException Timeout has been reached, see AWAIT_TIMEOUT_SEC.
     */
    public static void initialize() throws InterruptedException
    {
        if (mainInstance == null)
        {
            CountDownLatch initializedLatch = new CountDownLatch(1);
            Main.setInitializedLatch(initializedLatch);
            new Thread(() -> {
                GameApplication.launch(Main.class, new String[0]);
            }).start();
            assertTrue(initializedLatch.await(AWAIT_TIMEOUT_SEC, TimeUnit.SECONDS));

            GameApplication gameApplication = getApp();
            assertNotNull(gameApplication);
            assertTrue(gameApplication instanceof Main);

            mainInstance = (Main)gameApplication;
            mainInstance.setTestRunning(true);
        }
    }

    /**
     * De-initialize the helper class.
     * Does not actually shut the application down, as this prevents tests from running new application instances on the same JVM thread (JavaFX limitation).
     * @throws InterruptedException Timeout has been reached, see AWAIT_TIMEOUT_SEC.
     */
    public static void deInitialize() throws InterruptedException
    {
        if (mainInstance != null)
        {
            waitForRunLater(mainInstance::shutdown);

            // mainInstance = null;
        }
    }

    /**
     * Execute a Runnable on the JavaFX thread and wait until it has finished.
     * Used to circumvent the error "java.lang.IllegalStatException - Not on FX application thread”, when running code that interacts with JavaFX outside of its assigned UI thread.
     * @param runnable The Runnable to be executed on the JavaFX thread.
     * @throws InterruptedException Timeout has been reached, see AWAIT_TIMEOUT_SEC.
     */
    public static void waitForRunLater(Runnable runnable) throws InterruptedException {
        AtomicReference<CountDownLatch> runLaterLatch = new AtomicReference<>();
        runLaterLatch.set(new CountDownLatch(1));

        Platform.runLater(() ->{
            assertTrue(Platform.isFxApplicationThread());

            runnable.run();
            runLaterLatch.get().countDown();
        });

        assertTrue(runLaterLatch.get().await(AWAIT_TIMEOUT_SEC, TimeUnit.SECONDS));
    }

    /**
     * @return The current instance of the Main class. Null if initialize() has not been called.
     */
    public static Main getMainInstance()
    {
        return mainInstance;
    }

    /**
     * Clears all entities in the world
     */
    public static void clearAllEntities() {
        // A separate list for entities was used to avoid ConcurrentModification exception.
        List entities = new ArrayList<Entity>();        // List with all entities.

        // Add each and every entity from the game world to the list.
        for(Entity e : getGameWorld().getEntities()) {
            entities.add(e);
        }
        getGameWorld().removeEntities(entities);     // Remove all existing entities from the world.
    }
}
