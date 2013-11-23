
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
    
    geo = new GetGeoDetails(status);


    makeJson(userId,userName,messageTweet,imageUrl, lang, timeZone, userLocation, time);
  
}

  public void makeJson(String _id, String _user, String _message, 
    String _imageUrl, String _lang, String _timeZone, String _userLocation,
    String _time){
    JSONObject tweetInfos =  new JSONObject();
    JSONObject tweetId =  new JSONObject();

    tweetInfos.setString("userName",_user);
    tweetInfos.setString("message",_message);
    tweetInfos.setString("id",_id);
    tweetInfos.setString("imageUrl",_imageUrl);
    tweetInfos.setString("language",_lang);
    tweetInfos.setString("timeZone",_timeZone);
    tweetInfos.setString("userLocation",_userLocation);
    tweetInfos.setString("time",_time);
   // tweetInfos.setString("userLocation",_userAdress);
    //tweetInfos.setFloat("longitude",_longitude);
   // tweetInfos.setString("tweetTime",_tweetTime);

    tweets.setJSONObject(counter, tweetInfos);
    counter ++;
     
    if(timer.isFinished()){
        String fileName = nameJson();
        saveJSONArray(tweets, "jsons/"+fileName+".json");

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

  // onScrubGeo : récupération d'infos géographiques
  public void onScrubGeo(long userId, long upToStatusId) 
  {
    System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
  }

  public void onStallWarning(StallWarning warning){

  }
  // onException : une erreur est survenue (déconnexion d'internet, etc...)
  public void onException(Exception ex) 
  {
    ex.printStackTrace();
  }
}