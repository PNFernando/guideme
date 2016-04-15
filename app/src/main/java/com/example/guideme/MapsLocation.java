package com.example.guideme;


import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.vision.barcode.Barcode;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by pramoda-nf on 4/11/16.
 */

@EFragment(R.layout.fragment_maps)
public class MapsLocation extends Fragment {


    @ViewById
    MapView mapView;



//    MapController myMC = null;

    GeoPoint geoPoint = null;

    GoogleMap map;
    //    @ViewById
//    SearchBox googleSearchbox;
//    private List<Device> deviceList = new ArrayList<>();
    private Timer timer;
    private long delayTime = 1000 * 5;
    private long playBackDelayTime = 1000 * 5 * 3;
    float zoomlevel = 15;
    //    public static List<MarkDevice> markDevices = new ArrayList<>();
    private ArrayList<LatLng> polyLineList;
    Polyline playBackPolyline;
    Marker playBackMarker;

    @AfterViews
    void afterView() {
        mapView.onCreate(super.getArguments());
        timer = new Timer();

        // Gets to GoogleMapsCurrentLocation from the MapView and does initialization stuff
        map = mapView.getMap();
  //      map.getUiSettings().setMyLocationButtonEnabled(true);
//        map.getUiSettings().setRotateGesturesEnabled(false);
//        map.setMyLocationEnabled(true);
//
//        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
//        try {
//            MapsInitializer.initialize(this.getActivity());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Updates the location and zoom of the MapView
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(7.864596, 80.648603), 8);
//        map.animateCamera(cameraUpdate);


        setUpMap();


        //search box
//        googleSearchbox.enableVoiceRecognition(this);
//        googleSearchbox.setLogoText("Search here");
//        googleSearchbox.setOnLocationClickListener(new SearchBox.LocationOnclickListener() {
//            @Override
//            public void onClick() {
//                Intent intent = new Intent(getActivity(), com.egreen.apps.gpslocater.view.DeviceList_.class);
//                startActivity(intent);
//            }
//        });

//        loadDevicesByClient();
////        googleSearchbox.setMaxLength(5);
//
//        for (int x = 0; x < deviceList.size(); x++) {
//            SearchResult option = new SearchResult(deviceList.get(x), getResources().getDrawable(R.mipmap.ic_pointer_purple));
//            googleSearchbox.addSearchable(option);
//        }
//
//        googleSearchbox.setMenuListener(new SearchBox.MenuListener() {
//            @Override
//            public void onMenuClick() {
//                //Hamburger has been clicked
//                Toast.makeText(getActivity(), "Menu click", Toast.LENGTH_LONG).show();
//            }
//        });

//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    for (int i = 0; i < markDevices.size(); i++) {
//                        getLocationByImei(markDevices.get(i));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }


//        }, 0, delayTime);

    }





//    }















































    private void setUpMap() {


        Polyline line = map.addPolyline(new PolylineOptions()
                .add(new LatLng(6.8313797, 79.8075808), new LatLng(6.8619306, 79.841441))
                .width(5)
                .color(Color.RED));

//        Log.i("SETUPMAP","grrrrrrrrrrrrr");

        map.addMarker(new MarkerOptions().position(new LatLng(7.864596, 80.648603)).title("Marker").snippet("Snippet"));

        // Enable MyLocation Layer of Google Map
        map.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // set map type
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        map.animateCamera(CameraUpdateFactory.zoomTo(14));
        map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet("Consider yourself located"));

        LatLng myCoordinates = new LatLng(latitude, longitude);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myCoordinates, 12);
        map.animateCamera(yourLocation);


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myCoordinates)      // Sets the center of the map to LatLng (refer to previous snippet)
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


