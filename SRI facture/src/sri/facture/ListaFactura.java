/**
 * 
 */
package sri.facture;


import sri.facture.bd.DatabaseHelper;
import sri.facture.providers.FacturaProvider;

import com.sri.facture.R;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * @author Ricardo X. Campozano
 *
 */
public class ListaFactura extends ListActivity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.beta_lista_facturas);
	        
	        ListView miLista;
	        DatabaseHelper usdbh = new DatabaseHelper(this, "sri-facture.db", null, 1);
	 	 
	 	    SQLiteDatabase db = usdbh.getWritableDatabase();
	 		
 	        Cursor c;
 	        c =  db.rawQuery( "select * from factura", null);
	 	        
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

}
