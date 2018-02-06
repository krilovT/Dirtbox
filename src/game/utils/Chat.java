package game.utils;

import java.util.ArrayList;

import org.newdawn.slick.Input;

public class Chat {
	private String curr = "";
	public ArrayList<String> chat = new ArrayList<>();

	public void chatAddLine(String chatstring) {
		chat.add(chatstring);
	}

	/**
	 *
	 * @param key
	 * @param c
	 * @return Still in chat-mode
	 */
	public boolean keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_ENTER:
			Console.doCommand(curr);
			chatAddLine(curr);
			curr = "";
			return false;
		case Input.KEY_BACK:
			if (curr.length() > 0) {
				curr = curr.substring(0, curr.length() - 1);
			}
			break;
		default:
			if (' ' <= c && c <= '~') {
				curr += c;
			}
			break;
		}
		return true;
	}

	public String getMessage() {
		return curr;
	}
}
