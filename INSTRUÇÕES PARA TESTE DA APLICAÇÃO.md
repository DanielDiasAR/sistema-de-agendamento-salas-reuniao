# Titulo - Sistema de Agendamento de Salas de Reunião
## Instruções para Testar a Aplicação:


SASR - Sistema de Agendamento de Salas de Reunião

O SASR é uma aplicação Java console desenvolvida para gerenciar o agendamento de salas de reunião, garantindo que não haja conflitos de horários e respeitando regras de negócio específicas.


Tecnologias Utilizadas:

Java 17 (ou superior)
JDBC (Java Database Connectivity)
Oracle Database
Maven (Gerenciamento de dependências)


Pré-requisitos:

Java JDK instalado e configurado nas variáveis de ambiente.
Oracle Database (Local ou Cloud) acessível.
Driver JDBC do Oracle (Configurado no pom.xml).
Git para clonar e gerenciar o código.


## Como Testar a Aplicação:
Primeiramente, na classe ConnectionFactory, configure a instância com o BD Oracle com endereço completo e suas credenciais.
A aplicação está dividida em três módulos principais de visualização. Execute cada classe View para testar as funcionalidades:


## 1. Cadastro de Salas (SalaView)
Acesse o menu e escolha a opção 1.

Cadastre uma sala (Ex: Número "101", Capacidade "10", Recursos "Projetor").

Use a opção 3 para listar e anotar o ID gerado.


## 2. Cadastro de Colaboradores (ColaboradorView)
Escolha a opção 1.

Cadastre um colaborador (Ex: Nome "Daniel Dias", Depto "TI").

Use a opção 3 para listar e anotar o ID gerado.


## 3. Realizando Agendamentos (AgendamentoView)
Este módulo contém as validações críticas. Tente realizar os seguintes testes:

Teste de Sucesso: Agende uma reunião dentro do horário comercial (ex: 09:00 às 10:00).

Teste de Horário Comercial: Tente agendar para às 07:00 ou após às 18:00. O sistema deve bloquear.

Teste de Duração: Tente agendar uma reunião com mais de 4 horas de duração. O sistema deve bloquear.

Teste de Conflito: Tente agendar uma nova reunião para a mesma sala no mesmo horário de uma reunião já existente. O sistema exibirá a mensagem de erro detalhando quem já ocupa a sala.


## Regras de Negócio Implementadas:

Conflito de Horários: Uma sala não pode ser reservada por duas reuniões simultâneas.
Duração Máxima: Reuniões não podem exceder 4 horas.
Horário Comercial: Agendamentos permitidos apenas entre 08h e 18h.

