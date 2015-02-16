package com.shemeshapps.android_tms;

import com.shemeshapps.android_tms.Models.BasicClass;
import com.shemeshapps.android_tms.Models.DrexelTerm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        classes.remove(classes.size()-1);
        for(Element curClass:classes)
        {

            BasicClass basicClass = new BasicClass();
            Elements children = curClass.children();
            basicClass.subject = children.get(0).text();
            basicClass.courseNum = children.get(1).text();
            basicClass.type = children.get(2).text();
            basicClass.sec = children.get(3).text();
            basicClass.crn = children.get(4).child(0).child(0).text();
            String stringEnroll = children.get(4).child(0).attr("title");

            if(stringEnroll.equals("FULL"))
            {
                basicClass.maxEnroll = 0;
                basicClass.curEnrolled = 0;
                basicClass.classFull = true;
            }
            else
            {
                final Pattern pattern = Pattern.compile("^Max enroll=(\\\\d+); Enroll=(\\\\d+)$");
                final Matcher matcher = pattern.matcher(stringEnroll);
                matcher.find();
                basicClass.maxEnroll = Integer.parseInt(matcher.group(1));
                basicClass.curEnrolled = Integer.parseInt(matcher.group(2));
                basicClass.classFull = false;
            }


            basicClass.courseTitle = children.get(5).text();

            Element dateTime =  children.get(6).child(0).child(0).child(0);
            basicClass.days = dateTime.child(0).text();
            basicClass.stringTime = dateTime.child(1).text();



            basicClass.instructor = children.get(7).text();
            allClasses.add(basicClass);

        }

        return allClasses;
    }

}
