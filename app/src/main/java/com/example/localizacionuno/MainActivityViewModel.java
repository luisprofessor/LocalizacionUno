package com.example.localizacionuno;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> texto;
    private Context context;
    private LocationManager locationManager;
    private EscuchaPosicion escuchaPosicion;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getTexto() {
        if (texto == null) {
            texto = new MutableLiveData<>();
        }
        return texto;
    }

    public void hacerLectura() {
         locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);



        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            return;
        }

        escuchaPosicion=new EscuchaPosicion();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10.0f, escuchaPosicion);
    }

    public void pararLectura(){

        locationManager.removeUpdates(escuchaPosicion);
    }

    public class EscuchaPosicion implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double latitud=location.getLatitude();
            double longitud=location.getLongitude();
            String coordenada="Latitud: "+latitud+" Longitud: "+longitud;
            texto.setValue(coordenada);

        }

    }

}
