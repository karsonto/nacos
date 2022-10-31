/*
 * Copyright 1999-2021 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.plugin.discovery.configuration;

import com.alibaba.nacos.plugin.discovery.HttpPluginServiceManager;
import com.alibaba.nacos.plugin.discovery.filter.PluginCharacterEncodingFilter;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Http plugin configuration.
 *
 * @author karsonto
 */
@Configuration
public class HttpPluginConfiguration {
    
    private final Encoding properties;
    
    public HttpPluginConfiguration(ServerProperties properties) {
        this.properties = properties.getServlet().getEncoding();
    }
    
    @Bean
    HttpPluginServiceManager httpServiceManager() {
        return new HttpPluginServiceManager();
    }
    
    @Bean
    public CharacterEncodingFilter characterEncodingFilter(HttpPluginServiceManager httpServiceManager) {
        CharacterEncodingFilter filter = new PluginCharacterEncodingFilter(httpServiceManager);
        filter.setEncoding(this.properties.getCharset().name());
        filter.setForceRequestEncoding(this.properties.shouldForce(org.springframework.boot.web.servlet.server.Encoding.Type.REQUEST));
        filter.setForceResponseEncoding(this.properties.shouldForce(org.springframework.boot.web.servlet.server.Encoding.Type.RESPONSE));
        return filter;
    }
}
