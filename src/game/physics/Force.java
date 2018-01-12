package game.physics;

import org.newdawn.slick.geom.Vector2f;
public class Force {
	ForceType type;
	PhysicsObject creator, victim;
	Vector2f vec;
	public Force(Vector2f f, ForceType type, PhysicsObject creator, PhysicsObject victim) {
		this.vec = f.copy();
		this.type = type;
		this.creator = creator;
		this.victim = victim;
	}
	public void update(float frametime) {
		
	}
}
