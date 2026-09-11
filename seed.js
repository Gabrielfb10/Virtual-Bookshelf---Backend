/**
 * ============================================
 *  SEED DE TESTE — Virtual Bookshelf
 * ============================================
 * 
 *  Uso: node seed.js [URL_BASE]
 *  Exemplo: node seed.js http://localhost:8080
 * 
 *  O que faz:
 *   1. Faz login como admin (admin@admin.com / admin123)
 *   2. Cadastra 30 livros com capa placeholder
 *   3. Registra 15 usuários
 *   4. Para cada usuário, adiciona 5–10 livros aleatórios à estante
 *   5. Gera progresso aleatório em metade dos livros da estante
 * ============================================
 */

const BASE_URL = process.argv[2] || "http://localhost:8080";

// ─── Dados ──────────────────────────────────────────────

const USERS = [
  { nickname: "leitor_voraz",     name: "Carlos Silva",         email: "carlos@email.com",      password: "Senha123!" },
  { nickname: "ana_reads",        name: "Ana Oliveira",         email: "ana@email.com",          password: "Senha123!" },
  { nickname: "pedro_livros",     name: "Pedro Santos",         email: "pedro@email.com",        password: "Senha123!" },
  { nickname: "julia_pages",     name: "Júlia Fernandes",      email: "julia@email.com",        password: "Senha123!" },
  { nickname: "marcos_lit",       name: "Marcos Pereira",       email: "marcos@email.com",       password: "Senha123!" },
  { nickname: "camila_book",      name: "Camila Costa",         email: "camila@email.com",       password: "Senha123!" },
  { nickname: "rafael_reader",    name: "Rafael Almeida",       email: "rafael@email.com",       password: "Senha123!" },
  { nickname: "beatriz_bk",       name: "Beatriz Rodrigues",    email: "beatriz@email.com",      password: "Senha123!" },
  { nickname: "lucas_shelf",      name: "Lucas Martins",        email: "lucas@email.com",        password: "Senha123!" },
  { nickname: "fernanda_lit",     name: "Fernanda Lima",        email: "fernanda@email.com",     password: "Senha123!" },
  { nickname: "gustavo_reads",    name: "Gustavo Souza",        email: "gustavo@email.com",      password: "Senha123!" },
  { nickname: "mariana_pages",    name: "Mariana Barbosa",      email: "mariana@email.com",      password: "Senha123!" },
  { nickname: "thiago_books",     name: "Thiago Ribeiro",       email: "thiago@email.com",       password: "Senha123!" },
  { nickname: "isabela_reader",   name: "Isabela Carvalho",     email: "isabela@email.com",      password: "Senha123!" },
  { nickname: "diego_shelf",      name: "Diego Nascimento",     email: "diego@email.com",        password: "Senha123!" },
];

