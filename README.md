# Sistema de Gestão de Projetos

Este projeto foi desenvolvido como um trabalho acadêmico para a faculdade. Consiste em um sistema de desktop completo para gestão de projetos, utilizando **Java** com a biblioteca **Swing** para a interface gráfica e **MySQL** para a persistência de dados. A aplicação permite o gerenciamento de usuários, projetos e tarefas, além da geração de relatórios.

O projeto segue uma arquitetura com separação de responsabilidades, utilizando o padrão DAO (Data Access Object) para isolar a lógica de acesso ao banco de dados da interface do usuário.

## ✨ Funcionalidades

* **Autenticação de Usuário:** Tela de login segura que verifica as credenciais no banco de dados.
* **Gerenciamento de Usuários (CRUD):**
    * Cadastro, listagem e exclusão de usuários.
    * Atribuição de perfis (Administrador, Gerente, etc.).
* **Gerenciamento de Projetos (CRUD):**
    * Cadastro, listagem e exclusão de projetos.
    * Associação de um usuário responsável a cada projeto.
    * Definição de status (Planejado, Em andamento, Concluído).
* **Gerenciamento de Tarefas (CRUD):**
    * Cadastro, listagem e exclusão de tarefas.
    * Vinculação de cada tarefa a um projeto e a um responsável.
* **Relatórios e Exportação:**
    * Geração de relatórios de projetos ou tarefas.
    * Filtros por status.
    * Funcionalidade para exportar relatórios para arquivos de texto (`.txt`).
    * Funcionalidade de impressão.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK 11 ou superior)
* **Interface Gráfica:** Java Swing
* **Banco de Dados:** MySQL 8.0+
* **Build & Dependências:** Apache Maven
* **Conector:** MySQL Connector/J (JDBC)

## ⚙️ Pré-requisitos

Antes de começar, garanta que você tem os seguintes softwares instalados:

* Java Development Kit (JDK) - versão 11 ou mais recente.
* Apache Maven.
* Servidor de Banco de Dados MySQL.
* Uma IDE Java, como IntelliJ IDEA ou Eclipse.
* Um cliente de banco de dados, como MySQL Workbench ou DBeaver.

## 🚀 Instalação e Execução

Siga os passos abaixo para configurar e rodar o projeto em sua máquina local.

**1. Clone o Repositório**

```bash
git clone [URL_DO_SEU_REPOSITORIO]
cd [NOME_DA_PASTA_DO_PROJETO]
```

**2. Configure o Banco de Dados**
Abra seu cliente de banco de dados (ex: MySQL Workbench) e execute o script SQL abaixo. Ele criará o banco de dados `sistema_login` (se não existir), as tabelas necessárias e inserirá alguns usuários de exemplo.

```sql
CREATE DATABASE IF NOT EXISTS sistema_login;
USE sistema_login;

DROP TABLE IF EXISTS tarefas;
DROP TABLE IF EXISTS projetos;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    email VARCHAR(100) UNIQUE,
    cargo VARCHAR(50),
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    perfil VARCHAR(50)
);

CREATE TABLE projetos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    data_inicio DATE,
    data_fim DATE,
    id_responsavel INT,
    status VARCHAR(50),
    FOREIGN KEY (id_responsavel) REFERENCES usuarios(id)
);

CREATE TABLE tarefas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    id_projeto INT,
    id_responsavel INT,
    status VARCHAR(50),
    data_inicio_prevista DATE,
    data_fim_prevista DATE,
    data_inicio_real DATE,
    data_fim_real DATE,
    FOREIGN KEY (id_projeto) REFERENCES projetos(id),
    FOREIGN KEY (id_responsavel) REFERENCES usuarios(id)
);

INSERT INTO usuarios (nome, login, senha, perfil, cargo, cpf, email) VALUES
('Gustavo Administrador', 'admin', 'admin123', 'Administrador', 'SysAdmin', '111.111.111-11', 'gustavo@sistema.com'),
('Renan Gerente', 'gerente', 'gerente123', 'Gerente', 'Gerente de Projetos', '222.222.222-22', 'renan@sistema.com'),
('Caio Colaborador', 'colaborador', 'colab123', 'Colaborador', 'Desenvolvedor', '333.333.333-33', 'caio@sistema.com'),
('Lucas Estagiário', 'estagiario', 'estagio123', 'Estagiário', 'Desenvolvedor Jr', '444.444.444-44', 'lucas@sistema.com');
```

**3. Configure a Conexão**
Abra o projeto na sua IDE e navegue até o arquivo `src/main/java/Conexao.java`. Verifique se o nome de usuário e a senha do seu banco de dados correspondem aos definidos no arquivo.

```java
public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_login";
    private static final String USUARIO = "root";
    private static final String SENHA = "admin123"; // <-- Altere aqui se necessário
    // ...
}
```

**4. Execute o Projeto**
A IDE irá reconhecer o arquivo `pom.xml` e baixar automaticamente as dependências necessárias (como o conector MySQL).

* Navegue até o arquivo `src/main/java/Login.java`.
* Clique com o botão direito no arquivo e selecione **"Run 'Login.main()'"**.
* A tela de login será exibida. Use uma das credenciais inseridas no script SQL para entrar (ex: `admin` / `admin123`).