using MySql.Data.MySqlClient;
using System;

class UsuarioDAO
{
    public static void CriarUsuario(string nome, string email, string senha)
    {
        using (var conexao = Banco.Conectar())
        {
            if (conexao == null) return;

            string query = "INSERT INTO usuarios (nome, email, senha) VALUES (@nome, @email, @senha)";
            MySqlCommand cmd = new MySqlCommand(query, conexao);
            cmd.Parameters.AddWithValue("@nome", nome);
            cmd.Parameters.AddWithValue("@email", email);
            cmd.Parameters.AddWithValue("@senha", senha);

            cmd.ExecuteNonQuery();
            Console.WriteLine("Usuário cadastrado com sucesso!");
        }
    }



    public static void ListarUsuarios()
    {
    using (var conexao = Banco.Conectar())
    {
        if (conexao == null) return;

        string query = "SELECT * FROM usuarios";
        MySqlCommand cmd = new MySqlCommand(query, conexao);
        MySqlDataReader reader = cmd.ExecuteReader();

        while (reader.Read())
        {
            Console.WriteLine($"ID: {reader["id"]}, Nome: {reader["nome"]}, Email: {reader["email"]}");
        }
    }
    }



    public static void AtualizarUsuario(int id, string novoNome, string novoEmail)
    {
    using (var conexao = Banco.Conectar())
    {
        if (conexao == null) return;

        string query = "UPDATE usuarios SET nome = @nome, email = @email WHERE id = @id";
        MySqlCommand cmd = new MySqlCommand(query, conexao);
        cmd.Parameters.AddWithValue("@nome", novoNome);
        cmd.Parameters.AddWithValue("@email", novoEmail);
        cmd.Parameters.AddWithValue("@id", id);

        cmd.ExecuteNonQuery();
        Console.WriteLine("Usuário atualizado com sucesso!");
    }
    }


    public static void DeletarUsuario(int id)
    {
    using (var conexao = Banco.Conectar())
    {
        if (conexao == null) return;

        string query = "DELETE FROM usuarios WHERE id = @id";
        MySqlCommand cmd = new MySqlCommand(query, conexao);
        cmd.Parameters.AddWithValue("@id", id);

        cmd.ExecuteNonQuery();
        Console.WriteLine("Usuário deletado com sucesso!");
    }
    }

}

