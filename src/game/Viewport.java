package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import game.utils.Chat;
import game.world.World;

/**
 * Handles all drawing in the game. Does not, and should not handle ui drawing
 *
 * Useful because it allows the position of the "camera" (viewport) to move
 * around
 */
public class Viewport {
	private final float MIN_ZOOM = 13;
	private final float MAX_ZOOM = 75;

	private Graphics graphics;
	private Vector2f center = new Vector2f(); // in game units
	private Vector2f screenDimensions = new Vector2f(); // in pixels
	private float scaleFactor = 2.5f;
	private Vector2f movement = new Vector2f();

	private boolean resetTransformCache = true;
	private Transform cacheTransform;

	/**
	 * Expected range: 0.0 - 1.0
	 */
	public static float gamma = 0.0f;
	public static long globaltimer = 0;
	static long timerupdate = 0;

	public static boolean day = true;
	public static boolean DEBUG_MODE = false;

	public final Chat chat = new Chat();

	public Viewport() {
	}

	public Viewport(Graphics g) {
		this.graphics = g;
	}

	public void draw(Sprite s) {
		Transform t = getDrawTransform();

		Vector2f res = t.transform(s.loc.copy());
		// Cheap hack? removes a Math.ceil
		int nw = (int) (0.999999 + s.img.getWidth() * scaleFactor);
		int nh = (int) (0.999999 + s.img.getHeight() * scaleFactor);
		graphics.drawImage(s.getCachedImage(nw, nh), (int) res.x, (int) res.y);
	}

	public void draw(Shape s, Color c) {
		graphics.setColor(c);
		graphics.draw(s.transform(getDrawTransform()));
	}

	public void draw(String s, int x, int y, Color c) {
		graphics.setColor(c);
		graphics.drawString(s, x, y);
	}

	public void fill(Shape s, Color c) {
		graphics.setColor(c);
		graphics.fill(s.transform(getDrawTransform()));
	}

	public void printDebugInfo() {
		if (DEBUG_MODE) {
			System.out.println("Debug button pressed, debug mode OFF");
		} else {
			System.out.println("Debug button pressed, debug mode ON");
		}
		DEBUG_MODE = !DEBUG_MODE;
	}

	public void setGraphics(Graphics g) {
		this.graphics = g;
	}

	public void update(int delta) {
		globaltimer += System.currentTimeMillis() - timerupdate;
		double darknessvalue = 0.6 + Math
				.sin(2.0 * Math.PI * globaltimer
						/ World.DAY_NIGHT_DURATION)
				* 0.4;
		Color BackgroundColor = new Color((int) (darknessvalue * 0),
				(int) (darknessvalue * 127), (int) (darknessvalue * 255));
		graphics.setBackground(BackgroundColor);
		center.add(movement.copy().scale(delta / scaleFactor));
		if (day && globaltimer % World.DAY_NIGHT_DURATION > World.DAY_NIGHT_DURATION
				/ 2) {
			day = false;
		} else if (!day
				&& !(globaltimer % World.DAY_NIGHT_DURATION > World.DAY_NIGHT_DURATION)) {
			day = true;
		}
		resetTransformCache = true;
		timerupdate = System.currentTimeMillis();
	}

	public void renderChat() {
		int chatlength = Chat.chat.size();
		for (int i = 1; i <= 10; i++) {
			if (i <= chatlength
					&& (System.currentTimeMillis()
							- Chat.timeofmessage.get(chatlength - i) <= 5000
							|| Chat.displaychat)) {
				draw(Chat.chat.get(chatlength - i), 5,
						(int) screenDimensions.getY() - 25 - 20 * i, Color.white);
			}
		}
		draw(chat.getMessage(), 5, (int) screenDimensions.getY() - 25, Color.white);
	}

	public void setScreenCenter(Vector2f center) {
		screenDimensions.set(center.copy().scale(2f));

		resetTransformCache = true;
	}

	public Shape getViewShape() {
		return new Rectangle(0, 0, screenDimensions.x, screenDimensions.y);
	}

	public Shape getGameViewShape() {
		return getViewShape().transform(getInverseDrawTransform());
	}

	/**
	 * Note that this method implicitly depends on getInverseDrawTransform (if this
	 * method is changed, likely so should getInverseDrawTransform).
	 *
	 * @return transform mapping game position to screen position
	 */
	public Transform getDrawTransform() {
		Transform ret = cacheTransform;
		if (resetTransformCache) {
			ret = new Transform();

			// Note that the transforms are applied in reverse order
			// e.g. the first concatenated transform is applied last
			Transform[] trans = new Transform[] {
					Transform.createTranslateTransform(screenDimensions.x / 2,
							screenDimensions.y / 2),
					Transform.createScaleTransform(scaleFactor, scaleFactor),
					Transform.createTranslateTransform(-center.x, -center.y) };

			for (Transform ts : trans) {
				ret.concatenate(ts);
			}

			resetTransformCache = false;
			cacheTransform = ret;
		}
		return ret;
	}

	/**
	 * Note that this method implicitly depends on getDrawTransform (if this method
	 * is changed, likely so should getDrawTransform)
	 *
	 * @return transform mapping screen position to game position
	 */
	public Transform getInverseDrawTransform() {
		Transform t = new Transform();

		// Inverted order and transformation of getDrawTransform
		Transform[] trans = new Transform[] {
				Transform.createTranslateTransform(-screenDimensions.x / 2,
						-screenDimensions.y / 2),
				Transform.createScaleTransform(1f / scaleFactor, 1f / scaleFactor),
				Transform.createTranslateTransform(center.x, center.y) };

		for (int i = trans.length - 1; i >= 0; i--) {
			t.concatenate(trans[i]);
		}
		return t;
	}

	public void zoom(float factor) {
		setZoom(scaleFactor * factor);
	}

	public void setZoom(float factor) {
		scaleFactor = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, factor));
	}

	public void setCenter(Vector2f center) {
		this.center.set(center);
	}

	public Vector2f getCenter() {
		return center.copy();
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public void move(Vector2f displacement) {
		movement.set(displacement);
	}
}
