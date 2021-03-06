/*
 * Copyright 2017 the original author or authors.
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

package com.github.wonwoo.dynamodb.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.amazonaws.regions.Regions;

/**
 * @author wonwoo
 */
@ConfigurationProperties("spring.data.dynamodb")
public class DynamoProperties {

  /**
   * aws accessKey
   */
  private String accessKey;

  /**
   * aws secretKey
   */
  private String secretKey;

  /**
   * regions default AP_NORTHEAST_2
   */
  private Regions regions = Regions.AP_NORTHEAST_2;

  /**
   * dynamoDb Create Table readCapacityUnits default 10
   */
  private Long readCapacityUnits = 10L;

  /**
   * dynamoDb Create Table writeCapacityUnits default 10
   */
  private Long writeCapacityUnits = 10L;

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public Regions getRegions() {
    return regions;
  }

  public void setRegions(Regions regions) {
    this.regions = regions;
  }


  public void setReadCapacityUnits(Long readCapacityUnits) {
    this.readCapacityUnits = readCapacityUnits;
  }

  public void setWriteCapacityUnits(Long writeCapacityUnits) {
    this.writeCapacityUnits = writeCapacityUnits;
  }

  public Long getReadCapacityUnits() {
    return readCapacityUnits;
  }

  public Long getWriteCapacityUnits() {
    return writeCapacityUnits;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DynamoProperties that = (DynamoProperties) o;

    if (accessKey != null ? !accessKey.equals(that.accessKey) : that.accessKey != null) return false;
    if (secretKey != null ? !secretKey.equals(that.secretKey) : that.secretKey != null) return false;
    if (regions != that.regions) return false;
    if (readCapacityUnits != null ? !readCapacityUnits.equals(that.readCapacityUnits) : that.readCapacityUnits != null)
      return false;
    return writeCapacityUnits != null ? writeCapacityUnits.equals(that.writeCapacityUnits) : that.writeCapacityUnits == null;
  }

  @Override
  public int hashCode() {
    int result = accessKey != null ? accessKey.hashCode() : 0;
    result = 31 * result + (secretKey != null ? secretKey.hashCode() : 0);
    result = 31 * result + (regions != null ? regions.hashCode() : 0);
    result = 31 * result + (readCapacityUnits != null ? readCapacityUnits.hashCode() : 0);
    result = 31 * result + (writeCapacityUnits != null ? writeCapacityUnits.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "DynamoProperties{" +
        "accessKey='" + accessKey + '\'' +
        ", secretKey='" + secretKey + '\'' +
        ", regions=" + regions +
        ", readCapacityUnits=" + readCapacityUnits +
        ", writeCapacityUnits=" + writeCapacityUnits +
        '}';
  }
}