package com.thftgr.discord.audioCore;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nonnull;

public class audioEventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        if(event.getAuthor().isBot() ) return;
        if(event.getMessage().getContentRaw().equals("!play")){
            Guild guild = event.getGuild();
            AudioManager audioManager = guild.getAudioManager();

            AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(playerManager);

            AudioPlayer player = playerManager.createPlayer();
            TrackScheduler trackScheduler = new TrackScheduler(player);
            player.addListener(trackScheduler);
            audioManager.setSendingHandler(new AudioPlayerSendHandler(player));
            //audioManager.openAudioConnection(guild.getMember(event.getAuthor()).getVoiceState().getChannel());

        }




    }
}
