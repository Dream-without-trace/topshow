package com.luwei.services.course.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @program: topshow
 * @description: cms课程订单
 * @author: ZhangHongJie
 * @create: 2018-12-11 23:58
 **/

@ApiModel
public class CourseOrderCMSPageVo {


    @ApiModelProperty("报名课程ID")
    private Integer courseEnrolmentId;

    @ApiModelProperty("用户头像")
    private String avatarUrl;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("课程名称")
    private String title;

    @ApiModelProperty("课程日期")
    private Long startDate;

    @ApiModelProperty("上课时间")
    private String startTime;

    @ApiModelProperty("下课时间")
    private String endTime;

    @ApiModelProperty("封面")
    private String picture;

    @ApiModelProperty("是否已验票 ：1，未验票，2，已验票")
    private Integer isInspectTicket;

    @ApiModelProperty("下单时间")
    private Date createTime;

    public CourseOrderCMSPageVo() {
    }

    public CourseOrderCMSPageVo(Integer courseEnrolmentId, String avatarUrl, String nickname, String phone, String title,
                                Long startDate, String startTime, String endTime, String picture, Integer isInspectTicket,
                                Date createTime) {
        this.courseEnrolmentId = courseEnrolmentId;
        this.avatarUrl = avatarUrl;
        this.nickname = nickname;
        this.phone = phone;
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.picture = picture;
        this.isInspectTicket = isInspectTicket;
        this.createTime = createTime;
    }

    public Integer getCourseEnrolmentId() {
        return courseEnrolmentId;
    }

    public void setCourseEnrolmentId(Integer courseEnrolmentId) {
        this.courseEnrolmentId = courseEnrolmentId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        if (startDate != null && startDate >0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(new Date(startDate*1000));
        }else{
            return "";
        }
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getIsInspectTicket() {
        return isInspectTicket;
    }

    public void setIsInspectTicket(Integer isInspectTicket) {
        this.isInspectTicket = isInspectTicket;
    }

    public String getCreateTime() {
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(createTime);
        }
        return "";
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
