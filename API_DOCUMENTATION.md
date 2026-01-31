# Documentação da API

## Visão Geral

Esta documentação descreve todos os endpoints disponíveis na aplicação WebApp E-commerce.

## Autenticação

A maioria dos endpoints requer autenticação. O sistema utiliza sessão baseada em cookies.

### Login

**Endpoint:** `POST /login`

**Parâmetros:**
- `email` (string, obrigatório): Email do usuário
- `senha` (string, obrigatório): Senha do usuário

**Resposta de Sucesso:**
- Redirecionamento para `/` (usuário) ou `/admin` (administrador)

**Resposta de Erro:**
- Redirecionamento para `/login` com mensagem de erro

---

## Endpoints Públicos

### 1. Página Inicial

**GET /** 

Exibe a página inicial com produtos em destaque.

**Resposta:**
- HTML com lista de produtos
- Informações do carrinho
- Produtos destacados

### 2. Pesquisar Produtos

**GET /pesquisa**

**Parâmetros de Query:**
- `pesquisa` (string): Termo de busca

**Resposta:**
- Lista de produtos que correspondem à pesquisa
- Mensagem se nenhum produto for encontrado

### 3. Detalhes do Produto

**GET /detalhe/{id}**

**Parâmetros de Path:**
- `id` (integer): ID do produto

**Resposta:**
- Detalhes completos do produto
- Média de avaliações
- Lista de avaliações verificadas

---

## Endpoints de Usuário

### Carrinho de Compras

#### Adicionar ao Carrinho

**GET /adicionarCart/{id}**

**Autenticação:** Requerida

**Parâmetros de Path:**
- `id` (integer): ID do produto

**Resposta de Sucesso:**
- Produto adicionado ao carrinho
- Mensagem: "Produto adicionado ao carrinho com sucesso!"

**Resposta de Erro:**
- Erro se produto indisponível
- Redirecionamento para login se não autenticado

#### Ver Carrinho

**GET /cart**

**Autenticação:** Requerida

**Resposta:**
- Lista de produtos no carrinho
- Total do carrinho

#### Limpar Carrinho

**GET /removerCart**

**Autenticação:** Requerida

**Resposta:**
- Carrinho limpo
- Mensagem: "Carrinho limpo com sucesso"

#### Remover Item do Carrinho

**GET /removerCart/{id}**

**Autenticação:** Requerida

**Parâmetros de Path:**
- `id` (integer): ID do produto a remover

**Resposta:**
- Item removido
- Mensagem: "Produto removido do carrinho"

---

### Lista de Desejos

#### Ver Lista de Desejos

**GET /desejo**

**Autenticação:** Requerida

**Resposta:**
- Lista de produtos favoritos do usuário

#### Adicionar à Lista

**GET /desejo/{id}**

**Autenticação:** Requerida

**Parâmetros de Path:**
- `id` (integer): ID do produto

**Resposta:**
- Produto adicionado à lista
- Mensagem: "Produto adicionado à lista de desejos!"

#### Remover da Lista

**GET /desejo/remover/{id}**

**Autenticação:** Requerida

**Parâmetros de Path:**
- `id` (integer): ID do item da lista

**Resposta:**
- Item removido
- Mensagem: "Produto removido da lista de desejos"

---

### Pedidos

#### Checkout

**GET /pedidos/checkout**

**Autenticação:** Requerida

**Resposta:**
- Formulário de finalização de compra
- Resumo do carrinho
- Total a pagar

#### Finalizar Pedido

**POST /pedidos/finalizar**

**Autenticação:** Requerida

**Parâmetros de Form:**
- `endereco` (string, obrigatório): Endereço de entrega
- `formaPagamento` (string, obrigatório): Forma de pagamento

**Resposta de Sucesso:**
- Pedido criado
- Carrinho limpo
- Redirecionamento para detalhes do pedido
- Mensagem: "Pedido #{id} realizado com sucesso!"

**Resposta de Erro:**
- Erro se carrinho vazio
- Erro se produto sem estoque

#### Meus Pedidos

**GET /pedidos/meus**

**Autenticação:** Requerida

**Resposta:**
- Lista de pedidos do usuário
- Status de cada pedido

#### Detalhes do Pedido

**GET /pedidos/{id}**

**Autenticação:** Requerida

**Parâmetros de Path:**
- `id` (integer): ID do pedido

**Resposta:**
- Detalhes completos do pedido
- Itens do pedido
- Status atual

#### Cancelar Pedido

**GET /pedidos/cancelar/{id}**

**Autenticação:** Requerida

**Parâmetros de Path:**
- `id` (integer): ID do pedido

**Resposta de Sucesso:**
- Pedido cancelado
- Estoque devolvido
- Mensagem: "Pedido cancelado com sucesso!"

**Resposta de Erro:**
- Erro se pedido já entregue
- Erro se não for proprietário do pedido

---

### Avaliações

#### Nova Avaliação (Formulário)

**GET /avaliacoes/nova/{produtoId}**

**Autenticação:** Requerida

**Parâmetros de Path:**
- `produtoId` (integer): ID do produto

**Resposta:**
- Formulário de avaliação

#### Criar Avaliação

**POST /avaliacoes/nova/{produtoId}**

**Autenticação:** Requerida

**Parâmetros de Path:**
- `produtoId` (integer): ID do produto

**Parâmetros de Form:**
- `nota` (integer, obrigatório): Nota de 1 a 5
- `comentario` (string, opcional): Comentário sobre o produto

**Resposta de Sucesso:**
- Avaliação criada
- Mensagem: "Avaliação enviada com sucesso!"

**Resposta de Erro:**
- Erro se nota inválida (fora do range 1-5)

#### Listar Avaliações do Produto

**GET /avaliacoes/produto/{produtoId}**

**Parâmetros de Path:**
- `produtoId` (integer): ID do produto

**Resposta:**
- Lista de avaliações verificadas
- Média de notas
- Total de avaliações

---

## Endpoints de Administrador

### Dashboard

**GET /admin**

**Autenticação:** Requerida (Admin)

**Resposta:**
- Estatísticas do sistema
- Produtos disponíveis/indisponíveis
- Usuários ativos/desativados
- Total de produtos

---

### Gerenciamento de Produtos

#### Listar Produtos

**GET /listar**

**Autenticação:** Requerida (Admin)

**Resposta:**
- Lista completa de produtos

#### Novo Produto (Formulário)

**GET /salvar**

**Autenticação:** Requerida (Admin)

**Resposta:**
- Formulário para cadastrar produto

#### Salvar Produto

**POST /salvar**

**Autenticação:** Requerida (Admin)

**Parâmetros de Form:**
- `nome` (string, obrigatório)
- `preco` (decimal, obrigatório)
- `quantidade` (integer, obrigatório)
- `info` (string, opcional)
- `marca` (string, opcional)
- `desconto` (decimal, opcional)
- `categoria_id` (integer, opcional)
- `image` (file, opcional): Imagem do produto

**Resposta:**
- Produto salvo
- Imagem uploadada (se fornecida)
- Redirecionamento para lista

#### Editar Produto

**GET /editar/{id}**

**Autenticação:** Requerida (Admin)

**Parâmetros de Path:**
- `id` (integer): ID do produto

**Resposta:**
- Formulário preenchido com dados do produto

#### Deletar Produto

**GET /apagar/{id}**

**Autenticação:** Requerida (Admin)

**Parâmetros de Path:**
- `id` (integer): ID do produto

**Resposta:**
- Produto deletado
- Redirecionamento para lista

---

### Gerenciamento de Usuários

#### Usuários Ativos

**GET /usuarioAtivo**

**Autenticação:** Requerida (Admin)

**Resposta:**
- Lista de usuários ativos

#### Usuários Desativados

**GET /usuarioDesativado**

**Autenticação:** Requerida (Admin)

**Resposta:**
- Lista de usuários desativados

#### Ativar Usuário

**GET /ativar/{id}**

**Autenticação:** Requerida (Admin)

**Parâmetros de Path:**
- `id` (integer): ID do usuário

**Resposta:**
- Usuário ativado
- Redirecionamento para lista de desativados

#### Desativar Usuário

**GET /desativar/{id}**

**Autenticação:** Requerida (Admin)

**Parâmetros de Path:**
- `id` (integer): ID do usuário

**Resposta:**
- Usuário desativado
- Redirecionamento para lista de ativos

---

### Gerenciamento de Categorias

#### Listar Categorias (Admin)

**GET /categorias/admin/listar**

**Autenticação:** Requerida (Admin)

**Resposta:**
- Lista de todas as categorias

#### Nova Categoria

**GET /categorias/nova**

**Autenticação:** Requerida (Admin)

**Resposta:**
- Formulário para nova categoria

#### Salvar Categoria

**POST /categorias/salvar**

**Autenticação:** Requerida (Admin)

**Parâmetros de Form:**
- `nome` (string, obrigatório)
- `descricao` (string, opcional)

**Resposta de Sucesso:**
- Categoria criada
- Mensagem: "Categoria criada com sucesso!"

**Resposta de Erro:**
- Erro se já existir categoria com o mesmo nome

#### Editar Categoria

**GET /categorias/editar/{id}**

**Autenticação:** Requerida (Admin)

**Parâmetros de Path:**
- `id` (integer): ID da categoria

**Resposta:**
- Formulário preenchido

#### Remover Categoria

**GET /categorias/remover/{id}**

**Autenticação:** Requerida (Admin)

**Parâmetros de Path:**
- `id` (integer): ID da categoria

**Resposta de Sucesso:**
- Categoria removida

**Resposta de Erro:**
- Erro se categoria tiver produtos associados

---

### Gerenciamento de Pedidos (Admin)

#### Listar Todos os Pedidos

**GET /pedidos/admin/listar**

**Autenticação:** Requerida (Admin)

**Resposta:**
- Lista de todos os pedidos do sistema

#### Atualizar Status do Pedido

**POST /pedidos/admin/status/{id}**

**Autenticação:** Requerida (Admin)

**Parâmetros de Path:**
- `id` (integer): ID do pedido

**Parâmetros de Form:**
- `status` (string): Novo status (PENDENTE, CONFIRMADO, ENVIADO, ENTREGUE, CANCELADO)

**Resposta:**
- Status atualizado
- Mensagem: "Status atualizado com sucesso!"

---

### Gerenciamento de Avaliações (Admin)

#### Listar Avaliações Pendentes

**GET /avaliacoes/pendentes**

**Autenticação:** Requerida (Admin)

**Resposta:**
- Lista de avaliações não verificadas

#### Verificar Avaliação

**GET /avaliacoes/verificar/{id}**

**Autenticação:** Requerida (Admin)

**Parâmetros de Path:**
- `id` (integer): ID da avaliação

**Resposta:**
- Avaliação marcada como verificada
- Mensagem: "Avaliação verificada com sucesso!"

#### Remover Avaliação

**GET /avaliacoes/remover/{id}**

**Autenticação:** Requerida (Admin)

**Parâmetros de Path:**
- `id` (integer): ID da avaliação

**Resposta:**
- Avaliação removida
- Mensagem: "Avaliação removida com sucesso!"

---

## Códigos de Status HTTP

- **200 OK**: Requisição bem-sucedida
- **302 Found**: Redirecionamento
- **400 Bad Request**: Parâmetros inválidos
- **401 Unauthorized**: Não autenticado
- **403 Forbidden**: Sem permissão
- **404 Not Found**: Recurso não encontrado
- **500 Internal Server Error**: Erro no servidor

## Mensagens Flash

O sistema utiliza atributos flash para mensagens:

- `mensagem`: Mensagem de sucesso (verde)
- `erro`: Mensagem de erro (vermelho)
- `aviso`: Mensagem de aviso (amarelo)

---

**Versão da API:** 2.0  
**Última Atualização:** Fevereiro 2026
