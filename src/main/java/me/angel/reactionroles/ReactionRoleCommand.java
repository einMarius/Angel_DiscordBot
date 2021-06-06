package me.angel.reactionroles;

import me.angel.commands.types.ServerCommand;
import me.angel.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ReactionRoleCommand implements ServerCommand {

    private Main plugin;
    public ReactionRoleCommand(Main plugin) { this.plugin = plugin;}

    @Override
    public void performCommand(Member member, TextChannel textChannel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(member.hasPermission(Permission.ADMINISTRATOR)){
            if(textChannel.getIdLong() == plugin.REACTION_ROLES || textChannel.getIdLong() == 844183074707210261L) {

                EmbedBuilder reactionrole = new EmbedBuilder()
                        .setTitle("**Angel | Benachrichtigungen**")
                        .setDescription("**Reagiere mit dem jeweiligen Emoji, um bestimmte \nBenachrichtigungen zu erhalten.**")
                        .addField("**Ankündigung-Role**", ">>> ❗ `-` Ankündigung", false)
                        .addField("**Twitch-Role**", ">>> <:twitch:851146049304657970> `-` Twitch", false)
                        .setThumbnail(member.getGuild().getIconUrl())
                        .setFooter("Bot created by Marius")
                        .setColor(Color.decode("0x242323"));

                if(args.length == 1){
                    message.delete().submit()
                            .thenComposeAsync((v) -> textChannel.sendTyping().submit())
                            .thenComposeAsync((m) -> textChannel.sendMessage(reactionrole.build()).submit())
                            .thenComposeAsync((v) -> v.addReaction("❗").and(v.addReaction("<:twitch:848710734632058880")).submit())
                            .whenCompleteAsync((s, error)-> {
                                reactionrole.clear();
                                if(error != null) {
                                    error.printStackTrace();
                                    System.out.println("[AngelBot] Es gab einen Fehler beim erstellen der Nachricht!");
                                }
                            });

                } else {
                    message.delete().queue();
                    textChannel.sendMessage("Benutze: #reactionrole").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            } else {
                message.delete().queue();
                textChannel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        }
    }
}
