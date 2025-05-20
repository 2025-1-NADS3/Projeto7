package br.com.aula.fecapwallet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DBHelper extends SQLiteOpenHelper {

    // Nome e versão do banco
    private static final String DATABASE_NAME = "fecapay.db";
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela
    public static final String TABLE_USUARIOS = "usuarios";

    // Nomes das colunas
    public static final String COL_ID = "id";
    public static final String COL_NOME = "nome";
    public static final String COL_SOBRENOME = "sobrenome";
    public static final String COL_RA = "ra";
    public static final String COL_CELULAR = "celular";
    public static final String COL_CPF = "cpf";
    public static final String COL_EMAIL = "email";
    public static final String COL_SENHA = "senha";

    // Comando SQL para criar a tabela
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_USUARIOS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NOME + " TEXT NOT NULL, " +
                    COL_SOBRENOME + " TEXT NOT NULL, " +
                    COL_RA + " TEXT NOT NULL, " +
                    COL_CELULAR + " TEXT, " +
                    COL_CPF + " TEXT, " +
                    COL_EMAIL + " TEXT UNIQUE NOT NULL, " +
                    COL_SENHA + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Criação do banco
    @Override
    public void onCreate(SQLiteDatabase db) {
        android.util.Log.d("DBHelper", "Executando criação da tabela...");
        db.execSQL(SQL_CREATE_TABLE);
    }


    // Atualização do banco
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    // Método para inserir usuário com criptografia de senha e CPF
    public long inserirUsuario(String nome, String sobrenome, String ra, String celular, String cpf, String email, String senhaPura) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put(COL_NOME, nome);
        valores.put(COL_SOBRENOME, sobrenome);
        valores.put(COL_RA, ra);
        valores.put(COL_CELULAR, celular);

        // Criptografa o CPF
        String cpfCriptografado = CriptoUtils.criptografar(cpf);
        valores.put(COL_CPF, cpfCriptografado);

        valores.put(COL_EMAIL, email);

        // Criptografa a senha
        String senhaCriptografada = CriptoUtils.criptografar(senhaPura);
        valores.put(COL_SENHA, senhaCriptografada);

        long resultado = db.insert(TABLE_USUARIOS, null, valores);
        db.close();
        return resultado;
    }
}
