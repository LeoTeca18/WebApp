# Guia de Instalação Detalhado

## Requisitos do Sistema

### Software Necessário

1. **Java Development Kit (JDK) 20**
   - Download: https://www.oracle.com/java/technologies/downloads/
   - Verifique a instalação: `java --version`

2. **Apache Maven 3.6+**
   - Download: https://maven.apache.org/download.cgi
   - Verifique a instalação: `mvn --version`

3. **MySQL 8.0+**
   - Download: https://dev.mysql.com/downloads/mysql/
   - Verifique a instalação: `mysql --version`

4. **IDE (Opcional mas recomendado)**
   - IntelliJ IDEA: https://www.jetbrains.com/idea/download/
   - Eclipse: https://www.eclipse.org/downloads/
   - VS Code com Extension Pack for Java

## Passo a Passo da Instalação

### 1. Configuração do Banco de Dados

#### Instalar MySQL
```bash
# Windows: Baixe o instalador do site oficial
# Linux (Ubuntu/Debian):
sudo apt update
sudo apt install mysql-server

# Linux (CentOS/RHEL):
sudo yum install mysql-server
```

#### Iniciar o MySQL
```bash
# Windows: Inicie o serviço MySQL no Services
# Linux:
sudo systemctl start mysql
sudo systemctl enable mysql
```

#### Criar Banco de Dados
```bash
# Acesse o MySQL
mysql -u root -p

# No console do MySQL:
CREATE DATABASE ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'ecommerce_user'@'localhost' IDENTIFIED BY 'senha_segura';
GRANT ALL PRIVILEGES ON ecommerce.* TO 'ecommerce_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

### 2. Clonar o Projeto

```bash
# Via HTTPS
git clone https://github.com/seu-usuario/webapp.git

# Ou via SSH
git clone git@github.com:seu-usuario/webapp.git

# Entre no diretório
cd webapp/WebApp/App
```

### 3. Configurar application.properties

Edite o arquivo `src/main/resources/application.properties`:

```properties
# ===================================
# CONFIGURAÇÃO DO BANCO DE DADOS
# ===================================
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce?useSSL=false&serverTimezone=UTC
spring.datasource.username=ecommerce_user
spring.datasource.password=senha_segura
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ===================================
# CONFIGURAÇÃO JPA/HIBERNATE
# ===================================
# update: atualiza schema automaticamente
# create: recria schema a cada inicialização
# create-drop: recria e deleta ao finalizar
# validate: apenas valida o schema
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# ===================================
# CONFIGURAÇÃO DE LOGGING
# ===================================
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type=TRACE
logging.level.org.springframework=INFO

# ===================================
# CONFIGURAÇÃO DE UPLOAD
# ===================================
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
spring.servlet.multipart.file-size-threshold=2KB
# Altere o caminho conforme seu sistema
spring.servlet.multipart.location=src/main/resources/static/assets/images/upload

# ===================================
# CONFIGURAÇÃO THYMELEAF
# ===================================
spring.thymeleaf.mode=HTML
spring.thymeleaf.cache=false
spring.thymeleaf.encoding=UTF-8

# ===================================
# CONFIGURAÇÃO DO SERVIDOR
# ===================================
server.port=8080
server.error.whitelabel.enabled=true

# ===================================
# POOL DE CONEXÕES HIKARI
# ===================================
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.max-lifetime=1200000
spring.datasource.hikari.connection-timeout=20000
```

### 4. Criar Diretório de Upload

```bash
# Windows (PowerShell)
New-Item -ItemType Directory -Path "src\main\resources\static\assets\images\upload" -Force

