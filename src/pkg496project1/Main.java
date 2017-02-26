package pkg496project1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Scott Weagley
 * Project 1
 */
public class Main {

    public static void main(String[] args) {
        String fileName = "input.txt";
        
        try{
           FileReader reader = new FileReader(fileName);
           Scanner scanner = new Scanner(reader);
           ArrayList<Interval> intervals = new ArrayList();         
           
           System.out.println("Project 1: Channel Allocation");
           System.out.println("Reading the file: " + fileName + "\n");
           
           while(scanner.hasNextInt()){               
               int start = scanner.nextInt();
               int end = scanner.nextInt();
               
               Interval i = new Interval(start, end);
               intervals.add(i);                              
           }
            
           Collections.sort(intervals);
           LUC(intervals);
           intervals.forEach(item->item.setMarked(false));
           IterEFT(intervals);
           
        } catch (FileNotFoundException e){
            System.out.println("Error: " + e);
        } 
        

    }
    
    public static class Interval implements Comparable<Interval>{
        
        final int start;
        final int end;
        private boolean marked;
            
        public Interval(int inputStart, int inputEnd){
            super();
            start = inputStart;
            end = inputEnd;
            marked = false;
        }
            
        public int getStart(){
            return start;
        }
            
        public int getEnd(){
            return end;
        }
        
        public boolean getMarked(){
            return marked;
        }
        
        public void setMarked(boolean n){
            marked = n;
        }
        
        @Override
        public int compareTo(Interval compareInterval){
            int compareEnd = ((Interval) compareInterval).getEnd();                
            return this.end - compareEnd;
        }
        
        @Override
        public String toString() {
            return "(" + start + ", " + end + ")";
        }
    }
            
    public static void LUC(List<Interval> input){        
        List<Interval> temp = new ArrayList();
        int channel = 1;
        
        System.out.println("LUC:");
        
        for(int i = 0; i < input.size(); i++){
            if(!input.get(i).getMarked()){
            
                input.get(i).setMarked(true);
                temp.add(input.get(i));

                for(int j = i; j < input.size(); j++){

                    if(i != j && !input.get(j).getMarked() && temp.size() == 1){

                        if(temp.get(temp.size()-1).getEnd() < input.get(j).getStart()){
                            temp.add(input.get(j));
                            input.get(j).setMarked(true);
                        }

                    } else if(i != j && !input.get(j).getMarked() && temp.size() > 1){
                       
                        if(temp.get(temp.size()-1).getStart() > input.get(j).getStart()){
                            input.get(j-1).setMarked(false);
                            temp.remove(temp.size() -1);
                            temp.add(input.get(j));
                            input.get(j).setMarked(true);
                        }
                    }
                }
                System.out.print("\t" + channel + ": ");
                temp.forEach(item->System.out.print(item));
                System.out.println("");
                temp.clear();
                channel++;            
            }
        }   
        System.out.println("\n\tChannels Required:  " + (channel - 1) + "\n");
    } 
    
    public static void IterEFT(List<Interval> input){
        List<Interval> temp = new ArrayList();
        int channel = 1;
        
        System.out.println("InterEFT:");
        
        for(int i = 0; i < input.size(); i++){
            if(!input.get(i).getMarked()){
            
                input.get(i).setMarked(true);
                temp.add(input.get(i));

                for(int j = i; j < input.size(); j++){

                    if(i != j && !input.get(j).getMarked()){

                        if(temp.size() >= 1 && temp.get(temp.size()-1).getEnd() < input.get(j).getStart()){
                            temp.add(input.get(j));
                            input.get(j).setMarked(true);
                        }

                    }
                }
                System.out.print("\t" + channel + ": ");
                temp.forEach(item->System.out.print(item));
                System.out.println("");
                temp.clear();
                channel++;            
            }
        }   
        System.out.println("\n\tChannels Required:  " + (channel - 1)  + "\n");
    }
}


