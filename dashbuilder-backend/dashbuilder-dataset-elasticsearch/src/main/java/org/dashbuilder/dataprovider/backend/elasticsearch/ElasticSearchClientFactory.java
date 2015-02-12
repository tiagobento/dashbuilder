/**
 * Copyright (C) 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.dataprovider.backend.elasticsearch;

import org.dashbuilder.dataprovider.backend.elasticsearch.rest.client.ElasticSearchClient;
import org.dashbuilder.dataprovider.backend.elasticsearch.rest.client.impl.jest.ElasticSearchJestClient;
import org.dashbuilder.dataset.def.ElasticSearchDataSetDef;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ElasticSearchClientFactory {

    public ElasticSearchClient newClient() {
        return new ElasticSearchJestClient();
    }
    
    public ElasticSearchClient newClient(ElasticSearchDataSetDef elasticSearchDataSetDef) {
        ElasticSearchClient client = newClient();
        return configure(client, elasticSearchDataSetDef);
    }
    
    public static ElasticSearchClient configure(ElasticSearchClient client, ElasticSearchDataSetDef elasticSearchDataSetDef) {
        String serverURL = elasticSearchDataSetDef.getServerURL();
        String clusterName = elasticSearchDataSetDef.getClusterName();
        if (serverURL == null || serverURL.trim().length() == 0) throw new IllegalArgumentException("Server URL is not set.");
        if (clusterName == null || clusterName.trim().length() == 0) throw new IllegalArgumentException("Cluster name is not set.");

        client.serverURL(serverURL).clusterName(clusterName);

        String[] indexes = elasticSearchDataSetDef.getIndex();
        if (indexes != null && indexes.length > 0) client.index(indexes);
        String[] types  = elasticSearchDataSetDef.getType();
        if (types != null && types.length > 0) client.type(types);
        
        return client;
    }
    
}