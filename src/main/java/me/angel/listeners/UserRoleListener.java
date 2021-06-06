package me.angel.listeners;

import me.angel.main.Main;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserRoleListener extends ListenerAdapter {

    private Main plugin;
    public UserRoleListener(Main plugin) { this.plugin = plugin; }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e){
        if(e.getUser().isBot()) return;

        TextChannel channel = e.getTextChannel();

        if(channel.getIdLong() == plugin.VERIFIZIEREN) {
            if(e.getReactionEmote().getEmoji().equalsIgnoreCase("✅"))
                e.getGuild().addRoleToMember(e.getUserId(), e.getJDA().getRoleById(plugin.USER)).queue();
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent e){
        if(e.getUser().isBot()) return;

        TextChannel channel = e.getTextChannel();
        if(channel.getIdLong() == plugin.VERIFIZIEREN) {
            if(e.getReactionEmote().getEmoji().equalsIgnoreCase("✅"))
                e.getGuild().removeRoleFromMember(e.getUserId(), e.getJDA().getRoleById(plugin.USER)).queue();
        }
    }

}
