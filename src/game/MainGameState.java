package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainGameState extends DefaultGameState {
	private World world = new World();
	private Viewport vp = new Viewport();

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.getInput().addKeyListener(vp);
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1) throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		vp.setGraphics(g);
		world.draw(vp);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		vp.update(delta);
	}

	@Override
	public int getID() {
		return 0;
	}
}