const BOOKS = [
  { name: "Dom Casmurro",                       author: "Machado de Assis",          category: "BOOK",     genre: "Romance",            description: "A história de Bentinho e Capitu, uma das maiores obras da literatura brasileira.",              numberOfPages: 256 },
  { name: "O Senhor dos Anéis: A Sociedade do Anel", author: "J.R.R. Tolkien",       category: "BOOK",     genre: "Fantasia",           description: "Frodo Bolseiro embarca em uma jornada para destruir o Um Anel.",                                numberOfPages: 576 },
  { name: "1984",                                author: "George Orwell",             category: "BOOK",     genre: "Distopia",           description: "Em um mundo totalitário, Winston Smith luta contra o controle do Grande Irmão.",                 numberOfPages: 328 },
  { name: "O Pequeno Príncipe",                  author: "Antoine de Saint-Exupéry",  category: "BOOK",     genre: "Fábula",             description: "Um piloto encontra um príncipe vindo de um pequeno asteroide.",                                  numberOfPages: 96  },
  { name: "Harry Potter e a Pedra Filosofal",    author: "J.K. Rowling",              category: "BOOK",     genre: "Fantasia",           description: "Harry descobre que é um bruxo e começa sua jornada em Hogwarts.",                               numberOfPages: 309 },
  { name: "A Revolução dos Bichos",              author: "George Orwell",             category: "BOOK",     genre: "Sátira Política",    description: "Uma fábula sobre animais que tomam o controle de uma fazenda.",                                  numberOfPages: 152 },
  { name: "Cem Anos de Solidão",                 author: "Gabriel García Márquez",    category: "BOOK",     genre: "Realismo Mágico",    description: "A saga da família Buendía na fictícia cidade de Macondo.",                                      numberOfPages: 448 },
  { name: "O Hobbit",                            author: "J.R.R. Tolkien",            category: "BOOK",     genre: "Fantasia",           description: "Bilbo Bolseiro parte em uma aventura inesperada com anões e um mago.",                          numberOfPages: 310 },
  { name: "Duna",                                author: "Frank Herbert",             category: "BOOK",     genre: "Ficção Científica",  description: "Paul Atreides enfrenta intrigas políticas no deserto planeta Arrakis.",                          numberOfPages: 688 },
  { name: "O Alquimista",                        author: "Paulo Coelho",              category: "BOOK",     genre: "Aventura",           description: "Santiago, um jovem pastor, parte em busca de um tesouro no Egito.",                              numberOfPages: 208 },
  { name: "Memórias Póstumas de Brás Cubas",     author: "Machado de Assis",          category: "BOOK",     genre: "Romance",            description: "Um defunto-autor narra sua vida com ironia e filosofia.",                                        numberOfPages: 208 },
  { name: "Neuromancer",                         author: "William Gibson",            category: "BOOK",     genre: "Cyberpunk",          description: "Case, um hacker decadente, é recrutado para uma última missão no ciberespaço.",                  numberOfPages: 271 },
  { name: "Fundação",                            author: "Isaac Asimov",              category: "BOOK",     genre: "Ficção Científica",  description: "Hari Seldon prevê a queda do Império Galáctico e cria um plano para preservar o conhecimento.", numberOfPages: 244 },
  { name: "Crime e Castigo",                     author: "Fiódor Dostoiévski",        category: "BOOK",     genre: "Psicológico",        description: "Raskólnikov comete um assassinato e lida com a culpa que o consome.",                            numberOfPages: 551 },
  { name: "Orgulho e Preconceito",               author: "Jane Austen",               category: "BOOK",     genre: "Romance",            description: "Elizabeth Bennet e Mr. Darcy superam seus orgulhos e preconceitos.",                             numberOfPages: 432 },
  { name: "Naruto Vol. 1",                       author: "Masashi Kishimoto",         category: "MANGA",    genre: "Shounen",            description: "Naruto Uzumaki sonha em se tornar Hokage, o líder de sua vila ninja.",                           numberOfPages: 192 },
  { name: "One Piece Vol. 1",                    author: "Eiichiro Oda",              category: "MANGA",    genre: "Shounen",            description: "Monkey D. Luffy parte em busca do tesouro One Piece para se tornar o Rei dos Piratas.",          numberOfPages: 216 },
  { name: "Attack on Titan Vol. 1",              author: "Hajime Isayama",            category: "MANGA",    genre: "Seinen",             description: "A humanidade vive cercada por muralhas, protegida de titãs devoradores.",                       numberOfPages: 193 },
  { name: "O Guia do Mochileiro das Galáxias",   author: "Douglas Adams",             category: "BOOK",     genre: "Ficção Científica",  description: "Arthur Dent viaja pelo espaço após a Terra ser destruída.",                                     numberOfPages: 224 },
  { name: "Sapiens: Uma Breve História da Humanidade", author: "Yuval Noah Harari",   category: "BOOK",     genre: "Não-ficção",         description: "Uma análise da história da espécie humana desde o surgimento do Homo sapiens.",                  numberOfPages: 464 },
  { name: "O Código Da Vinci",                   author: "Dan Brown",                 category: "BOOK",     genre: "Thriller",           description: "Robert Langdon investiga um assassinato no Louvre e descobre um segredo milenar.",               numberOfPages: 454 },
  { name: "A Metamorfose",                       author: "Franz Kafka",               category: "BOOK",     genre: "Existencialismo",    description: "Gregor Samsa acorda transformado em um inseto gigante.",                                         numberOfPages: 98  },
  { name: "Death Note Vol. 1",                   author: "Tsugumi Ohba",              category: "MANGA",    genre: "Thriller",           description: "Light Yagami encontra um caderno com poder de matar qualquer pessoa cujo nome seja escrito.",    numberOfPages: 200 },
  { name: "Brave New World",                     author: "Aldous Huxley",             category: "BOOK",     genre: "Distopia",           description: "Uma sociedade futurista onde a felicidade é garantida por condicionamento e drogas.",             numberOfPages: 288 },
  { name: "National Geographic - Edição Especial", author: "National Geographic",     category: "MAGAZINE", genre: "Ciência",            description: "Edição especial sobre as descobertas mais recentes do universo.",                                numberOfPages: 120 },
  { name: "Nature - Biodiversidade 2025",        author: "Nature Publishing",          category: "JOURNAL",  genre: "Ciência",            description: "Artigos revisados sobre a crise de biodiversidade global.",                                      numberOfPages: 85  },
  { name: "O Nome do Vento",                     author: "Patrick Rothfuss",          category: "BOOK",     genre: "Fantasia",           description: "Kvothe narra sua vida, de órfão a lenda viva.",                                                 numberOfPages: 736 },
  { name: "Ensaio sobre a Cegueira",             author: "José Saramago",             category: "BOOK",     genre: "Romance",            description: "Uma epidemia de cegueira branca assola uma cidade sem nome.",                                   numberOfPages: 310 },
  { name: "O Processo",                          author: "Franz Kafka",               category: "BOOK",     genre: "Existencialismo",    description: "Josef K. é preso e processado por um crime que desconhece.",                                    numberOfPages: 255 },
  { name: "Fullmetal Alchemist Vol. 1",          author: "Hiromu Arakawa",            category: "MANGA",    genre: "Shounen",            description: "Os irmãos Elric buscam a Pedra Filosofal para restaurar seus corpos.",                           numberOfPages: 192 },
];

