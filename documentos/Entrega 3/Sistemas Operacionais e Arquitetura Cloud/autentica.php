<?php
include("conexao.php"); // Conexão com o banco

// Pegando dados enviados pelo app via POST
$nome = $_POST['usuario'];
$senha = $_POST['senha'];

// Consulta SQL para verificar usuário
$sql = "SELECT * FROM usuarios WHERE usuario = '$usuario' AND senha = '$senha'";
$executa = mysqli_query($conn, $sql) or die(mysqli_error($conn));
$total = mysqli_num_rows($executa);

if ($total > 0) {
    $resultado = mysqli_fetch_assoc($executa);
    
    // Cria um array com os dados
    $response = array(
        "usuario" => $resultado['usuario'],
        "senha" => $resultado['senha']
    );

    echo json_encode($response); // Resposta em JSON
} else {
    // Retorna campos vazios para indicar erro
    $response = array(
        "usuario" => "vazio",
        "senha" => "vazio"
    );

    echo json_encode($response); // Resposta em JSON
}
?>
