# Pãozim 🍞

### Aplicativo para assinatura de delivery de padaria 🏍️

- [Link para o diagrama UML do projeto](https://drive.google.com/drive/folders/1_JOy5EMATv9zNfBw88P1nsH5qACJKgTH?usp=sharing)

O projeto Pãozim foi idealizado para ser desenvolvido durante a disciplina de [Introdução ao Desenvolvimento de Sistemas de Software (MAC0350)](https://uspdigital.usp.br/jupiterweb/obterDisciplina?sgldis=MAC0350). A plataforma desenvolvida possibilita a assinatura de clientes em padarias próximas de sua localização, e recebem seus produtos diaria, semanal ou mensalmente conforme especificações do seu próprio plano escolhido.

### Observações
A conexão entre o backend e o frontend é feita através do ngrok. [Link para o tutorial do ngrok](https://medium.com/desenvolvendo-com-paixao/ngrok-do-localhost-para-o-mundo-5445ad08419)

Ao executar o server, se atente para as variáveis que devem ser alteradas:
- <i>BASE_URL</i> em "src/main/kotlin/com/pao/routes/UserRoute"
	- deve ser da forma "http://<seu_ip>:8000"

Variáveis para se atentar antes de executar o app:
- <i>BASE_URL</i>L em "app/kotlin+java/com/mobile/paozim/testdata/RetrofitClient"
	- deve ser a url passada pelo ngrok
- <i>sdk.dir</i> em "Gradle Sripts/local.properties"

### Como executar o projeto 💻

Supondo que você está usando a IDE Android Studio para visualização e edição do código base, siga as seguintes instruções para compilar e visualizar o projeto em tempo real usando o emulador Android no seu ambiente:

1. Clone o repositório
2. Abra a pasta do repositório no Android Studio
3. Sincronize o Gradle: Assim que o projeto for carregado, o Android Studio pode solicitar a sincronização dos arquivos do Gradle. Se não, você pode acionar isso manualmente selecionando "Sincronizar Projeto com Arquivos do Gradle" no menu "Arquivo".
4. Configure o Emulador Android: Se ainda não configurou um Emulador Android, pode fazê-lo indo ao menu "Ferramentas", selecionando "Gerenciador AVD" e então criando um novo dispositivo virtual clicando no botão "Criar Dispositivo Virtual".
5. Execute o Projeto: Uma vez que as dependências estiverem instaladas e o emulador estiver configurado, você pode executar o projeto clicando no botão verde de reprodução na barra de ferramentas, ou selecionando "Executar" -> "Executar 'app'" no menu superior. O Android Studio irá compilar seu projeto e implantá-lo no emulador.
6. Escolha o Emulador: Se você tiver vários emuladores configurados, o Android Studio irá solicitar que você escolha um. Selecione o emulador que deseja usar e clique em "OK".
7. Inicie o Aplicativo: Uma vez que a implantação estiver completa, o emulador será iniciado automaticamente e o seu aplicativo será lançado dentro dele.

É isso! Agora você deverá ter o seu projeto Kotlin sendo executado dentro de um emulador Android no Android Studio.
