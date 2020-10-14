package edu.chalmers.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import edu.chalmers.model.building.BuildManager;
import edu.chalmers.model.building.MapManager;
import edu.chalmers.model.wave.WaveManager;
import edu.chalmers.services.TileMap;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;


/**
 * Aggregate root class for the game.
 */
public class GenericPlatformer {

    private Entity player;
    private GameWorldFactory gameWorldFactory;
    private WaveManager waveManager;
    private MapManager mapManager;
    private BuildManager buildManager;
    private CollisionDetection collisionDetection;

    public GenericPlatformer()
    {
        this.gameWorldFactory = new GameWorldFactory();
        getGameWorld().addEntityFactory(this.gameWorldFactory);
    }

    public void initializeGame(String levelName) {
        setLevelFromMap(levelName);

        this.collisionDetection = new CollisionDetection(getPlayerComponent());
        this.mapManager = new MapManager(new TileMap().getBlockMapFromLevel(levelName));
        this.buildManager = new BuildManager(getPlayerComponent().getBuildRangeTiles(), mapManager);
        this.waveManager = new WaveManager(getPlayer());
        waveManager.generateNewWave();
    }

    public void remove()
    {
        this.waveManager.stopWaveTimer();
        getGameWorld().reset();
        getGameWorld().removeEntities(getGameWorld().getEntities());
        getGameWorld().removeEntityFactory(this.gameWorldFactory);
    }

    /**
     * Get method that creates a new player if no player is already created.
     * @return A player object.
     */
    public Entity getPlayer() {
        if(player == null || player.getComponents().size() == 0){
            createPlayer();
        }
        return player;
    }

    /**
     * Get method that gets the playerComponent of player
     * @return A player component.
     */
    public PlayerComponent getPlayerComponent() {
        return getPlayer().getComponent(PlayerComponent.class);
    }

    /**
     * Get method for buildManager.
     * @return buildManager.
     */
    public BuildManager getBuildManager(){
        return buildManager;
    }

    /**
     * Get method for gameWorldFactory.
     * @return gameWorldFactory.
     */
    public GameWorldFactory getGameWorldFactory(){
        return gameWorldFactory;
    }

    /**
     * Creates a player at position 0,0.
     */
    private void createPlayer() {
        Point2D spawnPoint = getGameWorld().getEntitiesByType(EntityType.PLAYERSPAWNPOINT).get(0).getPosition();
        player = spawn("player", spawnPoint.getX(), spawnPoint.getY());
    }

    /**
     * Get method for waveManager.
     * @return waveManager.
     */
    public WaveManager getWaveManager(){
        return waveManager;
    }
}
