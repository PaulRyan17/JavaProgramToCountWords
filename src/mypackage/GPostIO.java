/*PAUL RYAN
* 15115305*/
package mypackage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
/*THE GPostIO class is responsible for reading in the files and using the information from the files to
* create the gpost objects which can each be analysed individually*/
public class GPostIO {
     //get the text from the file and return an array list with the words
	 public static List<String> getWordsInFile(String path){
	        List<String> words = new ArrayList<String>();
	        try{
	            List<String> lines = Files.readAllLines(Paths.get(path));
	            for (String line : lines) {
	                    if(line.length() != 0){
                            //remove all the numbers in each line or any words containing numbers
                            line = line.replaceAll("\\w*\\d\\w*", "");
                            //now add all the words to my list including hyphens so words like
                            //oompa-loompa can pass
                            //through
                            Collections.addAll(words, line.split("[^\\w-]+"));
	                    }
	            }
	        }
	        catch(IOException e){
	            e.printStackTrace();
	        }

	        return words;
	 }
	 //get the noise words from the text file and return a list of the noisewords
	 private static List<String> getNoiseWords(String path){
	        List<String> noiseWords = new ArrayList<String>();
	        try{
	            List<String> lines = Files.readAllLines(Paths.get(path));
	            for(String line : lines){
	                Collections.addAll(noiseWords,line.split("[^\\w@'-]+"));
	            }
	        }
	        catch(IOException e){
	            e.printStackTrace();
	        }
	
	        return noiseWords;
	 }
	 //create the gpost objects for the text file
	 public static void analyse(String emailPath, String noiseWordsPath){
		 	//load the words in from the file
	        List<String> words = getWordsInFile(emailPath);
	        //list to hold the gpost objects
	        List <GPostEmail> gposts = new ArrayList<GPostEmail>();
	        //variable for the email of the person who sent the gpost
	        String emailOwner;
	        //the text of the gpost
	        List<String> usersWords;

	        for(int i = 0; i < words.size();i++){
                //get the emailOwner of the email
	            emailOwner = words.get(i);
                //set text to empty
	            usersWords = new ArrayList<String>();
	            //move to the start of the text of the gpost i.e. after gPostBegin
                //every email has the same format i.e. WillyWonka@gPost.com
	            i+=4;
                //keep adding the words to text until gpostend
	            while(!words.get(i).equals("gPostEnd")){
	                usersWords.add(words.get(i));
	                i++;
	            }
				//remove the noise words
				usersWords.removeAll(getNoiseWords(noiseWordsPath));
	            //i need to check if I already have an object for
	            //that user as users can have more than one message
	            if(!addIfExists(emailOwner, gposts, usersWords)){
	            	//user did not exist so I create a new gpost object
	            	gposts.add(new GPostEmail(emailOwner, usersWords));
	            }
	        }
		 	//before returning the objects i sort the objects using an anonymous comparator class
		 	Collections.sort(gposts, new Comparator<GPostEmail>() {
				@Override
				public int compare(GPostEmail o1, GPostEmail o2) {
					return o1.getEmailOwner().compareToIgnoreCase(o2.getEmailOwner());
				}
			});
            //return all the gpost objects sorted
         for(GPostEmail g : gposts){
             g.generateReport();
         }
	    }

	 //method to check if i already added the user - if i did i simply add more words
	 //to the list words within the object for that user
	 private static boolean addIfExists(String emailOwner, List<GPostEmail> gposts, List<String> userWords){
		 for(GPostEmail g : gposts){
			 if(g.getEmailOwner().equals(emailOwner)){
				 //add the text to the words
				 g.addToWords(userWords);
				 return true;
			 }
		 }
		 return false;
	 }
}
