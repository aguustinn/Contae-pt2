Contaê

O Contaê é um aplicativo Android desenvolvido para ajudar no controle e organização das finanças pessoais.

O projeto foi desenvolvido em Kotlin utilizando Jetpack Compose e surgiu a partir do Trabalho 1 da disciplina. Nesta segunda versão, o aplicativo recebeu novas telas, navegação entre as funcionalidades e gerenciamento de categorias e despesas.

Funcionalidades

Atualmente, o aplicativo possui:

Tela inicial e login;
Tela principal com resumo financeiro;
Cadastro de categorias;
Definição de limite para cada categoria;
Cadastro de despesas;
Associação de despesas às categorias;
Exclusão de categorias e despesas;
Visualização dos detalhes de cada categoria;
Visualização dos detalhes de cada despesa;
Cálculo do total gasto por categoria;
Cálculo do percentual utilizado do orçamento;
Tela de orçamento;
Tela de relatório mensal;
Navegação entre as principais telas através da barra inferior.

Os dados utilizados pelo aplicativo ficam armazenados apenas em memória durante a execução. Por isso, não é necessário configurar um banco de dados para executar o projeto.

Tecnologias utilizadas
Kotlin
Android
Jetpack Compose
Material 3
Navigation Compose
Gradle
Android Studio
Requisitos

Para executar o projeto, é necessário ter instalado:

Android Studio;
JDK 11 ou superior;
Android SDK;
Um emulador Android ou um dispositivo físico.

O projeto está configurado com:

minSdk = 24
targetSdk = 37
compileSdk = 37
Kotlin 2.2.10
Como executar
1. Clonar o repositório

Abra um terminal e execute:

git clone https://github.com/aguustinn/Contae.git

Depois entre na pasta do projeto:

cd Contae
2. Abrir no Android Studio

Abra o Android Studio e selecione:

File → Open

Depois escolha a pasta do projeto Contae.

Aguarde o Android Studio finalizar a sincronização do Gradle e baixar as dependências necessárias.

3. Executar o aplicativo

Depois que a sincronização terminar:

Escolha um emulador Android ou conecte um celular;
Selecione o módulo app;
Clique no botão Run ▶;
Aguarde o aplicativo ser instalado e iniciado.

Também é possível executar pelo terminal:

./gradlew assembleDebug

No Windows:

gradlew.bat assembleDebug
Estrutura do projeto

O projeto foi separado em alguns pacotes para facilitar a organização do código:

com.example.contae
│
├── data
│   └── Dados.kt
│
├── model
│   ├── Categoria.kt
│   └── Despesa.kt
│
├── navigation
│   ├── AppNavigation.kt
│   ├── TelaCategorias.kt
│   ├── TelaDespesas.kt
│   ├── TelaDetalhesCategoria.kt
│   ├── TelaDetalhesDespesa.kt
│   ├── TelaHome.kt
│   ├── TelaInicial.kt
│   ├── TelaLogin.kt
│   ├── TelaOrcamento.kt
│   └── TelaRelatorio.kt
│
└── screens
    └── Common.kt

model

Contém as classes que representam os principais dados do aplicativo:

Categoria
Despesa
data

Contém os dados utilizados durante a execução do aplicativo. As listas são criadas com mutableStateListOf, permitindo que a interface seja atualizada quando os dados são alterados.

navigation

Contém a configuração da navegação do aplicativo.

O arquivo AppNavigation.kt possui o NavHost, o NavController e o objeto Rotas, que centraliza as rotas utilizadas pelo aplicativo.

screens

Contém as telas e componentes da interface do aplicativo.

Navegação

A navegação principal é feita utilizando o Navigation Compose.

O aplicativo possui uma sequência inicial:

Tela Inicial
     ↓
   Login
     ↓
   Home
     ↓
Categorias / Despesas / Orçamento / Relatório

Nas telas principais, o usuário pode utilizar a barra de navegação inferior para trocar de seção.

As telas de detalhes recebem o id do item selecionado através da rota. Dessa forma, ao clicar em uma categoria ou despesa, o aplicativo abre os dados correspondentes àquele item.

Dados utilizados

Durante a execução são criadas algumas categorias e despesas de exemplo.

Exemplo de categoria:

Categoria(
    id = 1,
    nome = "Alimentação",
    limite = 850.0
)

Exemplo de despesa:

Despesa(
    id = 1,
    descricao = "Mercado",
    valor = 250.0,
    categoriaId = 1,
    data = "04/10/2026"
)

Esses dados podem ser alterados, adicionados ou removidos através da própria interface do aplicativo.

Trabalho 2

Nesta versão foram adicionados principalmente:

Navegação entre as telas;
Barra de navegação inferior;
Novas telas;
Cadastro de categorias;
Cadastro de despesas;
Exclusão de itens;
Tela de detalhes de categoria;
Tela de detalhes de despesa;
Cálculos relacionados ao orçamento;
Organização do código em diferentes arquivos;
Rotas centralizadas através do objeto Rotas.
Observação

Como o projeto utiliza armazenamento apenas em memória, os dados adicionados durante a utilização do aplicativo são perdidos quando o aplicativo é encerrado.

O objetivo desta versão é atender aos requisitos do trabalho e demonstrar o funcionamento da navegação, composição das telas e gerenciamento dos dados utilizando Jetpack Compose.
