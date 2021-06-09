package me.angel.reactionroles;

import me.angel.commands.types.ServerCommand;
import me.angel.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
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

        if(member.hasPermission(Permission.MESSAGE_MANAGE)){
            if(textChannel.getIdLong() == plugin.REACTION_ROLES || textChannel.getIdLong() == 851478568269250580L || textChannel.getIdLong() == 844183074707210261L) {

                EmbedBuilder reactionrole = new EmbedBuilder()
                        .setTitle("**Angel | Benachrichtigungen**")
                        .setDescription("**Reagiere mit dem jeweiligen Emoji, um bestimmte \nBenachrichtigungen zu erhalten.**")
                        .addField("**Ankündigung-Role**", ">>> :one: `-` Ankündigung", false)
                        .addField("**Twitch-Role**", ">>> :two: `-` Twitch", false)
                        .addField("**Umfrage-Role**", ">>> :three: `-` Umfrage", false)
                        .setThumbnail(member.getGuild().getIconUrl())
                        .setFooter("Bot created by @" + member.getJDA().getUserById(plugin.MARIUS_ID).getAsTag())
                        .setColor(Color.decode("0x242323"));

                if(args.length == 1){
                    message.delete().submit()
                            .thenComposeAsync((v) -> textChannel.sendTyping().submit())
                            .thenComposeAsync((m) -> textChannel.sendMessage(reactionrole.build()).submit())
                            .thenComposeAsync((v) -> v.addReaction("1⃣").and(v.addReaction("2⃣")).and(v.addReaction("3⃣")).submit())
                            .whenCompleteAsync((s, error)-> {
                                reactionrole.clear();
                                if(error != null) {
                                    error.printStackTrace();
                                    System.out.println("[AngelBot] Es gab einen Fehler beim Erstellen der Nachricht!");
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
