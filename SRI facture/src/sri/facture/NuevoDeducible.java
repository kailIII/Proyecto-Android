/**
 * 
 */
package sri.facture;

import sri.facture.providers.DeducibleProvider;
import sri.facture.providers.FacturaProvider;

import com.sri.facture.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Ricardo X. Campozano
 *
 */
public class NuevoDeducible extends Activity {
	String id_user="0"; 
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.beta_nuevo_deducible);
	        
	        
	    }

	 public void Save(View view){
	 		String talimentacion=((EditText) findViewById(R.id.talimentacion)).getText().toString();
	 		String teducacion=((EditText) findViewById(R.id.teducacion)).getText().toString();
	 		String tsalud=((EditText) findViewById(R.id.tsalud)).getText().toString();
	 		String tvestimenta=((EditText) findViewById(R.id.tvivienda)).getText().toString();
	 		String tvivienda=((EditText) findViewById(R.id.tvivienda)).getText().toString();
	 			 		
	 		double ta=0;
	 		double te=0;
	 		double ts=0;
	 		double tve=0;
	 		double tvi=0;
	 		
	 		if(!(talimentacion.trim().isEmpty())){
				ta=Double.parseDouble(talimentacion);
			}
	 		if(!(teducacion.trim().isEmpty())){
				te=Double.parseDouble(teducacion);
			}
	 		if(!(tsalud.trim().isEmpty())){
				ts=Double.parseDouble(tsalud);
			}
	 		if(!(tvestimenta.trim().isEmpty())){
				tve=Double.parseDouble(tvestimenta);
			}
	 		if(!(tvivienda.trim().isEmpty())){
				tvi=Double.parseDouble(tvivienda);
			}
	 		
	 		
	 		double tded=ta+te+ts+tve+tvi;
 			
	 		String numero="";
	        String fecha="";
	        String tgasto="";
	        String ciudad="";
	        String rucp="";
	        String prov="";
	        String tdeducible="0"; 
	        
	        Bundle extras = getIntent().getExtras();
	        if(extras!=null){
	        	numero=extras.getString("numero");
	        	//Log.d("my tag" ,"numero eviado de n deducible "+numero);
	        	fecha=extras.getString("fecha");
	        	tgasto=extras.getString("tgasto");
	        	ciudad=extras.getString("ciudad");
	        	rucp=extras.getString("rucp");
	        	prov=extras.getString("prov");
	            tdeducible = extras.getString("tded");
	            id_user=extras.getString("id_user");
	            //Log.d("my tag" ,"id eviado de n deducible "+id_user);
	        }
	 		
	        //Log.d("my tag" ,"numero eviado de n deducible "+numero);
 			Intent i = new Intent(this, NuevaFactura.class);
 			i.putExtra("ta", ta+"");
 	        i.putExtra("te", te+"");
 	        i.putExtra("ts", ts+"");
	        i.putExtra("tve", tve+"");
	        i.putExtra("tvi", tvi+"");
 	        i.putExtra("tded", tded+"");
 	        i.putExtra("numero", numero);
	 		i.putExtra("fecha", fecha);
	 		i.putExtra("tgasto", tgasto);
	 		i.putExtra("ciudad", ciudad);
	 		i.putExtra("rucp", rucp);
	 		i.putExtra("prov", prov);
	 		
	 		i.putExtra("id_user", id_user);
 		    startActivity(i);
	 		finish();
	 		
	 	}
}
