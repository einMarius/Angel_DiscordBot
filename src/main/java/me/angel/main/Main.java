package me.angel.main;

import me.angel.listeners.CommandListener;
import me.angel.reactionroles.ReactionRoleListener;
import me.angel.tempchannel.JoinMainChannelListener;
import me.angel.tempchannel.LeaveTempChannelListener;
import me.angel.tempchannel.MoveIntoMainChannelListener;
import me.angel.tempchannel.MoveOutTempChannelListener;
import me.angel.ticketsystem.ReactionAddListener;
import me.angel.utils.Util;
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

/*



               AAA                                                                         lllllll BBBBBBBBBBBBBBBBB                             tttt
              A:::A                                                                        l:::::l B::::::::::::::::B                         ttt:::t
             A:::::A                                                                       l:::::l B::::::BBBBBB:::::B                        t:::::t
            A:::::::A                                                                      l:::::l BB:::::B     B:::::B                       t:::::t
           A:::::::::A           nnnn  nnnnnnnn       ggggggggg   ggggg    eeeeeeeeeeee     l::::l   B::::B     B:::::B   ooooooooooo   ttttttt:::::ttttttt
          A:::::A:::::A          n:::nn::::::::nn    g:::::::::ggg::::g  ee::::::::::::ee   l::::l   B::::B     B:::::B oo:::::::::::oo t:::::::::::::::::t
         A:::::A A:::::A         n::::::::::::::nn  g:::::::::::::::::g e::::::eeeee:::::ee l::::l   B::::BBBBBB:::::B o:::::::::::::::ot:::::::::::::::::t
        A:::::A   A:::::A        nn:::::::::::::::ng::::::ggggg::::::gge::::::e     e:::::e l::::l   B:::::::::::::BB  o:::::ooooo:::::otttttt:::::::tttttt
       A:::::A     A:::::A         n:::::nnnn:::::ng:::::g     g:::::g e:::::::eeeee::::::e l::::l   B::::BBBBBB:::::B o::::o     o::::o      t:::::t
      A:::::AAAAAAAAA:::::A        n::::n    n::::ng:::::g     g:::::g e:::::::::::::::::e  l::::l   B::::B     B:::::Bo::::o     o::::o      t:::::t
     A:::::::::::::::::::::A       n::::n    n::::ng:::::g     g:::::g e::::::eeeeeeeeeee   l::::l   B::::B     B:::::Bo::::o     o::::o      t:::::t
    A:::::AAAAAAAAAAAAA:::::A      n::::n    n::::ng::::::g    g:::::g e:::::::e            l::::l   B::::B     B:::::Bo::::o     o::::o      t:::::t    tttttt
   A:::::A             A:::::A     n::::n    n::::ng:::::::ggggg:::::g e::::::::e          l::::::lBB:::::BBBBBB::::::Bo:::::ooooo:::::o      t::::::tttt:::::t
  A:::::A               A:::::A    n::::n    n::::n g::::::::::::::::g  e::::::::eeeeeeee  l::::::lB:::::::::::::::::B o:::::::::::::::o      tt::::::::::::::t
 A:::::A                 A:::::A   n::::n    n::::n  gg::::::::::::::g   ee:::::::::::::e  l::::::lB::::::::::::::::B   oo:::::::::::oo         tt:::::::::::tt
AAAAAAA                   AAAAAAA  nnnnnn    nnnnnn    gggggggg::::::g     eeeeeeeeeeeeee  llllllllBBBBBBBBBBBBBBBBB      ooooooooooo             ttttttttttt
                                                               g:::::g
                                                   gggggg      g:::::g
                                                   g:::::gg   gg:::::g
                                                    g::::::ggg:::::::g
                                                     gg:::::::::::::g
                                                       ggg::::::ggg
                                                          gggggg                                                                                               */


    final private String TOKEN = "TOKEN";

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



    private String[] twitchNames = new String[] { "einMarius" };

    private Main instance;
    private CommandManager commandManager;
    private Util util;

    public Map<Member, Long> tempchannels;

    private JDABuilder jdaBuilder;

    private Thread runTwitch;

    public static void main(String[] args) {

        new Main();

    }

    public Main() {

        tempchannels = new HashedMap<>();

        jdaBuilder = JDABuilder.createDefault(TOKEN);
        jdaBuilder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        jdaBuilder.setActivity(Activity.playing("auf Skywars.World"));
        jdaBuilder.setStatus(OnlineStatus.ONLINE);

        JDA bot = null;

        //--------------Bot-Register---------------//
        jdaBuilder.addEventListeners(new CommandListener(this));
        //jdaBuilder.addEventListeners(new GuildJoinListener());
        //jdaBuilder.addEventListeners(new GuildMemberLeave());
        //jdaBuilder.addEventListeners(new VerifyListener(this));
        jdaBuilder.addEventListeners(new ReactionRoleListener(this));
        jdaBuilder.addEventListeners(new JoinMainChannelListener(this));
        jdaBuilder.addEventListeners(new LeaveTempChannelListener(this));
        jdaBuilder.addEventListeners(new MoveIntoMainChannelListener(this));
        jdaBuilder.addEventListeners(new MoveOutTempChannelListener(this));
        jdaBuilder.addEventListeners(new ReactionAddListener());
        //--------------Bot-Register---------------//

        try {
            bot = jdaBuilder.build();
            bot.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("[AngelBot] Es gab einen Fehler beim Starten des Discord Bots!");
        }

        instance = this;
        util = new Util(this);
        commandManager = new CommandManager(this, bot);

        //util.executePost2();
        //runTwitchNotification();

        System.out.println("[AngelBot] Der Bot, sowie alle Systeme wurden gestartet!");

    }

    private void runTwitchNotification() {
        this.runTwitch = new Thread(() -> {
            try {
                Util.executePost("einmarius", jdaBuilder.build().getTextChannelById("851131058178097172"));
            } catch (LoginException e) {
                e.printStackTrace();
            }
        });
        this.runTwitch.setName("TwitchNotification");
        this.runTwitch.start();
    }

    public CommandManager getCommandManager() { return commandManager; }
}
