package com.anad.mobile.post.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.anad.mobile.post.Activity.EnterActivity;
import com.anad.mobile.post.Models.UserAccess;
import com.anad.mobile.post.R;
import com.anad.mobile.post.ReportManager.model.SearchReportItem;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.osmdroid.util.BoundingBox;
import org.osmdroid.wms.WMSEndpoint;
import org.osmdroid.wms.WMSLayer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * CREATE BY ELIAS MOHAMMADY 96.9.12
 */

public class Util {


    static {
        System.loadLibrary("keys");
    }


    public static native String getNativeKey();

    private static Util mUtilSingleton = null;
    private static final String TAG = "Util";
    private UserAccess userAccess;

    // send to web service for authentication
    private Util() {

    }

    public static Util getInstance() {
        if (mUtilSingleton == null)
            mUtilSingleton = new Util();
        return mUtilSingleton;
    }


    public boolean checkConnectivity(Context context) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            connected = true;
        Log.i(TAG, "checkConnectivity: " + connected);
        return connected;
    }

    public void setTypeFace(TextView textView, Context context) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile_Bold.ttf");
        textView.setTypeface(font);
    }

    public void setTypeFaceLight(TextView textView, Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile_Light.ttf");
        textView.setTypeface(font);
    }

    public void setTypeFaceEdt(EditText textView, Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile.ttf");
        textView.setTypeface(font);
    }

    public void setTypeFaceCheckBox(AppCompatCheckBox checkBox, Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile.ttf");
        checkBox.setTypeface(font);
    }

    public void setTypeFaceButton(Button button, Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile_Bold.ttf");
        button.setTypeface(font);
    }

    public void setTypeFaceNumber(TextView txt, Context context) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile(FaNum).ttf");
        txt.setTypeface(font);
    }

    public void setTypeFaceNumberEditText(AppCompatEditText txt, Context context) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile(FaNum).ttf");
        txt.setTypeface(font);
    }

    public void showToast(Context context, String textToShow) {
        Toast.makeText(context, textToShow, Toast.LENGTH_LONG).show();
    }

    public boolean checkPasswordValidity(String password) {
        Pattern upperCasePattern = Pattern.compile("[A-Z ]");
        Pattern lowerCasePattern = Pattern.compile("[a-z ]");
        Pattern numberPattern = Pattern.compile("[0-9 ]");
        return (password.length() > 7 && !password.contains("+") && !password.contains(" ")
                && upperCasePattern.matcher(password).find() && lowerCasePattern.matcher(password).find()
                && numberPattern.matcher(password).find());
    }

    public boolean checkVerifyPassWord(String password, String verifyPassword) {
        boolean isMatch = false;
        if (password.equalsIgnoreCase(verifyPassword))
            isMatch = true;
        Log.i(TAG, "checkVerifyPassWord: " + isMatch);
        return isMatch;
    }

    public String getDate() {
        Date d = new Date();
        Log.i(TAG, "getDate: " + d);
        return PersianCal.getPersianDate(d);
    }

    public String getDay() {
        Date d = new Date();
        Log.i(TAG, "getDate: " + d);
        return PersianCal.getDayOfWeek(d);
    }

    public String getTime() {

        return DateFormat.getTimeInstance().format(new Date());
    }


    public InputFilter getInputFilter() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i)) && !Character.toString(source.charAt(i)).equals("_") && !Character.toString(source.charAt(i)).equals("."))
                        return "";
                }
                return null;
            }

        };
        return filter;
    }

    public InputFilter[] getInputFilterForLength(int MAX) {
        InputFilter[] textArray = new InputFilter[1];
        textArray[0] = new InputFilter.LengthFilter(MAX);
        return textArray;
    }


    public LatLng createLatLng(String N, String E) {
        LatLng latLng = new LatLng(0, 0);
        ;
        double lat = 0;
        double lng = 0;
        if (N.length() < 12) {
            lat = 0;
        } else if (E.length() < 12) {
            lng = 0;
        } else {
            try {
                double LAT = Double.valueOf(N.substring(3, 5)) / 60;
                LAT = LAT + Double.valueOf(N.substring(6, 8)) / (60 * 60);
                LAT = LAT + Double.valueOf(N.substring(9, 12)) / (60 * 60 * 1000);
                lat += Double.valueOf(N.substring(0, 2)) + LAT;


                double LNG = Double.valueOf(E.substring(3, 5)) / 60;
                LNG = LNG + Double.valueOf(E.substring(6, 8)) / (60 * 60);
                LNG = LNG + Double.valueOf(E.substring(9, 12)) / (60 * 60 * 1000);
                lng += Double.valueOf(E.substring(0, 2)) + LNG;

                latLng = new LatLng(lat, lng);


            } catch (Exception exp) {
                lat = 0;
            }
        }
        return latLng;
    }

    public void setUserAccess(UserAccess userAccess) {
        this.userAccess = userAccess;

    }

    public UserAccess getUserAccess() {

        return userAccess;
    }


    public static String EncryptUsernamePassword(String username, String password) {
        String data = username + ":" + password;
        try {
            String encryptData = Base64.encodeToString(data.getBytes("UTF-8"), Base64.NO_CLOSE);
            encryptData = "Basic " + encryptData;

            return encryptData;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "can not encrypt";
        }
    }

    public static String DecryptRegCode(String regCode) {
        try {
            return new String(Base64.decode(regCode.getBytes("UTF-8"), Base64.NO_CLOSE));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "can not decrypt";
        }
    }

    public static String createSHA256(String input, String keyToEncrypt) {
        String output = "";
        SecretKey key = generateKey(keyToEncrypt);
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(input.getBytes());
            output = Base64.encodeToString(encVal, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return output;
    }

    private static SecretKey generateKey(String input) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = input.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            byte[] key = digest.digest();
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String deHashSHA256(String input, String keyToEncrypt) {
        String output = "";
        SecretKey key = generateKey(keyToEncrypt);
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodeString = Base64.decode(input, Base64.DEFAULT);
            byte[] decodeVal = c.doFinal(decodeString);
            output = new String(decodeVal);
            return output;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return output;
    }


    public static boolean isRooted() {


        String[] places = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : places) {
            if (new File(path).exists())
                return true;
        }

        return false;
    }

    public static int getRandomColor(){
        SecureRandom rand = new SecureRandom();
        int red = rand.nextInt(256);
        int green = rand.nextInt(256);
        int blue = rand.nextInt(256);
        return Color.argb(255,red,green,blue);
    }


    public static boolean authenticateUser(PostSharedPreferences sharedPref) {
        String k = getNativeKey();
        String value = deHashSHA256(sharedPref.getKeyOpenActivity(), k);
        String userName = sharedPref.getPrefUserName();
        String password = sharedPref.getPrefPassword();
        return (!userName.equals("") && !password.equals("") && k.equals(value));
    }

    public static void backToLoginActivity(Context context) {

        Intent i = new Intent(context, EnterActivity.class);
        (context).startActivity(i);
        Toast.makeText(context, R.string.bad_request_message, Toast.LENGTH_LONG).show();
        ((Activity) context).finish();

    }

    public static WMSEndpoint parseMap(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
        Document xmlDoc = docBuilder.parse(inputStream);

        XPath xPath = XPathFactory.newInstance().newXPath();
        String expressionVersion = "/WMT_MS_Capabilities";
        Node rootNode = (Node) xPath.compile(expressionVersion).evaluate(xmlDoc, XPathConstants.NODE);

        String expressionService = "/WMT_MS_Capabilities/Service/Name";
        String nameNode = xPath.compile(expressionService).evaluate(xmlDoc);
        expressionService = "/WMT_MS_Capabilities/Service/Title";
        String titleNode = xPath.compile(expressionService).evaluate(xmlDoc);
        expressionService = "/WMT_MS_Capabilities/Service/Abstract";
        String abstractionNode = xPath.compile(expressionService).evaluate(xmlDoc);

        String version = rootNode.getAttributes().getNamedItem("version").getNodeValue();

        String expressionUrl = "/WMT_MS_Capabilities/Capability/Request/GetCapabilities/DCPType/HTTP/Get/OnlineResource";
        String expressionLayerName = "/WMT_MS_Capabilities/Capability/Layer/Layer[contains(@Name,Anad)]/Name";
        String expressionLayerTitle = "/WMT_MS_Capabilities/Capability/Layer/Layer[contains(@Name,Anad)]/Title";
        String expressionLayerSRS = "/WMT_MS_Capabilities/Capability/Layer/Layer[contains(@Name,Anad)]/SRS";
        String expressionLayerBoundBox = "/WMT_MS_Capabilities/Capability/Layer/Layer[contains(@Name,Anad)]/BoundingBox";
        String expressionLayerStyleName = "/WMT_MS_Capabilities/Capability/Layer/Layer[contains(@Name,Anad)]/Style/Name";


        Node  urlNode = (Node) xPath.compile(expressionUrl).evaluate(xmlDoc,XPathConstants.NODE);
        NodeList layerNames = (NodeList) xPath.compile(expressionLayerName).evaluate(xmlDoc, XPathConstants.NODESET);
        NodeList layerTitle = (NodeList) xPath.compile(expressionLayerTitle).evaluate(xmlDoc, XPathConstants.NODESET);
        NodeList layerSRS = (NodeList) xPath.compile(expressionLayerSRS).evaluate(xmlDoc, XPathConstants.NODESET);
        NodeList layerBoundBox = (NodeList) xPath.compile(expressionLayerBoundBox).evaluate(xmlDoc, XPathConstants.NODESET);
        NodeList layerStyleName = (NodeList) xPath.compile(expressionLayerStyleName).evaluate(xmlDoc, XPathConstants.NODESET);

        String baseUrl = urlNode.getAttributes().getNamedItem("xlink:href").getNodeValue();
        List<WMSLayer> wmsLayers = new ArrayList<>();
        List<String> styleName;



           /* styleName = new ArrayList<>();

            double north = Double.parseDouble(layerBoundBox.item(3).getAttributes().getNamedItem("maxy").getNodeValue());//maxy
            double south = Double.parseDouble(layerBoundBox.item(3).getAttributes().getNamedItem("miny").getNodeValue());//miny
            double east = Double.parseDouble(layerBoundBox.item(3).getAttributes().getNamedItem("minx").getNodeValue());//minx
            double west = Double.parseDouble(layerBoundBox.item(3).getAttributes().getNamedItem("maxx").getNodeValue());//maxx
            BoundingBox bBox = new BoundingBox(north,east,south,west);
            styleName.add(layerStyleName.item(3).getFirstChild().getNodeValue());

            WMSLayer wmsLayer = new WMSLayer();
            wmsLayer.setName(layerNames.item(3).getFirstChild().getNodeValue());
            wmsLayer.setTitle(layerTitle.item(3).getFirstChild().getNodeValue());
            wmsLayer.setBbox(bBox);
            wmsLayer.setStyles(styleName);
            wmsLayer.getSrs().add(layerSRS.item(3).getFirstChild().getNodeValue());

            wmsLayers.add(wmsLayer);*/




        for (int i = 0; i < layerNames.getLength() - 1; i++) {

            styleName = new ArrayList<>();

            double north = Double.parseDouble(layerBoundBox.item(i).getAttributes().getNamedItem("maxy").getNodeValue());//maxy
            double south = Double.parseDouble(layerBoundBox.item(i).getAttributes().getNamedItem("miny").getNodeValue());//miny
            double east = Double.parseDouble(layerBoundBox.item(i).getAttributes().getNamedItem("minx").getNodeValue());//minx
            double west = Double.parseDouble(layerBoundBox.item(i).getAttributes().getNamedItem("maxx").getNodeValue());//maxx
            BoundingBox bBox = new BoundingBox(north,east,south,west);
            styleName.add(layerStyleName.item(i).getFirstChild().getNodeValue());

            WMSLayer wmsLayer = new WMSLayer();
            wmsLayer.setName(layerNames.item(i).getFirstChild().getNodeValue());
            wmsLayer.setTitle(layerTitle.item(i).getFirstChild().getNodeValue());
            wmsLayer.setBbox(bBox);
            wmsLayer.setStyles(styleName);
            wmsLayer.getSrs().add(layerSRS.item(i).getFirstChild().getNodeValue());

            wmsLayers.add(wmsLayer);


        }




        WMSEndpoint wmsEndpoint = new WMSEndpoint();
        wmsEndpoint.setName(nameNode);
        wmsEndpoint.setWmsVersion(version);
        wmsEndpoint.setDescription(abstractionNode);
        wmsEndpoint.setTitle(titleNode);
        wmsEndpoint.setLayers(wmsLayers);
        wmsEndpoint.setBaseurl(baseUrl);
        return wmsEndpoint;
    }

    public static String getCurrentDate(){
        String persianDate;

        Date c = new Date();
        JalaliCalendar jalaliCalendar = new JalaliCalendar();
        persianDate = jalaliCalendar.getJalaliDate(c);
        return changeDateFormat(persianDate);

    }

    public static String getYear(String date){
        return date.split("/")[0];
    }

    public static String getMonth(String date){
        return date.split("/")[1];
    }

    public static String getDay(String date){
        return date.split("/")[2];
    }


    private static String changeDateFormat(String date)
    {
        String[] dates = date.split("/");
        String year = dates[0];
        String month = dates[1];
        String day = dates[2];

        if(Integer.parseInt(month)<10)
        {
            month = "0"+ month;
        }
        if(Integer.parseInt(day)<10)
        {
            day = "0"+day;
        }

        return year+"/"+month+"/"+day;

    }
    public static Typeface getFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "IRANSansMobile(FaNum)_Light.ttf");
    }

    public static void gotoActivity(Context context, Class<?> className,@Nullable Bundle bundle,boolean isFinish){
        Intent intent = new Intent(context,className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        if(isFinish)
            ((Activity)context).finish();

    }

    public static String convertObjectToString(SearchReportItem searchReportItem){
        Gson gson = new Gson();
        return gson.toJson(searchReportItem);
    }
}
