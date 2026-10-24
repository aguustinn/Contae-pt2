# Contaê — Trabalho 3 (Room)

O Contaê é um aplicativo Android para organização e controle de finanças pessoais. Esta versão evolui o Trabalho 2: as telas e a navegação em Jetpack Compose foram mantidas, mas as categorias e despesas agora são gravadas em um banco SQLite local por meio do Room.

## O que mudou do Trabalho 2 para o Trabalho 3

No Trabalho 2, as listas `categorias` e `despesas` eram `mutableStateListOf` preenchidas com dados fixos. Isso permitia adicionar, editar e excluir itens durante a execução, mas os dados se perdiam quando o processo do app era encerrado.

No Trabalho 3, o Room passou a ser a fonte persistente dos dados. As listas observáveis que a interface usa são apenas um cache carregado do banco; não são mais a fonte definitiva. As telas recarregam as listas ao abrir, e as alterações executam operações no SQLite em coroutines com `Dispatchers.IO`.

## Entidades e campos

| Entidade | Tabela Room | Campos | Motivo |
|---|---|---|---|
| `Categoria` | `categorias` | `id`, `nome`, `limite` | O ID identifica o registro; o nome aparece nas telas e o limite é usado nos cálculos do orçamento. |
| `Despesa` | `despesas` | `id`, `descricao`, `valor`, `categoriaId`, `data` | O ID identifica a despesa; descrição, valor e data descrevem o gasto; `categoriaId` associa o gasto à categoria correspondente. |

Os IDs são `Int` com `@PrimaryKey(autoGenerate = true)`. Ao inserir, o app deixa o Room gerar o ID; ao editar, mantém o ID existente.

## DAO e consultas

- `CategoriaDao`: `inserir`, `buscarTodos`, `buscarPorId`, `atualizar` e `deletar`.
- `DespesaDao`: os quatro métodos básicos (`inserir`, `buscarTodos`, `atualizar`, `deletar`) e consultas adicionais `buscarPorId` e `buscarPorCategoria`.
- `AppDatabase`: reúne as duas entidades e disponibiliza os DAOs usando Singleton (`companion object`, `@Volatile` e `synchronized`).

As consultas filtradas permitem abrir uma tela de detalhes pelo ID recebido pela navegação e recuperar diretamente o registro no banco. A consulta por categoria também é usada para obter as despesas vinculadas e evitar manter despesas órfãs quando uma categoria é removida.

## CRUD persistente

- **Buscar:** as telas de Categorias e Despesas executam `LaunchedEffect(Unit)` para buscar os registros no Room.
- **Inserir:** os formulários chamam `Dados.salvarCategoria` ou `Dados.salvarDespesa`; o Room gera o ID.
- **Editar:** o formulário carrega os valores do item e salva a entidade com o mesmo ID.
- **Remover:** o item é excluído do banco, e as listas visíveis são sincronizadas novamente.
- **Detalhes:** as telas recebem somente o ID pela rota e buscam o registro com `buscarPorId`.

As operações de banco são executadas em `Dispatchers.IO`. As exceções são registradas com `Log.e`. O projeto também preenche o banco vazio com os exemplos iniciais que existiam no Trabalho 2, uma única vez, para que a primeira execução mantenha a experiência de demonstração.

## Tecnologias

- Kotlin e Android
- Jetpack Compose e Material 3
- Navigation Compose
- Room / SQLite
- KSP para gerar as implementações do Room
- Kotlin Coroutines

## Como executar

1. Abra o projeto no Android Studio.
2. Use JDK 17 ou a versão exigida pela instalação do Android Studio e pelo Gradle do projeto.
3. Aguarde a sincronização do Gradle (o plugin KSP precisa estar configurado antes de usar `ksp(room-compiler)`).
4. Execute o módulo `app) em um emulador ou aparelho Android.

O banco é local e o arquivo SQLite é administrado pelo app. Não há servidor, API ou sincronização em nuvem.

## Teste de aceitação: verificar persistência

1. Abra o app e acesse **Categorias** ou **Despesas**.
2. Cadastre um item de teste e confirme que ele aparece na lista.
3. Edite o item e salve; confira os valores alterados.
4. Feche o app completamente (encerre o processo; não basta apenas navegar para outra tela).
5. Abra o Contaê novamente e retorne à lista. O registro precisa continuar lá com os valores salvos.
6. Remova o item e confirme que ele deixa de aparecer, inclusive após reabrir o app.

## Documentação em vídeo e prints

A atividade exige evidência real do app funcionando. Os prints e vídeos precisam ser capturados pelo trio no emulador/aparelho; não são substituídos por imagens ilustrativas ou por este texto.

Organizem os arquivos em `docs/evidencias/` (instruções e roteiro em [docs/evidencias/README.md](docs/evidencias/README.md)) e adicionem links abaixo após gravar e enviar os arquivos:

- **Print:** tela com item cadastrado.
- **Vídeo 1:** adicionar, editar e remover uma categoria.
- **Vídeo 2:** adicionar uma despesa, fechar completamente o app, reabrir e mostrar que ela persiste.
- **Print/vídeo:** detalhes abertos a partir de uma rota com ID.

## Reflexão do trio

### Qual foi a dificuldade na migração de memória para Room?

No Trabalho 2, a interface alterava as listas diretamente. Com Room, foi necessário trocar essas alterações por operações assíncronas de inserir/atualizar/excluir e recarregar os dados persistidos. Também foi necessário deixar o Room gerar o ID ao inserir, preservando o ID existente durante a edição, e atualizar a interface depois das operações de banco.

### O que quebrou no caminho e como foi resolvido?

O principal ponto de atenção é não executar SQL na thread principal e não tratar a lista em memória como armazenamento definitivo. A implementação centraliza as operações na camada de dados, usa coroutines com `Dispatchers.IO`, registra erros no Logcat e sincroniza as listas observáveis após cada alteração. **Antes da entrega, o trio deve abrir e compilar o projeto no Android Studio e registrar qualquer ajuste específico que tenha sido necessário no ambiente.**

### Algum integrante teve dificuldade? Como resolveu?

**Completem esta resposta com a experiência real do trio.** Exemplo de tópicos para relatar: sincronização do Gradle/KSP, assinatura das consultas DAO, geração automática dos IDs, atualização da tela após uma coroutine e o teste após encerrar o app. Não atribuam dificuldades a integrantes sem confirmar o que aconteceu.

## Checklist de entrega

- [ ] O projeto sincroniza e compila no Android Studio.
- [ ] As duas entidades e os dois DAOs estão implementados.
- [ ] Inserir, editar e excluir persistem no SQLite.
- [ ] A tela de detalhes consulta o registro pelo ID.
- [ ] O teste de fechar e reabrir o app foi realizado ao vivo.
- [ ] Foram adicionados prints e vídeos reais à documentação.
- [ ] A reflexão do trio foi revisada e completada.
