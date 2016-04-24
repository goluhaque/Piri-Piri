package com.example.winged_elite.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link fragmentTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {link fragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentTwo extends Fragment implements Response.Listener,
        Response.ErrorListener {
/*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fragmentTwo() {
        // Required empty public constructor
    }
*/

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentTwo.
     */
/*
    // TODO: Rename and change types and number of parameters
    public static fragmentTwo newInstance(String param1, String param2) {
        fragmentTwo fragment = new fragmentTwo();
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
    EditText editText1;
    TextView tV1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_fragment_two, container, false);;
        final EditText editText = (EditText) rootView.findViewById(R.id.editText3);
        this.editText1 = editText;
        tV1 = (TextView) rootView.findViewById(R.id.textView2);
        int prevSpaces = 0;
        final fragmentTwo object = this;
        editText1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String partialSentence = s.toString();
                int numSpaces = StringUtils.countMatches(partialSentence, " ");
                int lastSpace = partialSentence.lastIndexOf(' ');
                RequestQueue mQueue = CustomVolleyRequestQueue.getInstance(getActivity()).getRequestQueue();
                final String REQUEST_TAG = "VolleyRequest";
                /*
                if(start+count >= lastSpace + 4) {
                    String partialWord = partialSentence.substring(lastSpace+1, start+count);
                    Toast.makeText(getActivity(), partialWord, Toast.LENGTH_SHORT).show();


                    //String url = "http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo";
                    String url = "http://52.160.99.213:5000/autocomplete?current="+partialWord;

                    final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(), object, object);
                    jsonRequest.setTag(REQUEST_TAG);
                    mQueue.add(jsonRequest);
                    //sendWordToApi(partialWord, wordCompletion());
                }
                */
                if(numSpaces % 5 == 0) {
                    Toast.makeText(getActivity(), partialSentence, Toast.LENGTH_SHORT).show();
                    //sendSentenceToApi(partialSentence, sentenceCompletion());
                    String url = "http://52.160.99.213:5000";

                    final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(), object, object);
                    jsonRequest.setTag(REQUEST_TAG);
                    mQueue.add(jsonRequest);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Object response) {
        //tV1.setText("Response is: " + response);
        try {
            Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
