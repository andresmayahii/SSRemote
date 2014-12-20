package com.itkloud.ssremote.parser;

import android.util.Xml;

import com.itkloud.ssremote.apis.SlideShare;
import com.itkloud.ssremote.config.CF;
import com.itkloud.ssremote.dto.ItemData;
import com.itkloud.ssremote.dto.ResultItems;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andressh on 20/12/14.
 */
public class SlideShareParse {

    private static final String NS = null;

    public static ResultItems parse(InputStream in,int type) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in,null);
            parser.nextTag();
            return readRoot(parser,type);
        } finally {
            in.close();
        }
    }

    private static ResultItems readRoot(XmlPullParser parser,int type) throws XmlPullParserException, IOException {
        ResultItems result = new ResultItems();
        List<ItemData> entries = new ArrayList<ItemData>();
        result.setItems(entries);

        String root = "Slideshows";
        if(type == CF.USER_TYPE) {
            root = "User";
        } else if(type == CF.TAG_TYPE) {
            root = "Tag";
        }

        parser.require(XmlPullParser.START_TAG,NS,root);
        while(parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() != XmlPullParser.START_TAG) continue;
            String name = parser.getName();
            if(name.equals("Name")) {
                result.setName(readString("Name",parser));
            }
            else if(name.equals("Count")) {
                result.setCount(readString("Count",parser));
            }else if(name.equals("Query")) {
                result.setName(readString("Query",parser));
            }
            else if(name.equals("TotalResults")) {
                result.setCount(readString("TotalResults",parser));
            } else if(name.equals("Slideshow")) {
                result.getItems().add(readSlideshow(parser));
            } else {
                skip(parser);
            }
        }

        return result;
    }

    private static ItemData readSlideshow(XmlPullParser parser) throws XmlPullParserException, IOException {
        ItemData item = new ItemData();
        parser.require(XmlPullParser.START_TAG,NS,"Slideshow");
        while(parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() != XmlPullParser.START_TAG) continue;
            String name = parser.getName();
            if(name.equals("Title")) {
                item.setTitle(readString("Title",parser));
            } else if(name.equals("ID")) {
                item.setId(readString("ID",parser));
            } else if(name.equals("ThumbnailSmallURL")) {
                item.setThumbnail(readString("ThumbnailSmallURL",parser));
            } else if(name.equals("PPTLocation")) {
                item.setDoc(readString("PPTLocation",parser));
            } else {
                skip(parser);
            }
        }
        return item;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if(parser.getEventType() != XmlPullParser.START_TAG) throw new IllegalStateException();
        int depth = 1;
        while(depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private static String readString(String tag, XmlPullParser parser) throws XmlPullParserException, IOException {
        String value = null;
        parser.require(XmlPullParser.START_TAG,NS,tag);

        if(parser.next() == XmlPullParser.TEXT) {
            value = parser.getText();
            parser.nextTag();
        }

        parser.require(XmlPullParser.END_TAG,NS,tag);

        return value;
    }
}
