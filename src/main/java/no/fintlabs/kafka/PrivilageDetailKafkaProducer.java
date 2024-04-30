package no.fintlabs.kafka;

import no.fintlabs.PrivilageDetail;
import no.fintlabs.kafka.entity.EntityProducer;
import no.fintlabs.kafka.entity.EntityProducerFactory;
import no.fintlabs.kafka.entity.EntityProducerRecord;
import no.fintlabs.kafka.entity.topic.EntityTopicNameParameters;
import org.springframework.stereotype.Service;

@Service
public class PrivilageDetailKafkaProducer {

    private final EntityProducerFactory entityProducerFactory;
    private final EntityProducer<PrivilageDetail> entityProducer;
    private final EntityTopicNameParameters entityTopicName;

    public PrivilageDetailKafkaProducer(EntityProducerFactory entityProducerFactory) {
        this.entityProducerFactory = entityProducerFactory;
        this.entityProducer = entityProducerFactory.createProducer(PrivilageDetail.class);
        entityTopicName = EntityTopicNameParameters
                .builder()
                .orgId("fintlabs.no")
                .domainContext("fint-core")
                .resource("access.control.privilage.detail")
                .build();
    }

    public void sendPrivilageDetail(PrivilageDetail privilageDetail) {
        entityProducer.send(
                EntityProducerRecord.<PrivilageDetail>builder()
                        .topicNameParameters(entityTopicName)
                        .value(privilageDetail)
                        .build()
        );
    }

}
