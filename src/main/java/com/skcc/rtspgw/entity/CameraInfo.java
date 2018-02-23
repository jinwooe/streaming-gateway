package com.skcc.rtspgw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLOUD_CCTV")
public class CameraInfo {
    @Id
    private Long cameraId;

    @Column(name = "SEG_ID")
    private String segmentId;

    @Column(name = "FLR_ID")
    private String floorId;

    @Column(name = "ZONE_ID")
    private String zoneId;

    @Column(name = "ORG_CD")
    private String orgCode;

    @Column(name = "SITE_ID")
    private String siteId;

    @Column(name = "CCTV_NM")
    private String cctvName;

    @Column(name = "RTSP_URL")
    private String rtspUrl;

    @Column(name = "RTSP_ID")
    private String rtspUserId;

    @Column(name = "RTSP_PW")
    private String rtspPassword;

    @Column(name = "DESCRIPTION")
    private String description;

    public Long getCameraId() {
        return cameraId;
    }

    public void setCameraId(Long cameraId) {
        this.cameraId = cameraId;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getCctvName() {
        return cctvName;
    }

    public void setCctvName(String cctvName) {
        this.cctvName = cctvName;
    }

    public String getRtspUrl() {
        return rtspUrl;
    }

    public void setRtspUrl(String rtspUrl) {
        this.rtspUrl = rtspUrl;
    }

    public String getRtspUserId() {
        return rtspUserId;
    }

    public void setRtspUserId(String rtspUserId) {
        this.rtspUserId = rtspUserId;
    }

    public String getRtspPassword() {
        return rtspPassword;
    }

    public void setRtspPassword(String rtspPassword) {
        this.rtspPassword = rtspPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
