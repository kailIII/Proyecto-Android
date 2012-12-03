/**
 * 
 */
package sri.facture;


import sri.facture.bd.DatabaseHelper;
import sri.facture.providers.FacturaProvider;

import com.sri.facture.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
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
	 				FacturaProvider.FECHA,
	 				FacturaProvider.TOTAL_GASTO
	 					 				
	 				 };
	 		int[] views = new int[] {R.id.rnum, R.id.rfecha, R.id.rtotal };

	 		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.row, c, columns, views);
	 		miLista=this.getListView();
	 		miLista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	 		//this.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	 		miLista.setLongClickable(true);
	 		miLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	 		    public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
	 		    	
	 		        return onLongListItemClick(v,pos,id);
	 		    }
	 		    
	 		});
	 		miLista.setAdapter(adapter);
	 		usdbh.close();
	        
	    }
	 
	 	public void Add(View view){
	 		Intent i = new Intent(this, NuevaFactura.class);
	 		i.putExtra("id_user", id_user);
	        startActivityForResult(i, 12);	        
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
	                	
	                	String id_factura=c.getString(0);
	                	
	                	deleteDeducibles(id_factura);
	                	getContentResolver().delete(Uri.parse("content://sriFacture.proveedor.Factura/factura/"+id_factura),  null, null);
	                    //miLista.refreshDrawableState();
	                    
	                    Intent in = new Intent(this, ListaFactura.class);
	                    in.putExtra("id_user", id_user);
	                    startActivity(in);
	                    finish();
	                    
	                }
	            }
	            
	        }
	 	}
	 	
	 	public void deleteDeducibles(String id_factura){
	 		DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
	    	SQLiteDatabase db = usdbh.getWritableDatabase();
	    	Cursor c =  db.rawQuery("select _id from deducible where id_factura='"+id_factura+"'", null);
	    	if(c.moveToFirst()){
	    		int row=c.getCount();
	    		for(int i=0;i<row;i++){
	    			getContentResolver().delete(Uri.parse("content://sriFacture.proveedor.Deducible/deducible/"+c.getString(0)),  null, null);
	    			c.moveToNext();
	    		}
	    	}
	    	usdbh.close();
	 	}
	 	
	 	protected boolean onLongListItemClick(View v, int pos, long id) {
	 		Cursor c=(Cursor)miLista.getItemAtPosition(pos);
	 		Intent i = new Intent(this, EditFactura.class);
	 		i.putExtra("id_user", id_user);
	 		i.putExtra("id_factura", c.getString(c.getColumnIndex("_id")));
	        startActivityForResult(i, 121);	
	 		return true;
	 	}
	 	
	 	protected void onActivityResult(int requestCode,int resultCode, Intent pData)            
	    {
	        if ( requestCode == 121 || requestCode == 12 )//Si el código de respuesta es igual al requestCode
	            {
	            if (resultCode == Activity.RESULT_OK )//Si resultCode es igual a ok
	                {
		            	Intent i = new Intent(this, ListaFactura.class);
		            	i.putExtra("id_user", id_user);
		                startActivity(i);
		                finish();
	                }
	            }
	        
	    }

}
