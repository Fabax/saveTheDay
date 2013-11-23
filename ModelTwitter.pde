public class ModelTwitter {
  //configuration de twitter
  Twitter twitter;
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
         cb.setOAuthConsumerKey("34sJKGiU71xaVBVeutDA");
    cb.setOAuthConsumerSecret("sMGrXuf2zbhS29cEV9HYHDeNoU45aoGWcw1t2JbJMMk");
    cb.setOAuthAccessToken("1272243708-woC2NKzPErcj9CAsUGURNOmS9OL4ISdFI9hyQmh");
    cb.setOAuthAccessTokenSecret("D846JFR6nH9v13icgBcLfyNCUVWg53R9jhWwjmwuBU");

      c = cb.build();
      TwitterFactory tf = new TwitterFactory(c);
      twitter = tf.getInstance();
}

  //Methode that will listen to a specifique hashtag and will return the result in live
  public void listenToHashtag(String[] _keyWords){
        TwitterStream ts = new TwitterStreamFactory(c).getInstance();
        FilterQuery filterQuery = new FilterQuery(); 
        filterQuery.track(_keyWords);
        // On fait le lien entre le TwitterStream (qui récupère les messages) et notre écouteur  
        ts.addListener(new TwitterListener(timerListener));
         // On démarre la recherche !
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