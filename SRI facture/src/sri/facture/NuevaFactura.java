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
	String talimentacion="0";
    String teducacion="0"; 
    String tsalud="0"; 
    String tvestimenta="0"; 
    String tvivienda="0"; 
	
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.beta_nueva_factura);  
	        
	        Bundle extras = getIntent().getExtras();
	        if(extras!=null){
	        	id_user=extras.getString("id_user");                        
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
    			
    			
    			//IO implemento
    			managedQuery(uriNuew, null, null, null, null);
    			
    			guardarDeducibles();
    			Intent dato = new Intent();
		        setResult(android.app.Activity.RESULT_OK,dato );
		        finish();
	 		}
	 		
	 	}
	 	
	 	public void detDeducibles(View view){
	 		Intent i = new Intent(this, NuevoDeducible.class);
	 		i.putExtra("id_user", id_user);
		    startActivityForResult(i, 13);
		    
	 	}
	 	
	 	public void guardarDeducibles(){
	 		
	 		int id=ultimoId();
	        Log.d("my tag" ,id+"");
	        
	        if(!(talimentacion.equals("0"))){
	        	deducibleItem(talimentacion,id,"1");
	        }
	        if(!(teducacion.equals("0"))){
	        	deducibleItem(teducacion,id,"2");
	        }
	        if(!(tsalud.equals("0"))){
	        	deducibleItem(tsalud,id,"3");
	        }
	        if(!(tvestimenta.equals("0"))){
	        	deducibleItem(tvestimenta,id,"4");
	        }
	        if(!(tvivienda.equals("0"))){
	        	deducibleItem(tvivienda,id,"5");
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
	 	
	 	public int ultimoId(){
	 		DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
        	SQLiteDatabase db = usdbh.getWritableDatabase();
        	Cursor c =  db.rawQuery( "select _id from factura where id_usuario='"+id_user+"'", null);
        	String proxima="1";
        	if ( c.moveToLast()) {
        		proxima=c.getString(c.getColumnIndex("_id"));
        		Log.d("my tag" ,"contar "+proxima);
        	}
        	usdbh.close();
        	return Integer.parseInt(proxima);
	 		
	 	}
	 	
	 	protected void onActivityResult(int requestCode,int resultCode, Intent pData)            
	    {
	        if ( requestCode == 13 )//Si el código de respuesta es igual al requestCode
	            {
	            if (resultCode == Activity.RESULT_OK )//Si resultCode es igual a ok
	                {
		            	talimentacion = pData.getExtras().getString("ta");
		            	teducacion = pData.getExtras().getString("te");
		            	tsalud = pData.getExtras().getString("ts");
		            	tvestimenta = pData.getExtras().getString("tve");
		            	tvivienda = pData.getExtras().getString("tvi");
	            	    String tdeducible = pData.getExtras().getString("tded");//Obtengo el string de la subactividad
	            		TextView ded=(TextView)findViewById(R.id.tdeducible);
	            		ded.setText(tdeducible);
	                }
	            }
	    }
	 	
	 	
	 	
	 	
	 	
	 	

}
