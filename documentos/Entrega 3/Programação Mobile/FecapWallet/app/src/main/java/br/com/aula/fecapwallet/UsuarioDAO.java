package br.com.aula.fecapwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsuarioDAO {

    private DBHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // CREATE
    public boolean cadastrarUsuario(String nome, String sobrenome, String ra, String celular,
                                    String cpf, String email, String senha) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String senhaCriptografada = CriptoUtils.criptografar(senha);
        String cpfCriptografado = CriptoUtils.criptografar(cpf);

        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COL_NOME, nome);
        valores.put(DBHelper.COL_SOBRENOME, sobrenome);
        valores.put(DBHelper.COL_RA, ra);
        valores.put(DBHelper.COL_CELULAR, celular);
        valores.put(DBHelper.COL_CPF, cpfCriptografado);
        valores.put(DBHelper.COL_EMAIL, email);
        valores.put(DBHelper.COL_SENHA, senhaCriptografada);

        long resultado = -1;
        try {
            resultado = db.insert(DBHelper.TABLE_USUARIOS, null, valores);
            if (resultado == -1) {
                Log.e("UsuarioDAO", "Erro ao cadastrar usuário. Verifique dados duplicados ou inválidos.");
            } else {
                Log.d("UsuarioDAO", "Usuário cadastrado com sucesso. ID: " + resultado);
            }
        } catch (Exception e) {
            Log.e("UsuarioDAO", "Exceção ao cadastrar usuário:", e);
        } finally {
            db.close();
        }

        return resultado != -1;
    }

    // READ - Login
    public boolean verificarLogin(String email, String senha) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String senhaCriptografada = CriptoUtils.criptografar(senha);

        String query = "SELECT * FROM " + DBHelper.TABLE_USUARIOS +
                " WHERE " + DBHelper.COL_EMAIL + " = ?" +
                " AND " + DBHelper.COL_SENHA + " = ?";

        String[] args = { email, senhaCriptografada };

        Cursor cursor = db.rawQuery(query, args);
        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }

    // UPDATE - Senha
    public boolean atualizarSenha(String email, String novaSenha) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String novaSenhaCriptografada = CriptoUtils.criptografar(novaSenha);

        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COL_SENHA, novaSenhaCriptografada);

        int linhasAfetadas = db.update(DBHelper.TABLE_USUARIOS, valores,
                DBHelper.COL_EMAIL + " = ?", new String[]{email});

        db.close();
        return linhasAfetadas > 0;
    }

    // DELETE - Conta
    public boolean excluirUsuario(String email, String senha) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String senhaCriptografada = CriptoUtils.criptografar(senha);

        int linhasAfetadas = db.delete(DBHelper.TABLE_USUARIOS,
                DBHelper.COL_EMAIL + " = ? AND " + DBHelper.COL_SENHA + " = ?",
                new String[]{email, senhaCriptografada});

        db.close();
        return linhasAfetadas > 0;
    }

    // GET NOME POR EMAIL
    public String getNomePorEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String nome = null;

        Cursor cursor = db.query(DBHelper.TABLE_USUARIOS,
                new String[]{DBHelper.COL_NOME},
                DBHelper.COL_EMAIL + " = ?",
                new String[]{email},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                nome = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_NOME));
            }
            cursor.close();
        }

        db.close();
        return nome;
    }

    // GET USUARIO POR EMAIL
    public Usuario getUsuarioPorEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Usuario usuario = null;

        Cursor cursor = db.query(DBHelper.TABLE_USUARIOS,
                null,
                DBHelper.COL_EMAIL + " = ?",
                new String[]{email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setNome(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_NOME)));
            usuario.setSobrenome(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_SOBRENOME)));
            usuario.setRa(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_RA)));
            usuario.setCelular(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_CELULAR)));
            usuario.setCpf(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_CPF)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_EMAIL)));
            cursor.close();
        }

        db.close();
        return usuario;
    }

    // UPDATE - Nome e Telefone
    public boolean atualizarNomeETelefone(String email, String novoNome, String novoTelefone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COL_NOME, novoNome);
        valores.put(DBHelper.COL_CELULAR, novoTelefone);
        int linhasAfetadas = db.update(DBHelper.TABLE_USUARIOS, valores,
                DBHelper.COL_EMAIL + " = ?", new String[]{email});
        db.close();
        return linhasAfetadas > 0;
    }
}
