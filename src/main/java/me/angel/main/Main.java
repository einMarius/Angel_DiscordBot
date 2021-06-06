package me.angel.main;

import me.angel.listeners.CommandListener;
import me.angel.listeners.GuildJoinListener;
import me.angel.listeners.GuildMemberLeave;
import me.angel.listeners.UserRoleListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main {

    final private String TOKEN = "TOKEN";

    /*
    *
    * Channel Id´s für den Angel Discord
    *
    */
    final public long VERIFIZIEREN = 825776388530765915L;
    final public long REACTION_ROLES = 848711038768906290L;

    /*
     *
     * Role Id´s für den Angel Discord
     *
     */
    final public long USER = 825004459775361064L;

    private Main instance;
    private CommandManager commandManager;

    private JDABuilder jdaBuilder;

    public static void main(String[] args) {

        new Main();

    }

    public Main() {

        instance = this;
        commandManager = new CommandManager(this);

        jdaBuilder = JDABuilder.createDefault(TOKEN);
        jdaBuilder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        jdaBuilder.setActivity(Activity.watching("auf den Discord..."));
        jdaBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        JDA bot = null;

        //--------------Bot-Register---------------//
        jdaBuilder.addEventListeners(new CommandListener(this));
        jdaBuilder.addEventListeners(new GuildJoinListener());
        jdaBuilder.addEventListeners(new GuildMemberLeave());
        jdaBuilder.addEventListeners(new UserRoleListener(this));
        //--------------Bot-Register---------------//

        try {
            bot = jdaBuilder.build();
            bot.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("[AngelBot] Es gab einen Fehler beim starten des Discord Bots!");
        }

        System.out.println("[AngelBot] Der Bot, sowie alle Systeme wurden gestartet!");

    }

    public CommandManager getCommandManager() { return commandManager; }
}
