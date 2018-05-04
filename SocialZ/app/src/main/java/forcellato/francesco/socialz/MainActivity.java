package forcellato.francesco.socialz;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            //prova();
            ;
        }*/
        Button b = findViewById(R.id.btnCarica);
        b.setOnClickListener((view) -> {
            new WebServiceTask().execute("force", "arturo");
        });

    }

    private class WebServiceTask extends AsyncTask<String, String, String> {
        private final String NAMESPACE = "http://web.util/"; //(1)
        private final String URL = "http://192.168.1.18:8080/WebServiceZ/SocialZ?WSDL"; //(2)
        private final String METHOD_NAME = "hello"; //(3)
        private final String SOAP_ACTION = ""; //non serve a niente ma va comunque usato


        @Override
        protected String doInBackground(String... strings) {
            String ris = "";
            for (String s : strings) {
                ris += prova(s);
            }
            return ris;
        }

        private String prova(String s) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                //aggiungiamo i @WebParam
                request.addProperty("name", s);
                //Create envelope
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                try {
                    //Invole web service
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    //Get the response
                    SoapObject response = (SoapObject) envelope.bodyIn;// dentro questo oggetto vienec aricata la risposta
                    return response.getProperty("return").toString();// si può sostituire “return” con un intero
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
