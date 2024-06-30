# Pãozim 🍞

### Aplicativo para assinatura de delivery de padaria 🏍️

- [Link para o diagrama UML do projeto](https://drive.google.com/drive/folders/1_JOy5EMATv9zNfBw88P1nsH5qACJKgTH?usp=sharing)
- [Link para a apresentação final do projeto](https://www.canva.com/design/DAGFMoHj2k4/reFSWZl9gewFk_NkRBdPlQ/edit?utm_content=DAGFMoHj2k4&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)
- [Apresentação em vídeo do projeto](https://youtu.be/7qgEI3Ispd8)

O projeto Pãozim foi idealizado para ser desenvolvido durante a disciplina de [Introdução ao Desenvolvimento de Sistemas de Software (MAC0350)](https://uspdigital.usp.br/jupiterweb/obterDisciplina?sgldis=MAC0350). A plataforma desenvolvida possibilita a assinatura de clientes em produtos de padaria próximos à sua localização, e recebem seus produtos diária, semanal ou mensalmente conforme solicitado. Também é possível fazer pedidos individuais para padarias e customizar o carrinho como quiser.

### Frontend 💻

O Frontend da aplicação foi feito utilizando Kotlin com estilização em XML. O aplicativo foi feito para ser executado em celulares Android. Pedidos feitos no aplicativo são atualizados em tempo real. Também é possível avaliar pedidos feitos, e o usuário permanece salvo após sair do aplicativo, o que facilita muito o seu uso.


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

Supondo que você está usando a IDE Android Studio para visualização e edição do código base, siga as seguintes instruções:

1. Clone o repositório
2. Execute o Backend através do IntelliJ (ele rodará por padrão no endpoint `localhost:8000`)
3. Quando o Backend estiver rodando, execute em outro terminal `ngrok http 8000`
4. Feito isso, copie o endereço público gerado pelo `ngrok` e cole como conteúdo em String da variável `BASE_URL` em `frontend\app\src\main\java\com\mobile\paozim\retrofit\RetrofitInstance.kt`
5. Abra a pasta do repositório no Android Studio
6. Sincronize o Gradle
7. Configure o Emulador Android ou defina um dispositivo físico para instalar a Build do aplicativo (pode precisar de drivers dependendo do fabricante do telefone)
8. Execute o Projeto no Android Studio

Após isso, o aplicativo deve funcionar normalmente no dispositivo ou emulador configurado!
