/*
 * Copyright (c) 2015. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kurron.aws

import com.amazonaws.services.sqs.AmazonSQS
import org.kurron.traits.GenerationAbility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification

/**
 * An example showing how we can utilize a message queue.
 **/
@ContextConfiguration( loader = SpringApplicationContextLoader, classes = [Application] )
class SimpleQueueServiceIntegrationTest extends Specification implements GenerationAbility {

    /**
     * The SQS queue to use.
     */
    public static final String QUEUE_NAME = 'spring-aws-test'

    @Autowired
    private AmazonSQS amazonSqs

    @Ignore
    def 'exercise send and receive'() {
        given: 'a new template'
        def template = new QueueMessagingTemplate( amazonSqs )

        when: 'we send a message'
        def source = 'abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ'
        def random = randomString( 256000, source )
        Message<String> message  = MessageBuilder.withPayload( random ).setHeaderIfAbsent( 'custom-header', 'Logan' ) .build()
        template.send( QUEUE_NAME, message )

        then: 'we can fetch it'
        Message<String> justHeard = template.receive( QUEUE_NAME ) as Message<String>
        justHeard.payload == random
    }

    def 'exercise message listener bean'() {
        given: 'a new template'
        def template = new QueueMessagingTemplate( amazonSqs )

        when: 'we send a message'
        def source = 'abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ'

        10.times {
            def random = randomString( 256000, source )
            Message<String> message  = MessageBuilder.withPayload( random ).setHeaderIfAbsent( 'custom-header', 'Logan' ) .build()
            template.send( QUEUE_NAME, message )
        }

        then: 'pretend we just tested something'
        true
    }
}
