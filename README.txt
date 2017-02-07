"ABANDONE TODA A ESPERANÇA AQUELE QUE AQUI ENTRAR!"

Nas palavras do grande Dante Alighieri eu recebo Você, brava alma, que se aventura por esses pacotes.

O que posso dizer sobre esse programa? Ele nasceu como um projeto muito organizado, com submódulos separados. Seguindo a ris-
ca as lógicas de porjetos OO. Vários padrões de pojetos estavam documentados e bem organizados. Mas como dizem, a pressa é 
inimiga da perfeição. Para cumprir os prazos tive que sacrificar a organização e partir para a programação procedural. O esti-
lo adotado foi o "Fazer Funcionar". Isso levou a criação de coisas genéricas e várias pseudo-classes adaptadas apenas para re-
solver problemas e requisições na medida que elas fossem aparecendo.

Espero que entendam que não sou uma má pessoa nem retardado por ter feito o sistema assim, eu apenas não tinha tempo.

Fica aqui então meu pedido para que não chinguem muito minha mãe ao tentar mexer nesse programa.

Aqui vai uma breve descrição das partes e pacotes do sistema:
	br.com.csl.lynx.api 				-> Interfaces de uso da lógica de negócio do sistema.
	br.com.csl.lynx.auth 				-> Controle de Autenticação usado pelo SpringSecurity.
	br.com.csl.lynx.config				-> Configuração de acesso ao Banco de Dados.
	br.com.csl.lynx.controller.common	-> Controladores Básicos do Sistema (Quase sem uso, não devia existir)
	br.com.csl.lynx.controller.generic	-> Controladores Abstratos do sistema.
	br.com.csl.lynx.controller.home		-> Controladores da seção Home de cada Vínculo.
	br.com.csl.lynx.controller.local	-> Controladores da parte de Endereço
	br.com.csl.lynx.controller.poste	-> Controladores da parte de Postes
	br.com.csl.lynx.controller.report	-> Controladores de Relatórios
	br.com.csl.lynx.controller.rip		-> Controladores para cada tipo de RIP
	br.com.csl.lynx.controller.service	-> Controladores da parte de Serviços
	br.com.csl.lynx.controller.user		-> Controladores da parte de Administração de Usuários
	br.com.csl.lynx.domain				-> Nem usa mais...
	br.com.csl.lynx.exception			-> Exceções do Sistema
	br.com.csl.lynx.facade				-> Facades pros serviços de Endereço e Rip
		RipFacade -> Essa parte é responsável pelas operações Batch no BD.
	br.com.csl.lynx.filter				-> Filtros de RIPs Usados
	br.com.csl.lynx.generator			-> Aqui é onde fica o gerador de número das RIPs que o Hibernate usa pra gerar o ID delas.
	br.com.csl.lynx.generic				-> Implementação Genérica da API do sistema.
	br.com.csl.lynx.handler				-> Os Handlers que tratam da lógica de negócio do sistema
		EnderecoHandler					-> Esse aqui trata da parte de apresentar e auto completar endereços pela rua ou bairro.
		FotoHandler						-> Esse salva e lista as fotos na devida pasta de movimentação.
		MovementHandler					-> Todas as movimentações do sistema são feitas aqui, Abrir Enviar Receber, ele trata
										   de criar novas movimentações e mudar o status da RIP (Menos em operações Batch, 
										   que são feitas na RipFacade).
		PrintRipHandler					-> Trata das Impressões das RIPs
		ReportHandler					-> Usa o JasperReports para gerar os PDFs
	br.com.csl.lynx.model				-> Modelos (Entidades, Objetos de Domínio). Alguns não são usados.
	br.com.csl.lynx.service				-> Interfaces de Serviços que extendem a interface genérica.
	br.com.csl.lynx.service.impl		-> Implementação dos Serviços mais específicos.
	br.com.csl.lynx.servlet				-> Servlet de imagens, que busca as fotos na pasta que não é acessível ao público.
	br.com.csl.lynx.session				-> Bean de sessão
	br.com.csl.lynx.support				-> Todos os ENUMS
	br.com.csl.lynx.utils				-> Algumas classes usadas para facilitar coisas.
	br.com.csl.utils.controller			-> Controlador genérico
	br.com.csl.utils.data				-> Interface da parte de Dados (Serviço e Acesso)
	br.com.csl.utils.data.impl			-> Implementações Concretas e Abstratas dos DAOs Serviços e Model(PrimeFaces) genéricos.
	br.com.csl.utils.entity				-> Interface genérica de Entidade Persistida
	br.com.csl.utils.exception			-> Exceções da usadas pela parte de acesso aos dados.
	
	
Boa Sorte!
BHC
	