package me.angel.utils;

import me.angel.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Clock;
import java.time.LocalDateTime;

public class Util {

    private Main plugin;

    public Util(Main plugin){
        this.plugin = plugin;
    }

    public static void executePost(String channelName, TextChannel textChannel){

        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL("https://api.twitch.tv/helix/streams?user_login=" + channelName);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer xk3ny6qo9ouf7jkvbm3dy832ixt1v4");
            connection.setRequestProperty("Client-Id", "37nxnwqba25hgk9heboh9nnu8ohqdn");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            JSONParser jsonParser = new JSONParser();
            String user_name = "";
            String game_name = "";
            String title = "";
            String thumbnailURL = "";
            URL postLink = new URL("https://www.twitch.tv/" + channelName);
            int viewercount = 0;

            //REQUEST
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

            //RESPONSE
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));

            String lines;

            while((lines = bufferedReader.readLine()) != null){
                JSONArray array = new JSONArray();
                array.add(jsonParser.parse(lines));

                for(Object o : array){
                    JSONObject jsonObject = (JSONObject) o;

                    user_name = (String) jsonObject.get("user_name");
                    game_name = (String) jsonObject.get("game_name");
                    title = (String) jsonObject.get("title");
                    thumbnailURL = (String) jsonObject.get("thumbnail_url");
                    viewercount = (int) jsonObject.get("viewer_count");

                }
            }
            bufferedReader.close();

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(user_name + " ist live auf Twitch!", String.valueOf(postLink))
                    .setTitle(title, String.valueOf(postLink))
                    .setDescription("Spielt " + game_name + " für " + viewercount + " Zuschauer.")
                    .setThumbnail(thumbnailURL)
                    .setTimestamp(LocalDateTime.now(Clock.systemUTC()))
                    .setFooter("Bot created by Marius")
                    .setColor(Color.decode("0x8100fa"));

            textChannel.sendMessage("||<@&848710448790110239>|| \n" + user_name + " ist jetzt Live!" + postLink).embed(embedBuilder.build()).queue();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null)
                connection.disconnect();
        }
    }

}
