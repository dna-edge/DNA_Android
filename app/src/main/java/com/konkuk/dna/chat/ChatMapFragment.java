package com.konkuk.dna.chat;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konkuk.dna.R;
import com.konkuk.dna.map.NMapPOIflagType;
import com.konkuk.dna.map.NMapViewerResourceProvider;
import com.konkuk.dna.post.Post;
import com.konkuk.dna.post.PostDetailActivity;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapProjection;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapCircleData;
import com.nhn.android.maps.overlay.NMapCircleStyle;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatMapFragment extends Fragment
        implements NMapView.OnMapStateChangeListener, NMapPOIdataOverlay.OnStateChangeListener
{
    private NMapContext mapContext;
    private NMapView mapView;
    private NMapController mapController;
    private NMapOverlayManager mOverlayManager;
    private NMapResourceProvider mMapViewerResourceProvider;

    private NMapPOIdata poiData;
    private NMapCircleData circleData;
    private NMapCircleStyle circleStyle;
    private JSONObject centerPosition;

    private static final String CLIENT_ID = "d58JXyIkF7YXEmOLrYSD"; // 애플리케이션 클라이언트 아이디 값

    public ChatMapFragment() {}

    public JSONObject getCenterPosition() {
        return this.centerPosition;
    }

    public JSONObject getMarkerPosition() {
        JSONObject markerPostition = new JSONObject();
        NGeoPoint nGeoPoint = poiData.getPOIitem(0).getPoint();

        try {
            markerPostition.put("longitude", nGeoPoint.longitude);
            markerPostition.put("latitude", nGeoPoint.latitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return markerPostition;
    }

    public void initMapCenter(double lng, double lat, float radius) {
        updatePositionMarker(lng, lat);
        updateRadiusCircle(lng, lat, radius);
        mapController.setZoomLevel(11);
    }

    public void drawPostLocations(final ArrayList<Post> posts) {
        NMapPOIdata postPoiData = new NMapPOIdata(1, mMapViewerResourceProvider);

        for(Post post: posts) {
            NMapPOIitem item = postPoiData.addPOIitem(post.getLongitude(), post.getLatitude(), post.getTitle(),
                    NMapPOIflagType.POST, postPoiData.count());
            item.setRightAccessory(true, NMapPOIflagType.CLICKABLE_ARROW);
        }

        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(postPoiData, null);
        poiDataOverlay.setOnStateChangeListener(new NMapPOIdataOverlay.OnStateChangeListener() {
            @Override
            public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {}

            @Override
            public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
                Intent postIntent = new Intent(getActivity(), PostDetailActivity.class);
                postIntent.putExtra("post", (Post) posts.get(nMapPOIitem.getId()));
                getActivity().startActivity(postIntent);
                nMapPOIdataOverlay.setHidden(true);
            }
        });
    }

    public void updatePositionMarker(double lng, double lat) {
        if (poiData.getPOIitem(0) != null) {
            poiData.getPOIitem(0).setPoint(new NGeoPoint(lng, lat));
        } else {
            if (getActivity().getClass().getSimpleName().equals("PostDetailActivity")) {
                poiData.addPOIitem(lng, lat, "", NMapPOIflagType.PIN, 0);
            } else if (getActivity().getClass().getSimpleName().equals("PostFormActivity")) {
                    NMapPOIitem item = poiData.addPOIitem(lng, lat, "", NMapPOIflagType.PIN, 0);
                    item.setPoint(mapController.getMapCenter());
                    item.setFloatingMode(NMapPOIitem.FLOATING_TOUCH | NMapPOIitem.FLOATING_DRAG);
            } else {
                poiData.addPOIitem(lng, lat, "", NMapPOIflagType.SPOT, 0);
            }
        }

        poiData.endPOIdata();

        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        mapController.setMapCenter(new NGeoPoint(lng, lat), 11);
    }

    public void updateRadiusCircle(double lng, double lat, float radius) {
        mOverlayManager.clearOverlays();
        updatePositionMarker(lng, lat);
        circleData = new NMapCircleData(1);
        circleData.initCircleData();
        circleData.addCirclePoint(lng, lat, radius);
        circleData.setCircleStyle(circleStyle);
        NMapPathDataOverlay pathDataOverlay = mOverlayManager.createPathDataOverlay();
        pathDataOverlay.addCircleData(circleData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_map, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapContext = new NMapContext(super.getActivity());
        mapContext.onCreate();

        centerPosition = new JSONObject();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = (NMapView)getView().findViewById(R.id.chatMapView);
        mapView.setClientId(CLIENT_ID);
        mapContext.setupMapView(mapView);
    }

    @Override
    public void onStart(){
        super.onStart();
        mapContext.onStart();

        mapView.setClickable(true);
        mapView.displayZoomControls(true);
        mapView.setEnabled(true);

        // DetailView에서 만들었을 경우에는 클릭하지 못하게 막습니다. (스크롤 문제)
        if (getActivity().getClass().getSimpleName().equals("PostDetailActivity")) {
            mapView.setClickable(false);
        } else {
            mapView.setClickable(true);
            mapView.setOnMapStateChangeListener(OnMapViewStateChangeListener); //리스너 등록
        }

        mapController = mapView.getMapController();
        mMapViewerResourceProvider = new NMapViewerResourceProvider(getActivity());
        mOverlayManager = new NMapOverlayManager(getActivity(),mapView,mMapViewerResourceProvider);

        poiData = new NMapPOIdata(0, mMapViewerResourceProvider);
        circleData = new NMapCircleData(0);
        circleData.initCircleData();
        circleStyle = new NMapCircleStyle(getActivity());
        circleStyle.setStrokeColor(getResources().getColor(R.color.grayLight), 50);
        circleStyle.setStrokeWidth(0.5F);
        circleStyle.setFillColor(getResources().getColor(R.color.red), 50);
        circleData.setCircleStyle(circleStyle);

        OnMapViewStateChangeListener.onMapInitHandler(mapView, null);
    }

    public NMapView.OnMapStateChangeListener OnMapViewStateChangeListener = new NMapView.OnMapStateChangeListener() {
        @Override
        public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
            if (nMapError == null) {
                mapController.setZoomLevel(11);
            } else {
                Log.d("초기화 중 에러 발생", nMapError.toString());
            }
        }

        @Override
        public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {
            try {
                centerPosition.put("longitude", nGeoPoint.longitude);
                centerPosition.put("latitude", nGeoPoint.latitude);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onMapCenterChangeFine(NMapView nMapView) {}

        @Override
        public void onZoomLevelChange(NMapView nMapView, int i) {}

        @Override
        public void onAnimationStateChange(NMapView nMapView, int i, int i1) {}
    };

    @Override
    public void onResume() {
        super.onResume();
        mapContext.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapContext.onPause();
        mOverlayManager.clearOverlays();
    }
    @Override
    public void onStop() {
        mapContext.onStop();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        mapContext.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {}

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {}

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {}

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {}

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {}

    @Override
    public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {}

    @Override
    public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {return;}
}