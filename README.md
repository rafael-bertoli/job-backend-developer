# Desafio Intelipost

1) Imagine que hoje tenhamos um sistema de login e perfis de usuários. O sistema conta com mais de 10 milhões de usuários, sendo que temos um acesso concorrente de cerca de 5 mil usuários. Hoje a tela inicial do sistema se encontra muito lenta. Nessa tela é feita uma consulta no banco de dados para pegar as informações do usuário e exibi-las de forma personalizada. Quando há um pico de logins simultâneos, o carregamento desta tela fica demasiadamente lento. Na sua visão, como poderíamos iniciar a busca pelo problema, e que tipo de melhoria poderia ser feita?

Tendo em vista que o problema ocorre quando há um pico de usuários simultâneos, a melhor forma de identificar o problema seria reproduzir a situação em um ambiente de homologação utilizando ferramentas que possibilitam fazer teste de stress como jmeter (http://jmeter.apache.org/) ou tsung (http://tsung.erlang-projects.org/) junto a um profiling na aplicação utilizando ferramentas de profiling como jProfiler (https://www.ej-technologies.com/products/jprofiler/overview.html) ou jProbe (http://www.javaperformancetuning.com/tools/jprobe/), dessa forma conseguimos analisar:

    * Tempo de execução de métodos, e assim conseguir localizar e corrigir possíveis gargalos de desempenho e até mesmo detectar lentidão em consultas ao banco de dados para uma possível melhoria em queries, indexação em tabelas, e identificar overload no banco de dados;
    * Frequência de execução do GC, para determinar o melhor GC a ser utilizado na aplicação;
    * Alocação de objetos e referências, para encontrar e corrigir possíveis vazamentos de memória;
    * Alocação e sincronização de Threads, para encontrar problemas de concorrência e bloqueio em acesso a dados.

Após executar as análises e as possíveis correções pode-se avaliar o uso de tecnologias na aplicação como:

    * Substituição de JPA/Hibernate por jdbc para  melhoria de desempenho;
    * Adoção de um pool de conexão para que a aplicação não tenha o custo de ficar abrindo conexão sempre que for necessário realizar querie, nesse ponto recomendo a utilização do HikariCP pelo excelente desempenho (https://github.com/brettwooldridge/HikariCP-benchmark);
    * Utilização de arquivos estáticos em cdn para reduzir requisições na aplicação e maximizar o cache do conteúdo no client;
    * Adoção de framework de cache (Redis ou Caffeine) para reduzir o overload no banco de dados e melhorar a performance na consulta a dados.

Mesmo após as melhorias e correções o aumento de usuários simultâneos ainda pode causar overload na aplicação, sendo necessário escalar de forma vertical (melhorar recurso da maquina onde a aplicação é hospedada) ou horizontal (adicionar mais nós da aplicação e utilizar balanceador de carga para distribuir os acessos entre os nós).

2) Com base no problema anterior, gostaríamos que você codificasse um novo sistema de login para muitos usuários simultâneos e carregamento da tela inicial. Lembre-se que é um sistema web então teremos conteúdo estático e dinâmico. Leve em consideração também que na empresa existe um outro sistema que também requisitará os dados dos usuários, portanto, este sistema deve expor as informações para este outro sistema de alguma maneira.


