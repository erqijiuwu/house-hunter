package com.neat.data.collect;

import java.io.IOException;
//import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLPeerUnverifiedException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
//import org.jsoup.nodes.Element;

public class HttpClient {
    public static void main(String[] arfs) {
        String str = getContentFromUrl("https://www.google.com/");
        System.out.println(str);

        try {
            List<Map<String, String>> list = getPropertiesListUrl(1);

            list.forEach(element -> {
                element.forEach((key, value) -> {
                    System.out.println(String.format("id=%s, href=%s", key, value));
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Map<String, String>> getPropertiesListUrl(int page) throws IOException {
        String https_url = "https://www.trademe.co.nz/browse/categoryattributesearchresults.aspx?cid=5748&search=1&nofilters=1&originalsidebar=1&rptpath=350-5748-&rsqid=f57ddb79c3b34ddbb04f35d99443ea5b&key=267294992&sort_order=property_feature";
//        String content = getContentFromUrl(String.format("%s&page=%d", https_url, page));
        Document doc = Jsoup.connect(String.format("%s&page=%d", https_url, page)).get();
        Elements detailPages = doc.select("#ListViewList a");

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        detailPages.forEach(element -> {
            String id = element.attr("id");
            String href = element.attr("href");
            Map<String, String> map = new HashMap<>();
            map.put(id, href);
            list.add(map);
        });

        return list;
    }

    private static String getContentFromUrl(String https_url) {
        StringBuilder buf = new StringBuilder();

        URL url;
        try {

            url = new URL(https_url);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            //dump all the content
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String input;

            while ((input = br.readLine()) != null) {
//                System.out.println(input);
                buf.append(input);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return buf.toString();
    }
}
