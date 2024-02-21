# spring-cloud-stream-kafka-transaction

- para colocar uma produção de mensagem de forma transacional, devemos fornecer:
  - transaction-id-prefix 
  - bean de transaçao do kafka, se não ele criará uma transaçao por evento enviado
  - consumidores com a configuração read_committed (padrão é read_uncomitted), ou seja, caso de alguma exception no produtor, a transação será revertida e o consumidor não receberá a mensagem
- lembrando que o @EnableTransactionManagemt procura os @transaction e no contexto do kafka, é fornecido pelo bean custom salientado acima
- outro ponto que a configuração spring.kafka.producer.transaction-id-prefix é para o spring kafka, ja para o cloud stream é este do project spring.cloud.stream.kafka.binder.transaction.transaction-id-prefix
- mais um ponto importante, que a transacao garante a entregua extamente uma vez da mensagem (idempotente)

## transacão jpa com transação kafka.
```
O gerenciador de transações JPA inicia uma nova transação JPA.
A operação do banco de dados começa, mas nenhum commit ocorre aqui, pois ainda estamos na execução do método.
A StreamBridge operação de envio aciona uma nova transação Kafka, sincronizando com a transação JPA por meio do gerenciador de sincronização de transações.
Quando o método termina, a transação JPA é confirmada primeiro, seguida pela transação Kafka.
```
obs:  ChainedTransactionManager (usado antigamente para mudar ordem  da transação kafka com jpa, não é sincronismo), está obsoleta.
- outro ponto, quando fornecemos um transactionManager customizado, o spring não fornece um JapTransactionManager por ex.
- para utilizar uma transação distribuida, no caso de um consumidor pegar o dado do kafka e persistir na base, use a function Consumer.
  - caso precise depois mandar a mensagem para outro topic, não use a function (perde a transação atomica), no final use um streamBridge 
 

## dlq
- quando o número máximo de tentativas do consumidor ultrapassa, o spring cloud stream abre uma nova transação, confirma o offset da mensagem (para o consumidor não receber mais)
- e após o mesmo envia ao um topic dlq especial

## extamente uma vez
- exemplo para garantir entrega uma vez para app usando kafka streams
```
spring:
  cloud:
    function:
      definition: messageProcessor
    stream:
      kafka:
        binder:
          transaction:
            transaction-id-prefix: tx-
          configuration:
            isolation.level: read_committed
            retries: 1
            acks: all
            processing.guarantee: exactly_once
```
- Ao definir processing.guaranteecomo exactly_once, o Kafka Streams garante que, se ocorrer uma exceção durante essas atividades ou se o aplicativo travar, toda a unidade  
  será revertida atomicamente como se nada tivesse acontecido
  