package com.didi.dts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link boardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link boardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class boardFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TEXT = "param1";
    String[] letters = {"א", "ב", "ג", "ד", "ה", "ו", "ז", "ח", "ט", "י", "כ", "ל", "מ", "נ", "ס", "ע", "פ", "צ", "ק", "ר", "ש", "ת", "ם", "ן", "ץ", "ף"};
    Button answerF[] = new Button[8];   // Max song name first word for now - 8
    Button answerS[] = new Button[6];   // Max song name second word for now - 6
    // TODO: Rename and change types of parameters
    private String mParam1;
    private OnFragmentInteractionListener mListener;

    public boardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment boardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static boardFragment newInstance(String param1) {
        boardFragment fragment = new boardFragment();
        Bundle args = new Bundle();
        args.putString(TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_board, container, false);

        String firstWord = mParam1;
        String secondWord = "";
        ArrayList<String> secondWordArray = new ArrayList<>();
        if (mParam1.contains(" ")) {
            firstWord = mParam1.split(" ")[0];
            secondWord = mParam1.split(" ")[1];
            secondWordArray.addAll((Arrays.asList(secondWord.split("(?!^)"))));
        }

        ArrayList<String> firstWordArray = new ArrayList<>(Arrays.asList(firstWord.split("(?!^)")));
        ArrayList<String> arrLetters = new ArrayList<>();
        arrLetters.addAll(firstWordArray);
        arrLetters.addAll(secondWordArray);

        // Fix first answer width and height according to the word
        float width = 50;
        float height = 50;
        float textSize = 30;
        LinearLayout.LayoutParams parms;
        if (firstWordArray.size() > 6) {
            width -= firstWordArray.size() * 1.3;
            height -= firstWordArray.size() * 1.3;
            textSize -= firstWordArray.size();
        }
        parms = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics()));

        for (int i = 0; i < firstWordArray.size(); i++) {
            String buttonID = "btnAF" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());
            answerF[i] = ((Button) v.findViewById(resID));
            answerF[i].setVisibility(View.VISIBLE);
            answerF[i].setLayoutParams(parms);
            answerF[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

            answerF[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Button) v).setText("");
                    v.findViewById((Integer) v.getTag()).setClickable(true);
                    v.findViewById((Integer) v.getTag()).setVisibility(View.VISIBLE);
                }
            });
        }

        for (int i = 0; i < secondWordArray.size(); i++) {
            String buttonID = "btnAS" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());
            answerS[i] = ((Button) v.findViewById(resID));
            answerS[i].setVisibility(View.VISIBLE);
            answerS[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Button) v).setText("");
                    v.findViewById((Integer) v.getTag()).setClickable(true);
                    v.findViewById((Integer) v.getTag()).setVisibility(View.VISIBLE);
                }
            });
        }

        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(24);  //0 to 25, heb letters

        while (arrLetters.size() < 14) {
            arrLetters.add(letters[random]);
            random = randomGenerator.nextInt(24);
        }
        Collections.shuffle(arrLetters);
        Button keyboard[] = new Button[14];
        for (int i = 0; i < 14; i++) {
            String buttonID = "btn" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());
            keyboard[i] = ((Button) v.findViewById(resID));
            keyboard[i].setText(arrLetters.get(i));
            keyboard[i].setOnClickListener(this);
        }

        return v;
    }

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

    @Override
    public void onClick(View v) {
        // get the text from the button and insert into the answer buttons
        for (Button b : answerF) {
            if (b == null)
                break;
            if (b.getText().equals("")) {
                b.setText(((Button) v).getText().toString());
                b.setTag(v.getId());
                v.setVisibility(View.INVISIBLE);
                v.setClickable(false);
                checkForWinning();
                return;
            }
        }
        for (Button b : answerS) {
            if (b == null)
                break;
            if (b.getText().equals("")) {
                b.setText(((Button) v).getText().toString());
                b.setTag(v.getId());
                v.setVisibility(View.INVISIBLE);
                v.setClickable(false);
                checkForWinning();
                return;
            }
        }
    }

    private void checkForWinning() {
        String userAnswer = "";
        for (Button b : answerF) {
            if (b == null)
                break;
            userAnswer += b.getText().toString();
        }
        if (answerS[0] != null) {
            userAnswer += " ";
            for (Button b : answerS) {
                if (b == null)
                    break;
                userAnswer += b.getText().toString();
            }
        }
        if (userAnswer.equals(mParam1)) {
            Toast.makeText(getActivity(), "כל הכבוד!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
