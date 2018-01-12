package game.physics;

import java.util.LinkedList;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class PhysicsObject {
	public Vector2f pos;
	public Vector2f prevpos;
	public Vector2f vel;
	private Vector2f accel;
	boolean updateAccel;
	LinkedList<Force> forcelist;
	private Shape hitbox;
	
	public PhysicsObject(Vector2f pos) {
		this();
		this.pos.set(pos);
	}
	public PhysicsObject() {
		this.pos = new Vector2f(0, 0);
		this.prevpos = pos.copy();
		this.vel = new Vector2f(0, 0);
		this.accel = new Vector2f(0, 0);
		this.updateAccel = false;
		this.forcelist = new LinkedList<Force>();
	}
	public Vector2f getaccel() {
		return this.accel;
	}
	public Shape getHitbox() {
		return this.hitbox;
	}
	public void setHitbox(Shape s) {
		this.hitbox = s;
	}
	public void updatePhysics(float frametime) {
		for(Force f : this.forcelist) {
			f.update(frametime);
		}
		if(this.updateAccel) {
			accel.set(0, 0);
			for(Force f : this.forcelist) {
				accel.add(f.vec);
			}
		}
		this.prevpos = pos.copy();
		this.pos.add(this.vel.scale(frametime));
		this.vel.add(this.getaccel().scale(frametime));
		this.getHitbox().setCenterX(this.pos.x);
		this.getHitbox().setCenterY(this.pos.y);
	}
	public void addForce(Force f) {
		forcelist.add(f);
		this.updateAccel = true;
	}
	public static void checkCollision(PhysicsObject p1, PhysicsObject p2) {
		if(!p1.hitbox.intersects(p2.hitbox)) {
			return;
		}
		LinkedList<Vector2f> collpoints = new LinkedList<Vector2f>();
		for(int p1index = 0; p1index < p1.hitbox.getPointCount(); p1index++) {
			Line l = new Line(p1.prevpos, p1.pos);
			
		}
		for(int p2index = 0; p2index < p1.hitbox.getPointCount(); p2index++) {
			Line l = new Line(p2.prevpos, p2.pos);
		}
	}
}
