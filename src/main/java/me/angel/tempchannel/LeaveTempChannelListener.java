package me.angel.tempchannel;

import me.angel.main.Main;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LeaveTempChannelListener extends ListenerAdapter {

    private Main plugin;
    public LeaveTempChannelListener(Main plugin) { this.plugin = plugin; }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent e){
        VoiceChannel voiceChannel = e.getChannelLeft();
        /*
        if(voiceChannel.getMembers().size() <= 0){
            if(plugin.tempchannels.contains(voiceChannel.getIdLong())){
                plugin.tempchannels.remove(voiceChannel.getIdLong());
                voiceChannel.delete().queue();
            }
        } */
        if(voiceChannel.getMembers().size() == 0){
            if(plugin.tempchannels.contains(voiceChannel.getIdLong())) {
                plugin.tempchannels.remove(voiceChannel.getIdLong());
                voiceChannel.delete().queue();
            }
        }
        onLeave(e.getChannelLeft());
    }

    private void onLeave(VoiceChannel channel) {
        if(channel.getMembers().size() <= 0){
            if(plugin.tempchannels.contains(channel.getIdLong())){
                plugin.tempchannels.remove(channel.getIdLong());
                channel.delete().queue();
            }
        }
    }
}
