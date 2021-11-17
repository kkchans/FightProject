import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound { 
	public static Clip clip;
	
	public static void Play(String fileName) {
		//음악 재생 메서드
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
			clip = AudioSystem.getClip();
			clip.stop();
			clip.open(ais);
			clip.start();
		}catch(Exception e) {}
	}
	
	public static void PlayStop() {
		//음악 멈춤 메서드
		clip.stop();
	
	}
	
	public static void fistBgm() {
		try {
			AudioInputStream fist_bgm = AudioSystem.getAudioInputStream(new File("./bgm/fist_bgm.wav"));
			Clip fist_clip = AudioSystem.getClip();
			fist_clip.stop();
			fist_clip.open(fist_bgm);
			fist_clip.start();
		}
		catch (LineUnavailableException e) {e.printStackTrace();}
		catch (IOException e) { e.printStackTrace(); }
		catch (UnsupportedAudioFileException e) { e.printStackTrace(); }

	}
	
}
