/**
 * 
 */
package sri.facture;

import sri.facture.bd.DatabaseHelper;

import com.sri.facture.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author Ricardo X. Campozano
 *
 */
public class ViewUser extends Activity{
	
	String id_user="0";
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beta_view_user);
        
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id_user = extras.getString("id_user");
        }
        
        llenarFormulario();
            
    }
	
	public void editarUsuario(View view){
		Intent i = new Intent(this, EditUser.class);
		i.putExtra("id_user", id_user);
    	startActivityForResult(i, 42);//42 -->4 ta opcion del menu + 2 ventana de esa opcion

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
    		
    		TextView vn=(TextView)findViewById(R.id.vnombre);
    		TextView va=(TextView)findViewById(R.id.vapellido);
    		TextView vc=(TextView)findViewById(R.id.vcedula);
    		TextView vp=(TextView)findViewById(R.id.vperiodo);
    		
    		vn.setText(nombre);
    		va.setText(apellido);
    		vc.setText(cedula);
    		vp.setText(periodo);
    	}
    	usdbh.close();
	}
	
	protected void onActivityResult(int requestCode,int resultCode, Intent pData)            
    {
        if ( requestCode == 42 )//Si el código de respuesta es igual al requestCode
            {
            if (resultCode == Activity.RESULT_OK )//Si resultCode es igual a ok
                {
            		llenarFormulario();
                }
            }
    }
}
