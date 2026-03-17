import java.util.*;

class week1week2{

    static class TokenBucket{
        int tokens;
        int maxTokens;
        long lastRefill;
        int refillRate; // tokens per second

        TokenBucket(int max,int rate){
            maxTokens=max;
            tokens=max;
            refillRate=rate;
            lastRefill=System.currentTimeMillis();
        }

        // refill tokens
        void refill(){
            long now=System.currentTimeMillis();
            long seconds=(now-lastRefill)/1000;
            int add=(int)(seconds*refillRate);

            if(add>0){
                tokens=Math.min(maxTokens,tokens+add);
                lastRefill=now;
            }
        }

        // allow request
        boolean allow(){
            refill();
            if(tokens>0){
                tokens--;
                return true;
            }
            return false;
        }
    }

    static class RateLimiter{

        Map<String,TokenBucket> map=new HashMap<>();
        int maxTokens=1000;
        int refillRate=1000/3600; // per second

        // check limit
        void check(String clientId){

            map.putIfAbsent(clientId,new TokenBucket(maxTokens,refillRate));
            TokenBucket tb=map.get(clientId);

            if(tb.allow()){
                System.out.println("Allowed ("+tb.tokens+" tokens left)");
            }else{
                System.out.println("Denied (Rate limit exceeded)");
            }
        }

        // status
        void status(String clientId){
            TokenBucket tb=map.get(clientId);
            if(tb!=null){
                System.out.println("Used: "+(tb.maxTokens-tb.tokens)+
                        ", Limit: "+tb.maxTokens+
                        ", Remaining: "+tb.tokens);
            }
        }
    }

    public static void main(String[] args)throws Exception{

        RateLimiter rl=new RateLimiter();

        String client="abc123";

// simulate requests
        for(int i=0;i<5;i++){
            rl.check(client);
            Thread.sleep(200);
        }

// simulate burst
        for(int i=0;i<1005;i++){
            rl.check(client);
        }

        rl.status(client);
    }
}