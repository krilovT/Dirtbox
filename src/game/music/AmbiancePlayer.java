package game.music;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.Dirtbox;
import game.MainGameState;
import game.Viewport;

public class AmbiancePlayer extends Thread {
	AmbiancePlayer() {
	}

	File daymusic = new File("data/music/bossintro.wav");

	public void playsound(File soundFile) {
		try {
			Clip audioclip = MusicPlayer.PlayFile(soundFile);
			audioclip.start();
			for (int i = 0; i < MusicPlayer.SongLength(soundFile)
					* 1000.0; i += 1000.0
							/ Dirtbox.DEFAULT_FRAME_RATE) {
				if (!MainGameState.inGame) {
					audioclip.stop();
					break;
				}
				Thread.sleep(
						(long) (1000.0 / Dirtbox.DEFAULT_FRAME_RATE));
			}
			audioclip.stop();
		} catch (InterruptedException | UnsupportedAudioFileException
				| IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			if (Viewport.day) {
				playsound(daymusic);
				if (!MainGameState.inGame) {
					break;
				}
			}
		}
	}
}
