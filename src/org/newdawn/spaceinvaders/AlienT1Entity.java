package org.newdawn.spaceinvaders;

public class AlienT1Entity extends AlienEntity {

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
	public AlienT1Entity(int vida, double moveSpeed, Game game, int x, int y) {
		super(vida, moveSpeed, game, "sprites/alien1.png", x, y);
	}

	public AlienT1Entity(Game game, int x, int y) {
		super(100, 75, game, "sprites/alien1.png", x, y);
	}
}