/**
 * 
 */
package sri.facture;

import sri.facture.providers.UsuarioProvider;


import com.sri.facture.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * @author Ricardo X. Campozano
 *
 */
public class NewUser extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beta_new_user);
        String array_spinner[];
        array_spinner=new String[2];
        array_spinner[0]="Semestral";
	    array_spinner[1]="Anual";
	    
	    Spinner s = (Spinner) findViewById(R.id.periodo);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
                s.setAdapter(adapter);
        
    }
	
	public void Guardar(View view){
		String nombre=((EditText) findViewById(R.id.nombre)).getText().toString();
    	String apellido=((EditText) findViewById(R.id.apellido)).getText().toString();
		String cedula=((EditText) findViewById(R.id.cedula)).getText().toString();
		String periodo=((Spinner) findViewById(R.id.periodo)).getSelectedItem().toString();
		
		String foto="default";
		
    	if(nombre.trim().isEmpty()||apellido.trim().isEmpty()||cedula.trim().isEmpty()){
			Toast.makeText(this,"Faltan campos por completar", Toast.LENGTH_SHORT).show();
		}
    	else{
    		
    			ContentValues values = new ContentValues();
				
    			values.put(
    					UsuarioProvider.NOMBRE,nombre);
    			values.put(
    					UsuarioProvider.APELLIDO,apellido);
    			values.put(
    					UsuarioProvider.CEDULA,cedula);
    			values.put(
    					UsuarioProvider.PERIODO,periodo);
    			values.put(
    					UsuarioProvider.USER,"");
    			values.put(
    					UsuarioProvider.PASS,"");
    			values.put(
    					UsuarioProvider.FOTO, "");
    			
    			
    			Uri uriNuew = getContentResolver().insert(UsuarioProvider.CONTENT_URI, values);
    			
    			//IO implemento
    			managedQuery(uriNuew, null, null, null, null);		
    			
    			Intent dato = new Intent();
		        //dato.putExtra("nombre","valor");//Dato que pasaremos a la actividad principal
		        setResult(android.app.Activity.RESULT_OK,dato );
		        //Nos devuelve a la actividad principal "ActividadPrincipal"
		        finish();
    		
    	}
	}
}
