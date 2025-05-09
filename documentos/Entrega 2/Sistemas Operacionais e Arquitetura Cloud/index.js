//Importando as bibliotecas necessarias, instaladas no projeto!
const express = require('express');
const mysql = require('mysql2');
const cors = require('cors');
const bodyParser = require('body-parser');


//Criando a API chamada app, Cors = Permite conexões externas (como de um app ou site). / BodyParser = Faz o app entender os dados enviados em JSON (por exemplo: { "email": "exemplo@email.com" }).
const app = express();
app.use(cors());
app.use(bodyParser.json());


//Criando a conexão com o banco de dados
const db = mysql.createConnection({
    host: 'androidpi.mysql.database.azure.com',
    port: 3306,
    user: 'adminandroidpi',
    password: 'Felipe123!',
    database:'androidbd',
    ssl: {
        rejectUnauthorized: true
    }
  });
// Testando a conexão com o banco de dados
db.connect(err => {
    if (err){
        console.log('Erro ao conectar com o banco', err);
    }
    else{
        console.log('Conectado ao MySQL com sucesso!');
    }
});






//Criando as rotas para Cadastro
app.post('/cadastro', (req,res) => {
    const { nome_completo, telefone, cpf, email, senha } = req.body;

    const query = 'INSERT INTO usuarios (nome_completo, telefone, cpf, email, senha) VALUES (?, ?, ?, ?, ?)';
    db.query(query, [nome_completo, telefone, cpf, email, senha], (err, result) => {
        if(err){
            console.log('Erro ao cadastrar', err);
            res.status(500).json({erro: 'Erro no servidor!'});
        }
        else{
            res.status(201).json({mensagem: 'Usuário cadastrado com sucesso!'});
        }
    });
});









//Criando as rotas para Login
app.post('/login', (req, res) => {
    const {email, senha} = req.body;

    const query = 'SELECT * FROM usuarios WHERE email = ? AND senha = ?';
    db.query(query, [email, senha], (err, result) => {
        if(err){
            res.status(500).json({erro: 'Erro no servidor'});
        }
        else{
            if(result.length > 0){
                res.json({mensagem:'Login bem sucedido', usuario: result[0] });
            }
            else{
                res.status(401).json({mensagem:'Email ou senha inválidos' });
            }
        }
    });
});


app.listen(3000, () => {
    console.log('API rodando em http://androidpi.mysql.database.azure.com/');
});