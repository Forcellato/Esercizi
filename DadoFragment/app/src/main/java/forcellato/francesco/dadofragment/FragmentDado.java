package forcellato.francesco.dadofragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.Random;

import forcellato.francesco.dadofragment.animazionecubo.CubeAnimation;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link FragmentDado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDado extends Fragment {
    public static final int NODIR = 0;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    private static final long DURATION = 500;


    private static final String ARG_PARAM1 = "param1";
    private static final String DIRECTION = "direction";

    private int prec;

    public FragmentDado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param prec Parameter 1.
     * @return A new instance of fragment FragmentDado.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDado newInstance(int prec, int direction) {
        FragmentDado fragment = new FragmentDado();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, prec);
        args.putInt(DIRECTION, direction);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentDado nextInstance(int prec, int direction, FragmentDado f) {
        Bundle b = new Bundle();
        b.putInt(ARG_PARAM1, f.getArguments().getInt(ARG_PARAM1));
        b.putInt(DIRECTION, direction);
        f.setArguments(b);
        return newInstance(prec, direction);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            prec = getArguments().getInt(ARG_PARAM1);
        }
    }

    private int getRandom(int prec) {
        Random r = new Random();
        int ris = 0;
        do {
            ris = r.nextInt(6) + 1;
        } while (ris == prec);
        this.prec = ris;
        return ris;
    }

    public int getPrec() {
        return prec;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ris = inflater.inflate(R.layout.fragment_dado, container, false);
        ImageView faccia = ris.findViewById(R.id.imgFaccia);
        int rand = getRandom(prec);
        switch (rand) {
            case 1:
                faccia.setImageResource(R.drawable.uno);
                faccia.setBackgroundColor(getResources().getColor(R.color.uno));
                break;
            case 2:
                faccia.setImageResource(R.drawable.due);
                faccia.setBackgroundColor(getResources().getColor(R.color.due));
                break;
            case 3:
                faccia.setImageResource(R.drawable.tre);
                faccia.setBackgroundColor(getResources().getColor(R.color.tre));
                break;
            case 4:
                faccia.setImageResource(R.drawable.quattro);
                faccia.setBackgroundColor(getResources().getColor(R.color.quattro));
                break;
            case 5:
                faccia.setImageResource(R.drawable.cinque);
                faccia.setBackgroundColor(getResources().getColor(R.color.cinque));
                break;
            case 6:
                faccia.setImageResource(R.drawable.sei);
                faccia.setBackgroundColor(getResources().getColor(R.color.sei));
                break;
        }
        return ris;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(getArguments().getInt(DIRECTION), enter, DURATION);
    }
}
