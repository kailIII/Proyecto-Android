/**
 * 
 */
package sri.facture;

import com.sri.facture.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Ricardo X. Campozano
 *
 */
public class EditFactura extends Activity {
	String id_user="0";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.beta_nueva_factura);  
        
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
        	//Log.d("my tag" ,"numero es "+numero);
        	fecha=extras.getString("fecha");
        	tgasto=extras.getString("tgasto");
        	ciudad=extras.getString("ciudad");
        	//Log.d("my tag" ,"ciudad eviado de n deducible "+ciudad);
        	rucp=extras.getString("rucp");
        	prov=extras.getString("prov");
            tdeducible = extras.getString("tded");
            Log.d("my tag" ,"deducible es "+tdeducible);
            id_user=extras.getString("id_user");
            
            EditText num =(EditText) findViewById(R.id.numero);
            EditText fe =(EditText) findViewById(R.id.fecha);
            EditText ga =(EditText) findViewById(R.id.gasto);
            EditText ciu =(EditText) findViewById(R.id.ciudad);
            EditText rp =(EditText) findViewById(R.id.rprov);
            EditText pro =(EditText) findViewById(R.id.prov);
            TextView ded = (TextView) findViewById(R.id.tdeducible);
            
            num.setText(numero);
            fe.setText(fecha);
            ga.setText(tgasto);
            ciu.setText(ciudad);
            rp.setText(rucp);
            pro.setText(prov);
            ded.setText(tdeducible);
        }
	}
	
	public void detDeducibles(View view){
 		String numero=((EditText) findViewById(R.id.numero)).getText().toString();
 		String fecha="2012/05/12";
 		String tgasto=((EditText) findViewById(R.id.gasto)).getText().toString();
 		//String tdeducible=((EditText) findViewById(R.id.deducible)).getText().toString();
 		String ciudad=((EditText) findViewById(R.id.ciudad)).getText().toString();
 		String rucp=((EditText) findViewById(R.id.rprov)).getText().toString();
 		String prov=((EditText) findViewById(R.id.prov)).getText().toString();
 		
 		//Log.d("my tag" ,"numero enviado en detalle deducibles "+numero);
 		Intent i = new Intent(this, NuevoDeducible.class);
 		i.putExtra("numero", numero);
 		i.putExtra("fecha", fecha);
 		i.putExtra("tgasto", tgasto);
 		i.putExtra("ciudad", ciudad);
 		i.putExtra("rucp", rucp);
 		i.putExtra("prov", prov);
 		i.putExtra("id_user", id_user);
 		
 		String alimentacion="";
 		String educacion="";
 		String salud="";
 		String vivienda="";
 		String vestimenta="";
 		String tded="";
 		Bundle extras = getIntent().getExtras();
        if(extras!=null){
        	alimentacion=extras.getString("ta");
        	alimentacion=extras.getString("ta");
        	alimentacion=extras.getString("ta");
        	alimentacion=extras.getString("ta");
        	alimentacion=extras.getString("ta");
        	alimentacion=extras.getString("ta");
        }
 		i.putExtra("ta", alimentacion+"");
	    i.putExtra("te", educacion+"");
	    i.putExtra("ts", salud+"");
        i.putExtra("tve", vestimenta+"");
        i.putExtra("tvi", vivienda+"");
	    i.putExtra("tded", tded+"");
	    startActivity(i);
	    finish();
 	}
}
