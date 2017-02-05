package com.cihathazargmail.a4square;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;                                                                 //Bu bolude yine gerekli siniflar eklendi
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import android.app.ListActivity;

public class ResultShowActivity extends ListActivity {                                              //Listeleme islemleri yapilacagi icin "extend ListActivity" ekleniyor

    final String CLIENT_ID = "WPG4FCZ3KHIBWMH50PT5OCCPQUQSGTQBQYU2HIB4YRAS0054";
    final String CLIENT_SECRET = "N2TDDRBN4NRKJ1YWBZTL1COIYNWYXNLPZOVNY2R41H3UFOVZ";                //Foursquare webservice lerini kullanmak icin CLIENT_ID,CLIENT_SECRET degiskenleri entegre ediliyor

    String konum;
                                                                                                    //Burada arraylist, adapter ve konum gibi degiskenlerimiz tanimlaniyor
    ArrayList<FoursquareVenue> venuesList;
    ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_result);                                                     //screen_resullt arayuzu cagirilip gosteriliyor burada

        if (getIntent().hasExtra("veri")){
            konum = getIntent().getStringExtra("veri");                                             //MainActvity den gonderilen konum degiskeni burda aliniyor
        }

        new fourquare().execute();                                                                  //Asagida bulunan ve tum islemleri gercekleyecek olan sinif.fonk cagiriliyor
    }

    private class fourquare extends AsyncTask<View, Void, String> {

        String temp;

        @Override
        protected String doInBackground(View... urls) {                                             //WebService ile foursquare a gonderilen url commit bu sinifta yaziliyor

            temp = makeCall("https://api.foursquare.com/v2/venues/search?near="+konum+"&oauth_token=0QBQVL3M3G0SVZICQ3ZT1ATF1PXZQXDGGB0NPR0ROJP55JDO&v=20170204");
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            if (temp == null) {                                                                     //url komutu commit ediltikten sonra bos deger donerse bura da yakalanacaktir

            } else {                                                                                //webService ten deger donunce buraya girecek ve islemler devam edecektir, sorunsuz devam ediyor

                venuesList = (ArrayList<FoursquareVenue>) parseFoursquare(temp);                    //Mekan sonuclari burada donuyor

                List<String> listTitle = new ArrayList<String>();

                for (int i = 0; i < venuesList.size(); i++) {                                       //Bu for dongusunde foursquare dan dondurulen mekanlar listeye dolduruluyor

                    listTitle.add(i, venuesList.get(i).getName() + ", " + venuesList.get(i).getCategory() + ", " + venuesList.get(i).getCity());
                }

                myAdapter = new ArrayAdapter<String>(ResultShowActivity.this, R.layout.row_layout, R.id.listText, listTitle);
                                                                                                    //Yukarida olusturulan mekan listesi burada xml dosyasina ekleniyor
                setListAdapter(myAdapter);
            }
        }
    }

    public static String makeCall(String url) {

        StringBuffer buffer_string = new StringBuffer(url);                                         //url string olusturluyor
        String replyString = "";

        HttpClient httpclient = new DefaultHttpClient();                                            //HttpClient tanimlandi

        HttpGet httpget = new HttpGet(buffer_string.toString());                                    //HttpGet tanimlandi

        try {
            HttpResponse response = httpclient.execute(httpget);                                    //Try-Catch baslatilip, HttpClient calistirldiktan sonra donen cevap aliniyor
            InputStream is = response.getEntity().getContent();

            BufferedInputStream bis = new BufferedInputStream(is);                                  //girilen string calistiriliyor
            ByteArrayBuffer baf = new ByteArrayBuffer(20);

            int current = 0;

            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            replyString = new String(baf.toByteArray());                                            //sonuc, string olarak islenmeye hazir

        } catch (Exception e) {                                                                     //Islemlerin hata vermesi durumunda hata firlatilacaktir
            e.printStackTrace();
        }

        return replyString.trim();
    }

    private static ArrayList<FoursquareVenue> parseFoursquare(final String response) {              //WebService araciligiyla foursquare dan cekilecek verilen JSON ile denetlenip isleniyor

        ArrayList<FoursquareVenue> temp = new ArrayList<FoursquareVenue>();

        try {
            JSONObject jsonObject = new JSONObject(response);                                       //Donen cevap incelenmek uzere JsonObject olusturuluyor
            if (jsonObject.has("response")) {
                if (jsonObject.getJSONObject("response").has("venues")) {
                    JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FoursquareVenue poi = new FoursquareVenue();
                        if (jsonArray.getJSONObject(i).has("name")) {
                            poi.setName(jsonArray.getJSONObject(i).getString("name"));

                            if (jsonArray.getJSONObject(i).has("location")) {
                                if (jsonArray.getJSONObject(i).getJSONObject("location").has("address")) {
                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("city")) {
                                        poi.setCity(jsonArray.getJSONObject(i).getJSONObject("location").getString("city"));
                                    }
                                    if (jsonArray.getJSONObject(i).has("categories")) {
                                        if (jsonArray.getJSONObject(i).getJSONArray("categories").length() > 0) {
                                            if (jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).has("icon")) {
                                                poi.setCategory(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getString("name"));
                                            }
                                        }
                                    }
                                    temp.add(poi);                                                  //Bulunan mekanlar kullanici arayuzune eklenip gosteriliyor
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<FoursquareVenue>();
        }
        return temp;

    }
}
