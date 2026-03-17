import java.util.*;

public class week1week2{

    static class PlagiarismDetector{

        int N=3; // n-gram size
        Map<String,Set<String>> index=new HashMap<>();

        // Generate n-grams
        List<String> getNGrams(String text){
            List<String> grams=new ArrayList<>();
            String[] words=text.toLowerCase().split("\\s+");
            for(int i=0;i<=words.length-N;i++){
                StringBuilder sb=new StringBuilder();
                for(int j=0;j<N;j++){
                    sb.append(words[i+j]).append(" ");
                }
                grams.add(sb.toString().trim());
            }
            return grams;
        }

        // Add document to database
        void addDocument(String docId,String text){
            List<String> grams=getNGrams(text);
            for(String g:grams){
                index.putIfAbsent(g,new HashSet<>());
                index.get(g).add(docId);
            }
        }

        // Analyze document
        void analyze(String docId,String text){
            List<String> grams=getNGrams(text);
            Map<String,Integer> matchCount=new HashMap<>();

            for(String g:grams){
                if(index.containsKey(g)){
                    for(String d:index.get(g)){
                        matchCount.put(d,matchCount.getOrDefault(d,0)+1);
                    }
                }
            }

            System.out.println("Total n-grams: "+grams.size());

            for(String d:matchCount.keySet()){
                int matches=matchCount.get(d);
                double similarity=(matches*100.0)/grams.size();
                System.out.println("Matched with "+d+" -> "+matches+" n-grams");
                System.out.println("Similarity: "+similarity+"%");
                if(similarity>50){
                    System.out.println("PLAGIARISM DETECTED");
                }
            }
        }
    }

    public static void main(String[] args){

        PlagiarismDetector pd=new PlagiarismDetector();

// Existing documents
        pd.addDocument("essay_089","data structures and algorithms are important for coding");
        pd.addDocument("essay_092","java programming language is widely used in software development");

// New document
        String newDoc="data structures and algorithms are widely used in programming";

        pd.analyze("essay_123",newDoc);
    }
}}