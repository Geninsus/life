/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.newGame;

/**
 *
 * @author Olivier
 */
public class NewGame {
    private int mapWidth;
    private int mapHeight;
    private String mapName;

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public String getMapName() {
        return mapName;
    }
    
    
}
