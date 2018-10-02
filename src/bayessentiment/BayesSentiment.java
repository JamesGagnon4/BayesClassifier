
package bayessentiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BayesSentiment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
  int posLength;
  File file = new File("C://Users/James Gagnon/Documents/Machine Learning/Movie Reviews/TrainingDataPos.txt");
  posLength = checkLen(file);
  String[] PosWords = preProc(file, posLength);
  ArrayList<String> pos = new ArrayList<String>(Arrays.asList(PosWords));
  
  int negLength;
  File file2 = new File("C://Users/James Gagnon/Documents/Machine Learning/Movie Reviews/TrainingDataNeg.txt");
  negLength = checkLen(file2);
  String[] negWords = preProc(file2, negLength);
  ArrayList<String> negs = new ArrayList<String>(Arrays.asList(negWords));
  
    int testLength;
  File file3 = new File("C://Users/James Gagnon/Documents/Machine Learning/Movie Reviews/TestData1.txt");
  testLength = checkLen(file3);
  String[] testWords = preProc(file3, testLength);
  ArrayList<String> test = new ArrayList<String>(Arrays.asList(testWords));
  Scanner testWord = new Scanner(file3);
  
  Scanner s = new Scanner(new File("C://Users/James Gagnon/Documents/Machine Learning/Movie Reviews/StopWords.txt"));
ArrayList<String> StopList = new ArrayList<String>();
while (s.hasNext()){
    StopList.add(s.next());
}
s.close();
  pos = remStopWords(pos,StopList);      //remove stopwords.
  negs = remStopWords(negs,StopList);
  test = remStopWords(test,StopList);
  
  bayesTest(testWord,pos,negs, StopList);
  
    }
    
    public static double checkProb(String word, ArrayList<String> list){
        int count = 0;
        for(int i = 0; i < list.size();i++){
            if(word.equals(list.get(i))){
              //  System.out.println("Match found for "+word);
                count++;
           
        }
    }
        double prob = (double)count/(double)list.size();
      //  System.out.println(prob);
        return prob;
    }   
    public static String[] preProc(File file, int length) throws FileNotFoundException{
       Scanner input = new Scanner(file); 
     String[] list = new String[length];
     int count = 0;
     while(input.hasNext()){
     String line = input.next();
     list[count]= line;
     count++;
    
    }
        return list; 
    }
    public static int checkLen(File file) throws FileNotFoundException{
        int count = 0;
     Scanner input = new Scanner(file); 
     while(input.hasNext()){
     String line = input.next();
     count++;
     //System.out.println(line)
    } 
     return count;
    }
    public static ArrayList<String> remStopWords(ArrayList<String> list, ArrayList<String> remWords){
        for(int i = 0;i<list.size();i++){
            if(remWords.contains(list.get(i))){
                list.remove(list.get(i));
            }
        }
        return list;
    }
    public static void bayesTest(Scanner test, ArrayList<String>pos,ArrayList<String>neg, ArrayList<String> StopList){
        double positive, negative;
        int countPos = 0;
        int countNeg = 0;
        positive = 0;
        negative = 0;
        int count = 0;
        while(test.hasNext()){
        String tested = test.nextLine();
       count++;
        String[]split = tested.split("\\s+");    //Split line into individual words
        ArrayList<String> splitList = new ArrayList<String> (Arrays.asList(split));
       splitList = remStopWords(splitList,StopList);
         
        for(int i = 0; i < splitList.size(); i++){
        
        positive += checkProb(splitList.get(i), pos);
        negative += checkProb(splitList.get(i), neg);    //check for positive or negative values.
        }
        if(positive > negative){
           countPos++;
           //System.out.println("Positive.");
        }

        else { countNeg++;}
        positive=0;
        negative=0;   //reset pos and negative stat values before loop iterates
    }
        System.out.println("Total count is "+count);
        System.out.println("Positive counts are "+countPos);
        System.out.println("Negative counts are "+countNeg);
}

}