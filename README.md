# Desafio Intelipost

1) Imagine que hoje tenhamos um sistema de login e perfis de usuários. O sistema conta com mais de 10 milhões de usuários, sendo que temos um acesso concorrente de cerca de 5 mil usuários. Hoje a tela inicial do sistema se encontra muito lenta. Nessa tela é feita uma consulta no banco de dados para pegar as informações do usuário e exibi-las de forma personalizada. Quando há um pico de logins simultâneos, o carregamento desta tela fica demasiadamente lento. Na sua visão, como poderíamos iniciar a busca pelo problema, e que tipo de melhoria poderia ser feita?

Tendo em vista que o problema ocorre quando há um pico de usuários simultâneos, a melhor forma de identificar o problema seria reproduzi-lo em um ambiente de homologação utilizando ferramentas que possibilitam fazer teste de stress como jmeter (http://jmeter.apache.org/) ou tsung (http://tsung.erlang-projects.org/) junto a um profiling na aplicação utilizando ferramentas de profiling como jProfiler (https://www.ej-technologies.com/products/jprofiler/overview.html) ou jProbe (http://www.javaperformancetuning.com/tools/jprobe/), dessa forma conseguimos analisar:

    * Tempo de execução de métodos, e assim conseguir localizar e corrigir possíveis gargalos de desempenho e até mesmo detectar lentidão em consultas ao banco de dados para uma possível melhoria em queries, indexação em tabelas, e identificar overload no banco de dados;
    * Frequência de execução do GC, para determinar o melhor GC a ser utilizado na aplicação;
    * Alocação de objetos e referências, para encontrar e corrigir possíveis vazamentos de memória;
    * Alocação e sincronização de Threads, para encontrar problemas de concorrência e bloqueio em acesso a dados.

Após executar as análises e as possíveis correções pode-se avaliar o uso de tecnologias na aplicação como:

    * Substituição de JPA/Hibernate por jdbc para  melhoria de desempenho;
    * Adoção de um pool de conexão para que a aplicação não tenha o custo de ficar abrindo conexão sempre que for necessário realizar querie, nesse ponto recomendo a utilização do HikariCP pelo excelente desempenho (https://github.com/brettwooldridge/HikariCP-benchmark);
    * Utilização de arquivos estáticos em cdn para reduzir requisições na aplicação e maximizar o cache do conteúdo no client;
    * Adoção de framework de cache (Redis ou Caffeine) para reduzir o overload no banco de dados e melhorar a performance na consulta a dados.
    * Configuração em parâmetros da JVM para otimizar a utilização de recursos.

Mesmo após as melhorias e correções o aumento de usuários simultâneos ainda pode causar overload na aplicação, sendo necessário escalar de forma vertical (melhorar recurso da máquina onde a aplicação é hospedada) ou horizontal (adicionar mais nós da aplicação e utilizar balanceador de carga para distribuir os acessos entre os nós).

2) Com base no problema anterior, gostaríamos que você codificasse um novo sistema de login para muitos usuários simultâneos e carregamento da tela inicial. Lembre-se que é um sistema web então teremos conteúdo estático e dinâmico. Leve em consideração também que na empresa existe um outro sistema que também requisitará os dados dos usuários, portanto, este sistema deve expor as informações para este outro sistema de alguma maneira.

## A Solução
#### Técnologias utilizadas
   * Java 8
   * Maven
   * Springboot 2
   * HikariCP connection pool
   * Spring JDBC
   * Spring Cache Redis
   * Spring Session (Redis)
   * Thymeleaf
   * Spring Cloud Netflix Eureka
   * Spring Cloud Netflix Ribbon (Load Balancer)
   
A solução foi dividida em 3 módulos:
### eureka-service
   Módulo de discovery para gerenciar os nós do serviço de usuário.
### user-service
   Módulo de micro-serviço de usuário para expor os dados via Rest API. O módulo pode ser escalado de forma horizontal de acordo com a necessidade de atendar a alta demanda, o serviço possui uma camada de cache para diminuir o overload no banco de dados. O serviço pode ser consumido por outros sistemas utilizando Basic Authorization (username: Intelipost | password: 0e16f2217c24791f17e62de0e907a312). (https://github.com/rafael-bertoli/job-backend-developer/blob/desafio/Intelipost-UserService.postman_collection.json)
### web-front: 
   Módulo de login, este módulo realiza integração com o serviço de usuário utilizando ribbon load balancer para distribuir a carga entre os nós do serviço. O módulo conta também com spring session redis para que possa ser escalado conforme a necessidade sem ter problemas de sessão.
   O módulo pode ser acessado utilizando as seguintes credenciais:
```
login: admin@system.com
senha: 123456

login: user@system.com
senha: 123456

login: operator@system.com
senha: 123456
```

### Deploy
Faça download dos arquivos (eureka-service.jar, user-service.jar e web-front.jar) do último release (https://github.com/rafael-bertoli/job-backend-developer/releases/tag/untagged-c35ab3c40706d63b785a)
#### Iniciar módulo eureka-server
```
$ java -jar eureka-service.jar
```
O módulo pode ser acesso em http://localhost:8401/
#### Iniciar módulo user-service
* Para utilizar o serviço com h2db (banco de dados em memória) e redis server embedded:
```
$ java -jar user-service.jar --server.port=8301 --spring.redis.port=6301 -spring.redis.embedded=true
```
* Para utilizar o serviço com mysql e redis server externo:
```
$ java -Dspring.profiles.active=production -jar user-service.jar --server.port=8301 --spring.redis.port=6301 --db.url=localhost --db.port=3306 --db.schema=intelipost --db.user=root --db.pass=root --spring.redis.embedded=false --spring.redis.host=localhost --spring.redis.password=1234
```
A documentação do serviço pode ser acessada em http://localhost:8301/swagger-ui.html
#### Iniciar módulo web-front
```
$ java -jar web-front.jar --server.port=8501 --spring.redis.port=6501 --spring.redis.host=localhost --spring.redis.embedded=true
```
O módulo pode ser acessado em http://localhost:8501/
