import java.util.*;

class week1week2{

    static class Transaction{
        int id,amount;
        String merchant,time,account;

        Transaction(int i,int a,String m,String t,String acc){
            id=i;amount=a;merchant=m;time=t;account=acc;
        }
    }

    static class SystemDS{

        List<Transaction> list=new ArrayList<>();

        void add(Transaction t){
            list.add(t);
        }

        // classic two sum
        void twoSum(int target){
            Map<Integer,Transaction> map=new HashMap<>();

            for(Transaction t:list){
                int comp=target-t.amount;
                if(map.containsKey(comp)){
                    System.out.println("Pair: "+map.get(comp).id+" , "+t.id);
                }
                map.put(t.amount,t);
            }
        }

        // duplicate detection
        void duplicates(){
            Map<String,List<Transaction>> map=new HashMap<>();

            for(Transaction t:list){
                String key=t.amount+"-"+t.merchant;
                map.putIfAbsent(key,new ArrayList<>());
                map.get(key).add(t);
            }

            for(String k:map.keySet()){
                List<Transaction> l=map.get(k);
                if(l.size()>1){
                    System.out.print("Duplicate: ");
                    for(Transaction t:l){
                        System.out.print(t.id+" ");
                    }
                    System.out.println();
                }
            }
        }

        // k-sum (simple recursion)
        void kSum(int k,int target){
            findK(0,k,target,new ArrayList<>());
        }

        void findK(int index,int k,int target,List<Integer> res){
            if(k==0&&target==0){
                System.out.println(res);
                return;
            }
            if(index>=list.size()||k<0||target<0)return;

            Transaction t=list.get(index);

// include
            res.add(t.id);
            findK(index+1,k-1,target-t.amount,res);

// exclude
            res.remove(res.size()-1);
            findK(index+1,k,target,res);
        }
    }

    public static void main(String[] args){

        SystemDS ds=new SystemDS();

        ds.add(new Transaction(1,500,"StoreA","10:00","acc1"));
        ds.add(new Transaction(2,300,"StoreB","10:15","acc2"));
        ds.add(new Transaction(3,200,"StoreC","10:30","acc3"));
        ds.add(new Transaction(4,500,"StoreA","11:00","acc4"));

// two sum
        ds.twoSum(500);

// duplicates
        ds.duplicates();

// k sum
        ds.kSum(3,1000);
    }
}