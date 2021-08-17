/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 7, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)

@State(Scope.Benchmark)
public class MyBenchmark2 {
    String filename = "data.csv";

    @Param({"1","4","6","10","12"})
    public String m;

    String nameOFfile = "src/main/java/org/sample/data.csv";



    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
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


    //@Benchmark
    public void getTotalCostOfOrdersDuring(Blackhole bh){
        CourseworkResit file =  new CourseworkResit(nameOFfile);

        bh.consume(file.getTotalCostOfOrdersDuring(Integer.parseInt(m), nameOFfile));

    }

    //@Benchmark
    public void getTotalCostOfOrdersDuring2(Blackhole bh){
        CourseworkResit file =  new CourseworkResit(nameOFfile);

        bh.consume(file.getTotalCostOfOrdersDuring2(Integer.parseInt(m), nameOFfile));
    }

     //@Benchmark
    public void getTotalCostOfOrdersDuring3(Blackhole bh){

        CourseworkResit file =  new CourseworkResit(nameOFfile);

        bh.consume(file.getTotalCostOfOrdersDuring3(Integer.parseInt(m), nameOFfile));

    }



}
