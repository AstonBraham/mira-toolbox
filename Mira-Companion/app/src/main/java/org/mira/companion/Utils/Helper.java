package org.mira.companion.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.util.Log;

import org.mira.companion.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

/**
 * Created by @AstonBraham
 * Updated on 24/05/2018.
 */

public class Helper {


    // For future use
    static  int[] ps4ports = {9020, 9030, 9998, 9999};

    static ProgressDialog pDialog;
    private static SharedPreferences mSharedPreferences;


    public static  void createMiraCompanionFolder()
    {

        try {
            File sdCard = Environment.getExternalStorageDirectory();
             String[] folder = {"Payloads", "Exploits", "Homebrews", "Plugins", "Media"};

            for (int i=0; i<folder.length; i++)
            {
                File dir = new File(sdCard.getAbsolutePath() + "/MiraCompanion/"+folder[i]);
                if (!dir.exists()) {
                    if (dir.mkdirs()) {
                        Log.i("MiraCompanion", "Directory "+folder[i]+ " Created");
                    }
                }
            }

        }
        catch (Exception ex)
        {
            Log.e("MiraCompanion", "Error creating folders  :"+ex.getMessage());
        }
    }


    public static final String md5(final String ss) {
        final String toHash = ss+"";
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(toHash.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


   // Open a browser window to the URL specified.
    public static Intent openLink(String url) {
        // if protocol isn't defined use http by default
        if (!TextUtils.isEmpty(url) && !url.contains("://")) {
            url = "http://" + url;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        return intent;
    }

    public static Intent openLink(URL url) {
        return openLink(url.toString());
    }


    public static  String randomString( int len ){
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }


    public static void showLoadingDialog(Context mContext) {
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(mContext.getString(R.string.please_wait));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public static void showLoadingDialog(Context mContext, String message) {
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(message);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    public static void hideLoadingDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }


    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param btn1
     *            the btn1
     * @param btn2
     *            the btn2
     * @param listener1
     *            the listener1
     * @param listener2
     *            the listener2
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, String msg, String btn1,
                                         String btn2, DialogInterface.OnClickListener listener1,
                                         DialogInterface.OnClickListener listener2)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.MiraCustomDialogs));
//        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
         builder.setTitle(R.string.app_name);
        builder.setMessage(msg).setCancelable(false)
                .setPositiveButton(btn1, listener1);
        if (btn2 != null && listener2 != null)
            builder.setNegativeButton(btn2, listener2);

        AlertDialog alert = builder.create();
        alert.show();
        return alert;

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param btn1
     *            the btn1
     * @param btn2
     *            the btn2
     * @param listener1
     *            the listener1
     * @param listener2
     *            the listener2
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, int msg, int btn1,
                                         int btn2, DialogInterface.OnClickListener listener1,
                                         DialogInterface.OnClickListener listener2)
    {

        return showDialog(ctx, ctx.getString(msg), ctx.getString(btn1),
                ctx.getString(btn2), listener1, listener2);

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param btn1
     *            the btn1
     * @param btn2
     *            the btn2
     * @param listener
     *            the listener
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, String msg, String btn1,
                                         String btn2, DialogInterface.OnClickListener listener)
    {

        return showDialog(ctx, msg, btn1, btn2, listener,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                    }
                });

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param btn1
     *            the btn1
     * @param btn2
     *            the btn2
     * @param listener
     *            the listener
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, int msg, int btn1,
                                         int btn2, DialogInterface.OnClickListener listener)
    {

        return showDialog(ctx, ctx.getString(msg), ctx.getString(btn1),
                ctx.getString(btn2), listener);

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param listener
     *            the listener
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, String msg,
                                         DialogInterface.OnClickListener listener)
    {

        return showDialog(ctx, msg, ctx.getString(android.R.string.ok), null,
                listener, null);
    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param listener
     *            the listener
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, int msg,
                                         DialogInterface.OnClickListener listener)
    {

        return showDialog(ctx, ctx.getString(msg),
                ctx.getString(android.R.string.ok), null, listener, null);
    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, String msg)
    {

        return showDialog(ctx, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                dialog.dismiss();
            }
        });

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, int msg)
    {

        return showDialog(ctx, ctx.getString(msg));

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param title
     *            the title
     * @param msg
     *            the msg
     * @param listener
     *            the listener
     */
    public static void showDialog(Context ctx, int title, int msg,
                                  DialogInterface.OnClickListener listener)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(msg).setCancelable(false)
                .setPositiveButton(android.R.string.ok, listener);
        builder.setTitle(title);
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Get utf8 byte array.
     * @param str
     * @return  array of NULL if error was found
     */
    public static byte[] getUTF8Bytes(String str) {
        try { return str.getBytes("UTF-8"); } catch (Exception ex) { return null; }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    public static String loadFileAsString(String filename) throws java.io.IOException {
        final int BUFLEN=1024;
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8=false;
            int read,count=0;
            while((read=is.read(bytes)) != -1) {
                if (count==0 && bytes[0]==(byte)0xEF && bytes[1]==(byte)0xBB && bytes[2]==(byte)0xBF ) {
                    isUTF8=true;
                    baos.write(bytes, 3, read-3); // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read);
                }
                count+=read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
        } finally {
            try{ is.close(); } catch(Exception ex){}
        }
    }

    /**
     * Returns MAC address of the given interface name.
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return  mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
    }

    /**
     * Get IP address from first non-localhost interface
     * @param -ipv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();

                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now don't deal with exceptions
        return "";
    }


}
