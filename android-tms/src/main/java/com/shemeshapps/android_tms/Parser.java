package com.shemeshapps.android_tms;

import com.shemeshapps.android_tms.Models.BasicClass;
import com.shemeshapps.android_tms.Models.DrexelTerm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomer on 1/6/15.
 */
public class Parser {

    public static List<DrexelTerm> ParseHomepage(String html)
    {
        Element body = Jsoup.parse(html).body();
        Elements terms = body.getElementsByClass("term");
        List<DrexelTerm> allDrexelTerms = new ArrayList<>();
        int index = 1;
        for(Element term:terms)
        {
            allDrexelTerms.add(new DrexelTerm(term.getElementsByAttribute("href").text(),term.getElementsByAttribute("href").attr("href"),index));
            index++;
        }
        return allDrexelTerms;
    }


    public static List<BasicClass> ParseClassList(String html)
    {
        List<BasicClass> allClasses = new ArrayList<>();
        Element body = Jsoup.parse(html).body();
        Elements classes = body.getElementsByClass("tableHeader").get(0).parent().children();
        classes.remove(0);

        return allClasses;
    }

}
