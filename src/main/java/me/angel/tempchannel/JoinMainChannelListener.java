package me.angel.tempchannel;

import me.angel.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Random;

public class JoinMainChannelListener extends ListenerAdapter {

    private Main plugin;

    public JoinMainChannelListener(Main plugin) { this.plugin = plugin; }

    String[] colours =  new String[] { "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff" };

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent e){
        if (e.getMember().getUser().isBot()) return;

        VoiceChannel voiceChannel = e.getChannelJoined();
        TextChannel textChannel = e.getGuild().getTextChannelById(plugin.TEMP_CHANNEL);
        //Colour for Embed
        Random rand = new Random();
        int i = rand.nextInt(colours.length);
        String colour = colours[i];

        EmbedBuilder textChannelEmbed = new EmbedBuilder()
                .setTitle("**Einstellungen des temporären Channels**")
                .setDescription("**Hey " + e.getMember().getAsMention() + "\nDein privater Channel wurde erstellt und du wurdest hineingemovet!\n" +
                        "Du kannst in diesem Channel sowohl die Permissions, als auch den Channel im Allgemeinen einstellen.**")
                .setThumbnail(e.getMember().getUser().getAvatarUrl())
                .setColor(Color.decode("0x"+colour))
                .setFooter("Bot created by Marius")
                .setTimestamp(LocalDateTime.now(Clock.systemUTC()));

        if(voiceChannel.getIdLong() == plugin.JOIN_CHANNEL) {
            if (!plugin.tempchannels.containsKey(e.getMember())) {
                Category category = voiceChannel.getParent();



                textChannel.sendMessage(textChannelEmbed.build()).queue();
                VoiceChannel tempChannel = category.createVoiceChannel("⏳ | " + e.getMember().getEffectiveName() + "´s Channel").complete();
                tempChannel.upsertPermissionOverride(e.getMember()).grant(Permission.MANAGE_CHANNEL).grant(Permission.VOICE_MOVE_OTHERS).grant(Permission.MANAGE_PERMISSIONS).queue((channel) -> {
                    channel.getGuild().moveVoiceMember(e.getMember(), tempChannel).queue();
                });

                plugin.tempchannels.put(e.getMember(), tempChannel.getIdLong());
                textChannelEmbed.clear();
            } else {

                EmbedBuilder textChannelEmbed1 = new EmbedBuilder()
                        .setTitle("**Information**")
                        .setDescription("**Hey " + e.getMember().getAsMention() + "\nDu hast bereits einen temporären Channel!\n" +
                                "Du wurdest in diesen zurückgemovet!**")
                        .setThumbnail(e.getMember().getUser().getAvatarUrl())
                        .setColor(Color.decode("0x"+colour))
                        .setFooter("Bot created by Marius")
                        .setTimestamp(LocalDateTime.now(Clock.systemUTC()));

                textChannel.sendMessage(textChannelEmbed1.build()).queue();
                e.getMember().getGuild().moveVoiceMember(e.getMember(), e.getGuild().getVoiceChannelById(plugin.tempchannels.get(e.getMember()))).queue();
            }
        }
    }
}
