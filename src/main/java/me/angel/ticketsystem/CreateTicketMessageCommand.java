package me.angel.ticketsystem;

import me.angel.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class CreateTicketMessageCommand implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel textChannel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(member.hasPermission(Permission.MANAGE_CHANNEL)) {
            if(textChannel.getIdLong() == 858292951825645588L) {
                if (args.length == 1) {

                    message.delete().queue();

                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setTitle("**Support**")
                            .setDescription("Um ein Ticket zu erstellen reagiere mit :envelope_with_arrow:")
                            .setFooter("Bot created by Marius", textChannel.getGuild().getIconUrl())
                            .setColor(Color.GREEN);

                    //📩

                    textChannel.sendMessage(embedBuilder.build()).setActionRow(Button.secondary("Buttons", "📩")).queue();

                } else {
                    message.delete().queue();
                    textChannel.sendTyping().queue();
                    textChannel.sendMessage("Benutze #createticket!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            } else {
                message.delete().queue();
                textChannel.sendTyping().queue();
                textChannel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        }
    }
}
