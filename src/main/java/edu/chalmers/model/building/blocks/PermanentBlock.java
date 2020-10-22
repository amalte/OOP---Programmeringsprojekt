package edu.chalmers.model.building.blocks;

import edu.chalmers.model.building.IBlock;

/**
 * @author Malte Åkvist
 * <p>
 * PermanentBlock that can't be removed (platforms in the game)
 */
public class PermanentBlock implements IBlock {

    /**
     * Method to check if object is destroyable
     *
     * @return boolean
     */
    @Override
    public boolean canBeDestroyed() {
        return false;
    }

    /**
     * Remove entity from world
     */
    @Override
    public void remove() {
        // Cant be removed
    }
}
