# spring-cloud-stream-kafka-transaction

- para colocar uma produção de mensagem de forma transacional, devemos fornecer:
  - transaction-id-prefix 
  - bean de transaçao do kafka, se não ele criará uma transaçao por evento enviado
- lembrando que o @EnableTransactionManagemt procura os @transaction e no contexto do kafka, é fornecedor pelo bean custom salientado acima
- outro ponto que a configuração spring.kafka.producer.transaction-id-prefix e para o spring kafka, para o cloud stream e este do project spring.cloud.stream.kafka.binder.transaction.transaction-id-prefix