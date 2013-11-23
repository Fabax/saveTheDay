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
String[] hashtags = {"doctorwho","savetheday","doctorwhof4","dalek","DrWho"," DayOfTheDoctor"};
boolean strartSketch = true;
boolean initTwitter = true;
int sec ;


void setup(){
}

void draw(){
	sec = second();
	if(strartSketch && sec == 59){
		start();
		strartSketch = false;
	}
}

void start(){
	if(initTwitter){
		model = new ModelTwitter(60000);	
		model.listenToHashtag(hashtags);
		println("in");
		initTwitter = false;
	}else{
		println("out");
	}
}