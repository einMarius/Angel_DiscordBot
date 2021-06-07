package me.angel.tempchannel;

import me.angel.main.Main;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class GetChannelLimitListener extends ListenerAdapter {

    private Main plugin;
    public GetChannelLimitListener(Main plugin) { this.plugin = plugin; }

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        if(e.getMember().getUser().isBot()) return;

        TextChannel textChannel = e.getTextChannel();
        String content = e.getMessage().getContentRaw();
        if(!plugin.channellimit.containsKey(e.getMember())) {
            if (e.getChannel().getIdLong() == 851535846284984400L) {
                try{
                    plugin.channellimit.put(e.getMember(), Integer.valueOf(content));
                }catch (NumberFormatException ex){
                    textChannel.sendMessage("Du musst eine Zahl angeben!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            }
        }else {
            textChannel.sendMessage("Du bist bereits in einem temporärem Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
