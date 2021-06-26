package me.angel.reactionroles;

import me.angel.main.Main;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

public class ReactionRoleListener extends ListenerAdapter {

    private Main plugin;
    public ReactionRoleListener(Main plugin) { this.plugin = plugin; }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
        if(event.getUser().isBot()) return;

        if(event.getChannel().getIdLong() == plugin.REACTION_ROLES || event.getChannel().getIdLong() == 851478568269250580L) {

            if(event.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("1⃣")) {
                AuditableRestAction<Void> action = event
                        .getGuild()
                        .addRoleToMember(event.getUserId(), event.getGuild().getRoleById(851477087809437776L));
                action.queue();
                return;
            //Twitch Emote
            }
            if(event.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("2⃣")) {
                AuditableRestAction<Void> action = event
                        .getGuild()
                        .addRoleToMember(event.getUserId(), event.getGuild().getRoleById(848710448790110239L));
                action.queue();
                return;
            }
            if(event.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("3⃣")) {
                AuditableRestAction<Void> action = event
                        .getGuild()
                        .addRoleToMember(event.getUserId(), event.getGuild().getRoleById(851477225110241280L));
                action.queue();
                return;
            }
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent e){

        if(e.getChannel().getIdLong() == plugin.REACTION_ROLES || e.getChannel().getIdLong() == 851478568269250580L) {

            if(e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("1⃣")) {
                AuditableRestAction<Void> action = e
                        .getGuild()
                        .removeRoleFromMember(e.getUserId(), e.getGuild().getRoleById(851477087809437776L));
                action.queue();
                return;
            }
            if(e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("2⃣")) {
                AuditableRestAction<Void> action = e
                        .getGuild()
                        .removeRoleFromMember(e.getUserId(), e.getGuild().getRoleById(848710448790110239L));
                action.queue();
                return;
            }
            if(e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("3⃣")) {
                AuditableRestAction<Void> action = e
                        .getGuild()
                        .removeRoleFromMember(e.getUserId(), e.getGuild().getRoleById(851477225110241280L));
                action.queue();
                return;
            }
        }
    }
}
