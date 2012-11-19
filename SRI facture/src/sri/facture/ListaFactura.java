/**
 * 
 */
package sri.facture;


import sri.facture.bd.DatabaseHelper;
import sri.facture.providers.FacturaProvider;

import com.sri.facture.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * @author Ricardo X. Campozano
 *
 */
public class ListaFactura extends ListActivity {
	
	String id_user="0"; 
	ListView miLista;
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.beta_lista_facturas);
	        
	        
	        Bundle extras = getIntent().getExtras();
	        if(extras!=null){
	            id_user = extras.getString("id_user");
	        }
	        
	        DatabaseHelper usdbh = new DatabaseHelper(this, "sri-facture.db", null, 1);
	        SQLiteDatabase db = usdbh.getWritableDatabase();
	 		
 	        Cursor c;
 	        c =  db.rawQuery( "select * from factura where id_usuario='"+id_user+"';", null);
	 	        
	 		String[] columns = new String[] {
	 				
	 				FacturaProvider.NUMERO,
	 				FacturaProvider.FECHA
	 					 				
	 				 };
	 		int[] views = new int[] {R.id.contactID, R.id.contactName };

	 		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.row, c, columns, views);
	 		miLista=this.getListView();
	 		miLista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	 		//this.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	 		miLista.setAdapter(adapter);
	 		usdbh.close();
	        
	    }
	 
	 	public void Add(View view){
	 		Intent i = new Intent(this, NuevaFactura.class);
	 		i.putExtra("id_user", id_user);
	        startActivity(i);
	        finish();
	 	}
	 	
	 	public void Edit(View view){
	 		
	 	}
	 	
	 	public void Delete(View view){
	 		//Obtengo los elementos seleccionados de mi lista
	        SparseBooleanArray seleccionados = miLista.getCheckedItemPositions();
	 
	        if(seleccionados==null || seleccionados.size()==0){
	            //Si no había elementos seleccionados...
	            Toast.makeText(this,"No hay elementos seleccionados",Toast.LENGTH_SHORT).show();
	        }else{
	            //si los había, miro sus valores            
	           	 
	            //Recorro my "array" de elementos seleccionados
	            final int size=seleccionados.size();
	            for (int i=0; i<size; i++) {
	                //Si valueAt(i) es true, es que estaba seleccionado
	                if (seleccionados.valueAt(i)) {
	                    //en keyAt(i) obtengo su posición
	                	Cursor c=(Cursor)miLista.getItemAtPosition(seleccionados.keyAt(i));
	                	
	                    //resultado.append("El elemento "+c.getString(1)+" estaba seleccionado\n");
	                	Log.d("my tag2" ,"yo soy "+c.getString(0));
	                	getContentResolver().delete(Uri.parse("content://sriFacture.proveedor.Factura/factura/"+c.getString(0)),  null, null);
	                    //miLista.refreshDrawableState();
	                    
	                	
	                    Intent in = new Intent(this, ListaFactura.class);
	                    in.putExtra("id_user", id_user);
	                    startActivity(in);
	                    finish();
	                    
	                }
	            }
	            
	        }
	 	}

}
