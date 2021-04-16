## Estrutura das pastas

O workspace possui as pastas padrão:
    - `src`: Pasta que contém os códigos-fonte.
    - `lib`: Pasta que contém as dependências.

A pasta src possui a pasta classes e esta, por sua vez possui outras 2 pastas:
    - `controladores`: Pasta que contém os códigos de códigos de controle (setar as partidas, ler o log).
    - `dependencias`: Pasta que contém os objetos usados no projeto.


## Regras para setar as partidas

No código consideramos que quando um player se desconecta os dados, id e quantidade de kills, dele são perdidos.