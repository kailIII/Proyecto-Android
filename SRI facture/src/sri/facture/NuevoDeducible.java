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
	String alimentos="0";
	String educacion="0";
	String salud="0";
	String vestimenta="0";
	String vivienda="0";
	
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.beta_nuevo_deducible);
	        
	        Bundle extras = getIntent().getExtras();
	        if(extras!=null){
	        	id_user=extras.getString("id_user");
	        	alimentos=extras.getString("alimentos");
	        	educacion=extras.getString("educacion");
	        	salud=extras.getString("salud");
	        	vestimenta=extras.getString("vestimenta");
	        	vivienda=extras.getString("vivienda");
	        	
	        	llenarFormulario();
	        }
	    }
	
	 public void llenarFormulario(){
		 EditText ta=(EditText)findViewById(R.id.talimentacion);
		 EditText te=(EditText)findViewById(R.id.teducacion);
		 EditText ts=(EditText)findViewById(R.id.tsalud);
		 EditText tve=(EditText)findViewById(R.id.tvestimenta);
		 EditText tvi=(EditText)findViewById(R.id.tvivienda);
		 
		 ta.setText(alimentos);
		 te.setText(educacion);
		 ts.setText(salud);
		 tve.setText(vestimenta);
		 tvi.setText(vivienda);
	 }

	 public void Save(View view){
	 		String talimentacion=((EditText) findViewById(R.id.talimentacion)).getText().toString();
	 		String teducacion=((EditText) findViewById(R.id.teducacion)).getText().toString();
	 		String tsalud=((EditText) findViewById(R.id.tsalud)).getText().toString();
	 		String tvestimenta=((EditText) findViewById(R.id.tvestimenta)).getText().toString();
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
 			
 			Intent i = new Intent();
 			i.putExtra("ta", ta+"");
 	        i.putExtra("te", te+"");
 	        i.putExtra("ts", ts+"");
	        i.putExtra("tve", tve+"");
	        i.putExtra("tvi", tvi+"");
 	        i.putExtra("tded", tded+"");
 	        
	 		setResult(android.app.Activity.RESULT_OK,i );
	 		finish();
	 		
	 	}
}
