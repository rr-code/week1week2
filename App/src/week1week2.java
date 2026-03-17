import java.util.*;

class week1week2{

    static class Entry{
        String domain;
        String ip;
        long expiry;
        Entry(String d,String i,long ttl){
            domain=d;
            ip=i;
            expiry=System.currentTimeMillis()+ttl*1000;
        }
    }

    static class DNSCache{
        int capacity;
        Map<String,Entry> map;
        LinkedList<String> lru;
        int hits=0,miss=0;

        DNSCache(int cap){
            capacity=cap;
            map=new HashMap<>();
            lru=new LinkedList<>();
        }

        String resolve(String domain){
            long now=System.currentTimeMillis();
            if(map.containsKey(domain)){
                Entry e=map.get(domain);
                if(e.expiry>now){
                    hits++;
                    lru.remove(domain);
                    lru.addFirst(domain);
                    return "Cache HIT -> "+e.ip;
                }else{
                    map.remove(domain);
                    lru.remove(domain);
                }
            }
            miss++;
            String ip=queryUpstream(domain);
            put(domain,ip,5); //TTL=5s example
            return "Cache MISS -> "+ip;
        }

        void put(String domain,String ip,int ttl){
            if(map.size()>=capacity){
                String last=lru.removeLast();
                map.remove(last);
            }
            Entry e=new Entry(domain,ip,ttl);
            map.put(domain,e);
            lru.addFirst(domain);
        }

        String queryUpstream(String domain){
            Random r=new Random();
            return "172.217.14."+r.nextInt(255);
        }

        void clean(){
            long now=System.currentTimeMillis();
            Iterator<String> it=lru.iterator();
            while(it.hasNext()){
                String d=it.next();
                if(map.get(d).expiry<=now){
                    it.remove();
                    map.remove(d);
                }
            }
        }

        void stats(){
            int total=hits+miss;
            double rate=total==0?0:(hits*100.0/total);
            System.out.println("Hit Rate: "+rate+"%");
        }
    }

    public static void main(String[] args)throws Exception{
        DNSCache cache=new DNSCache(3);

        System.out.println(cache.resolve("google.com"));
        Thread.sleep(1000);
        System.out.println(cache.resolve("google.com"));

        Thread.sleep(6000);
        cache.clean();
        System.out.println(cache.resolve("google.com"));

        cache.stats();
    }
}