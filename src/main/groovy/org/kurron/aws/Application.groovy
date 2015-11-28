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

import com.amazonaws.services.sqs.AmazonSQSAsync
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory
import org.springframework.context.annotation.Bean

/**
 * Main driver and configuration for the application.
 */
@SpringBootApplication
@EnableConfigurationProperties( ApplicationProperties )
//@EnableContextInstanceData
//@ImportResource( locations = ['classpath:config/aws-context.xml'] )
class Application {

    /**
     * Called to start the entire application. Typically, java -jar foo.jar.
     * @param args any arguments to the program.
     */
    static void main( String[] args ) {
        SpringApplication.run( Application, args )
    }

/*
    @Value( '${ami-id:N/A}' )
    private String amiId

    @Value( '${hostname:N/A}' )
    private String hostname

    @Value( '${instance-type:N/A}' )
    private String instanceType

    @Value( '${services/domain:N/A}' )
    private String serviceDomain
*/

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory( AmazonSQSAsync amazonSqs ) {
        def factory = new SimpleMessageListenerContainerFactory()
        factory.amazonSqs = amazonSqs
        factory.autoStartup = true
        factory.maxNumberOfMessages = 5
        factory
    }

    @Bean
    QueueListener queueListener() {
        new QueueListener()
    }
}
