package game.physics;

import org.newdawn.slick.geom.Vector2f;

public class Force {
	PhysicsObject creator, victim;
	Vector2f f;
	public Force(Vector2f f) {
		this.f = f.copy();
	}
}
