/**
 * Used for statically controlling all game sounds from anywhere
 */

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class AudioHandler
{
	private static AudioClip music;
	private static AudioClip laser;
	private static AudioClip laserContinuous;
	private static boolean mute;
	private static boolean laserRunning;
	
	/**
	 * Load all static sound resources
	 */
	public static void loadAudio()
	{
		try
		{
			music = Applet.newAudioClip(new URL("file:" + System.getProperty("user.dir") + "/sound/Pamgaea.wav"));
			laser = Applet.newAudioClip(new URL("file:" + System.getProperty("user.dir") + "/sound/laser.wav"));
			laserContinuous = Applet.newAudioClip(new URL("file:" + System.getProperty("user.dir") + "/sound/laser_continuous.wav"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts the music looping
	 */
	public static void startMusic()
	{
		music.loop();
	}
	
	/**
	 * Stops the music
	 */
	public static void stopMusic()
	{
		music.stop();
	}
	
	/**
	 * Plays the laser sound, and starts the continuous sound looping
	 */
	public static void startLaser()
	{
		if (!mute)
		{
			laser.play();
			laserContinuous.loop();
			laserRunning = true;
		}
	}
	
	/**
	 * Stops the continuous laser sound
	 */
	public static void stopLaser()
	{
		laserContinuous.stop();
		laserRunning = false;
	}
	
	/**
	 * Toggles whether the game is muted or not right now
	 */
	public static void toggleMute()
	{
		mute = !mute;
		if (mute)
		{
			// Stop all sounds
			music.stop();
			laserContinuous.stop();
			laser.stop();
		}
		else
		{
			// Start sounds
			startMusic();
			if (laserRunning)
			{
				laserContinuous.loop();
			}
		}
	}
}