# Nosso Handora

## TODOs

- Terminar o CRUD da classe `Curso`
  - Só pode aumentar o número de vagas, nunca diminuir
- Modelagem de seções e atividades

## Dúvidas

- Lock otimista com mapeamento muitos-para-muitos
- Ordem de operações na transação: atualização da entidade e atualização de tabela de relacionamento

## Feito

- Adicionar a busca de cursos por nome com LIKE usando `Specification`
- Adicionar relacionamento com `Pessoa`
- Criar controlador para cadastrar pessoa no curso verificando o número de vagas
- Refatorar o método `matricular`
- Implementar lock para tratar concorrência
- Mudança de atributo de atualização vagasDisponiveis de Integer para boolean
- Mudança de POST para PATCH no controlador para matricular uma pessoa em um curso
