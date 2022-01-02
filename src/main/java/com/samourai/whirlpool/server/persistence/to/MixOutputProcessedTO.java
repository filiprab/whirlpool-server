package com.samourai.whirlpool.server.persistence.to;

import com.samourai.whirlpool.server.persistence.to.shared.EntityCreatedTO;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "mixOutputProcessed")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"address"})})
public class MixOutputProcessedTO extends EntityCreatedTO {
  private String address;

  public MixOutputProcessedTO() {}

  public MixOutputProcessedTO(String address) {
    this.address = address;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
