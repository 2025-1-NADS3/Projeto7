<?php
// Configurações do banco de dados
$host = "";
$user = "";
$pass = "";
$database = "";

// Conexão com o banco de dados
$conn = mysqli_connect($host, $user, $pass, $database);

// Verifica a conexão
if (!$conn) {
    die("Falha na conexão: " . mysqli_connect_error());
}

// Você pode retornar uma mensagem simples para testes
echo "Conexão bem-sucedida!";
?>
