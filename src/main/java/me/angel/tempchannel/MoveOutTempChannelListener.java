package me.angel.tempchannel;

import me.angel.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Random;

public class MoveOutTempChannelListener extends ListenerAdapter {

    private Main plugin;
    public MoveOutTempChannelListener(Main plugin) { this.plugin = plugin; }

    private String[] colours =  new String[] { "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff" };

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent e){

        VoiceChannel joinedChannel = e.getChannelJoined();
        VoiceChannel leftChannel = e.getChannelLeft();

        if(leftChannel.getIdLong() == plugin.tempchannels.get(e.getMember())){
            if(leftChannel.getMembers().size() <= 0) {
                TextChannel textChannel = e.getGuild().getTextChannelById(plugin.TEMP_CHANNEL);

                //Colour for Embed
                Random rand = new Random();
                int i = rand.nextInt(colours.length);
                String colour = colours[i];
                EmbedBuilder textChannelEmbed = new EmbedBuilder()
                        .setTitle("**Löschung des Channels**")
                        .setDescription("**Hey " + e.getMember().getAsMention() + "\nDein privater Channel wurde gelöscht!\n" +
                                "Du kannst dir einen neuen erstellen lassen, indem du in Join-Channel joinst.**")
                        .setThumbnail(e.getMember().getUser().getAvatarUrl())
                        .setColor(Color.decode("0x"+colour))
                        .setFooter("Bot created by Marius")
                        .setTimestamp(LocalDateTime.now(Clock.systemUTC()));

                textChannel.sendMessage(textChannelEmbed.build()).queue((v) -> {
                    textChannelEmbed.clear();
                    leftChannel.delete().queue();
                    plugin.tempchannels.remove(e.getMember());
                });
            }
        }
    }
}
