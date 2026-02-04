# 🛒 Sistema E-commerce - WebApp

Sistema completo de e-commerce desenvolvido em Java com Spring Boot, oferecendo funcionalidades para gerenciamento de produtos, usuários, pedidos, avaliações e muito mais.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Executando a Aplicação](#executando-a-aplicação)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [API Endpoints](#api-endpoints)
- [Banco de Dados](#banco-de-dados)

## 🎯 Sobre o Projeto

Este é um sistema completo de e-commerce que permite gerenciar produtos, realizar vendas online, controlar estoque, gerenciar usuários e muito mais. O sistema possui dois tipos de acesso: **Usuário** e **Administrador**, cada um com suas respectivas funcionalidades.

### Diferenciais do Sistema

- ✅ **Sistema de Avaliações**: Usuários podem avaliar produtos com notas e comentários
- ✅ **Categorização de Produtos**: Organização por categorias para facilitar navegação
- ✅ **Gerenciamento de Pedidos**: Acompanhamento completo do status dos pedidos
- ✅ **Lista de Desejos**: Salvamento de produtos favoritos
- ✅ **Carrinho de Compras**: Sistema intuitivo para gerenciar itens antes da compra
- ✅ **Controle de Estoque**: Atualização automática de estoque após vendas
- ✅ **Sistema de Descontos**: Aplicação de descontos percentuais em produtos
- ✅ **Múltiplos Status de Pedido**: PENDENTE, CONFIRMADO, ENVIADO, ENTREGUE, CANCELADO
- ✅ **Upload de Imagens**: Suporte para imagens de produtos
- ✅ **Pesquisa Avançada**: Busca por nome, categoria e outros filtros

## 🚀 Funcionalidades

### Para Usuários

- 🔐 **Autenticação e Cadastro**
  - Registro de novos usuários
  - Login com email e senha
  - Perfil de usuário

- 🛍️ **Compras**
  - Navegação por catálogo
  - Visualização detalhada de produtos
  - Carrinho de compras
  - Finalizar compra com endereço

- 📦 **Pedidos**
  - Histórico de pedidos
  - Acompanhamento de status
  - Cancelamento de pedidos

- ⭐ **Avaliações**
  - Avaliar produtos (1 a 5 estrelas)
  - Comentários sobre produtos
  - Visualizar média de avaliações

- 💝 **Lista de Desejos**
  - Salvar produtos favoritos
  - Acesso rápido aos favoritos

### Para Administradores

- 👥 **Gerenciamento de Usuários**
  - Visualizar usuários
  - Ativar/desativar contas

- 📦 **Gerenciamento de Produtos**
  - CRUD completo de produtos
  - Upload de imagens
  - Controle de estoque
  - Aplicar descontos

- 🏷️ **Gerenciamento de Categorias**
  - CRUD de categorias
  - Associar produtos

- 📋 **Gerenciamento de Pedidos**
  - Visualizar todos os pedidos
  - Atualizar status

- ⭐ **Gerenciamento de Avaliações**
  - Verificar/aprovar avaliações
  - Remover avaliações inadequadas

## 🛠️ Tecnologias Utilizadas

### Backend
- Java 20
- Spring Boot 3.0.5
- Spring Data JPA
- Hibernate
- Maven

### Banco de Dados
- MySQL 8

### Frontend
- Thymeleaf
- HTML5/CSS3
- JavaScript
- Bootstrap

## 📋 Pré-requisitos

- Java JDK 20+
- Maven 3.6+
- MySQL 8.0+
- Git

## 📥 Instalação

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/webapp.git
cd webapp
```

### 2. Configure o Banco de Dados
```sql
CREATE DATABASE ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Configure application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 4. Compile o projeto
```bash
mvn clean install
```

## 🚀 Executando a Aplicação

```bash
mvn spring-boot:run
```

Acesse: **http://localhost:8080**

## 📁 Estrutura do Projeto

```
/
├── .gitignore               # Arquivos ignorados pelo Git
├── API_DOCUMENTATION.md     # Documentação da API
├── INSTALLATION.md          # Guia de instalação
├── README.md                # Este arquivo
└── App/                     # Aplicação Spring Boot
    ├── .gitignore
    ├── .mvn/                # Maven wrapper
    ├── mvnw                 # Maven wrapper script (Linux/Mac)
    ├── mvnw.cmd             # Maven wrapper script (Windows)
    ├── pom.xml              # Dependências Maven
    └── src/
        ├── main/
        │   ├── java/Web/App/
        │   │   ├── Controller/       # Controladores REST e MVC
        │   │   ├── Model/DTO/        # Entidades e DTOs
        │   │   ├── Repository/       # Repositórios JPA
        │   │   ├── Service/          # Lógica de negócios
        │   │   ├── Global/           # Dados globais
        │   │   └── Upload/           # Upload de arquivos
        │   └── resources/
        │       ├── static/           # CSS, JS, imagens
        │       ├── templates/        # Templates Thymeleaf
        │       └── application.properties
        └── test/                     # Testes unitários
```

## 🗄️ Banco de Dados

### Entidades Principais
- **Usuario**: Dados dos usuários
- **Produto**: Informações de produtos
- **Categoria**: Categorias de produtos
- **Pedido**: Pedidos realizados
- **ItemPedido**: Itens de cada pedido
- **Avaliacao**: Avaliações de produtos
- **ListaDesejo**: Produtos favoritos

## 📝 Novas Funcionalidades Adicionadas

### 1. Sistema de Avaliações
- Usuários podem avaliar produtos de 1 a 5 estrelas
- Comentários opcionais
- Sistema de verificação para admins
- Cálculo automático de média

### 2. Sistema de Categorias
- Organização de produtos por categoria
- Navegação facilitada
- Filtros por categoria

### 3. Sistema de Pedidos Completo
- Criação de pedidos a partir do carrinho
- Rastreamento de status
- Histórico completo
- Cancelamento de pedidos
- Atualização automática de estoque

### 4. Melhorias nos Produtos
- Campo de desconto percentual
- Campo de marca
- Status ativo/inativo
- Data de cadastro
- Métodos auxiliares (preço com desconto, disponibilidade)

### 5. Melhorias nos Usuários
- Campos adicionais (CPF, telefone, endereço)
- Data de cadastro
- Relacionamento com pedidos e avaliações
- Métodos auxiliares (isAdmin, total gasto)

## 📚 Documentação

Todo o código está documentado com JavaDoc incluindo:
- Descrição de classes e métodos
- Parâmetros e retornos
- Exceções lançadas
- Exemplos de uso
- Informações de versão e autoria

## 🔒 Segurança

⚠️ **Nota**: Para produção, implemente:
- Criptografia de senhas (BCrypt)
- Spring Security
- HTTPS
- CSRF Protection
- JWT/Session Management

## 📝 Melhorias Futuras

- [ ] Spring Security
- [ ] Sistema de cupons
- [ ] Gateway de pagamento
- [ ] Email notifications
- [ ] API REST completa
- [ ] Cache (Redis)
- [ ] Sistema de recomendação

## 👨‍💻 Autores

Sistema E-commerce Team - 2026

## 📄 Licença

Este projeto está sob a licença MIT.

---

⭐ Desenvolvido com ❤️ usando Java e Spring Boot