# Linux/Mac
mkdir -p src/main/resources/static/assets/images/upload
```

### 5. Instalar Dependências

```bash
mvn clean install
```

Se houver erros, tente:
```bash
mvn clean install -U
mvn dependency:resolve
```

### 6. Executar a Aplicação

#### Opção 1: Via Maven
```bash
mvn spring-boot:run
```

#### Opção 2: Via JAR
```bash
mvn clean package
java -jar target/App-0.0.1-SNAPSHOT.jar
```

#### Opção 3: Via IDE
- Abra o projeto na IDE
- Localize a classe `AppApplication.java`
- Clique com botão direito > Run 'AppApplication'

### 7. Verificar Instalação

Abra o navegador e acesse:
```
http://localhost:8080
```

Você deverá ver a página inicial do e-commerce.

## Configurações Adicionais

### Alterar Porta do Servidor

No `application.properties`:
```properties
server.port=9090
```

### Habilitar Debug Mode

```properties
debug=true
logging.level.root=DEBUG
```

### Configurar Profile (Desenvolvimento/Produção)

Crie arquivos separados:
- `application-dev.properties`
- `application-prod.properties`

Ative com:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Dados Iniciais (Seeds)

### Criar Usuário Admin Manualmente

```sql
USE ecommerce;

INSERT INTO usuario (nome, email, senha, papel, status, data_cadastro) 
VALUES ('Admin', 'admin@webapp.com', 'admin123', 'admin', 1, NOW());

INSERT INTO usuario (nome, email, senha, papel, status, data_cadastro) 
VALUES ('Usuario Teste', 'user@webapp.com', 'user123', 'user', 1, NOW());
```

### Criar Categorias Iniciais

```sql
INSERT INTO categoria (nome, descricao, ativa) VALUES
('Eletrônicos', 'Produtos eletrônicos e tecnologia', 1),
('Roupas', 'Vestuário e acessórios', 1),
('Alimentos', 'Produtos alimentícios', 1),
('Livros', 'Livros e publicações', 1),
('Casa', 'Produtos para casa e decoração', 1);
```

### Criar Produtos de Teste

```sql
INSERT INTO produto (nome, preco, quantidade, informacao, ativo, desconto, marca, data_cadastro, categoria_id) VALUES
('Notebook Dell', 3500.00, 10, 'Notebook com 8GB RAM e 256GB SSD', 1, 10.0, 'Dell', NOW(), 1),
('Mouse Logitech', 89.90, 50, 'Mouse wireless', 1, 0, 'Logitech', NOW(), 1),
('Teclado Mecânico', 299.00, 30, 'Teclado mecânico RGB', 1, 15.0, 'Redragon', NOW(), 1),
('Camiseta Nike', 129.90, 100, 'Camiseta esportiva', 1, 20.0, 'Nike', NOW(), 2),
('Calça Jeans', 199.00, 75, 'Calça jeans masculina', 1, 0, 'Levis', NOW(), 2);
```

## Problemas Comuns e Soluções

### Erro: "Access denied for user"
**Solução**: Verifique usuário e senha no application.properties

### Erro: "Table doesn't exist"
**Solução**: Configure `spring.jpa.hibernate.ddl-auto=create` na primeira execução

### Erro: "Port 8080 already in use"
**Solução**: Altere a porta ou finalize o processo que está usando a porta 8080

```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <pid> /F

# Linux/Mac
lsof -i :8080
kill -9 <pid>
```

### Erro: "File upload failed"
**Solução**: Verifique permissões da pasta de upload e o caminho configurado

### Erro de dependências Maven
**Solução**:
```bash
mvn clean
mvn dependency:purge-local-repository
mvn install
```

## Próximos Passos

Após a instalação bem-sucedida:

1. ✅ Acesse o sistema em http://localhost:8080
2. ✅ Faça login com usuário admin (se criou)
3. ✅ Cadastre produtos e categorias
4. ✅ Teste as funcionalidades
5. ✅ Configure backup do banco de dados

## Suporte

Se encontrar problemas:
1. Verifique os logs em `logs/spring-boot-logger.log`
2. Consulte a documentação do Spring Boot
3. Abra uma issue no GitHub
4. Entre em contato com o suporte

---

**Instalação Concluída!** 🎉

Agora você está pronto para usar o sistema!
