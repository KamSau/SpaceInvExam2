package org.newdawn.spaceinvaders;

/**
 * An entity which represents one of our space invader aliens.
 * 
 * @author Kevin Glass
 */
public class AlienT2Entity extends AlienEntity {

	/**
	 * Create a new alien entity
	 *
	 * @param vida      Vida del alien
	 * @param moveSpeed Velocidad a la que se movera el alien.
	 * @param game      The game in which this entity is being created
	 * @param ref       The sprite which should be displayed for this alien
	 * @param x         The intial x location of this alien
	 * @param y         The intial y location of this alient
	 */
	public AlienT2Entity(int vida, double moveSpeed, Game game, int x, int y) {
		super(vida, moveSpeed, game, "sprites/alien2.png", x, y);
	}

	public AlienT2Entity(Game game, int x, int y) {
		super(200, 85, game, "sprites/alien2.png", x, y);
	}
}