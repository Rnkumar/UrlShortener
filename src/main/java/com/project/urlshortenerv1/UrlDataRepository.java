package com.project.urlshortenerv1;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlDataRepository extends MongoRepository<UrlDataHolder,String> {
    UrlDataHolder findBy_id(ObjectId _id);
}
