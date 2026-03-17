import java.util.*;

class week1week2{

    static class Autocomplete{

        Map<String,Integer> freq=new HashMap<>();

        // update frequency
        void update(String query){
            freq.put(query,freq.getOrDefault(query,0)+1);
        }

        // search top k suggestions
        List<String> search(String prefix,int k){

            PriorityQueue<String> pq=new PriorityQueue<>(
                    (a,b)->{
                        if(freq.get(a)==freq.get(b)){
                            return b.compareTo(a);
                        }
                        return freq.get(a)-freq.get(b);
                    }
            );

            for(String q:freq.keySet()){
                if(q.startsWith(prefix)){
                    pq.add(q);
                    if(pq.size()>k){
                        pq.poll();
                    }
                }
            }

            List<String> res=new ArrayList<>();
            while(!pq.isEmpty()){
                res.add(pq.poll());
            }
            Collections.reverse(res);
            return res;
        }
    }

    public static void main(String[] args){

        Autocomplete ac=new Autocomplete();

// add queries
        ac.update("java tutorial");
        ac.update("java tutorial");
        ac.update("javascript guide");
        ac.update("java download");
        ac.update("java download");
        ac.update("java download");

// search
        List<String> result=ac.search("jav",3);

        for(String s:result){
            System.out.println(s+" ("+ac.freq.get(s)+")");
        }
    }
}