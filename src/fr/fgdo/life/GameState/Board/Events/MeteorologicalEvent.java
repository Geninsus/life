/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fgdo.life.GameState.Board.Events;

import fr.fgdo.life.Creature.Creature;
import fr.fgdo.life.GameObject.GameObject;
import fr.fgdo.life.GameState.Board.Board;
import fr.fgdo.life.Life;
import fr.fgdo.math.Point;
import java.awt.Color;
import java.util.Observable;

/**
 *
 * @author olivbau
 */
public class MeteorologicalEvent extends GameObject{
    private final MeteorologicalEventsTypes type;
    private int duration = Life.rand.nextInt(400)+100;
    private MeteorologicalEventListener listener;

    public MeteorologicalEvent(MeteorologicalEventsTypes type, MeteorologicalEventListener listener) {
        this.type = type;
        this.listener = listener;
        this.color = new Color((float)1.0,(float)0.0,(float)0.0,(float)0.5);
        radius = Life.rand.nextInt(100)+100;
        center = new Point<>(0,0);
    }
    
    public MeteorologicalEvent(MeteorologicalEventsTypes type, int maxX, int maxY, MeteorologicalEventListener listener) {
        this.type = type;
        this.listener = listener;
        this.color = new Color((float)1.0,(float)0.0,(float)0.0,(float)0.5);
        radius = Life.rand.nextInt(30)+30;
        center = new Point<>(Life.rand.nextInt(maxX),Life.rand.nextInt(maxY));
    }


    public MeteorologicalEvent(int radius, Point<Integer> center, MeteorologicalEventsTypes type, MeteorologicalEventListener listener) {
        this.radius = radius;
        this.listener = listener;
        this.color = new Color((float)1.0,(float)0.0,(float)0.0,(float)0.5);
        this.center = center;
        this.type = type;
    }

    public MeteorologicalEventsTypes getType() {
        return type;
    }
    
    public void update() {
        duration--;
        if (duration == 0) {
            listener.meteorologicalEventOver(this);
        }
    }

    public void checkCreature(Creature creature) {
        if ( (creature.getCenter().x - center.x)*(creature.getCenter().x - center.x) + (creature.getCenter().y - center.y)*(creature.getCenter().y - center.y) < radius*radius) {
            creature.removeLife(1);
        }
    }
}
