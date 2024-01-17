## Rick and Morty Characters App
## Descrição

O Rick and Morty Characters App é um aplicativo Android desenvolvido em Kotlin, que utiliza a arquitetura MVVM com Jetpack Compose para a criação de uma interface de usuário moderna e dinâmica. O aplicativo consome dados da API de Rick and Morty para exibir uma lista paginada de personagens, permitindo filtrar e visualizar detalhes específicos de cada um.

## App
https://github.com/verissteph/rickandmortyapi/assets/57150624/17aa5f8b-a02f-426c-8431-340041e57d2a


## Tecnologias Utilizadas

Linguagem: Kotlin

Arquitetura: MVVM (Model-View-ViewModel)

Biblioteca de UI: Jetpack Compose

Injeção de Dependência: Koin

Comunicação com a API: Retrofit

Testes Unitários: JUnit, MockK, Koin-Test

Navegação: Voyager

Suporte a rotação de tela

## Funcionalidades
Abaixo estão descritas as funcionalidades encontradas no app
## Listagem de Personagens

Exibe uma lista paginada de personagens de Rick and Morty.
## Detalhes do Personagem

Permite visualizar detalhes individuais de cada personagem, incluindo informações como status, espécie, e nome.
## Filtragem

Oferece a capacidade de filtrar os personagens por nome e status, proporcionando uma experiência de busca personalizada.

## Estrutura do Projeto
app: Contém a lógica principal do aplicativo, incluindo as telas Compose, ViewModel e integração com a API.

data: Responsável pela camada de dados, contendo os modelos, repositórios e interfaces de comunicação com a API.

di: Configuração dos módulos Koin para injeção de dependência.

## Executando o Projeto
Para executar o projeto, certifique-se de ter o Android Studio instalado. Clone este repositório, abra o projeto no Android Studio e execute em um emulador ou dispositivo físico.

## Features Futuras e Melhorias

Favoritos: Adicionar a capacidade de marcar personagens como favoritos.

Suporte a Dark Mode: Implementar suporte ao modo escuro.

Cache de Imagens: Otimizar o carregamento de imagens com um sistema de cache.

Melhorias na Interface do Usuário: Aprimorar a experiência visual com animações e feedbacks.

Testes Instrumentados e de Integração: Expandir a cobertura de testes.

Paginação: Utilizar o Paging 3.0

