# 📚 VirtualBookshelf

O **VirtualBookshelf** é um sistema completo para gerenciamento de leitura e acompanhamento de progresso literário. Ele permite que usuários adicionem livros do catálogo às suas estantes pessoais, registrem páginas lidas, deixem notas sobre a leitura e ganhem experiência (XP) para evoluir de nível conforme leem, transformando o hábito da leitura em uma experiência interativa e gamificada.

## 🖼️ Demonstração Visual

*(Adicione aqui capturas de tela do sistema, como o Dashboard do usuário, a estante de livros, ou um GIF mostrando a atualização de progresso de um livro)*

## ⚙️ Tecnologias Utilizadas

A API backend foi construída utilizando ferramentas modernas e robustas do ecossistema Java. Além disso, um frontend básico em React foi gerado com o auxílio de IA exclusivamente para ilustrar o consumo da API e demonstrar um exemplo de aplicação da interface.

**Backend:**
- **Java 21**
- **Spring Boot** (Web, Data JPA, Security)
- **JSON Web Tokens (JWT)** para autenticação
- **PostgreSQL** (Produção) / **H2 Database** (Desenvolvimento)
- **Flyway** (Migrations)
- **MapStruct** e **Lombok**
- **SpringDoc OpenAPI** (Swagger)

**Frontend (Ilustrativo):**
- **React** (com Vite)

---

## 🚀 Guia de Instalação e Execução

### Pré-requisitos
Antes de iniciar, certifique-se de ter os seguintes itens instalados na sua máquina:
- **Java Development Kit (JDK) 21** ou superior
- **Maven** (caso não queira usar o wrapper embutido)
- **Node.js** (opcional, apenas para rodar o script de geração de massa de dados)

### Passo a Passo da Instalação

1. **Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/virtual-bookshelf.git
cd virtual-bookshelf
```

2. **Configure as Variáveis de Ambiente:**
Faça uma cópia do arquivo de exemplo `.env.example` e renomeie-o para `.env`. Configure os valores (especialmente o `JWT_SECRET`) conforme detalhado na próxima seção.

3. **Inicie o Servidor Backend (Modo Dev com H2):**
```bash
./mvnw spring-boot:run
```
A API estará disponível em: `http://localhost:8080`.
O Swagger da API poderá ser acessado em: `http://localhost:8080/swagger-ui.html`.

*(Opcional)* **Script de População de Dados:**
Se desejar testar a aplicação com livros e usuários fictícios pré-cadastrados, com o servidor rodando, execute o script Node.js na raiz do projeto:
```bash
node seed.js
```

### Variáveis de Ambiente
O projeto utiliza o arquivo `.env` para gerenciar segredos e configurações de banco de dados. Use o arquivo `.env.example` fornecido na raiz do projeto como base:

```properties
# .env
DB_URL=jdbc:h2:mem:testdb
DB_CLASSNAME=org.h2.Driver
DB_USER=book
DB_PASSWORD=123
JWT_SECRET=sua-chave-super-secreta-de-pelo-menos-32-caracteres
JWT_EXPIRATION=86400000
```
> **Nota:** É obrigatório definir uma chave segura e forte no `JWT_SECRET` para que o Spring Security inicie corretamente.

---

## 🏗️ Arquitetura e Contexto

### Organização e Estrutura
O backend foi construído seguindo os padrões arquiteturais limpos do Spring (Arquitetura em Camadas):
- **Controllers (`/controller`)**: Camada de roteamento e endpoints REST. Recebem as requisições HTTP e validam os dados.
- **Services (`/service`)**: Onde a lógica de negócio (como o cálculo de XP e níveis de leitura) está implementada. 
- **Repositories (`/repository`)**: Camada de acesso a dados usando Spring Data JPA.
- **Models (`/model`)**: Entidades que mapeiam diretamente as tabelas do banco de dados.
- **DTOs (`/dto`) e Mappers (`/mapper`)**: Separação clara entre os dados que transitam na API e os do banco, utilizando MapStruct para conversão automática.

### Decisões Técnicas
- **Autenticação Stateless (JWT):** Adotada para facilitar a escalabilidade e consumo por parte do frontend. O token guarda informações vitais do usuário, e a aplicação dispensa sessões de servidor (`SessionCreationPolicy.STATELESS`).
- **Sistema de Níveis (Gamificação):** O ganho de XP e a progressão de níveis foram calculados dinamicamente na camada de serviço (`ShelfService`). Ao alcançar certas metas, o usuário automaticamente sobe de nível.
- **Profiles de Banco de Dados:** O sistema utiliza `application-dev.properties` (H2 Database in-memory para desenvolvimento rápido) e `application-prod.properties` (PostgreSQL para produção robusta), ambos controlados via Flyway para garantia de versão de esquema.
- **Admin Seeder:** Para garantir o acesso inicial de gerenciamento do sistema, foi criado um `CommandLineRunner` que cadastra um usuário Administrador padrão na primeira vez que o banco de dados é inicializado vazio.

---
