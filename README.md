# Planejador Financeiro

Este projeto é um sistema simples de planejamento financeiro, desenvolvido em Java, com o objetivo de ajudar usuários a gerenciar suas finanças pessoais de forma eficiente.

## Funcionalidades Principais
- **Gerenciamento de Transações:** Permite registrar entradas (receitas) e saídas (despesas), facilitando o controle do saldo do usuário.
- **Gestão de Metas Financeiras:** O usuário pode criar, atualizar e remover metas financeiras, acompanhando o progresso até atingir seus objetivos.

## Estrutura do Projeto
O código-fonte principal está localizado em `app/src/main/java/planejadorfinanceiro/` e contém as seguintes classes:
- `App.java`: Ponto de entrada da aplicação.
- `Cliente.java`: Representa o usuário do sistema, armazenando informações pessoais, transações e metas.
- `GerenciadorFinanceiro.java`: Responsável por orquestrar as operações de criação, atualização e remoção de transações e metas.
- `Transacao.java`, `TransacaoFactory.java` e `TipoTransacao.java`: Gerenciam as transações financeiras (entradas e saídas).
- `Meta.java`: Representa uma meta financeira, permitindo acompanhar o progresso até o valor alvo.

## Como funciona
O sistema permite que um cliente registre suas transações financeiras e defina metas de economia ou investimento. As operações são realizadas por meio do `GerenciadorFinanceiro`, que manipula as listas de transações e metas do cliente.

## Observações
Este projeto é um exemplo acadêmico e pode ser expandido para incluir funcionalidades como interface gráfica, persistência de dados, autenticação, entre outros. 