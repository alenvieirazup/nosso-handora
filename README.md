# Nosso Handora

## TODOs

- Terminar o CRUD da classe `Curso`
  - Só pode aumentar o número de vagas, nunca diminuir

## Dúvidas

- Lock otimista com mapeamento muitos-para-muitos
- Ordem de operações na transação: atualização da entidade e atualização de tabela de relacionamento

## Feito

- Adicionar a busca de cursos por nome com LIKE usando `Specification`
- Adicionar relacionamento com `Pessoa`
- Criar controlador para cadastrar pessoa no curso verificando o número de vagas
- Refatorar o método `matricular`
- Implementar lock para tratar concorrência
