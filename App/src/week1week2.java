import java.util.*;

class week1week2{

    static class Slot{
        String plate;
        long entryTime;
        boolean occupied;

        Slot(){
            plate=null;
            occupied=false;
        }
    }

    static class ParkingLot{

        Slot[] table;
        int size;
        int occupiedCount=0;
        int totalProbes=0;

        ParkingLot(int n){
            size=n;
            table=new Slot[n];
            for(int i=0;i<n;i++)table[i]=new Slot();
        }

        // hash function
        int hash(String plate){
            return Math.abs(plate.hashCode())%size;
        }

        // park vehicle
        void park(String plate){
            int index=hash(plate);
            int probes=0;

            while(table[index].occupied){
                index=(index+1)%size;
                probes++;
            }

            table[index].plate=plate;
            table[index].occupied=true;
            table[index].entryTime=System.currentTimeMillis();

            occupiedCount++;
            totalProbes+=probes;

            System.out.println("Assigned spot #"+index+" ("+probes+" probes)");
        }

        // exit vehicle
        void exit(String plate){
            int index=hash(plate);

            while(table[index].occupied){
                if(table[index].plate.equals(plate)){
                    long duration=(System.currentTimeMillis()-table[index].entryTime)/1000;
                    table[index].occupied=false;
                    table[index].plate=null;
                    occupiedCount--;

                    double fee=duration*0.01;

                    System.out.println("Spot #"+index+" freed. Duration: "+duration+"s Fee: $"+fee);
                    return;
                }
                index=(index+1)%size;
            }

            System.out.println("Vehicle not found");
        }

        // stats
        void stats(){
            double occupancy=(occupiedCount*100.0)/size;
            double avgProbes=occupiedCount==0?0:(totalProbes*1.0/occupiedCount);

            System.out.println("Occupancy: "+occupancy+"%");
            System.out.println("Avg Probes: "+avgProbes);
        }
    }

    public static void main(String[] args)throws Exception{

        ParkingLot pl=new ParkingLot(10);

        pl.park("ABC1234");
        pl.park("ABC1235");
        pl.park("XYZ9999");

        Thread.sleep(2000);

        pl.exit("ABC1234");

        pl.stats();
    }
}