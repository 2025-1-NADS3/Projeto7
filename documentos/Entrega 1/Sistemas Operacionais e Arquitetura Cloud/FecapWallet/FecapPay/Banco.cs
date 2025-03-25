using MySql.Data.MySqlClient;
using System;

class Banco
{
    private static string conexaoString = "server=localhost;database=DBFECAPWALLET;user=root;password=;";

    public static MySqlConnection Conectar()
    {
        MySqlConnection conexao = new MySqlConnection(conexaoString);
        try
        {
            conexao.Open();
            Console.WriteLine("Conexão bem-sucedida!");
            return conexao;
        }
        catch (Exception ex)
        {
            Console.WriteLine("Erro ao conectar: " + ex.Message);
            return null;
        }
    }
}
