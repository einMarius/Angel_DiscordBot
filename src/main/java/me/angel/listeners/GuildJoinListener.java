package me.angel.listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e){
        if(e.getUser().isBot()) return;

        e.getGuild().getDefaultChannel().sendMessage("Willkommen auf dem **Angels Clan Discord** " +
                "\nNAAAAAAAA " + e.getMember().getAsMention() + ", wir hoffen, dass du Spaß hast! <:pepe_love:850810581719973888> <:pepe_hacker:850810581719973888>").queue();

    }
}
