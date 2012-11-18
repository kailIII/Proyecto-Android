/**
 * 
 */
package sri.facture;

import sri.facture.providers.FacturaProvider;
import sri.facture.providers.UsuarioProvider;

import com.sri.facture.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Ricardo X. Campozano
 *
 */
public class NuevaFactura extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.beta_nueva_factura);       
	        
	    }
	 
	 	public void Save(View view){
	 		String numero=((EditText) findViewById(R.id.numero)).getText().toString();
	 		String fecha="2012/05/12";
	 		String tgasto=((EditText) findViewById(R.id.gasto)).getText().toString();
	 		//String tdeducible=((EditText) findViewById(R.id.deducible)).getText().toString();
	 		String ciudad=((EditText) findViewById(R.id.ciudad)).getText().toString();
	 		String rucp=((EditText) findViewById(R.id.rprov)).getText().toString();
	 		String prov=((EditText) findViewById(R.id.prov)).getText().toString();
	 		//insert into factura values('1','22','22','2007/05/12','333','44','ciud','nlo','1');
	 		if(numero.trim().isEmpty()){
				Toast.makeText(this,"Faltan campos por completar", Toast.LENGTH_SHORT).show();
			}
	 		else{
	 			ContentValues values = new ContentValues();
				
    			values.put(
    					FacturaProvider.NUMERO,numero);
    			values.put(
    					FacturaProvider.FECHA,fecha);
    			values.put(
    					FacturaProvider.TOTAL_GASTO,tgasto);
    			values.put(
    					FacturaProvider.CIUDAD,ciudad);
    			values.put(
    					FacturaProvider.RUC_PROVEEDOR,rucp);
    			values.put(
    					FacturaProvider.N_PROVEEDOR,prov);
    			values.put(
    					FacturaProvider.ID_USUARIO,"1");
    			
    			Uri uriNuew = getContentResolver().insert(FacturaProvider.CONTENT_URI, values);
    			
    			//IO implemento
    			managedQuery(uriNuew, null, null, null, null);		
    			
    			Intent i = new Intent(this, ListaFactura.class);
    		     startActivity(i);
	 		}
	 		
	 	}
	 	
	 	

}
