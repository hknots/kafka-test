package no.fintlabs.kafka;

import no.fintlabs.PrivilageDetail;
import no.fintlabs.SecurityService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicNameParameters;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
public class PrivilageDetailKafkaConsumer {

    private final EntityTopicNameParameters entityTopicNameParameters;
    private final EntityConsumerFactoryService entityConsumerFactoryService;
    private final SecurityService securityService;

    public PrivilageDetailKafkaConsumer(EntityConsumerFactoryService entityConsumerFactoryService,
                                        SecurityService securityService) {
        this.securityService = securityService;
        this.entityTopicNameParameters = EntityTopicNameParameters
                .builder()
                .orgId("fintlabs.no")
                .domainContext("fint-core")
                .resource("access.control.privilage.detail")
                .build();
        this.entityConsumerFactoryService = entityConsumerFactoryService;
        entityConsumerFactoryService
                .createFactory(PrivilageDetail.class, this::consumeRecord)
                .createContainer(entityTopicNameParameters);
    }

    private void consumeRecord(ConsumerRecord<String, PrivilageDetail> consumerRecord) {
        securityService.addPrivilageDetail(consumerRecord.value());
    }

}
