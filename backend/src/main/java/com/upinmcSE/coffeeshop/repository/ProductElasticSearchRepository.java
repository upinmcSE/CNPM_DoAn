package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductElasticSearchRepository extends ElasticsearchRepository<ProductDocument, String> {
}
