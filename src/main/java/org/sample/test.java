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
        int n = 12;

        String[] items = new String[n];

        List lines = null;
        try {
            lines = Files.lines(Paths.get("data.csv"))
                    .skip(1).collect(Collectors.toList());
            System.out.println(lines);
        }catch (Exception e){

            System.out.println("error reading file");
        }


        List<String> matchingName= Stream.of(lines.toString().split(","))
                .collect(Collectors.toList());
        Integer counter= 1;
        Integer i = 0;

        //change datastructure to ArrayList
        String[] listOfItems = new String[999];
        //System.out.println(matchingName.get(4996));
        for (String line : matchingName) {
            if (counter<5008 & i < 999) {
                listOfItems[i] = matchingName.get(counter);
                counter += 5;
                i+=1;

            }
            //System.out.println(Arrays.toString(listOfItems));
        }


        //System.out.println(Arrays.toString(listOfItems));
        List uniqueList = Arrays.stream(listOfItems)
                .distinct().collect(Collectors.toList());

        System.out.println("uni "+uniqueList);
        Map<String, Integer> map = new TreeMap<>();
        for (Object x: uniqueList) {
            map.put(x.toString(),0);
        }
        for (int x =0 ; x<=  listOfItems.length-1; x++){
            for(int y=0 ; y <= uniqueList.size()-1;y++){
                if(listOfItems[x].equals(uniqueList.get(y))){
                    map.put(uniqueList.get(y).toString(), map.get(uniqueList.get(y)) + 1);
                    //System.out.println("x"+listOfItems[x] +"  y"+ uniqueList.get(y));
                }

            }
        }

        //https://www.delftstack.com/howto/java/how-to-sort-a-map-by-value-in-java/#:~:text=500%202%3D1020-,Sort%20a%20Map%20Using%20sorted()%20Method,key%2C%20value%3E%20by%20values.
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        System.out.println("sublist:  "+list.subList(0,n));

        // return items;

      //  System.out.println(getTotal(2,"data.csv"));


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


