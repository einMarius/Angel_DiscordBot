package me.angel.listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        if(event.getUser().isBot()) return;

        event.getGuild().getDefaultChannel().sendMessage("Willkommen auf dem **Angels Clan Discord** " +
                "\nNAAAAAAAA " + event.getMember().getAsMention() + ", wir hoffen, dass du Spaß hast! <:pepe_love:850810581719973888> <:pepe_hacker:850810581719973888>").queue();

    }
}
