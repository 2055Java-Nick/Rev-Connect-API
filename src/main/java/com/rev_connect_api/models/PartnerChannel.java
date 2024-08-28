package com.rev_connect_api.models;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "partner_channels")
public class PartnerChannel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long businessUserId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "url", nullable = false)
  @URL(message = "Invalid URL format for partner channel: ${validatedValue}")
  private String url;

  public PartnerChannel() {
  }

  public PartnerChannel(Long businessUserId, String name, String url) {
    this.businessUserId = businessUserId;
    this.name = name;
    this.url = url;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getBusinessUserId() {
    return businessUserId;
  }

  public void setBusinessUserId(Long businessUserId) {
    this.businessUserId = businessUserId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    PartnerChannel that = (PartnerChannel) o;

    if (id != null ? !id.equals(that.id) : that.id != null)
      return false;
    if (businessUserId != null ? !businessUserId.equals(that.businessUserId) : that.businessUserId != null)
      return false;
    if (name != null ? !name.equals(that.name) : that.name != null)
      return false;
    return url != null ? url.equals(that.url) : that.url == null;
  }

  @Override
  public String toString() {
    return "PartnerChannel{" +
        "id=" + id +
        ", businessUserId=" + businessUserId +
        ", name='" + name + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
