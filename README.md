# Sistema Loja Informática

Projeto desenvolvido para a disciplina de Análise e Desenvolvimento de Sistemas, com o objetivo de criar um sistema de gestão comercial para uma loja de informática.

O sistema possui backend em Java com Spring Boot, banco de dados MySQL, autenticação com JWT, controle de usuários, clientes, produtos, vendas, estoque, relatórios e interface gráfica em Java Swing integrada à API.

---

## Tecnologias Utilizadas

* Java 21
* Spring Boot 3
* Spring Web
* Spring Data JPA
* Spring Security
* JWT
* MySQL
* Maven
* Java Swing
* Git e GitHub
* Thunder Client para testes da API

---

## Funcionalidades do Sistema

O sistema permite:

* Cadastro, listagem, atualização e exclusão de usuários.
* Cadastro, listagem, atualização e exclusão de clientes.
* Cadastro, listagem, atualização e exclusão de produtos.
* Login de usuários com geração de token JWT.
* Proteção dos endpoints da API usando autenticação Bearer Token.
* Registro de vendas.
* Cálculo automático do valor total da venda.
* Baixa automática no estoque após a venda.
* Bloqueio de venda quando o estoque é insuficiente.
* Emissão de relatórios de vendas.
* Emissão de relatório de estoque.
* Interface Swing integrada à API REST.

---

## Arquitetura do Projeto

O projeto foi estruturado em camadas, seguindo boas práticas de organização:

```text
lojainfo
├── config
├── controller
├── domain
│   └── model
├── dto
├── exception
├── repository
├── security
├── service
└── swing
```

### Camadas

**Controller:** responsável por receber as requisições HTTP e retornar as respostas da API.

**Service:** contém as regras de negócio do sistema, como autenticação, vendas, cálculo de total e controle de estoque.

**Repository:** responsável pela comunicação com o banco de dados usando Spring Data JPA.

**Domain/Model:** contém as entidades do sistema, como Usuario, Cliente, Produto, Venda e ItemVenda.

**Security:** contém as classes responsáveis pela geração, validação e filtro do token JWT.

**Swing:** contém as telas gráficas do sistema desktop.

---

## Entidades Principais

### Usuario

Representa os usuários do sistema.

Campos principais:

* id
* username
* password
* perfil

### Cliente

Representa os clientes cadastrados.

Campos principais:

* id
* nome
* email
* telefone
* endereco

### Produto

Representa os produtos da loja.

Campos principais:

* id
* nome
* categoria
* marca
* estoque
* preco

### Venda

Representa uma venda realizada.

Campos principais:

* id
* dataVenda
* valorTotal
* cliente
* itens

### ItemVenda

Representa os itens pertencentes a uma venda.

Campos principais:

* id
* quantidade
* precoUnitario
* venda
* produto

---

## Segurança com JWT

O sistema utiliza autenticação baseada em JWT.

O fluxo funciona da seguinte forma:

1. O usuário é cadastrado no sistema.
2. A senha é criptografada usando BCrypt.
3. O usuário realiza login pelo endpoint `/auth/login`.
4. A API retorna um token JWT.
5. Para acessar endpoints protegidos, o token deve ser enviado no header da requisição.

Exemplo de header:

```text
Authorization: Bearer TOKEN_GERADO
```

Endpoints públicos:

```text
POST /usuarios
POST /auth/login
```

Os demais endpoints exigem autenticação.

---

## Endpoints da API

### Autenticação

```http
POST /auth/login
```

Exemplo de body:

```json
{
  "username": "admin2",
  "password": "123456"
}
```

Resposta esperada:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Usuários

```http
GET /usuarios
GET /usuarios/{id}
POST /usuarios
PUT /usuarios/{id}
DELETE /usuarios/{id}
```

Exemplo de cadastro:

```json
{
  "username": "admin2",
  "password": "123456",
  "perfil": "ADMIN"
}
```

---

### Clientes

```http
GET /clientes
GET /clientes/{id}
POST /clientes
PUT /clientes/{id}
DELETE /clientes/{id}
```

Exemplo de cadastro:

```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "telefone": "11999999999",
  "endereco": "Rua Exemplo, 123"
}
```

---

### Produtos

