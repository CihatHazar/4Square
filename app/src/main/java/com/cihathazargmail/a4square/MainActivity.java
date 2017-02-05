package com.cihathazargmail.a4square;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;                                                                           //Gerekli kutuphaneler burada ekleniyor.
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity{

    Button ara;                                                                                     //search butonu tanimlaniyor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                                                     ////cagirilacak layout belirlendi

        final EditText yer = (EditText) findViewById(R.id.sehirBolge);
        final EditText tur = (EditText) findViewById(R.id.mekanCesidi);                             //arayuzde gozuken veritext lerinden verileri iceri cekebilmek icin tanimlamalar yapiliyor

        ara = (Button)findViewById(R.id.searchButton);                                              //buton degiskenimiz arayuzdeki butona baglaniyor

        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tur.getText().toString().trim().length() < 3){

                    tur.setError("En az 3 karakter giriniz!");                                      //bu if dongusunde kategori icin minimum 3 karakter girilmesi icin kontrol yapiliyor
                }
                else                                                                                //kategori icin karakter sinamasi basarili olursa else blogu calisip uygulamayi tetikliyor
                {
                    Intent i = new Intent(MainActivity.this, ResultShowActivity.class);             //intent olusturulup, kullaniciya sonuclarin gosterilmesi icin gerekli layout cagriliyor

                    if(yer.getText().toString().isEmpty())
                        i.putExtra("veri", "Levent");
                    else                                                                            //Kullanicinin sehir-bolge girip girmedigi kontrol ediliyor
                    i.putExtra("veri", yer.getText().toString());                                   //ve putExtra fonksiyonu ile ResultShowActivity sinifina konum degerini gonderiyoruz

                    startActivity(i);                                                               //diger activity e gecis yapiliyor.

                }

            }
        });
    }




}
