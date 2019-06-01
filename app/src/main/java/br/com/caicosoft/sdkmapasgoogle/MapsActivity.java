package br.com.caicosoft.sdkmapasgoogle;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Muda Exibição do Mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Passa Cordenadas
        final LatLng casa = new LatLng(-6.445385, -37.100230);
        marcaLocal(casa); // marca a minah casa definido acima
        desenhandoCirculo(casa); // circula minha casa
        desenhandoPoligono(); // desenha um poligono com pontos definidos

        // Evento de Clique
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                desenhaLinha(casa, latLng); // desenha uma linha de casa até o ponto clicado

                Toast.makeText(getApplicationContext(), "onClick - Lat: "+latitude+" Long: "+longitude, Toast.LENGTH_LONG).show();

                marcaLocal(latLng);

            }
        });

        // Evento do Clique Longo
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                Toast.makeText(getApplicationContext(), "onLongClick - Lat: "+latitude+" Long: "+longitude, Toast.LENGTH_LONG).show();

                marcaLocal(latLng);

            }
        });
    }

    public void marcaLocal(LatLng latLng){
        // Personaliza
        mMap.addMarker(
                new MarkerOptions()
                        .position( latLng )
                        .title("Local")
                        .snippet("Descrição") //descricao para o local
                        .icon(
                                BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_ORANGE
                                )
                        )
        );

        // Cuida da questão do zom
        mMap.moveCamera( // 2.0 até 21.0
                CameraUpdateFactory.newLatLngZoom( latLng, 15));
    }

    public void desenhandoCirculo(LatLng centroCirculo){
        // Circulo
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center( centroCirculo ); // define o centro do meu circulo
        circleOptions.radius(400); // distancia que eu quero criar para o circulo em METROS
        circleOptions.fillColor(Color.argb(128, 255, 153, 0)); // define cor do circulo - alpha de 0 a 255
        circleOptions.strokeWidth(10); // espesura da corda
        circleOptions.strokeColor(Color.GRAY); // cor da borda

        mMap.addCircle(circleOptions);
    }

    public void desenhandoPoligono(){
        // Poligono de Quantos pontos quiser
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.strokeColor(Color.GRAY);
        polygonOptions.strokeWidth(10);
        polygonOptions.fillColor(Color.argb(128, 255, 153, 0));
        polygonOptions.add(new LatLng(-6.445332, -37.100235));
        polygonOptions.add(new LatLng(-6.445321, -37.099945));
        polygonOptions.add(new LatLng(-6.445620, -37.100171));

        mMap.addPolygon(polygonOptions);
    }

    public void desenhaLinha(LatLng origem, LatLng destino){
        PolylineOptions  polylineOptions = new PolylineOptions();
        polylineOptions.add(origem);
        polylineOptions.add(destino);
        polylineOptions.color(Color.GRAY);
        polylineOptions.width(20);

        mMap.addPolyline(polylineOptions);
    }
}
