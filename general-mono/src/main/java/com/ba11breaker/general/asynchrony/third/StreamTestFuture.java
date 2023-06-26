package com.ba11breaker.general.asynchrony.third;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamTestFuture {
    public static String rpcCall(String ip, String param) {
        System.out.println(ip + " rpcCall:" + param);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param;
    }

    public static void withoutFuture() {
        List<String> ipList = new ArrayList<String>();

        for (int i = 1; i <= 10; ++i) {
            ipList.add("192.168.9." + i);
        }

        long start = System.currentTimeMillis();
        List<String> result = new ArrayList<>();

        for (String ip: ipList) {
            result.add(rpcCall(ip, ip));
        }

        result.stream().forEach(r -> System.out.println(r));
        System.out.println("cost:" + (System.currentTimeMillis()-start));
    }

    public static void withFuture() {
        List<String> ipList = new ArrayList<String>();

        for (int i = 1; i <= 10; ++i) {
            ipList.add("192.168.9." + i);
        }

        long start = System.currentTimeMillis();
        List<CompletableFuture<String>> futureList = ipList.stream()
        .map((ip) -> {
            return CompletableFuture.supplyAsync(() -> {
                return rpcCall(ip, ip);
            });
        })
        .collect(Collectors.toList());

        List<String> resultList = futureList.stream().map(
            future -> future.join()
        ).collect(Collectors.toList());
        resultList.stream().forEach(r -> System.out.println(r));

        System.out.println("cost:" + (System.currentTimeMillis()-start));
    }

    public static void main(String[] args) {
        withoutFuture();
        withFuture();
    }
}
