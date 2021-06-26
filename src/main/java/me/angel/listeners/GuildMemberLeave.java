package me.angel.listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberLeave extends ListenerAdapter {

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event){
        if(event.getUser().isBot()) return;

        event.getGuild().getDefaultChannel().sendMessage("**" + event.getMember().getAsMention() + "** hat uns leider verlassen, wir werden dich vermissen. <:PepeYikes:851152650275782697> <:PepeHands:851152650275782697>").queue();

    }

}
