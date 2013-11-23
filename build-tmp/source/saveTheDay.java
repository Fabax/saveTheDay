import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import twitter4j.conf.*; 
import twitter4j.internal.async.*; 
import twitter4j.internal.logging.*; 
import twitter4j.json.*; 
import twitter4j.internal.util.*; 
import twitter4j.management.*; 
import twitter4j.auth.*; 
import twitter4j.api.*; 
import twitter4j.util.*; 
import twitter4j.internal.http.*; 
import twitter4j.*; 
import twitter4j.internal.json.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class saveTheDay extends PApplet {



//import twitter4j.internal.org.json.*;











ModelTwitter model; 
String[] hashtags = {"doctorwho","savetheday","doctorwhof4","dalek","DrWho"," DayOfTheDoctor"};
boolean strartSketch = true;
boolean initTwitter = true;
int sec ;


public void setup(){
}

public void draw(){
	sec = second();
	if(strartSketch && sec == 59){
		start();
		strartSketch = false;
	}
}

public void start(){
	if(initTwitter){
		model = new ModelTwitter(60000);	
		model.listenToHashtag(hashtags);
		println("in");
		initTwitter = false;
	}else{
		println("out");
	}
}
  Twitter twitter;
public class ModelTwitter {
  //configuration de twitter

  User user;
  Configuration c;
  Status status;
  String[] hashTags;

  int timerListener ; 

  //--------------------------------------
  //  CONSTRUCTOR
  //--------------------------------------
  
  public ModelTwitter (int _time) {
    timerListener = _time;
    twitterConfiguration();
  }

  //----- FIN DE GETTERS AND SETTERS

  // CONFIGURATION
public void twitterConfiguration() {
      ConfigurationBuilder cb = new ConfigurationBuilder();

      cb.setDebugEnabled(true);
//Cl\u00e9 3
    cb.setOAuthConsumerKey("bHAfupgNYRVoSC27eHRqg");
    cb.setOAuthConsumerSecret("GgxgSLDO8Rqaoctl2cPlPct95dNtq31rPF8HHNmi9g");
    cb.setOAuthAccessToken("1272243708-zVoQBEQPJZpPuuzzq6nhhiVfveo8VaxzSaVLpJj");
    cb.setOAuthAccessTokenSecret("VcwmL284xm80uZHIYUarkWRmsqyFM2WWsX8qdm1qoQpAe");
    //Cl\u00e9 4
    // cb.setOAuthConsumerKey("Pbi0uLWNe1fIEZLUGKTwqA");
    // cb.setOAuthConsumerSecret("LWf4225LihNfBUEplzkEMXcoqsG5XP3sjOJpavwahqc");
    // cb.setOAuthAccessToken("1272243708-S0bIRKBCfwzEwdc1FY8EMZFPsbtcwj4kz400wyV");
    // cb.setOAuthAccessTokenSecret("0Dw1q4CjoJym3GlccCBdmoaRpBfvOTVfIryWVjqX51pWv");

      c = cb.build();
      TwitterFactory tf = new TwitterFactory(c);
      twitter = tf.getInstance();
}

  //Methode that will listen to a specifique hashtag and will return the result in live
  public void listenToHashtag(String[] _keyWords){
        TwitterStream ts = new TwitterStreamFactory(c).getInstance();
        FilterQuery filterQuery = new FilterQuery(); 
        filterQuery.track(_keyWords);
        // On fait le lien entre le TwitterStream (qui r\u00e9cup\u00e8re les messages) et notre \u00e9couteur  
        ts.addListener(new TwitterListener(timerListener));
         // On d\u00e9marre la recherche !
        ts.filter(filterQuery);  
  }  
  // USER INFOS -----------------

  //get the user informations
  public void getUserInformations(String[] _users) {
    String[] userList = _users;
    for (int i = 0; i<userList.length; i++){
      try {
        user = twitter.showUser(userList[i]);
        displayUserInformations();
      } catch (TwitterException te) {
        println("Failed to get user informations " + te.getMessage());
        exit();
      }
    }
  }

  //display the user information (for debug)
  public void displayUserInformations() {
    println("getLocation(): "+user.getLocation());
    println("getFriendsCount(): "+user.getFriendsCount());
    println("getFollowersCount(): "+user.getFollowersCount());
    println("getDescription(): "+user.getDescription());
    println("getCreatedAt() : "+user.getCreatedAt() );
    println("getDescriptionURLEntities(): "+user.getDescriptionURLEntities());
    println("getFavouritesCount() : "+user.getFavouritesCount() );
  }// END OF USER INFOS -----------------

}
// Learning Processing
// Daniel Shiffman
// http://www.learningprocessing.com

// Example 10-5: Object-oriented timer

class Timer {
 
  int savedTime; // When Timer started
  int totalTime; // How long Timer should last
  
  Timer(int tempTotalTime) {
    totalTime = tempTotalTime;
  }
  
  // Starting the timer
  public void start() {
    // When the timer starts it stores the current time in milliseconds.
    savedTime = millis(); 
  }
  
  // The function isFinished() returns true if 5,000 ms have passed. 
  // The work of the timer is farmed out to this method.
  public boolean isFinished() { 
    // Check how much time has passed
    int passedTime = millis()- savedTime;
    if (passedTime > totalTime) {
      return true;
    } else {
      return false;
    }
  }
  
  public void reset(int tempTotalTime) {
    totalTime = tempTotalTime;
  }
}

public class TwitterListener implements StatusListener{
  // onStatus : nouveau message qui vient d'arriver 
  Timer timer;
  JSONArray tweets ;
  int counter;
  Place place;

  public TwitterListener(int _time){
    timer = new Timer(_time);
    timer.start();
    tweets =  new JSONArray();
    counter = 0;
  }

  public String nameJson(){
    int m = minute();  // Values from 0 - 59
    int h = hour();    // Values from 0 - 23
    String myDate = h+"-"+m;
    return myDate;
  }

  public String tweetTime(){
    int m = minute();  // Values from 0 - 59
    int h = hour();    // Values from 0 - 23
    int s = second();    // Values from 0 - 23
    String myDate = h+"-"+m+"-"+s;
    return myDate;
  }

  public void onStatus(Status status) {
        //get the informations we want to return 
    String userName = status.getUser().getScreenName();
    String userId = Long.toString(status.getUser().getId());
    String messageTweet = status.getText();
    String imageUrl = status.getUser().getProfileImageURL();
    String lang = status.getUser().getLang();
    String timeZone = status.getUser().getTimeZone();
    String userLocation = status.getUser().getLocation();
    String time = tweetTime();

    makeJson(userId,userName,messageTweet,imageUrl, lang, timeZone, userLocation, time);
  
}

  public void makeJson(String _id, String _user, String _message, 
    String _imageUrl, String _lang, String _timeZone, String _userLocation,
    String _time){
    JSONObject tweetInfos =  new JSONObject();
    JSONObject tweetId =  new JSONObject();

    tweetInfos.setString("id",_id);
    tweetInfos.setString("userName",_user);
    tweetInfos.setString("message",_message);
    tweetInfos.setString("imageUrl",_imageUrl);
    tweetInfos.setString("language",_lang);
    tweetInfos.setString("userLocation",_userLocation);
    tweetInfos.setString("timeZone",_timeZone);
    tweetInfos.setString("time",_time);


    tweets.setJSONObject(counter, tweetInfos);
    counter ++;
     
    if(timer.isFinished()){
        String fileName = nameJson();
        saveJSONArray(tweets, "jsons/"+fileName+"-"+counter+".json");

        tweets =  new JSONArray();
        timer.start();
        counter = 0;
        }
  }

  // onDeletionNotice
  public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) 
  {
  }
  // onTrackLimitationNotice
  public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
  }  

  // onScrubGeo : r\u00e9cup\u00e9ration d'infos g\u00e9ographiques
  public void onScrubGeo(long userId, long upToStatusId) 
  {
    System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
  }

  public void onStallWarning(StallWarning warning){

  }
  // onException : une erreur est survenue (d\u00e9connexion d'internet, etc...)
  public void onException(Exception ex) 
  {
    ex.printStackTrace();
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "saveTheDay" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
