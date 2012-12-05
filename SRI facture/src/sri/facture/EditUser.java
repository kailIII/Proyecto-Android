/**
 * 
 */
package sri.facture;

import sri.facture.bd.DatabaseHelper;
import sri.facture.providers.UsuarioProvider;

import com.sri.facture.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Ricardo X. Campozano
 *
 */
public class EditUser extends Activity{
	
	String id_user="0";
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.beta_edit_user);
        
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id_user = extras.getString("id_user");
        }
        
        llenarFormulario();
            
    }
	
	public void llenarFormulario(){
		DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
    	SQLiteDatabase db = usdbh.getWritableDatabase();
    	Cursor c =  db.rawQuery("select * from usuario where _id='"+id_user+"'", null);
    	
    	if(c.moveToFirst()){
    		String nombre=c.getString(c.getColumnIndex("nombre"));
    		String apellido=c.getString(c.getColumnIndex("apellido"));
    		String cedula=c.getString(c.getColumnIndex("cedula"));
    		String periodo=c.getString(c.getColumnIndex("periodo"));
    		
    		EditText vn=(EditText)findViewById(R.id.enombre);
    		EditText va=(EditText)findViewById(R.id.eapellido);
    		EditText vc=(EditText)findViewById(R.id.ecedula);
    		    		
    		vn.setText(nombre);
    		va.setText(apellido);
    		vc.setText(cedula);
    		
    		String array_spinner[];
            array_spinner=new String[2];
            if(periodo.equals("semestral")){
    	        array_spinner[0]="Semestral";
    	        array_spinner[1]="Anual";
            }else{
            	array_spinner[0]="Anual";
    	        array_spinner[1]="Semestral";
            }
            Spinner s = (Spinner) findViewById(R.id.eperiodo);
            ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, array_spinner);
                    s.setAdapter(adapter);
    		
    	}
    	usdbh.close();
	}
	
	public void Guardar(View view)
    {
		String nombre=((EditText) findViewById(R.id.enombre)).getText().toString();
    	String apellido=((EditText) findViewById(R.id.eapellido)).getText().toString();
		String cedula=((EditText) findViewById(R.id.ecedula)).getText().toString();
		String periodo=((Spinner) findViewById(R.id.eperiodo)).getSelectedItem().toString();
		
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
    			    			
    			String uriString="content://"+UsuarioProvider.PROVIDER_NAME+"/"+UsuarioProvider.ENTIDAD+"/"+id_user;
				Uri CONTENT_URI = Uri.parse(uriString);
				getContentResolver().update(CONTENT_URI, values,null,null);
    			
				Intent dato = new Intent();
		        //dato.putExtra("nombre","valor");//Dato que pasaremos a la actividad principal
		        setResult(android.app.Activity.RESULT_OK,dato );
		        //Nos devuelve a la actividad principal "ActividadPrincipal"
		        finish();
    			
    	}
		
        
    }
}
