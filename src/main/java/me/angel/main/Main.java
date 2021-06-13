package me.angel.main;

import me.angel.listeners.CommandListener;
import me.angel.reactionroles.ReactionRoleListener;
import me.angel.tempchannel.JoinMainChannelListener;
import me.angel.tempchannel.LeaveTempChannelListener;
import me.angel.tempchannel.MoveIntoMainChannelListener;
import me.angel.tempchannel.MoveOutTempChannelListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.commons.collections4.map.HashedMap;

import javax.security.auth.login.LoginException;
import java.util.Map;

public class Main {

    final private String TOKEN = "TOKEN";
    final public long MARIUS_ID = 374932063423561729L;

    /*
    *
    * Channel Id´s für den Angel Discord
    *
    */
    final public long VERIFIZIEREN = 825776388530765915L;
    final public long REACTION_ROLES = 848711038768906290L;
    final public long ABSTIMMUNGEN = 825344586712219649L;
    final public long JOIN_CHANNEL = 852245359019425842L;
    final public long TEMP_CHANNEL = 851535846284984400L;
    final public long MEMES = 825345761050099732L;
    final public long TICKET_DC_BOT = 851131058178097172L;

    /*
     *
     * Role Id´s für den Angel Discord
     *
     */
    final public long USER = 825004459775361064L;
    final public long UMFRAGE = 851477225110241280L;

    private Main instance;
    private CommandManager commandManager;

    public Map<Member, Long> tempchannels;

    private JDABuilder jdaBuilder;

    public static void main(String[] args) {

        new Main();

    }

    public Main() {

        instance = this;
        commandManager = new CommandManager(this);

        tempchannels = new HashedMap<>();

        jdaBuilder = JDABuilder.createDefault(TOKEN);
        jdaBuilder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        jdaBuilder.setActivity(Activity.watching("auf den Discord..."));
        jdaBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);

        JDA bot = null;

        //--------------Bot-Register---------------//
        jdaBuilder.addEventListeners(new CommandListener(this));
        //jdaBuilder.addEventListeners(new GuildJoinListener());
        //jdaBuilder.addEventListeners(new GuildMemberLeave());
        //jdaBuilder.addEventListeners(new UserRoleListener(this));
        jdaBuilder.addEventListeners(new ReactionRoleListener(this));
        jdaBuilder.addEventListeners(new JoinMainChannelListener(this));
        jdaBuilder.addEventListeners(new LeaveTempChannelListener(this));
        jdaBuilder.addEventListeners(new MoveIntoMainChannelListener(this));
        jdaBuilder.addEventListeners(new MoveOutTempChannelListener(this));
        //--------------Bot-Register---------------//

        try {
            bot = jdaBuilder.build();
            bot.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("[AngelBot] Es gab einen Fehler beim Starten des Discord Bots!");
        }

        System.out.println("[AngelBot] Der Bot, sowie alle Systeme wurden gestartet!");

    }

    public CommandManager getCommandManager() { return commandManager; }
}
