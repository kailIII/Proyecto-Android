package sri.facture;




import sri.facture.providers.CategoriaProvider;
import com.sri.facture.R;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Cursor c = managedQuery(CategoriaProvider.CONTENT_URI, null, null, null, null);

        if(c != null){
	        if (c.moveToFirst())
	        {
	            do
	            {
	               int id = c.getInt(c.getColumnIndex(CategoriaProvider._ID));
	               String nom = c.getString(c.getColumnIndex(CategoriaProvider.NOMBRE));
	               
	               Toast toastNotifica = Toast.makeText(getApplicationContext(), "Aqui: " + id + ">>" + nom, Toast.LENGTH_LONG);
	               toastNotifica.show();
	         
	            } while (c.moveToNext());
	        }
        }
        else{
        	Toast toastNotifica = Toast.makeText(getApplicationContext(), "Falla el cursor", Toast.LENGTH_LONG);
            toastNotifica.show();
        }

//Aqui un comentario
        // Aqui otro comentario
        //Mas comentarios
        //seguimos con comentarios
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
