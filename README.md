# Sistema Loja de Informática

## Sobre o Projeto

Sistema desenvolvido para gerenciamento de uma loja de informática, permitindo o cadastro e consulta de clientes, produtos, usuários, vendas e itens de venda.

O projeto foi desenvolvido utilizando Java 21, Spring Boot, Spring Data JPA e MySQL, seguindo uma arquitetura em camadas.

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Git
- GitHub
- Thunder Client

---

## Arquitetura do Projeto

```text
src/main/java/lojainfo
│
├── controller
├── service
├── repository
├── domain/model
└── LojainfoApplication
```

### Entidades

- Cliente
- Produto
- Usuario
- Venda
- ItemVenda

### Controllers

- ClienteController
- ProdutoController
- UsuarioController
- VendaController
- ItemVendaController

### Services

- ClienteService
- ProdutoService
- UsuarioService
- VendaService
- ItemVendaService

### Repositories

- ClienteRepository
- ProdutoRepository
- UsuarioRepository
- VendaRepository
- ItemVendaRepository

---

## Funcionalidades

### Clientes

- Cadastrar clientes
- Consultar clientes

### Produtos

- Cadastrar produtos
- Consultar produtos

### Usuários

- Cadastrar usuários
- Consultar usuários

### Vendas

- Registrar vendas
- Consultar vendas

### Itens de Venda

- Registrar itens da venda
- Consultar itens da venda

---

## Banco de Dados

Banco utilizado:

```sql
MySQL
```

Criação do banco:

```sql
CREATE DATABASE infoloja;
```

---

## Endpoints

### Clientes

```http
GET /clientes
POST /clientes
```

### Produtos

```http
GET /produtos
POST /produtos
```

### Usuários

```http
GET /usuarios
POST /usuarios
```

### Vendas

```http
GET /vendas
POST /vendas
```

### Itens de Venda

```http
GET /itens-venda
POST /itens-venda
```

---

## Como Executar

### Clonar o projeto

```bash
git clone https://github.com/SEU-USUARIO/sistema-loja-informatica.git
```

### Acessar a pasta

```bash
cd sistema-loja-informatica
```

### Configurar o banco MySQL

Criar o banco:

```sql
CREATE DATABASE infoloja;
```

Configurar o arquivo:

```properties
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/infoloja
spring.datasource.username=root
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Executar a aplicação

Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux/Mac:

```bash
./mvnw spring-boot:run
```

---

## Testes

Os testes da API foram realizados utilizando o Thunder Client integrado ao Visual Studio Code.

Exemplos de requisições:

### Cadastro de Cliente

```json
{
  "nome": "Luis Silva",
  "email": "luis@email.com",
  "telefone": "12999999999",
  "endereco": "Rua das Flores"
}
```

### Cadastro de Produto

```json
{
  "nome": "RTX 4060",
  "categoria": "Placa de Vídeo",
  "marca": "NVIDIA",
  "estoque": 10,
  "preco": 2499.90
}
```

---

## Autor

Luis

Projeto desenvolvido para fins acadêmicos na disciplina de Engenharia de Software.
