package game.physics;

import org.newdawn.slick.geom.Vector2f;

public class PhysicsObject {
	public Vector2f pos;
	public Vector2f vel;
	private Vector2f accel;
	
	public PhysicsObject(Vector2f pos) {
		this();
		this.pos.set(pos);
	}
	public PhysicsObject() {
		this.pos = new Vector2f(0, 0);
		this.vel = new Vector2f(0, 0);
		this.accel = new Vector2f(0, 0);
	}
	public Vector2f getaccel() {
		return this.accel;
	}
}