// ─── Helpers ────────────────────────────────────────────

/** Gera um PNG 1x1 pixel em memória para usar como capa placeholder */
function createPlaceholderPng() {
  const png = Buffer.from([
    0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A,
    0x00, 0x00, 0x00, 0x0D, 0x49, 0x48, 0x44, 0x52,
    0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01,
    0x08, 0x02, 0x00, 0x00, 0x00, 0x90, 0x77, 0x53,
    0xDE, 0x00, 0x00, 0x00, 0x0C, 0x49, 0x44, 0x41,
    0x54, 0x08, 0xD7, 0x63, 0xF8, 0xCF, 0xC0, 0x00,
    0x00, 0x00, 0x02, 0x00, 0x01, 0xE2, 0x21, 0xBC,
    0x33, 0x00, 0x00, 0x00, 0x00, 0x49, 0x45, 0x4E,
    0x44, 0xAE, 0x42, 0x60, 0x82,
  ]);
  return new Blob([png], { type: "image/png" });
}

function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function shuffle(arr) {
  const a = [...arr];
  for (let i = a.length - 1; i > 0; i--) {
    const j = randomInt(0, i);
    [a[i], a[j]] = [a[j], a[i]];
  }
  return a;
}

async function req(method, path, body, token) {
  const headers = {};
  if (token) headers["Authorization"] = `Bearer ${token}`;

  let fetchOptions = { method, headers };

  if (body instanceof FormData) {
    fetchOptions.body = body;
  } else if (body) {
    headers["Content-Type"] = "application/json";
    fetchOptions.body = JSON.stringify(body);
  }

  const res = await fetch(`${BASE_URL}${path}`, fetchOptions);
  const text = await res.text();

  let data;
  try { data = JSON.parse(text); } catch { data = text; }

  if (!res.ok) {
    const msg = typeof data === "object" ? (data.message || JSON.stringify(data)) : data;
    throw new Error(`[${res.status}] ${method} ${path} -> ${msg}`);
  }
  return data;
}

function sleep(ms) {
  return new Promise(r => setTimeout(r, ms));
}

// ─── Etapas ─────────────────────────────────────────────

async function loginAs(email, password) {
  const token = await req("POST", "/auth/login", { email, password });
  return token;
}

async function registerUser(user) {
  await req("POST", "/auth/register", user);
}

async function createBook(book, adminToken) {
  const form = new FormData();
  const coverBlob = createPlaceholderPng();
  form.append("cover", coverBlob, `cover_${Date.now()}.png`);
  form.append("data", new Blob([JSON.stringify(book)], { type: "application/json" }));
  return await req("POST", "/books/", form, adminToken);
}

async function addBookToShelf(bookId, token) {
  return await req("POST", "/users/me/shelf/", { bookId }, token);
}

async function updateProgress(shelfItemId, currentPage, token) {
  return await req("PUT", `/users/me/shelf/${shelfItemId}/progress`, { currentPage }, token);
}

async function updateReadingStatus(shelfItemId, status, token) {
  return await req("PUT", `/users/me/shelf/${shelfItemId}`, { status }, token);
}

// ─── Main ───────────────────────────────────────────────

