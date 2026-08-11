Budgeting API
Uma API REST inteligente criada para facilitar o controle de despesas diárias usando inteligência artificial para processar comandos de voz.
Esse projeto foi desenvolvido como um desafio prático do bootcamp Santander 2026 - AI Java Back-end na DIO. O objetivo principal foi transformar o processo tradicional de registro manual de gastos em uma experiência simples: o usuário envia uma gravação de áudio, e a aplicação cuida do restante.

Como Funciona?
    1. Envio de Áudio: O usuário manda uma gravação de voz pelo terminal ou aplicativo (ex: "Gastei 35 reais no mercado ontem").
    2. Processamento Semântico: A API recebe o arquivo e utiliza o modelo gemini-1.5-flash da Google.
    3. Extração e Classificação: A IA interpreta a mensagem, extrai a descrição da despesa, padroniza o valor financeiro e define automaticamente a categoria correspondente (ex: ALIMENTACAO).
    4. Persistência: Os dados organizados são validados e armazenados no PostgreSQL.

Tecnologias e Ferramentas
    • Java 21: Versão LTS aproveitando recursos modernos da linguagem.
    • Spring Boot 3.2.5: Base para a construção da API REST.
    • Spring Data JPA: Abstração do ORM para comunicação com o banco de dados.
    • PostgreSQL: Banco relacional para armazenamento seguro das transações.
    • Docker & Docker Compose: Containerização do ambiente de banco de dados.
    • Google Gemini API (1.5-Flash): Processamento e estruturação de dados não estruturados de áudio.

Endpoints Principais
Método	Endpoint	O que faz?
POST	/carteira/ia	Recebe um áudio (multipart/form-data) e registra a despesa via IA.
GET	/carteira	Retorna o histórico completo de transações registradas.
GET	/carteira/categoria/{nome}	Lista as despesas filtradas por categoria (ex: ALIMENTACAO, SAUDE, LAZER).


Como Rodar o Projeto na Sua Máquina
Pré-requisitos
    • Java 21 configurado no ambiente.
    • Docker Desktop instalado.
    • Uma chave de API gratuita do Google AI Studio.
1. Configurar as Variáveis de Ambiente
No arquivo src/main/resources/application.properties, adicione a sua chave da API do Gemini:
gemini.api.key=SUA_CHAVE_AQUI