//    @Background
//    void loadDevicesByClient() {
//        String clientId = (String) sessionManagement.getSESSION().get("clientId");
//        if (clientId != null) {
//            try {
//                deviceList = deviceService.getDeviceByClientId(clientId);
//                if (deviceList != null) {
//                    for (Device device : deviceList) {
//                        if (device.isDeviceStatus()) {
//                            MarkDevice markDevice = new MarkDevice();
//                            List<LatLng> polyLineList = new ArrayList<>();
//                            markDevice.setImei(device.getImeNum());
//                            markDevice.setTitle(device.getDeviceName());
//                            markDevice.setPolylinePointList(polyLineList);
//                            markDevices.add(markDevice);
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        for (int i = 0; i < markDevices.size(); i++) {
//            Log.i("LoopRun", i + "");
//            getLocationByImei(markDevices.get(i));
//        }
//        realTimeUpdateLocation();
//    }

//    @UiThread
//    void realTimeUpdateLocation() {
//        Log.i("TimerRun", "");
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    for (int i = 0; i < markDevices.size(); i++) {
//                        getLocationByImei(markDevices.get(i));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 0, delayTime);
//    }

//    @Background
//    public void getLocationByImei(MarkDevice markDevice) {
//        Log.i("runLocationByImri", "");
//        Location currentLocation = null;
//        try {
//            currentLocation = locationService.getLastLocationByImeNumber(markDevice.getImei());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (currentLocation != null) {
//            LatLng latLng = new LatLng(Double.parseDouble(currentLocation.getLatitude()), Double.parseDouble.g(currentLocationetLongtude()));
//            if (markDevice.getPreviousLocation() != null && (Double.parseDouble(currentLocation.getLatitude()) != markDevice.getPreviousLocation().latitude || Double.parseDouble(currentLocation.getLongtude()) != markDevice.getPreviousLocation().longitude)) {
//                markDevice.setCurrentLocation(latLng);
//                markPath(markDevice.getPreviousLocation(), latLng, markDevice);
//                createMarcker(markDevice, latLng.latitude, latLng.longitude, markDevice.getTitle());
//            } else {
//                markDevice.setCurrentLocation(latLng);
//                createMarcker(markDevice, latLng.latitude, latLng.longitude, markDevice.getTitle());
//            }
//        }
//    }

//    @UiThread
//    void createMarcker(MarkDevice markDevice, double lat, double lng, String title) {
//        if (markDevice.getMarker() == null) {
//            markDevice.setMarker(map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).draggable(true)));
//            markDevice.getMarker().setTitle(title);
//            markDevice.getMarker().showInfoWindow();
//        } else {
//            markDevice.getMarker().setPosition(new LatLng(lat, lng));
//        }
//        markDevice.setPreviousLocation(new LatLng(lat, lng));
//        if (markDevices.size() == 1) {
//            zoomMap(lat, lng);
//        }
//    }
//
//    @UiThread
//    void markPath(LatLng latLng1, LatLng latLng2, MarkDevice markDevice) {
//        if (markDevice.getPolyline() == null) {
//            markDevice.setPolyline(map.addPolyline(new PolylineOptions().add(latLng1, latLng2).width(5).color(R.color.c_deep_sky_blue)));
//            markDevice.getPolylinePointList().add(latLng1);
//            markDevice.getPolylinePointList().add(latLng2);
//        } else {
//            markDevice.getPolylinePointList().add(latLng2);
//            markDevice.getPolyline().setPoints(markDevice.getPolylinePointList());
//        }
//    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    void zoomMap(double lat, double lng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoomlevel);
        map.animateCamera(cameraUpdate);
    }

    @UiThread
    void print(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    //remove from index markDevices
//    public static void removeActiveMarkDevice(String imei) {
//        for (MarkDevice markDevice : markDevices) {
//            if (markDevice.getImei().equals(imei)) {
//                if (markDevice.getMarker() != null) {
//                    markDevice.getMarker().remove();
//                }
//                if (markDevice.getPolyline() != null) {
//                    markDevice.getPolyline().remove();
//                }
//                markDevices.remove(markDevice);
//            }
//        }
//    }

    // add form index markDevice
//    public static void addActiveMarkDevice(Device device) {
//        MarkDevice markDevice = new MarkDevice();
//        List<LatLng> polyLineList = new ArrayList<>();
//        markDevice.setImei(device.getImeNum());
//        markDevice.setTitle(device.getDeviceName());
//        markDevice.setPolylinePointList(polyLineList);
//        markDevices.add(markDevice);
//    }

//    public void createPlayBackPath(List<Location> deviceLocationTimeRange) {
//        List<LatLng> latLngs = new ArrayList<>();
//        for (int i = deviceLocationTimeRange.size(); i > 0; i--) {
//            // markPoint(location);
//            latLngs.add(new LatLng(Double.parseDouble(deviceLocationTimeRange.get(i - 1).getLatitude()), Double.parseDouble(deviceLocationTimeRange.get(i - 1).getLongtude())));
//        }
//        drowPlayBack(latLngs);
//    }

//    @UiThread
//    void markPoint(LatLng latLng1, LatLng latLng2, List<LatLng> latLngs) {
//        if (playBackPolyline == null) {
//            playBackPolyline = map.addPolyline(new PolylineOptions().add(latLng1, latLng2).width(5).color(R.color.c_deep_sky_blue));
//            playBackMarker = map.addMarker(new MarkerOptions().position(new LatLng(latLng2.latitude, latLng2.longitude)).draggable(true));
//        } else {
//            playBackPolyline.setPoints(latLngs);
//            playBackMarker.setPosition(latLng2);
//        }
//
//        zoomMap(latLng2.latitude, latLng2.longitude);
//    }
//
//    @UiThread
//    void drowPlayBack(final List<LatLng> latLngs) {
//        map.clear();
//        timer.cancel();
//        Timer timer = new Timer();
//        for (int i = 0; i < latLngs.size() - 1; i++) {
//            final int finalI = i;
////            timer.schedule(new TimerTask() {
////                @Override
////                public void run() {
////                    try {
//            LatLng latLng1 = new LatLng(latLngs.get(finalI).latitude, latLngs.get(finalI).longitude);
//            LatLng latLng2 = new LatLng(latLngs.get(finalI + 1).latitude, latLngs.get(finalI + 1).longitude);
//            markPoint(latLng1, latLng2, latLngs);
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
////            }, 0, delayTime);
//        }
//
//    }
}
