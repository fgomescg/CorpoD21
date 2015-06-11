package corpode21.com.br.corpod21.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import corpode21.com.br.corpod21.R;

/**
 * Created by Fabio on 24/03/2015.
 */
public class AlertManager {

    public static void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Setting alert dialog icon
            //alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
