/**
 * 
 */
package sri.facture;

import sri.facture.bd.DatabaseHelper;
import sri.facture.providers.DeducibleProvider;
import sri.facture.providers.FacturaProvider;
import sri.facture.providers.UsuarioProvider;

import com.sri.facture.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
public class NuevaFactura extends Activity {
	String id_user="0"; 
	@Override
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
	        	Log.d("my tag" ,"numero es "+numero);
	        	fecha=extras.getString("fecha");
	        	tgasto=extras.getString("tgasto");
	        	ciudad=extras.getString("ciudad");
	        	Log.d("my tag" ,"ciudad eviado de n deducible "+ciudad);
	        	rucp=extras.getString("rucp");
	        	prov=extras.getString("prov");
	            tdeducible = extras.getString("tded");
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
    					FacturaProvider.ID_USUARIO,id_user);
    			
    			Uri uriNuew = getContentResolver().insert(FacturaProvider.CONTENT_URI, values);
    			
    			guardarDeducibles();
    			//IO implemento
    			managedQuery(uriNuew, null, null, null, null);		
    			
    			Intent i = new Intent(this, ListaFactura.class);
    			i.putExtra("id_user", id_user);
    		     startActivity(i);
    		     finish();
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
	 		
	 		Log.d("my tag" ,"numero enviado en detalle deducibles "+numero);
	 		Intent i = new Intent(this, NuevoDeducible.class);
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
	        int proxima=contar()+1; 
	        
	        if(!(talimentacion.equals("0"))){
	        	deducibleItem(talimentacion,proxima,"1");
	        }
	        if(!(teducacion.equals("0"))){
	        	deducibleItem(teducacion,proxima,"2");
	        }
	        if(!(tsalud.equals("0"))){
	        	deducibleItem(tsalud,proxima,"3");
	        }
	        if(!(tvestimenta.equals("0"))){
	        	deducibleItem(tvestimenta,proxima,"4");
	        }
	        if(!(tvivienda.equals("0"))){
	        	deducibleItem(tvivienda,proxima,"5");
	        }
	        
	        
	 	}
	 	
	 	public void deducibleItem(String item,int factura,String categoria){
	 		ContentValues values = new ContentValues();
			
			values.put(
					DeducibleProvider.TOTAL,item);
			values.put(
					DeducibleProvider.ID_FACTURA,factura+"");
			values.put(
					DeducibleProvider.ID_CATEGORIA,categoria);
			
			Uri uriNuew = getContentResolver().insert(DeducibleProvider.CONTENT_URI, values);
			managedQuery(uriNuew, null, null, null, null);
	 	}
	 	
	 	public int contar(){
	 		DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
        	SQLiteDatabase db = usdbh.getWritableDatabase();
        	Cursor c =  db.rawQuery( "select count(_id) from factura where id_usuario='"+id_user+"'", null);
        	String proxima="1";
        	if ( c.moveToFirst() ) {
        		proxima=c.getString(0);
        	}
        	usdbh.close();
        	return Integer.parseInt(proxima);
	 		
	 	}
	 	
	 	
	 	
	 	

}
