package game.blocks;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * For blocks that aren't empty but should have different collision stuff.
 *
 * @author s-weia
 *
 */
public class LiquidBlock extends Block {
	private Shape hitbox;

	protected LiquidBlock(BlockType t, float xpos, float ypos) {
		super(t, t.sx, t.sy, xpos, ypos);

		// change size later
		hitbox = new Rectangle(super.getPos().x, super.getPos().y, 1, 1);
	}

	@Override
	public Shape getHitbox() {
		return this.hitbox;
	}
}
