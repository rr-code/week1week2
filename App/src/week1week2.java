import java.util.*;

class week1week2{

    static class Cache{

        // L1 (LRU using LinkedHashMap)
        LinkedHashMap<String,String> L1=new LinkedHashMap<>(100,0.75f,true){
            protected boolean removeEldestEntry(Map.Entry<String,String> e){
                return size()>3;
            }
        };

        // L2
        HashMap<String,String> L2=new HashMap<>();

        // L3 (database)
        HashMap<String,String> L3=new HashMap<>();

        Map<String,Integer> accessCount=new HashMap<>();

        int l1Hit=0,l2Hit=0,l3Hit=0,total=0;

        // get video
        void get(String id){
            total++;

// L1
            if(L1.containsKey(id)){
                l1Hit++;
                System.out.println("L1 HIT -> "+id);
                return;
            }

// L2
            if(L2.containsKey(id)){
                l2Hit++;
                System.out.println("L2 HIT -> "+id);

// promote to L1
                L1.put(id,L2.get(id));
                return;
            }

// L3
            if(L3.containsKey(id)){
                l3Hit++;
                System.out.println("L3 HIT -> "+id);

// add to L2
                L2.put(id,L3.get(id));
                accessCount.put(id,accessCount.getOrDefault(id,0)+1);

// promote if frequent
                if(accessCount.get(id)>1){
                    L1.put(id,L3.get(id));
                }
                return;
            }

            System.out.println("MISS -> "+id);
        }

        // stats
        void stats(){
            System.out.println("L1 Hit Rate: "+(l1Hit*100.0/total)+"%");
            System.out.println("L2 Hit Rate: "+(l2Hit*100.0/total)+"%");
            System.out.println("L3 Hit Rate: "+(l3Hit*100.0/total)+"%");
        }
    }

    public static void main(String[] args){

        Cache c=new Cache();

// preload database
        c.L3.put("video1","data1");
        c.L3.put("video2","data2");
        c.L3.put("video3","data3");

// access pattern
        c.get("video1");
        c.get("video1");
        c.get("video2");
        c.get("video1");
        c.get("video3");
        c.get("video1");

        c.stats();
    }
}