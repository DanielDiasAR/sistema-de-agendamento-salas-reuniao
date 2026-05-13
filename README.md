# Avaliacao Estagio
# Titulo - Sistema de Agendamento de Salas de Reunião
## Problema:
Uma empresa precisa de um sistema para controlar o agendamento de suas salas de reunião.
O sistema deve permitir que os colaboradores reservem salas, evitando conflitos de horário e garantindo que os recursos necessários estejam disponíveis.

### Requisitos Funcionais:

CRUD Completo para:
Salas de Reunião: Número da sala, localização, capacidade de pessoas, recursos disponíveis (projetor, TV, quadro branco, etc.).
Colaboradores: Nome, departamento, e-mail.
Agendamentos: Sala, colaborador responsável pela reserva, data, hora de início, hora de fim, título da reunião, participantes.
Na tela de agendamento, os campos Sala e Colaborador devem ser comboboxes (dropdowns) populados com os dados das respectivas tabelas do banco.

### Regras de Validação ao salvar um agendamento:
Conflito de Horários: Uma sala não pode ser reservada por duas reuniões no mesmo horário.
Duração Máxima: Nenhuma reunião pode durar mais de 4 horas.
Horário Comercial: As reuniões só podem ser agendadas entre 8h e 18h.

### Mensagens de feedback:
Em caso de conflito: "A sala [X] já está reservada para o horário das [H1] às [H2] no dia [D]."
Em caso de sucesso: "Agendamento para [Título da Reunião] registrado com sucesso."


# Banco de dados: 
Utilizar um banco de dados para implementar o seguinte diagrama de entidade e relacionamentos:
