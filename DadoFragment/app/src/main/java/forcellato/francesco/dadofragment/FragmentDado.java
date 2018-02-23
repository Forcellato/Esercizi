package forcellato.francesco.dadofragment;


import android.content.Context;
import android.graphics.Color;
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
    private static final String ARG_PARAM1 = "param1";
    private static final String DIRECTION = "direction";
    private static int prec;
    private static Random r = new Random();

    public FragmentDado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentDado.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDado newInstance(int direction) {
        FragmentDado fragment = new FragmentDado();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, prec);
        args.putInt(DIRECTION, direction);
        fragment.setArguments(args);
        prec = 0;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            prec = getArguments().getInt(ARG_PARAM1);
        }
    }

    public void setDirection(int direction) {
        Bundle b = new Bundle();
        b.putInt(ARG_PARAM1, getArguments().getInt(ARG_PARAM1));
        b.putInt(DIRECTION, direction);
        setArguments(b);
    }

    private int getRandom() {
        int ris;
        do {
            ris = r.nextInt(6) + 1;
        } while (ris == prec);
        prec = ris;
        return ris;
    }

    private int getRandomColor() {
        int color = getResources().getColor(R.color.casuale);
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[0] = r.nextFloat() * 360;
        color = Color.HSVToColor(hsv);
        return color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ris = inflater.inflate(R.layout.fragment_dado, container, false);
        ImageView faccia = ris.findViewById(R.id.imgFaccia);
        Context context = faccia.getContext();
        int id = context.getResources().getIdentifier("d" + getRandom(), "drawable", context.getPackageName());
        faccia.setImageResource(id);
        faccia.setBackgroundColor(getRandomColor());
        return ris;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(getArguments().getInt(DIRECTION), enter, getResources().getInteger(R.integer.durataAnimazione));
    }
}