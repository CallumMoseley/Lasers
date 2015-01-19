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
	
	public static void startMusic()
	{
		music.loop();
	}
	
	public static void stopMusic()
	{
		music.stop();
	}
	
	public static void startLaser()
	{
		if (!mute)
		{
			laser.play();
			laserContinuous.loop();
			laserRunning = true;
		}
	}
	
	public static void stopLaser()
	{
		laserContinuous.stop();
		laserRunning = false;
	}
	
	public static void toggleMute()
	{
		mute = !mute;
		if (mute)
		{
			music.stop();
			laserContinuous.stop();
			laser.stop();
		}
		else
		{
			startMusic();
			if (laserRunning)
			{
				laserContinuous.loop();
			}
		}
	}
}