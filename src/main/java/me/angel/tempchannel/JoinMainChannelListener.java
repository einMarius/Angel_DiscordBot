package me.angel.tempchannel;

import me.angel.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class JoinMainChannelListener extends ListenerAdapter {

    private Main plugin;

    public JoinMainChannelListener(Main plugin) { this.plugin = plugin; }

    String[] colours =  new String[] { "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff" };

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent e){
        if (e.getMember().getUser().isBot()) return;

        VoiceChannel voiceChannel = e.getChannelJoined();
        TextChannel textChannel = e.getGuild().getTextChannelById(851535846284984400L);
        //Colour for Embed
        Random rand = new Random();
        int i = rand.nextInt(colours.length);
        String colour = colours[i];

        EmbedBuilder textChannelEmbed = new EmbedBuilder()
                .setTitle("**Einstellungen des temporären Channels")
                .setDescription("**Hey " + e.getMember().getAsMention() + "\nSchreib die Anzahl der Channel Mitglieder in den Chat!**")
                .setThumbnail(e.getGuild().getIconUrl())
                .setColor(Color.decode("0x"+colour));

        if(voiceChannel.getIdLong() == 851531704031248430L) {
            Category category = voiceChannel.getParent();

            textChannel.sendMessage(textChannelEmbed.build()).queue(());

            /*textChannel.sendMessage(textChannelEmbed.build()).submit()
                    .thenComposeAsync((tempChannel) -> category.createVoiceChannel("⏳ | " + e.getMember().getEffectiveName() + "´s Channel").submit())
                    //.thenComposeAsync((voiceChannel1) -> voiceChannel.getManager().setUserLimit(plugin.channellimit.get(e.getMember())))
                    .thenComposeAsync((m) -> m.getManager().setUserLimit(plugin.channellimit.get(e.getMember())).submit())
                    .thenComposeAsync((v) -> e.getMember().getGuild().moveVoiceMember(e.getMember(), tempChannel).submit())
                    .whenCompleteAsync((s, error) -> {
                       plugin.channellimit.remove(e.getMember());
                       if(error != null) {
                           error.printStackTrace();
                           System.out.println("Es gab einen Fehler");
                       }

                    });

             */
        }
    }

}
