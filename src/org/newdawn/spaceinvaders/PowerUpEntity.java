package org.newdawn.spaceinvaders;

public class PowerUpEntity extends Entity {
	private double moveSpeed = 250;
	private Game game;

	public PowerUpEntity(Game game,String ref,int x,int y) {
		super(ref,x,y);
		this.moveSpeed = moveSpeed;
		this.game = game;
		dx = -moveSpeed;
	}

	public void move(long delta) {
		if ((dx < 0) && (x < 10)) {
			game.updatePowerLogic();
		}
		if ((dx > 0) && (x > 750)) {
			game.updatePowerLogic();
		}
		super.move(delta);
	}

	public void doLogic() {
		dx = -dx;
		y += 10;
		if (y > 570) {
			game.removeEntity(this);
		}
	}

	public void collidedWith(Entity other) {
	}
}