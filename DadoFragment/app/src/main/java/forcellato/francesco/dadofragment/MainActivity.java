package forcellato.francesco.dadofragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    float x1, x2, y1, y2;
    private final static float MIN_DISTANCE = 150.0f;
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private long t;
    private Long down;

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            if (System.currentTimeMillis() - t > 500) {
                float x = se.values[0];
                float y = se.values[1];
                float z = se.values[2];
                mAccelLast = mAccelCurrent;
                mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
                float delta = mAccelCurrent - mAccelLast;
                mAccel = mAccel * 0.9f + delta; // perform low-cut filter
                if (mAccel > 15) {
                    t = System.currentTimeMillis();
                    changeFragment(FragmentDado.LEFT, false);
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        FragmentIniziale f = FragmentIniziale.newInstance();
        ft.add(R.id.fragment, f);
        ft.commit();

        t = System.currentTimeMillis();
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() < 2) {
            if (System.currentTimeMillis() - t > 500) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        down = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        down = null;
                        x2 = event.getX();
                        y2 = event.getY();
                        float deltaX = x2 - x1;
                        float deltaY = y2 - y1;
                        int direzione = FragmentDado.NODIR;

                        if (Math.abs(deltaX) > MIN_DISTANCE) {
                            if (x2 > x1) { // Left to Right swipe action
                                direzione = FragmentDado.RIGHT;
                            } else { // Right to Left swipe action
                                direzione = FragmentDado.LEFT;
                            }
                        }
                        if (Math.abs(deltaY) > MIN_DISTANCE && Math.abs(deltaY) > Math.abs(deltaX)) {
                            if (y2 > y1) { //Up to Down swipe action
                                direzione = FragmentDado.DOWN;
                            } else { //Down to Up swipe action
                                direzione = FragmentDado.UP;
                            }
                        }
                        if (direzione != FragmentDado.NODIR) {
                            changeFragment(direzione, false);
                        }
                        t = System.currentTimeMillis();
                        break;
                }
            }
        } else if (down != null && System.currentTimeMillis() - down > 500) {
            down = null;
            x1 = event.getX();
            y1 = event.getY();
            changeFragment(FragmentDado.RIGHT, true);
        }
        return super.onTouchEvent(event);
    }

    public synchronized void changeFragment(int direction, boolean inizio) {
        // act only in portrait mode
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentById(R.id.fragment) instanceof FragmentDado && !inizio) {
            ft.replace(R.id.fragment, FragmentDado.nextInstance(direction, (FragmentDado) fm.findFragmentById(R.id.fragment)));
        } else if (!inizio) {
            switch (direction) {
                case FragmentDado.UP:
                    ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_up);
                    break;
                case FragmentDado.DOWN:
                    ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_down);
                    break;
                case FragmentDado.LEFT:
                    ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left);
                    break;
                case FragmentDado.RIGHT:
                    ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                    break;
            }
            ft.replace(R.id.fragment, FragmentDado.newInstance(direction));
        } else {
            ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
            ft.replace(R.id.fragment, FragmentIniziale.newInstance());
        }
        ft.commit();
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onStop();
    }

    @Override
    protected void onStart() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        super.onStart();
    }
}
