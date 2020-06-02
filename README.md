
# IBM - Teste técnico #1

### Requisição inicial
A requisição inicial passa como parâmetro page=1, por padrão. Dela são extraídos valores como total de resultados, para inicialização do Array **titles**, página atual (igual a 1) e total de páginas, o que determina as próximas iterações.
### Estrutura de repetição
Dentro de uma estrutura WHILE, executada enquanto a página atual for menor ou igual ao total de páginas, são feitas as requisições das respectivas páginas, exceto a 1ª, que já temos o seu resultado.
Cada iteração navega nos títulos retornados pela requisição e atualiza o Array **titles** com cada título.
### Ordenação
Com o Array **titles** completo, é feita uma ordenação comum, de complexidade n*n.
### Retorno
É feita a navegação pelo Array já ordenado e a impressão de cada item na tela.
### Desafios encontrados
O fato de o endpoint especificado ser HTTPS (com certificado self-signed) me exigiu atenção especial, pela pouca experiência com certificados no Java. Optei por não desabilitar a validação SSL por não ser uma boa prática em uma situação real.