# 📚 VirtualBookshelf

O **VirtualBookshelf** é um sistema para gerenciamento de leitura e acompanhamento de progresso literário desenvolvido para fins de aprendizado. Ele permite que usuários adicionem livros do catálogo às suas estantes pessoais, registrem páginas lidas, atribuam notas sobre a leitura e ganhem experiência (XP) para evoluir de nível conforme leem, transformando o hábito da leitura em uma experiência interativa e gamificada. 

## 🖼️ Demonstração Visual
### Tela de Login
<img width="1918" height="943" alt="Captura de tela 2026-09-11 212310" src="https://github.com/user-attachments/assets/043ac7fc-0c89-43de-95ca-befa6c3fad19" />

### Tela de Cadastro
<img width="1918" height="944" alt="Captura de tela 2026-09-11 212338" src="https://github.com/user-attachments/assets/d511d5b4-d79e-4326-8fa6-b1b5c11c3dab" />

### Tela do Catálogo de Livros e Estante do Usuário
<img width="1280" height="627" alt="Cadastro de novo livro por admin" src="https://github.com/user-attachments/assets/2cfc1431-983b-45d3-a92b-69f16e86ab19" />

### Tela de Adição de Livros ao Catálogo
> Tela disponível apenas para o Usuário Administrador.
<img width="1280" height="629" alt="Fluxo de adição de livro a estante e atualização de progresso e status" src="https://github.com/user-attachments/assets/fa345c35-c701-4d0b-b80a-356724a7777e" />

### Tela de Gestão de Usuários
> Tela disponível apenas para o Usuário Administrador.
<img width="1918" height="942" alt="Captura de tela 2026-09-11 223322" src="https://github.com/user-attachments/assets/0d8bc7ae-f7f4-4097-a983-3f660354593b" />

### Tela dos Dados do Usuário
<img width="1918" height="943" alt="Captura de tela 2026-09-11 223347" src="https://github.com/user-attachments/assets/1e6aad34-76ec-4f7b-b2fa-9fefabccbe24" />


## ⚙️ Tecnologias Utilizadas

A API backend foi construída utilizando ferramentas modernas e robustas do ecossistema Java. O projeto conta também com uma interface de usuário completa.

**Backend:**
- **Java 21**
- **Spring Boot** (Web, Data JPA, Security)
- **JSON Web Tokens (JWT)** para autenticação
- **PostgreSQL** (Produção) / **H2 Database** (Desenvolvimento)
- **Flyway** (Migrations)
- **MapStruct** e **Lombok**
- **SpringDoc OpenAPI** (Swagger)
- **LangChain4j & Ollama** (Integração com LLM local para complementar a criação de texto para a sinopse dos livros)

**Frontend:**
- **React** (com Vite)
- 🔗 [Repositório do Frontend](https://github.com/Gabrielfb10/Virtual-Bookshelf---Frontend)

**Infraestrutura:**
- **Docker & Docker Compose** (Containerização da aplicação e orquestração do banco de dados)

---

## 🚀 Guia de Instalação e Execução

### Pré-requisitos
Antes de iniciar, certifique-se de ter os seguintes itens instalados na sua máquina:
- **Docker** e **Docker Compose** (Recomendado)
- **Java Development Kit (JDK) 21** ou superior (Para execução manual sem Docker)
- **Maven** (caso não queira usar o wrapper embutido)
- **Node.js** (opcional, apenas para rodar o script de geração de massa de dados)

### Passo a Passo da Instalação

1. **Clone o repositório:**
```bash
git clone https://github.com/Gabrielfb10/Virtual-Bookshelf---Backend.git
cd Virtual-Bookshelf---Backend
```

2. **Configure as Variáveis de Ambiente:**
Faça uma cópia do arquivo de exemplo `.env.example` e renomeie-o para `.env`. Configure os valores (especialmente o `JWT_SECRET`) conforme detalhado na próxima seção.

3. **Inicie a Aplicação (Recomendado via Docker):**
Com o Docker configurado, você pode iniciar o banco de dados PostgreSQL e a API simultaneamente com um único comando:
```bash
docker-compose up -d --build
```
A API estará disponível em: `http://localhost:8081`.

*(Alternativa)* **Inicie o Servidor Localmente (Modo Dev):**
Se preferir rodar diretamente na sua máquina sem Docker (utilizará o banco H2 em memória por padrão):
```bash
./mvnw spring-boot:run
```
Neste modo, a API estará em: `http://localhost:8080`.

O **Swagger** da API poderá ser acessado adicionando `/swagger-ui/index.html` à URL base do servidor que estiver utilizando.

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
