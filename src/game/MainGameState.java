package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import game.entities.Entity;
import game.physics.PhysicsObject;

public class MainGameState implements DefaultGameState {
	private World world = new World();
	private Viewport vp = new Viewport();
	Entity box1, box2;

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.getInput().addKeyListener(vp);
		box1 = new Entity(new Image("data/characters/stalin.jpg"), 1, 1, new Vector2f(0, 0));
		box2 = new Entity(new Image("data/characters/stalin.jpg"), 1, 1, new Vector2f(1000, 2));
		box1.vel.add(new Vector2f(1, 0));
		box2.vel.add(new Vector2f(0, 0));
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		vp.setScreenCenter(new Vector2f(gc.getWidth()/2, gc.getHeight()/2));
	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1) throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		vp.setGraphics(g);
		world.draw(vp);
		box1.draw(vp);
		box2.draw(vp);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		vp.update(delta);
		box1.updatePhysics(delta/10f);
		box2.updatePhysics(delta/10f);
		PhysicsObject.checkCollision(box1, box2);
		//PhysicsObject.checkCollision(box2, box1);
	}

	@Override
	public int getID() {
		return 0;
	}
}