# Pãozim 🍞

### Aplicativo para assinatura de delivery de padaria 🏍️

- [Link para o diagrama UML do projeto](https://drive.google.com/drive/folders/1_JOy5EMATv9zNfBw88P1nsH5qACJKgTH?usp=sharing)
- [Link para a apresentação final do projeto](https://www.canva.com/design/DAGFMoHj2k4/reFSWZl9gewFk_NkRBdPlQ/edit?utm_content=DAGFMoHj2k4&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)
- [Apresentação em vídeo do projeto](https://youtu.be/7qgEI3Ispd8)

O projeto Pãozim foi idealizado para ser desenvolvido durante a disciplina de [Introdução ao Desenvolvimento de Sistemas de Software (MAC0350)](https://uspdigital.usp.br/jupiterweb/obterDisciplina?sgldis=MAC0350). A plataforma desenvolvida possibilita a assinatura de clientes em produtos de padaria próximos à sua localização, e recebem seus produtos diária, semanal ou mensalmente conforme solicitado. Também é possível fazer pedidos individuais para padarias e customizar o carrinho como quiser.

### Frontend 📲

O Frontend da aplicação foi feito utilizando Kotlin com estilização em XML. O aplicativo foi feito para ser executado em celulares Android. Pedidos feitos no aplicativo são atualizados em tempo real. Também é possível avaliar pedidos feitos, e o usuário permanece salvo após sair do aplicativo, o que facilita muito o seu uso. 

Deste a gravação do vídeo, atualizamos as operações de assinatura e agora a listagem é essa [signature_list.jpg](signature_list.jpg) e a tela de pagamento é essa [signature_pay.jpg](signature_pay.jpg)


### Backend 💻

A conexão entre o backend e o frontend é feita através do ngrok. [Link para o tutorial do ngrok](https://medium.com/desenvolvendo-com-paixao/ngrok-do-localhost-para-o-mundo-5445ad08419)

Variáveis para se atentar antes de executar o app:
- <i>BASE_URL</i> em [frontend/app/src/main/java/com/mobile/paozim/retrofit/RetrofitInstance.kt](frontend/app/src/main/java/com/mobile/paozim/retrofit/RetrofitInstance.kt)
	- deve ser a url passada pelo ngrok

Ao executar o server, se atente para as variáveis que devem ser alteradas:
- <i>jdbcUrl</i> em [backend/src/main/kotlin/com/pao/repositories/DatabaseFactory.kt](backend/src/main/kotlin/com/pao/repositories/DatabaseFactory.kt)
  	- deve ter o nome do seu banco de dados (aqui nomeado "pao") e a senha (aqui sendo "database")

O banco de dados utilizado foi o postgreSQL.
Para usar alguns dados de teste, após executar o server pela primeira vez, é possível importar os dados da pasta [dadosTeste](dadosTeste). Para isso:

1. Abra o pgAdmin 4 e vá para a database
2. Vá para Tables
3. Clique com o botão direito na tabela que deseja preencher
4. Selecione Import/Expor Data
5. Passe o filename e na aba options coloque ';' como delimitador
   
- Para não gerar problemas de chave estrangeira, faça esse passo a passo na seguinte orderm: usuario, sellers, itens, categories e rating

### Como executar o projeto 🚨

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
