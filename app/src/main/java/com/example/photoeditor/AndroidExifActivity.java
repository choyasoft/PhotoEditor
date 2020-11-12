package com.example.photoeditor;

import java.io.IOException;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.media.ExifInterface.TAG_GPS_LATITUDE;
import static android.media.ExifInterface.TAG_GPS_LATITUDE_REF;
import static android.media.ExifInterface.TAG_GPS_LONGITUDE;

import static com.example.photoeditor.MainActivity.mCurrentPhotoPath;
import static java.sql.DriverManager.println;

public class AndroidExifActivity extends Activity {

    String imagefile =mCurrentPhotoPath;
    ImageView image;
    TextView Exif;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_exif);

        image = (ImageView) findViewById(R.id.image);
        Exif = (TextView)findViewById(R.id.exif);
        ImageView image = (ImageView)findViewById(R.id.image);

        Bitmap bm = BitmapFactory.decodeFile(imagefile);
        image.setImageBitmap(bm);

        Exif.setText(ReadExif(imagefile));
    }


    public String ReadExif(String file){
        String exif="Ruta de archivo: " + file;
        try {
            ExifInterface exifInterface = new ExifInterface(file);

            exif += "\nDatos de ubicación GPS:";

            exif += "\n Latitud: " + exifInterface.getAttribute(TAG_GPS_LATITUDE);
            String lat1 = exifInterface.getAttribute(TAG_GPS_LATITUDE);
              // exif += "\n Latitud REF: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            exif += "\n Longitud: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String lon1 = exifInterface.getAttribute(TAG_GPS_LATITUDE);
           //    exif += "\n TAG_GPS_LONGITUDE_REF: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);


           /*
          double latDouble = Double.parseDouble(lat1);
          double lonDouble = Double.parseDouble(lon1);
          LatLng latlng = new LatLng(Double.parseDouble(lat1),Double.parseDouble(lon1));
        */
            Toast.makeText(this, lat1.replaceAll("[^,.\\dA-Za-z0-9]", " "), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q="+ lat1.replaceAll("[^,.\\dA-Za-z0-9]", " ")+lon1.replaceAll("[^,.\\dA-Za-z0-9]", " ")));
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(AndroidExifActivity.this,
                    e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        return exif;
    }

}
