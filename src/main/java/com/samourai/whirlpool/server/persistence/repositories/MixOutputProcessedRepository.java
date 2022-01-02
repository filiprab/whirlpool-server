package com.samourai.whirlpool.server.persistence.repositories;

import com.samourai.whirlpool.server.persistence.to.MixOutputProcessedTO;
import com.samourai.whirlpool.server.persistence.to.MixOutputTO;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface MixOutputProcessedRepository extends CrudRepository<MixOutputProcessedTO, Long> {

  Optional<MixOutputTO> findByAddress(String address);

  Collection<MixOutputTO> deleteByAddress(String addresses);
}
