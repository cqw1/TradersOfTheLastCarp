package com.totlc.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.HashMap;

/**
 *  Wrapper class for managing and switching between streaming music tracks for the game.
 *  Tracks by name/usage:
 *      test0
 *      test1
 *      test2
 */
public class MusicPlayer {

    private static int NUM_TRACKS;
    private HashMap<String, String> trackListByName;
    private HashMap<Integer, String> trackListByNumber;

    public Music getCurrentSong() {
        return currentSong;
    }

    private Music currentSong = null;
    private int currentTrackNumber = -1;

    // Tracklist pathnames as a hashmap.

    public MusicPlayer(){
        trackListByName = new HashMap<String, String>(NUM_TRACKS);
        trackListByNumber = new HashMap<Integer, String>(NUM_TRACKS);
        // Add paths to audio files.
        trackListByName.put("test0", "music/Misc6.mp3");
        trackListByName.put("test1", "music/Misc9.mp3");
        trackListByName.put("test2", "music/Dungeon5.mp3");
        trackListByName.put("test3", "music/HiveSemaphore1.mp3");
        trackListByName.put("test4", "music/Tremble.mp3");
        trackListByName.put("test5", "music/Shop1.mp3");

        int count = 0;
        for (String name : trackListByName.keySet()){
            trackListByNumber.put(count, trackListByName.get(name));
            count++;
        }
    }

    // Sets the current song.
    public int setSong(String name){
        currentSong = Gdx.audio.newMusic(Gdx.files.internal(trackListByName.get(name)));
        currentSong.setLooping(true);
        return 0;
    }

    // Load and lay a non-tracklist song.
    public void playSong(String pathname){
        if (currentSong != null){
            currentSong = Gdx.audio.newMusic(Gdx.files.internal(pathname));
        }
    }

    // Play next song.
    public void playNext(){
        if (currentSong == null || currentTrackNumber == -1 || currentTrackNumber == trackListByNumber.size() - 1){
            currentSong = Gdx.audio.newMusic(Gdx.files.internal(trackListByNumber.get(0)));
            currentTrackNumber = 0;
        } else {
            currentSong = Gdx.audio.newMusic(Gdx.files.internal(trackListByNumber.get(currentTrackNumber++)));
            currentTrackNumber++;
        }
    }

    // Play previous song.
    public void playPrevious(){
        if (currentSong == null || currentTrackNumber == -1 || currentTrackNumber == 0){
            currentSong = Gdx.audio.newMusic(Gdx.files.internal(trackListByNumber.get(trackListByNumber.size() - 1)));
            currentTrackNumber = trackListByNumber.size() - 1;
        } else {
            currentSong = Gdx.audio.newMusic(Gdx.files.internal(trackListByNumber.get(currentTrackNumber--)));
            currentTrackNumber--;
        }
    }

    // Resumes the current song.
    public void play(){
        if (currentSong != null){
            currentSong.play();
        }
    }

    // Pauses the current song.
    public void pause(){
        if (currentSong != null){
            currentSong.pause();
        }
    }

    // Stops the current song.
    public void stop(){
        if (currentSong != null){
            currentSong.stop();
        }
    }

    // Clean up and dispose of player. Called when game closes.
    public void dispose(){
        currentSong.dispose();
    }
}
