/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.apm.plugin.spring.cloud.gateway.v412x;

import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.plugin.spring.cloud.gateway.v4x.define.EnhanceObjectCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.netty.http.client.HttpClientConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpClientFinalizerConstructorV412InterceptorTest {

    private static final String URI = "http://localhost:8080/get";
    private final EnhancedInstance enhancedInstance = new EnhancedInstance() {
        private EnhanceObjectCache enhanceObjectCache;

        @Override
        public Object getSkyWalkingDynamicField() {
            return enhanceObjectCache;
        }

        @Override
        public void setSkyWalkingDynamicField(Object value) {
            this.enhanceObjectCache = (EnhanceObjectCache) value;
        }
    };
    private HttpClientConfig httpClientConfig;
    private HttpClientFinalizerConstructorV412Interceptor httpClientFinalizerConstructorInterceptor;

    @Before
    public void setUp() {
        httpClientConfig = mock(HttpClientConfig.class);
        when(httpClientConfig.uri()).thenReturn(URI);
        httpClientFinalizerConstructorInterceptor = new HttpClientFinalizerConstructorV412Interceptor();
    }

    @Test
    public void onConstruct() {
        httpClientFinalizerConstructorInterceptor.onConstruct(enhancedInstance, new Object[]{httpClientConfig});
        final EnhanceObjectCache enhanceCache = (EnhanceObjectCache) enhancedInstance.getSkyWalkingDynamicField();
        assertNotNull(enhanceCache);
        assertEquals(enhanceCache.getUrl(), URI);
    }
}
