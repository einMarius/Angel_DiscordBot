package me.angel.ticketsystem;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.util.Objects;

public class ReactionAddListener extends ListenerAdapter {

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        if(Objects.requireNonNull(event.getUser()).isBot()) return;

        if(event.getChannel().getIdLong() == 858292951825645588L) {
            if(event.getButton().getLabel().equalsIgnoreCase("📩")) {

                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("**Support**")
                        .setDescription("Es wird sich bald jemand um dich kümmern! \nUm das Ticket zu schließen musst du auf den unten gelegenen Button klicken!")
                        .setColor(Color.GREEN)
                        .setFooter("Bot created by Marius", event.getGuild().getIconUrl());

                event.getGuild().createTextChannel("ticket-" + event.getUser().getName(), event.getTextChannel().getParent()).queue(supportChannel -> {
                    //PERMISSIONS NOCH EINSTELLEN!
                    supportChannel.upsertPermissionOverride(event.getMember()).grant(Permission.VIEW_CHANNEL);
                    supportChannel.sendMessage("Willkommen " + event.getMember().getAsMention()).embed(embedBuilder.build())
                            .setActionRow(Button.secondary("Buttons", "🔒")).queue();
                });
            }
        }
    }
}
