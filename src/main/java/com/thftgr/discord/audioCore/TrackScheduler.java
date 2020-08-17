package com.thftgr.discord.audioCore;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class TrackScheduler {

    public TrackScheduler(AudioPlayer player) {
    }

    public void onPlayerPause(AudioPlayer player) {
        // Player was paused
    }

    public void onPlayerResume(AudioPlayer player) {
        // Player was resumed
    }

    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        // A track started playing
    }

    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            // Start next track
        }
        
    }

    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        // An already playing track threw an exception (track end event will still be received separately)
    }

    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        // Audio track has been unable to provide us any audio, might want to just start a new track
    }

}
