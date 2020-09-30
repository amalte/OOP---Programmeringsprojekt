package edu.chalmers.model.building;

import edu.chalmers.utils.Coords;

import java.util.*;

public class MapManager {
    private HashMap<Coords, IBlock> blockMap;

    public MapManager(HashMap<Coords, IBlock> blockMap) {
        this.blockMap = blockMap;
    }


    private HashSet<Coords> getAllLevitatingTilesOnMap() {
        HashSet<Coords> levitatingTiles = new HashSet<>();

        for (Coords tile : blockMap.keySet()) {
            if(!levitatingTiles.contains(tile) && isTileLevitatingDFS(tile)) {
                levitatingTiles.addAll(getConnectedTiles(tile));
            }
        }

        for (Coords coords: levitatingTiles) {
            System.out.println("Ye boi LMAO " + coords.x());
        }

        return levitatingTiles;
    }

    /**
     * Method that removes all neighbour blocks that are levitating (not connected to a permanent block)
     * @param tile all neighbours of this tile will be removed if they are levitating
     */
    public void removeLevitatingNeighbours(Coords tile) {   // Method should be called when any block on the map has been removed

        List<Coords> popNeighbours = getPopulatedNeighbourTiles(tile);
        HashSet<Coords> levitatingTiles = new HashSet<>();  // Tiles that are levitating and will be removed

        for(int i = 0; i < popNeighbours.size(); i++) {
            Coords tileToCheck = popNeighbours.get(i);

            if(isTileLevitatingDFS(tileToCheck)) {
                levitatingTiles.addAll(getConnectedTiles(tileToCheck));     // Add all connected tiles of the levitating tile (they are also levitating)
            }
        }

        for (Coords levitatingTile : levitatingTiles) {    // Removes all levitating blocks
            blockMap.get(levitatingTile).remove();
            removeBlockFromMap(levitatingTile);
        }
    }

    private boolean isTileLevitatingDFS(Coords tile) {
        if(blockMap.get(tile) == null) return true;

        return isTileLevitatingDFS(tile, new HashSet<>());
    }

    // Method checks if a tile with a block on it in any way is connected to a permanent block
    private boolean isTileLevitatingDFS(Coords tile, HashSet<Coords> visitedTiles) {
        if(!blockMap.get(tile).canBeDestroyed()) {   // Found a path if the block is indestructible
            return false;
        }
        visitedTiles.add(tile);

        List<Coords> popNeighbours = getPopulatedNeighbourTiles(tile);
        for (Coords neighbour : popNeighbours) {
            if(!visitedTiles.contains(neighbour)) {   // Check tile if neighbour is not already visited
                if(!isTileLevitatingDFS(neighbour, visitedTiles)) {
                    return false;
                }
            }
        }

        return true; // if no path to a permanent block is found
    }

    /**
     * Method that checks if a tile is empty
     * @param tile the tile to check
     * @return boolean
     */
    public boolean isTileEmpty(Coords tile) {
        return blockMap.get(tile) == null;
    }

    /**
     * Method that checks if a tile has any neighbours
     * @param tile the tile to check
     * @return boolean
     */
    public boolean isTileConnected(Coords tile) {
        return blockMap.containsKey(getTileAbove(tile)) || blockMap.containsKey(getTileRight(tile)) || blockMap.containsKey(getTileBelow(tile)) || blockMap.containsKey(getTileLeft(tile));
        //return !getTileAbove(tile).equals(null) || !getTileRight(tile).equals(null) || !getTileBelow(tile).equals(null) || !getTileLeft(tile).equals(null);
    }

    /**
     * Get method for blockMap
     * @return HashMap<Coords, IBlock> blockMap which connects all positions on the map to a block (if it exists)
     */
    public HashMap<Coords, IBlock> getBlockMap() { return blockMap; }

    public void addBlockToMap(Coords tile, IBlock block) {
        blockMap.putIfAbsent(tile, block);
    }

    public void removeBlockFromMap(Coords tile) {
        blockMap.remove(tile);
    }

    private HashSet<Coords> getConnectedTiles(Coords tile) {
        return getConnectedTiles(tile, new HashSet<>());
    }

    private HashSet<Coords> getConnectedTiles(Coords tile, HashSet<Coords> connectedTiles) {
        connectedTiles.add(tile);

        List<Coords> popNeighbours = getPopulatedNeighbourTiles(tile);
        for (Coords neighbour : popNeighbours) {
            if(!connectedTiles.contains(neighbour)) {  // Check the neighbours of neighbour if it isn't null and it is not already visited
                getConnectedTiles(neighbour, connectedTiles);
            }
        }

        return connectedTiles;
    }

    private List<Coords> getPopulatedNeighbourTiles(Coords tile) {
        List<Coords> populatedNeighbourTiles = new ArrayList<>();

        for (Coords coords : getNeighbourTiles(tile)) {
            if (blockMap.get(coords) != null) populatedNeighbourTiles.add(coords);
        }
        return populatedNeighbourTiles;
    }

    private List<Coords> getNeighbourTiles(Coords tile) {
        return Arrays.asList(getTileAbove(tile), getTileRight(tile), getTileBelow(tile), getTileLeft(tile));
    }

    private Coords getTileAbove(Coords tile) { return new Coords(tile.x(), tile.y()-1); }
    private Coords getTileRight(Coords tile) { return new Coords(tile.x()+1, tile.y()); }
    private Coords getTileBelow(Coords tile) { return new Coords(tile.x(), tile.y()+1); }
    private Coords getTileLeft(Coords tile) { return new Coords(tile.x()-1, tile.y()); }
}
