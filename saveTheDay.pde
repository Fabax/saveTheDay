import twitter4j.conf.*;
import twitter4j.internal.async.*;
//import twitter4j.internal.org.json.*;
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


ModelTwitter model; 
String[] hashtags = {"#doctorwho","#savetheday"};
GetGeoDetails geo;


void setup(){
	model = new ModelTwitter(60000);	
	model.listenToHashtag(hashtags);
}

void draw(){

}
