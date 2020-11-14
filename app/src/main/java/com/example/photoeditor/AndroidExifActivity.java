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


           // double latDouble = Double.parseDouble(latitude);
           // double lonDouble = Double.parseDouble(longitude);

            double latitude = 0;
            exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String latitudeConv = GPS.convert(latitude);
            exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            double longitude = 0;
            exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String longitudeConv = GPS.convert(longitude);
            //double longitude = 0;
            //exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, GPS.convert(longitude));
            //exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, GPS.longitudeRef(longitude));
            exifInterface.saveAttributes();



            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q="+latitudeConv+", "+longitudeConv));
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
