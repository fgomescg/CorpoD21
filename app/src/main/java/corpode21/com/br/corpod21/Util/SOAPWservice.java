package corpode21.com.br.corpod21.Util;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;

/**
 * Created by Fabio on 19/05/2015.
 */
public class SOAPWservice {

    public static SoapObject getWsResponse(String NAMESPACE, String METHOD, String MAIN_REQUEST_URL, HashMap<String, String> parameters) {

        //Objeto composto pelo NameSpace e pelo metodo que queremos chamar
        SoapObject soap = new SoapObject(NAMESPACE,
                METHOD);

        for (String key : parameters.keySet()) {

            PropertyInfo property = new PropertyInfo();
            property.setName(key);
            property.setValue(parameters.get(key));
            soap.addProperty(property);
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httpTransport = new HttpTransportSE(MAIN_REQUEST_URL);

        try {
            httpTransport.call(NAMESPACE + METHOD, envelope);

            //Log.d("httpTransportDump", httpTransport.requestDump.toString());
            //Log.d("httpTransportResponse", httpTransport.responseDump.toString());
            //httpTransport.debug = true;
            SoapObject res=(SoapObject)envelope.bodyIn;

            return res;
            //SoapObject t=(SoapObject)res.getProperty(0);
            //return t.getProperty("Retorno").toString();

        } catch (Exception e) {
            Log.e("GB", e.getMessage());

            return null;
        }
    }
}
