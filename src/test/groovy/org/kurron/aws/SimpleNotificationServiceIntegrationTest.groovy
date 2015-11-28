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

import com.amazonaws.services.sns.AmazonSNS
import org.kurron.traits.GenerationAbility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * An example showing how we can utilize a message topic.
 **/
@ContextConfiguration( loader = SpringApplicationContextLoader, classes = [Application] )
class SimpleNotificationServiceIntegrationTest extends Specification implements GenerationAbility {

    /**
     * The SQS queue to use.
     */
    public static final String TOPIC_NAME = 'spring-aws-test'

    @Autowired
    private AmazonSNS amazonSns

    def 'exercise send and receive'() {
        given: 'a new template'
        def template = new NotificationMessagingTemplate( amazonSns )

        when: 'we send a message'
        def source = 'abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ'
        def message = randomString( 256, source )
        template.sendNotification( TOPIC_NAME, message, 'Spring AWS Test' )

        then: 'pretend we can fetch it'
        true
    }
}
