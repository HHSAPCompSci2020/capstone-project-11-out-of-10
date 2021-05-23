package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;


/**
 * Represents the sound effects of game
 * @author Timothy Li
 *
 */
public class Sound implements JayLayerListener
{

	private JayLayer sound;
	private boolean first;

	/**
	 * Constructs different sounds that can be made
	 */
	public Sound () {

		first = true;
		//String[] soundEffects = new String[]{"eat.mp3","title2.mp3","title3.mp3","title4.mp3","title5.mp3"};
		String[] song1 = new String[]{"eat.mp3"};
		String[] song2 = new String[] {"reproduce.mp3"};
		String[] song3 = new String[] {"empty.mp3"};


		sound=new JayLayer("audio/","audio/",false);
		sound.addPlayList();
		sound.addPlayList();
		sound.addPlayList();
		sound.addSongs(0,song1);
		sound.addSongs(1,song2);
		sound.addSongs(2, song3);
		//sound.addSoundEffects(soundEffects);
		sound.changePlayList(0);
		sound.addJayLayerListener(this);

		// TODO Add more customizations to the panel

	}


	/**
	 * Makes a chomping noise, much like an animal eating
	 */
	public void makeEatSound() {
		sound.changePlayList(0);
		sound.nextSong();
	}

	/**
	 * Makes click noise
	 */
	public void makeReproduceSound() {
		sound.changePlayList(1);
		sound.nextSong();
	}

	@Override
	public void songEnded() {
	}


	@Override
	public void playlistEnded() {
	}


	@Override
	public void musicStarted() {
		
	}


	@Override
	public void musicStopped() {
		if(!first)
			sound.changePlayList(2);
		else {
			first = false;
		}
	}
	
	
	


}
