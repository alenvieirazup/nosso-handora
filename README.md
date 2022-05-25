# Nosso Handora

## TODOs

- Modelagem de seções e atividades
- Fazer o ExceptionHandler
- Acrescentar novos conteúdos estudados (migration, teste, log)

## Dúvidas

- (Thiago) Lock otimista com mapeamento muitos-para-muitos
- (Eloy) Ordem de operações na transação: atualização da entidade e atualização de tabela de relacionamento
- (Denes) Put e pach quando existe atributos de relacionamento
- (Alen) Em métodos de atualizar podemos passar o request para a entidade

## Feito

- Adicionar a busca de cursos por nome com LIKE usando `Specification`
- Adicionar relacionamento com `Pessoa`
- Criar controlador para cadastrar pessoa no curso verificando o número de vagas
- Refatorar o método `matricular`
- Implementar lock para tratar concorrência
- Mudança de atributo de atualização vagasDisponiveis de Integer para boolean
- Mudança de POST para PATCH no controlador para matricular uma pessoa em um curso
- Método atualizar curso com restrição para atualização do número de vagas quando existe pessoas matriculadas
- Método desativar
- Acrescentar atributo ativo na busca dinâmica
