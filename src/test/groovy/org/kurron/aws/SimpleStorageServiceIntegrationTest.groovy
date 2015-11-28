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

import org.kurron.categories.InputStreamEnhancements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.core.io.ResourceLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * An example showing how we can manage S3 resources.
 **/
@ContextConfiguration( loader = SpringApplicationContextLoader, classes = [Application] )
class SimpleStorageServiceIntegrationTest extends Specification {

    @Autowired
    private ResourceLoader resourceLoader

    def 'exercise download'() {
        given: 'an existing resource'
        def uri = 's3://org-kurron-spring-aws/batman.jpg'

        when: 'we download it'
        def resource = resourceLoader.getResource( uri )
        assert resource

        then: 'it matches the well-known fingerprint'
        def stream = resource.getInputStream()
        def hash = use( InputStreamEnhancements ) { ->
            stream.toMD5()
        }
        hash == '883f832e1e4d452b549cf29252ad4821'
    }
}
