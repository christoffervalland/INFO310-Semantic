package converting;


import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ArtistConverter {

	/**
	 * Class for converting artistInfo from JSON format to, java objects.
	 * @author Lars Johnsen
	 * @return Artist return a java object of the artist.
	 */
	public Artist convertArtist(JsonObject artistObj){

		if(artistObj.get("artist") != null){
			JsonObject artist = artistObj.get("artist").getAsJsonObject();
			ArrayList<String> similarArtistsURL = new ArrayList<String>();
			ArrayList<String> tagsArray = new ArrayList<String>();

			String name = artist.get("name").getAsString();
			String artistURL = artist.get("url").getAsString();
			JsonObject bioObject = artist.get("bio").getAsJsonObject();
			String bio = bioObject.get("summary").getAsString();

			//Similar comes in an array of JsonObjects as URLS to lastfm.
			if(artist.get("similar").isJsonObject()){
				JsonObject similarObject = artist.get("similar").getAsJsonObject();


				//If similarobject is an Array loop trough and collect all instances.
				if(similarObject.get("artist").isJsonArray()){
					JsonArray similarArtistArray = (JsonArray) similarObject.get("artist");

					for (JsonElement artistInSimilarArray : similarArtistArray){
						JsonObject similarArtistObject = (JsonObject) artistInSimilarArray;

						similarArtistsURL.add(similarArtistObject.get("url").getAsString());
					}	
				}
			}
			if(artist.get("tags").isJsonObject()){
				JsonObject tagsObject = artist.get("tags").getAsJsonObject();
			
				JsonElement obj = tagsObject.get("tag");
				if(obj.isJsonArray()){
					JsonArray tagsJsonArray = (JsonArray) tagsObject.get("tag");
					if(tagsJsonArray.isJsonArray()){	
						for(JsonElement tagsInArtist : tagsJsonArray){
							JsonObject tag = (JsonObject) tagsInArtist;
							tagsArray.add(tag.get("name").getAsString());
						
						}
					}
				}
				else{

				}
			}

			Artist artistInstance = new Artist(name, similarArtistsURL, tagsArray, artistURL, bio);
			return artistInstance;

		}else{return null;
		}
	}

}


