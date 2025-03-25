using System;

class Program
{
    static void Main()
    {
        Console.WriteLine("1 - Criar Usuário");
        Console.WriteLine("2 - Listar Usuários");
        Console.WriteLine("3 - Atualizar Usuário");
        Console.WriteLine("4 - Deletar Usuário");
        Console.Write("Escolha uma opção: ");
        int opcao = int.Parse(Console.ReadLine());

        switch (opcao)
        {
            case 1:
                Console.Write("Nome: ");
                string nome = Console.ReadLine();
                Console.Write("Email: ");
                string email = Console.ReadLine();
                Console.Write("Senha: ");
                string senha = Console.ReadLine();
                UsuarioDAO.CriarUsuario(nome, email, senha);
                break;
            
            case 2:
                UsuarioDAO.ListarUsuarios();
                break;

            case 3:
                Console.Write("ID do usuário: ");
                int idUpdate = int.Parse(Console.ReadLine());
                Console.Write("Novo Nome: ");
                string novoNome = Console.ReadLine();
                Console.Write("Novo Email: ");
                string novoEmail = Console.ReadLine();
                UsuarioDAO.AtualizarUsuario(idUpdate, novoNome, novoEmail);
                break;

            case 4:
                Console.Write("ID do usuário: ");
                int idDelete = int.Parse(Console.ReadLine());
                UsuarioDAO.DeletarUsuario(idDelete);
                break;

            default:
                Console.WriteLine("Opção inválida!");
                break;
        }
    }
}