```http
GET /produtos
GET /produtos/{id}
POST /produtos
PUT /produtos/{id}
DELETE /produtos/{id}
```

Exemplo de cadastro:

```json
{
  "nome": "Notebook Dell Inspiron",
  "categoria": "Notebook",
  "marca": "Dell",
  "estoque": 10,
  "preco": 3500.00
}
```

---

### Vendas

```http
GET /vendas
GET /vendas/{id}
POST /vendas
DELETE /vendas/{id}
```

Exemplo de venda:

```json
{
  "cliente": {
    "id": 1
  },
  "itens": [
    {
      "produto": {
        "id": 1
      },
      "quantidade": 2
    }
  ]
}
```

Ao registrar a venda, o sistema:

* busca o cliente informado;
* busca o produto informado;
* valida o estoque disponível;
* calcula o valor total;
* reduz o estoque automaticamente;
* salva a venda e seus itens.

---

### Relatórios

```http
GET /relatorios/vendas
GET /relatorios/vendas/periodo?inicio=2026-06-01&fim=2026-06-30
GET /relatorios/vendas/total
GET /relatorios/estoque
```

Esses endpoints permitem consultar:

* vendas registradas;
* vendas por período;
* valor total vendido;
* situação atual do estoque.

---

## Interface Swing

O sistema possui uma interface gráfica desenvolvida com Java Swing.

A interface permite:

* realizar login pela API;
* armazenar o token JWT durante a sessão;
* listar produtos;
* registrar vendas;
* visualizar relatórios;
* sair da sessão.

As telas principais são:

```text
TelaLoginSwing
TelaMenuSwing
TelaProdutosSwing
TelaVendaSwing
TelaRelatoriosSwing
SessaoUsuario
```

A tela de login envia usuário e senha para a API. Após o login, o token JWT é armazenado e utilizado nas requisições protegidas.

---

## Banco de Dados

O sistema utiliza MySQL.

Nome do banco utilizado:

```sql
infoloja
```

Configuração principal no arquivo `application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/infoloja
    username: root
    password: ceub123456

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8080

jwt:
  secret: 1234567890123456789012345678901234567890123456789012345678901234
  expiration: 86400000
```

---

## Como Executar o Projeto

### 1. Criar o banco no MySQL

```sql
CREATE DATABASE infoloja;
```

### 2. Entrar na pasta do backend

```cmd
cd lojainfo
```

### 3. Rodar o projeto

```cmd
.\mvnw.cmd spring-boot:run
```

A API será executada em:

```text
http://localhost:8080
```

### 4. Testar a API

Utilize Thunder Client, Postman ou outra ferramenta semelhante.

Primeiro cadastre um usuário, depois faça login e copie o token JWT.

---

## Como Testar a Interface Swing

1. Deixe o Spring Boot rodando.
2. Abra o arquivo `TelaLoginSwing.java`.
3. Execute o arquivo como aplicação Java.
4. Faça login com um usuário cadastrado.
5. Utilize o menu para listar produtos, registrar vendas e visualizar relatórios.

---

## Design Pattern Utilizado

O projeto utiliza o padrão Repository, por meio do Spring Data JPA.

Esse padrão separa a lógica de acesso aos dados das demais camadas do sistema, facilitando manutenção, organização e reutilização do código.

Exemplo:

```text
ProdutoController -> ProdutoService -> ProdutoRepository -> Banco de Dados
```

---

## Validações Implementadas

O sistema possui validações importantes, como:

* impedir login com usuário inexistente;
* impedir login com senha incorreta;
* proteger endpoints sem token JWT;
* impedir venda sem cliente;
* impedir venda sem itens;
* impedir venda com produto inexistente;
* impedir venda com quantidade menor ou igual a zero;
* impedir venda quando o estoque é insuficiente.

---

## Conclusão

O Sistema Loja Informática atende aos principais requisitos de uma aplicação comercial, permitindo o controle de clientes, produtos, usuários, vendas, estoque e relatórios.

Além disso, o projeto utiliza autenticação JWT, criptografia de senha com BCrypt, arquitetura em camadas, persistência em banco MySQL e interface gráfica Swing integrada à API REST.

Com isso, o sistema demonstra uma aplicação funcional, organizada e adequada para a gestão de uma loja de informática.
