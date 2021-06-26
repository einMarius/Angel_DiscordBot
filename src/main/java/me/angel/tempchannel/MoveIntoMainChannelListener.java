package me.angel.tempchannel;

import me.angel.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Random;

public class MoveIntoMainChannelListener extends ListenerAdapter {

    private Main plugin;
    public MoveIntoMainChannelListener(Main plugin) { this.plugin = plugin; }

    private String[] colours =  new String[] { "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff" };

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event){
        if(event.getMember().getUser().isBot()) return;
        VoiceChannel voiceChannel = event.getChannelJoined();

        if(voiceChannel.getIdLong() == plugin.JOIN_CHANNEL){
            if(!plugin.tempchannels.containsKey(event.getMember())){

                TextChannel textChannel = event.getGuild().getTextChannelById(851535846284984400L);
                //Colour for Embed
                Random rand = new Random();
                int i = rand.nextInt(colours.length);
                String colour = colours[i];

                EmbedBuilder textChannelEmbed = new EmbedBuilder()
                        .setTitle("**Einstellungen des temporären Channels**")
                        .setDescription("**Hey " + event.getMember().getAsMention() + "\nDein privater Channel wurde erstellt und du wurdest hineingemovet!\n" +
                                "Du kannst in diesem Channel sowohl die Permissions, als auch den Channel im Allgemeinen einstellen.**")
                        .setThumbnail(event.getMember().getUser().getAvatarUrl())
                        .setColor(Color.decode("0x"+colour))
                        .setFooter("Bot created by Marius")
                        .setTimestamp(LocalDateTime.now(Clock.systemUTC()));

                Category category = voiceChannel.getParent();

                textChannel.sendMessage(textChannelEmbed.build()).queue();
                VoiceChannel tempChannel = category.createVoiceChannel("⏳ | " + event.getMember().getEffectiveName() + "´s Channel").complete();
                tempChannel.upsertPermissionOverride(event.getMember()).grant(Permission.MANAGE_CHANNEL).grant(Permission.VOICE_MOVE_OTHERS).grant(Permission.MANAGE_PERMISSIONS).queue((channel) -> {
                    channel.getGuild().moveVoiceMember(event.getMember(), tempChannel).queue();
                });

                plugin.tempchannels.put(event.getMember(), tempChannel.getIdLong());
            } else {

                TextChannel textChannel = event.getGuild().getTextChannelById(851535846284984400L);
                //Colour for Embed
                Random rand = new Random();
                int i = rand.nextInt(colours.length);
                String colour = colours[i];

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
