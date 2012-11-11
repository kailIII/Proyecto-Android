package sri.facture.bd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
*
* @author Ricardo X. Campozano
*/
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "sri-facture.db";
    public static final int DATABASE_VERSION = 1;
       
    public static final String FICHERO_LOCAL_DATABASE_NAME = "schema_sri-facture.sqlite";
    private Context context;
    
    /*Constructor tipico*/
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        iniBase();
    }

    /* Constructor default*/
    public DatabaseHelper(Context context, String name, CursorFactory factory,
            int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.w("DatabaseHelper", "Actualizando la base de datos de version " +
                oldVersion + "a" + newVersion + ", por lo que destruira los datos viejos");
        db.execSQL("DROP TABLE IF EXISTS factura"); //Falta definir
        onCreate(db);
        iniBase();
    }

    /**
     * Este metodo permite copiar el archivo de bases de datos SQLite desde la
     * carpeta assets al repsitorio del dispositivo android.
     * @param inputStream cadena del archivo origen
     * @param outputStream cadena de salida para el archivo destino
     */
    public void copyDB(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        // ---copy 1k bytes at a time
        byte[] buffer = new byte[1024];
        int length;
        int i = 0;
        while((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
    
    public void iniBase(){
    	android.util.Log.w("DB", "CREANDO..");
    	try{
            String destPath = "/data/data/" + this.context.getPackageName()
                    + "/databases/" + DatabaseHelper.DATABASE_NAME;
            File file = new File(destPath);
            if (!file.exists()) {
            	android.util.Log.w("DB", "NO EXISTE");
                copyDB(this.context.getAssets().open(DatabaseHelper.FICHERO_LOCAL_DATABASE_NAME), new FileOutputStream(destPath));
            }
            else
            	android.util.Log.w("DB", "EXISTE");
        } catch(FileNotFoundException e) {
        	android.util.Log.w("DB", "error FILE: " +e.toString());
        } catch (IOException e){
        	android.util.Log.w("DB", "error IO: "+e.toString());
        }
		
	}
}