async function main() {
  console.log("==================================================");
  console.log("   SEED - Virtual Bookshelf                       ");
  console.log("==================================================");
  console.log(`URL: ${BASE_URL}\n`);

  // 1. Login como admin
  console.log("[1/4] Fazendo login como admin...");
  let adminToken;
  try {
    adminToken = await loginAs("admin@admin.com", "admin123");
  } catch (e) {
    console.error("FALHA ao logar como admin. O AdminSeeder ja rodou?");
    console.error("  Certifique-se de que a aplicacao esta rodando e que o admin foi criado.");
    console.error(`  Erro: ${e.message}`);
    process.exit(1);
  }
  console.log("Admin autenticado.\n");

  // 2. Cadastrar 30 livros
  console.log("[2/4] Cadastrando 30 livros...");
  const bookIds = [];
  for (let i = 0; i < BOOKS.length; i++) {
    try {
      const book = await createBook(BOOKS[i], adminToken);
      bookIds.push(book.id);
      console.log(`   [${i + 1}/${BOOKS.length}] ${BOOKS[i].name}`);
    } catch (e) {
      console.warn(`   AVISO: Erro ao cadastrar "${BOOKS[i].name}": ${e.message}`);
    }
    await sleep(50);
  }
  console.log(`${bookIds.length} livros cadastrados.\n`);

  if (bookIds.length === 0) {
    console.error("Nenhum livro foi cadastrado. Abortando.");
    process.exit(1);
  }

  // 3. Registrar 15 usuarios
  console.log("[3/4] Registrando 15 usuarios...");
  for (let i = 0; i < USERS.length; i++) {
    try {
      await registerUser(USERS[i]);
      console.log(`   [${i + 1}/${USERS.length}] ${USERS[i].name} (${USERS[i].email})`);
    } catch (e) {
      console.warn(`   AVISO: Erro ao registrar "${USERS[i].name}": ${e.message}`);
    }
    await sleep(50);
  }
  console.log(`Usuarios registrados.\n`);

  // 4. Para cada usuario: adicionar livros a estante + gerar progresso
  console.log("[4/4] Populando estantes e gerando progresso...\n");

  for (const user of USERS) {
    let token;
    try {
      token = await loginAs(user.email, user.password);
    } catch (e) {
      console.warn(`   AVISO: Nao foi possivel logar como ${user.email}: ${e.message}`);
      continue;
    }

    // Escolher 5-10 livros aleatorios
    const numBooks = randomInt(5, 10);
    const selectedBookIds = shuffle(bookIds).slice(0, numBooks);

    const shelfItems = [];
    for (const bookId of selectedBookIds) {
      try {
        const shelfItem = await addBookToShelf(bookId, token);
        shelfItems.push(shelfItem);
      } catch (e) {
        // Livro ja na estante ou outro erro
      }
      await sleep(30);
    }

    console.log(`   ${user.name}: ${shelfItems.length} livros na estante`);

    // Gerar progresso em metade dos livros
    const halfCount = Math.ceil(shelfItems.length / 2);
    const itemsToProgress = shuffle(shelfItems).slice(0, halfCount);

    for (const item of itemsToProgress) {
      try {
        const totalPages = item.book.numberOfPages;
        if (!totalPages || totalPages <= 0) continue;

        // Primeiro, marcar como "READING"
        await updateReadingStatus(item.id, "READING", token);
        await sleep(30);

        // Gerar progresso aleatorio (10% a 100% do livro)
        const progressPercent = randomInt(10, 100);
        const currentPage = Math.min(Math.round(totalPages * progressPercent / 100), totalPages);

        await updateProgress(item.id, currentPage, token);
        await sleep(30);

        // Se leu tudo, o backend ja marca como COMPLETED automaticamente
        if (currentPage < totalPages && randomInt(0, 3) === 0) {
          // 25% de chance de pausar a leitura
          await updateReadingStatus(item.id, "PAUSED", token);
        }

      } catch (e) {
        // Erro de progresso individual, continua
      }
    }

    console.log(`       -> Progresso gerado em ${itemsToProgress.length} livros`);
  }

  console.log("\n==================================================");
  console.log("   SEED CONCLUIDO COM SUCESSO!                    ");
  console.log("==================================================");
  console.log(`\nResumo:`);
  console.log(`   ${bookIds.length} livros cadastrados`);
  console.log(`   ${USERS.length} usuarios registrados`);
  console.log(`   Estantes populadas com 5-10 livros cada`);
  console.log(`   Progresso aleatorio em ~50% dos livros\n`);
  console.log(`Credenciais de teste:`);
  console.log(`   Admin:   admin@admin.com / admin123`);
  console.log(`   Usuario: carlos@email.com / Senha123! (e outros)\n`);
}

main().catch(err => {
  console.error("\nErro fatal:", err.message);
  process.exit(1);
});
