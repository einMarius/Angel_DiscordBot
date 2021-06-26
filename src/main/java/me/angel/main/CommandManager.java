package me.angel.main;

import me.angel.commands.types.ServerCommand;
import me.angel.commands.use.ClearCommand;
import me.angel.commands.use.MemeCommand;
import me.angel.commands.use.UmfrageCommand;
import me.angel.reactionroles.ReactionRoleCommand;
import me.angel.ticketsystem.CreateTicketMessageCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    private Main plugin;
    public ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager(Main plugin){

        this.plugin = plugin;
        this.commands = new ConcurrentHashMap<>();

        this.commands.put("reactionrole", new ReactionRoleCommand(plugin));
        this.commands.put("clear", new ClearCommand());
        this.commands.put("umfrage", new UmfrageCommand(plugin));
        this.commands.put("plsmeme", new MemeCommand(plugin));
        this.commands.put("createticket", new CreateTicketMessageCommand());

    }

    public boolean perform(String command, Member member, TextChannel channel, Message message) {

        ServerCommand cmd;
        if ((cmd = this.commands.get(command.toLowerCase())) != null) {

            cmd.performCommand(member, channel, message);
            return true;
        }
        return false;
    }

}
