package org.sample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CourseworkResit {

    // Please do not hardcode the filename into the program! It's ok to change the program to prompt the user
    // for the filename, but when I download assignments from LC the filenames get changed so hardcoding can cause problems.
    public CourseworkResit(String filename) {
        //System.out.println(filename);
        // your code here if needed.


    }

    //A main method that will take command line arguments to call the given methods.
    //args [0] = data file filename
    //args [1] = order ID
    //args [2] = month m, as an int e.g. 1 for January (in line with data.csv)
    //args [3] = n for the bottom n least-ordered items
    public static void main(String[] args) {
        // add validation of args -- please do not change the orderings of these
        CourseworkResit cw = new CourseworkResit(args[0]); // add arguments if needed
        System.out.println(cw.getOrder(Integer.parseInt(args[1]),(args[0])));
        System.out.println();
        System.out.println(cw.getTotalCostOfOrdersDuring(Integer.parseInt(args[2]),args[0]));
        System.out.println();
        List<Map.Entry<String, Integer>> bottomItems = cw.getLeastPopularItems2(Integer.parseInt(args[3]),args[0]);
        bottomItems.parallelStream().forEach(s -> System.out.println(s));
       // System.out.println(bottomItems);
        //for (int i = 0; i < Integer.parseInt(args[3]); i++) {
        //    System.out.println(bottomItems[i]+",");
        //}
        System.out.println();
    }

    private static ArrayList<String> lines = new ArrayList<String>();

    //    Your application will provide a method to return details of an order chosen via the ID
    public String getOrder(int id,String filename){
        String order = "";
        //        your code
        ArrayList<String> lines = new ArrayList<String>();
        lines= getLines(filename);

        String match;
        ArrayList<String> idList = new ArrayList<String>();
        for(String element: lines){
            try (Scanner lineScanner = new Scanner(element)){
                lineScanner.useDelimiter(",");
                if (lineScanner.hasNext()){
                    // System.out.println("after next");
                    idList.add(lineScanner.next());
                    if ( id == idList.size()){
                        order = lines.get(idList.size());
                    }
                }
            }catch (Exception e){
                order = "out of bounds";
            }
        }
        return order;
    }

    public String getOrder2(int id,String filename){
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

                }
            }
        } catch (Exception e){
            System.out.println("Error: "+ e);
        }
        return finalOrder;
    }

    public String getOrder3(int id,String filename){
        String stringId = String.valueOf(id);
        ArrayList<String> lines = new ArrayList<String>();
        Optional<String> matchingID = null;

        try {
            lines= getLines(filename);
            matchingID = lines.stream()
                    .filter(x -> x.startsWith(stringId))
                    .findFirst();
        }catch (Exception e)
        {
            System.out.println("Error: "+ e);
        }


        return matchingID.get();
    }

    //    Your application will provide a method to return the total cost of orders made in a particular month
    public double getTotalCostOfOrdersDuring(int m, String filename){
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
    public double getTotalCostOfOrdersDuring2(int m, String filename){
        String line;
        int monthAsNumber = m;
        ArrayList<Double> allNums = new ArrayList();
        double totalCost=0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            //skips first line
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
            System.out.println("Error: "+ e);
        }
        for (double num: allNums) {
            totalCost = totalCost + num;
        }
        return totalCost;
    }
    public double getTotalCostOfOrdersDuring3(int m, String filename){
        int monthAsNumber = m;
        ArrayList<String> lines = new ArrayList<String>();
        double totalCost = 0.0;
        List<String> matchingName = null;
        int i = 9;
        Date date =null;
        int y =  7;
        try{
            lines= getLines(filename);
            matchingName= Stream.of(lines.toString().split(","))
                    .map (x -> new String(x))
                    .collect(Collectors.toList());
            SimpleDateFormat dateFormat =new SimpleDateFormat("dd/MM/yyyy");
            for (String line: matchingName) {
                if (i <5006) {
                    date = dateFormat.parse(matchingName.get(i));
                    if(date.getMonth() == monthAsNumber-1 )
                    {
                        totalCost = totalCost + Double.parseDouble(matchingName.get(y));
                    }
                    i += 5;
                    y += 5;

                }
            }

        }catch (Exception e){
            System.out.println("Error: "+ e);
        }

        return totalCost;


    }

    //    Your application will provide a method to return the least popular items based on number of times ordered
    public List<Map.Entry<String, Integer>> getLeastPopularItems(int n, String filename){

        //        your code

        String[] items = new String[n];
        List lines = null;
        try {
            lines = Files.lines(Paths.get("data.csv"))
                    .skip(1).collect(Collectors.toList());
        }catch (Exception e){

            System.out.println("error reading file");
        }


        List<String> matchingName= Stream.of(lines.toString().split(","))
                .collect(Collectors.toList());
        Integer counter= 1;
        Integer i = 0;

        //change datastructure to ArrayList
        String[] listOfItems = new String[999];
        System.out.println(matchingName.get(4996));
        for (String line : matchingName) {
            if (counter<4997 & i < 999) {
                listOfItems[i] = matchingName.get(counter);
                counter += 5;
                i+=1;

            }
            //System.out.println(Arrays.toString(listOfItems));
        }
        //System.out.println(Arrays.toString(listOfItems));
        List uniqueList = Arrays.stream(listOfItems)
                .distinct().collect(Collectors.toList());
        Map<String, Integer> map = new TreeMap<>();
        for (Object x: uniqueList) {
            map.put(x.toString(),0);
        }
        for (int x =0 ; x<= listOfItems.length-1; x++){
            for(int y=0 ; y <= uniqueList.size()-1;y++){
                if(listOfItems[x].equals(uniqueList.get(y))){
                    map.put(uniqueList.get(y).toString(), map.get(uniqueList.get(y)) + 1);
                }
                //System.out.println("x"+listOfItems[x] +"  y"+ uniqueList.get(y));
            }
        }

        //https://www.delftstack.com/howto/java/how-to-sort-a-map-by-value-in-java/#:~:text=500%202%3D1020-,Sort%20a%20Map%20Using%20sorted()%20Method,key%2C%20value%3E%20by%20values.
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        System.out.println(list.subList(0,n));

        // return items;


        return list.subList(0,n);
    }


    public List<Map.Entry<String,Integer>> getLeastPopularItems2(int n, String filename){

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

        return sorted;
    }


    public ArrayList<String> getLines(String file){
        ArrayList<String> lines = new ArrayList<String>();
        try (Scanner s = new Scanner(new File(file));) {
            while (s.hasNextLine()) {
                lines.add(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lines;
    }

}
