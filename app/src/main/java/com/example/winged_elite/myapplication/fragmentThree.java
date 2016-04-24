package com.example.winged_elite.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.graphics.Matrix;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class fragmentThree extends Fragment implements SurfaceHolder.Callback, Response.Listener,
        Response.ErrorListener {
    /*// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fragmentThree() {
        // Required empty public constructor
    }
*/
 /*   // TODO: Rename and change types and number of parameters
    public static fragmentThree newInstance(String param1, String param2) {
        fragmentThree fragment = new fragmentThree();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
*/
    public static final String DATA_PATH = Environment
            .getExternalStorageDirectory().toString() + "/CameraTake/";

    public static final int CAMERA_REQUEST = 1;
    public static final int LOAD_PICTURE = 2;
    public static final String lang = "eng";


    public static ArrayList<String> phoneList, urlList, dateList, emailList;

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    Camera.PictureCallback rawCallback;
    Camera.ShutterCallback shutterCallback;
    Camera.PictureCallback jpegCallback;

    Button clickPicture;
    ImageButton loadPicture;
    EditText editText1;
    public HashSet hashSet;
    public String result;
    public String path;
    public String encodedImage;
    public String url;
    private static final String TAG = "MainActivity.java";

    public static final String REQUEST_TAG = "VolleyRequest";
    public RequestQueue mQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] paths = new String[]{DATA_PATH, DATA_PATH + "tessdata/"};

        //Create directories if they do not exist
        for (String path : paths) {
            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
                    //return;
                } else {
                    Log.v(TAG, "Created directory " + path + " on sdcard");
                }
            }

        }
        path = DATA_PATH + "camera_mage.jpg";
        Log.d("Debug", "Before super");
        super.onCreate(savedInstanceState);
        Log.d("Debug", "Before super");
        final View rootView = inflater.inflate(R.layout.fragment_fragment_three, container, false);;

        surfaceView = (SurfaceView) rootView.findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        jpegCallback = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {

                    outStream = new FileOutputStream(String.format(path, System.currentTimeMillis()));
                    Log.d("Picture", path);
                    encodedImage = Base64.encodeToString(data, Base64.DEFAULT);
                    encodedImage = encodedImage.replace("\n", "");
                    Log.d("Image", "jh");
                    sendRequest();
                    outStream.write(data);

                    outStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "FNF", Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "IOE", Toast.LENGTH_LONG).show();

                } finally {
                }
                //onPhotoTaken();
                refreshCamera();
            }
        };


        phoneList = new ArrayList<String>();
        urlList = new ArrayList<String>();
        dateList = new ArrayList<String>();
        emailList = new ArrayList<String>();
        hashSet = new HashSet();

        clickPicture = (Button) rootView.findViewById(R.id.clickButton);
        // clickPicture.setOnClickListener(new ButtonClickHandler());

        editText1 = (EditText) rootView.findViewById(R.id.editText1);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_three, container, false);
    }

/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
*/
    public void captureImage(View v) throws IOException {
        camera.takePicture(null, null, jpegCallback);
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e) {
        }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
        }
    }

/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
*/

    //@Override
    /*public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //@Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open(1);
            camera.setDisplayOrientation(90);
        } catch (RuntimeException e) {
            System.err.println(e);
            return;
        }

        Camera.Parameters param;
        param = camera.getParameters();
        param.setPreviewSize(352, 288);
        camera.setParameters(param);

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
    }

    //@Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera();
    }

    //@Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public void onPhotoTaken() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);



        try {
            ExifInterface exif = new ExifInterface(path);
            int exifOrientation =
                    exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

            Log.v(TAG, "Orient: " + exifOrientation);

            int rotate = 0;

            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 0;
                    break;
                default: rotate = 90;
                    break;
            }

            Log.v(TAG, "Rotation: " + rotate);

            if (rotate != 0) {

                // Getting width & height of the given image.
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
            }

            // Convert to ARGB_8888, required by tess
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        } catch (IOException e) {
            Log.e(TAG, "Couldn't correct orientation: " + e.toString());
        }

        // _image.setImageBitmap( bitmap );

        Log.v(TAG, "Before baseApi");

        //extractText(bitmap);

    }
    public void sendRequest()
    {
        String new_url = url + encodedImage;
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                .GET, new_url,
                new JSONObject(), this, this);;

        jsonRequest.setTag(REQUEST_TAG);
        mQueue.add(jsonRequest);

    }

    public void textSentiment(String input) {
        String url = "http://52.160.99.213:5000/sentiment?current=" + input;
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                .GET, url,
                new JSONObject(), this, this);;

        jsonRequest.setTag(REQUEST_TAG);
        mQueue.add(jsonRequest);
    }
    @Override
    public void onStart() {
        super.onStart();
        mQueue = CustomVolleyRequestQueue.getInstance(this.getActivity())
                .getRequestQueue();
        //String url = "http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo";
        url = "http://52.160.99.213:5000/faceemotionDetection?current=";
        clickPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText1.getText().toString();
                textSentiment(input);
                //camera.takePicture(null, null, jpegCallback);
                //sendRequest();
            }
        });
    }

    /*//@Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }*/

    //@Override
    public void onErrorResponse(VolleyError error) {
        editText1.setText(error.getMessage());
    }

  //  @Override
    public void onResponse(Object response) {
        editText1.setText("Response is: " + response);

        try {
            editText1.setText(editText1.getText() + "\n\n" + ((JSONObject) response).getString
                    ("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
