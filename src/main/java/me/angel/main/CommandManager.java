package me.angel.main;

import me.angel.commands.types.ServerCommand;
import me.angel.commands.use.ClearCommand;
import me.angel.commands.use.MemeCommand;
import me.angel.commands.use.UmfrageCommand;
import me.angel.reactionroles.ReactionRoleCommand;
import me.angel.ticketsystem.CreateTicketMessageCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    private Main plugin;
    private JDA bot;
    public ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager(Main plugin, JDA bot){

        this.bot = bot;
        this.plugin = plugin;
        this.commands = new ConcurrentHashMap<>();

        this.commands.put("reactionrole", new ReactionRoleCommand(plugin));
        this.commands.put("clear", new ClearCommand());
        this.commands.put("umfrage", new UmfrageCommand(plugin));
        this.commands.put("plsmeme", new MemeCommand(plugin));
        this.commands.put("createticket", new CreateTicketMessageCommand());

        CommandListUpdateAction commands = bot.updateCommands();
        commands.addCommands(new CommandData("reactionrole", "Sendet die Nachricht für Reactionsoles."));
        commands.addCommands(new CommandData("clear", "Cleared eine bestimmte Anzahl an Nachrichten (max. 100).")
                .addOption(OptionType.INTEGER, "amount", "Zahl der zu löschenden Nachrichten.", true));
        commands.addCommands(new CommandData("umfrage", "Sendet eine Umfrage.")
                .addOption(OptionType.STRING, "umfrage", "Satz der Umfrage.", true));
        commands.addCommands(new CommandData("plsmeme", "Sendet ein random Meme von Reddit."));
        commands.addCommands(new CommandData("createticket", "Sendet die Nachricht für Tickets"));

        commands.queue();

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
