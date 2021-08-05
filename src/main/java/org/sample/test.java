package org.sample;



import org.openjdk.jmh.infra.Blackhole;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;




public class test {



    public static void main(String[] args) throws IOException {
        int n = 5;

        Map<String,Integer> output= new TreeMap<>();
        ArrayList<String> itemList = new ArrayList<String>();
        //
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
            int i = 0;
            //skips first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                itemList.add(data[1]);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        HashSet<String> uniqueItemList = new HashSet(itemList);

        Consumer<String> addConsumer = t -> output.put(t,Collections.frequency(itemList,t));

        uniqueItemList.parallelStream().forEach(addConsumer);

        //output.forEach((key, value) -> System.out.println(key + " : " + value));
        List<Map.Entry<String,Integer>> sorted = output.entrySet().parallelStream().sorted(Map.Entry.comparingByValue()).limit(n).
                collect(Collectors.toList());



        System.out.println(getTotal(2,"data.csv"));


        //sorted.stream().forEach(s -> System.out.println(s));
        //System.out.println(matchingName.get(2));
        //System.out.println(list.subList(0,3).toString());
        //list.forEach(System.out::println);
    }

    public static double getTotal(int m, String filename){
        String line;
        int monthAsNumber = m;
        double totalCost= 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int i = 0;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                Date date = dateFormat.parse(data[4]);

                if(date.getMonth() == monthAsNumber-1){
                    totalCost= totalCost+ Double.parseDouble(data[2]);
                }
            }
        } catch (Exception e){
            System.out.println("Error: "+ e);
        }

        return totalCost;
    }




    public static ArrayList<String> getLines(String fileName){

        ArrayList<String> lines = new ArrayList<String>();
        try (Scanner s = new Scanner(new File(fileName));) {
            while (s.hasNextLine()) {
                lines.add(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lines;
    }




    public static String getOrder2(int id, String filename){
        String buffer;
        String stringId = String.valueOf(id);
        String line;
        String finalOrder="";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if(data[0].equals(stringId) ){
                    //System.out.println(data[0]+""+data[1]+data[2]+data[3]);
                    finalOrder= data[0]+" "+data[1]+data[2]+data[3];
                    break;
                }

            }
        } catch (Exception e){
            System.out.println("could not be located");
        }

        System.out.println(finalOrder);
        return finalOrder;
    }



    public static double getTotalCostOfOrdersDuring2(int monthAsNumber, String filename){
        String line;
        String finalOrder="";
        ArrayList<Double> allNums = new ArrayList();
        double totalCost=0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                Date date = dateFormat.parse(data[4]);

                double number = 0;
                if(date.getMonth() == monthAsNumber-1 )
                {
                    number = Double.parseDouble(data[2]);
                    allNums.add(number);
                }
            }
        } catch (Exception e){
            System.out.println("there is an error");
        }

        for (double num: allNums) {
            totalCost = totalCost + num;
        }
        return totalCost;
    }

    public static double getTotalCostOfOrdersDuring3(int monthAsNumber, String filename){

        ArrayList<String> lines = new ArrayList<String>();
        double totalCost = 0.0;
        List<String> matchingName = null;
        try{
            lines= getLines(filename);

            matchingName= Stream.of(lines.toString().split(","))
                    .map (x -> new String(x))
                    .collect(Collectors.toList());
            SimpleDateFormat dateFormat =new SimpleDateFormat("dd/MM/yyyy");
            int i = 9;
            int y =  7;




        }catch (Exception e){
            System.out.println("Error: "+ e);
        }

        return totalCost;
    }















}


