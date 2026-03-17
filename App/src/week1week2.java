import java.util.*;

class week1week2{

    static class Dashboard{

        Map<String,Integer> pageViews=new HashMap<>();
        Map<String,Set<String>> uniqueUsers=new HashMap<>();
        Map<String,Integer> sourceCount=new HashMap<>();

        // process event
        void processEvent(String url,String userId,String source){

// page views
            pageViews.put(url,pageViews.getOrDefault(url,0)+1);

// unique users
            uniqueUsers.putIfAbsent(url,new HashSet<>());
            uniqueUsers.get(url).add(userId);

// source count
            sourceCount.put(source,sourceCount.getOrDefault(source,0)+1);
        }

        // get top pages
        List<String> getTopPages(int k){
            PriorityQueue<String> pq=new PriorityQueue<>(
                    (a,b)->pageViews.get(a)-pageViews.get(b)
            );

            for(String url:pageViews.keySet()){
                pq.add(url);
                if(pq.size()>k){
                    pq.poll();
                }
            }

            List<String> result=new ArrayList<>();
            while(!pq.isEmpty()){
                result.add(pq.poll());
            }
            Collections.reverse(result);
            return result;
        }

        // display dashboard
        void getDashboard(){

            System.out.println("Top Pages:");

            List<String> top=getTopPages(10);

            for(String url:top){
                int views=pageViews.get(url);
                int unique=uniqueUsers.get(url).size();
                System.out.println(url+" - "+views+" views ("+unique+" unique)");
            }

            System.out.println("\nTraffic Sources:");
            for(String s:sourceCount.keySet()){
                System.out.println(s+" - "+sourceCount.get(s));
            }
        }
    }

    public static void main(String[] args)throws Exception{

        Dashboard db=new Dashboard();

// simulate events
        db.processEvent("/article/breaking-news","user1","google");
        db.processEvent("/article/breaking-news","user2","facebook");
        db.processEvent("/sports/championship","user1","direct");
        db.processEvent("/sports/championship","user3","google");
        db.processEvent("/article/breaking-news","user1","google");

// simulate real-time update every 5 sec
        Thread.sleep(2000);
        db.getDashboard();
    }
}