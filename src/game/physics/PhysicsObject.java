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
		this.pos.add(this.vel.copy().scale(frametime));
		this.vel.add(this.getaccel().copy().scale(frametime));
		this.getHitbox().setCenterX(this.pos.x);
		this.getHitbox().setCenterY(this.pos.y);
	}
	public void addForce(Force f) {
		forcelist.add(f);
		this.updateAccel = true;
	}
	public static void checkCollision(PhysicsObject p1, PhysicsObject p2) {
		if(!p1.hitbox.intersects(p2.hitbox)) {
			//return;
		}
		boolean collides = false;
		LinkedList<Vector2f> collpoints = new LinkedList<Vector2f>();
		LinkedList<Vector2f> normals = new LinkedList<Vector2f>();
		float maxdist = 0;
		for(int p1index = 0; p1index < p1.hitbox.getPointCount(); p1index++) {
			float[] point0 = p1.hitbox.getPoint(p1index);
			Vector2f point1 = new Vector2f(point0[0], point0[1]);
			point1.add(p1.prevpos.copy().sub(p1.pos));
			Line l = new Line(new Vector2f(point0[0],point0[1]), point1);
			for(int p2index = 0; p2index < p2.hitbox.getPointCount(); p2index++) {
				float[] vertex0 = p2.hitbox.getPoint(p2index);
				float[] vertex1 = p2.hitbox.getPoint((p2index + 1) % p2.hitbox.getPointCount());
				Line side = new Line(new Vector2f(vertex0[0], vertex0[1]), new Vector2f(vertex1[0], vertex1[1]));
				Vector2f collpos = l.intersect(side);
				if(collpos != null && l.on(collpos)) {
					collpoints.add(collpos);
					collides = true;
					Vector2f vector = new Vector2f(side.getDX(), side.getDY());
					Vector2f normal = vector.getPerpendicular();
					normals.add(normal);
					float dist = collpos.copy().sub(new Vector2f(point0[0], point0[1])).length();
					System.out.println("Point: " + point0[0] + ", " + point0[1]);
					if(dist > maxdist) {
						maxdist = dist;
					}
				}
			}
		}
		
		Vector2f movementvec = p1.prevpos.copy().sub(p1.pos).normalise().scale(maxdist);
		p1.pos.add(movementvec);
		
		for(int p2index = 0; p2index < p2.hitbox.getPointCount(); p2index++) {
			Line l = new Line(p2.prevpos, p2.pos);
		}
		if(collides) {
			System.out.println("COLLISION");
		}
	}
}
