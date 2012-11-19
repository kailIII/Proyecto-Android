/**
 * 
 */
package sri.facture;

import sri.facture.providers.DeducibleProvider;
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
import android.widget.TextView;
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
	        
	        
	        String tdeducible="0"; 
	        Bundle extras = getIntent().getExtras();
	        if(extras!=null){
	            tdeducible = extras.getString("tded");
	            
	            TextView ded = (TextView) findViewById(R.id.tdeducible);
	            ded.setText(tdeducible);
	        }
	        
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
    			
    			guardarDeducibles();
    			//IO implemento
    			managedQuery(uriNuew, null, null, null, null);		
    			
    			Intent i = new Intent(this, ListaFactura.class);
    		     startActivity(i);
	 		}
	 		
	 	}
	 	
	 	public void detDeducibles(View view){
	 		Intent i = new Intent(this, NuevoDeducible.class);
		    startActivity(i);
	 	}
	 	
	 	public void guardarDeducibles(){
	 		String talimentacion="0";
	        String teducacion="0"; 
	        String tsalud="0"; 
	        String tvestimenta="0"; 
	        String tvivienda="0"; 
	        
	        Bundle extras = getIntent().getExtras();
	        if(extras!=null){
	            talimentacion = extras.getString("ta");
	            teducacion = extras.getString("te");
	            tsalud = extras.getString("ts");
	            tvestimenta = extras.getString("tve");
	            tvivienda = extras.getString("tvi");	            
	        }
	        
	        
	        if(!(talimentacion.equals("0"))){
	        	deducibleItem(talimentacion,"1","1");
	        }
	        if(!(teducacion.equals("0"))){
	        	deducibleItem(teducacion,"1","2");
	        }
	        if(!(tsalud.equals("0"))){
	        	deducibleItem(tsalud,"1","3");
	        }
	        if(!(tvestimenta.equals("0"))){
	        	deducibleItem(tvestimenta,"1","4");
	        }
	        if(!(tvivienda.equals("0"))){
	        	deducibleItem(tvivienda,"1","5");
	        }
	        
	        
	 	}
	 	
	 	public void deducibleItem(String item,String factura,String categoria){
	 		ContentValues values = new ContentValues();
			
			values.put(
					DeducibleProvider.TOTAL,item);
			values.put(
					DeducibleProvider.ID_FACTURA,factura);
			values.put(
					DeducibleProvider.ID_CATEGORIA,categoria);
			
			Uri uriNuew = getContentResolver().insert(DeducibleProvider.CONTENT_URI, values);
			managedQuery(uriNuew, null, null, null, null);
	 	}
	 	
	 	
	 	
	 	

}
