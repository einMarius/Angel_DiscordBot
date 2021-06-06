package me.angel.listeners;

import me.angel.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class CommandListener extends ListenerAdapter {

    private Main plugin;
    public CommandListener(Main plugin) { this.plugin = plugin; }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        String message = e.getMessage().getContentDisplay();

        if(e.getAuthor().isBot()) return;
        if (e.isFromType(ChannelType.TEXT)) {
            TextChannel channel = e.getTextChannel();
            if (message.startsWith("(")) {
                String args[] = message.substring(1).split(" ");
                if (args.length > 0) {
                    if (!plugin.getCommandManager().perform(args[0], e.getMember(), channel, e.getMessage())) {

                        EmbedBuilder info = new EmbedBuilder()
                                .setTitle("Information")
                                .setDescription("Der Befehl ist nicht bekannt")
                                .setFooter("Created by Marius", e.getGuild().getIconUrl())
                                .setColor(Color.RED);

                        channel.sendTyping().queue();
                        channel.sendMessage(info.build()).complete().delete().queueAfter(6, TimeUnit.SECONDS);
                        info.clear();
                    }
                }
            }
        }
    }
}
