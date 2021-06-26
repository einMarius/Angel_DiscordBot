package me.angel.tempchannel;

import me.angel.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Random;

public class JoinMainChannelListener extends ListenerAdapter {

    private Main plugin;

    public JoinMainChannelListener(Main plugin) { this.plugin = plugin; }

    String[] colours =  new String[] { "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff" };

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event){
        if (event.getMember().getUser().isBot()) return;

        VoiceChannel voiceChannel = event.getChannelJoined();
        TextChannel textChannel = event.getGuild().getTextChannelById(plugin.TEMP_CHANNEL);
        //Colour for Embed
        Random rand = new Random();
        int i = rand.nextInt(colours.length);
        String colour = colours[i];

        if(voiceChannel.getIdLong() == plugin.JOIN_CHANNEL) {
            if (!plugin.tempchannels.containsKey(event.getMember())) {
                Category category = voiceChannel.getParent();

                EmbedBuilder textChannelEmbed = new EmbedBuilder()
                        .setTitle("**Einstellungen des temporären Channels**")
                        .setDescription("**Hey " + event.getMember().getAsMention() + "\nDein privater Channel wurde erstellt und du wurdest hineingemovet!\n" +
                                "Du kannst in diesem Channel sowohl die Permissions, als auch den Channel im Allgemeinen einstellen.**")
                        .setThumbnail(event.getMember().getUser().getAvatarUrl())
                        .setColor(Color.decode("0x"+colour))
                        .setFooter("Bot created by Marius")
                        .setTimestamp(LocalDateTime.now(Clock.systemUTC()));

                textChannel.sendMessage(textChannelEmbed.build()).queue();
                VoiceChannel tempChannel = category.createVoiceChannel("⏳ | " + event.getMember().getEffectiveName() + "´s Channel").complete();
                tempChannel.upsertPermissionOverride(event.getMember()).grant(Permission.MANAGE_CHANNEL).grant(Permission.VOICE_MOVE_OTHERS).grant(Permission.MANAGE_PERMISSIONS).queue((channel) -> {
                    channel.getGuild().moveVoiceMember(event.getMember(), tempChannel).queue();
                });

                plugin.tempchannels.put(event.getMember(), tempChannel.getIdLong());
                textChannelEmbed.clear();

            } else {

                EmbedBuilder textChannelEmbed = new EmbedBuilder()
                        .setTitle("**Information**")
                        .setDescription("**Hey " + event.getMember().getAsMention() + "\nDu hast bereits einen temporären Channel!\n" +
                                "Du wurdest in diesen zurückgemovet!**")
                        .setThumbnail(event.getMember().getUser().getAvatarUrl())
                        .setColor(Color.decode("0x"+colour))
                        .setFooter("Bot created by Marius")
                        .setTimestamp(LocalDateTime.now(Clock.systemUTC()));

                textChannel.sendMessage(textChannelEmbed.build()).queue();
                event.getMember().getGuild().moveVoiceMember(event.getMember(), event.getGuild().getVoiceChannelById(plugin.tempchannels.get(event.getMember()))).queue();
            }
        }
    }
}
