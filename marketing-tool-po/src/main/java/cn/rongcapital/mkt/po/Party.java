package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class Party extends BaseQuery {

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 账户
     */
    private String accountName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Boolean gender;

    /**
     * 年龄
     */
    private Byte age;

    /**
     * 家庭地址
     */
    private String homeAddress;

    /**
     * 工作地址
     */
    private String workAddress;

    /**
     * 家庭情况
     */
    private String homeStatus;

    /**
     * 工作情况
     */
    private String workStatus;

    /**
     * 会员等级
     */
    private String memberLevel;

    /**
     * 手机
     */
    private Integer mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * QQ号
     */
    private Integer qq;

    /**
     * 微博号
     */
    private String weibo;

    /**
     * 订单总数
     */
    private Integer orderCount;

    /**
     * 订单总金额
     */
    private Long orderAmount;

    /**
     * 购物车商品数量
     */
    private Integer cartItemCount;

    /**
     * 收藏商品数量
     */
    private Integer favoriteItemCount;

    /**
     * 个人薪水
     */
    private Long salary;

    /**
     * 线下活动参加次数
     */
    private Integer offlineActivityAttendence;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress == null ? null : homeAddress.trim();
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress == null ? null : workAddress.trim();
    }

    public String getHomeStatus() {
        return homeStatus;
    }

    public void setHomeStatus(String homeStatus) {
        this.homeStatus = homeStatus == null ? null : homeStatus.trim();
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus == null ? null : workStatus.trim();
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel == null ? null : memberLevel.trim();
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public Integer getQq() {
        return qq;
    }

    public void setQq(Integer qq) {
        this.qq = qq;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo == null ? null : weibo.trim();
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getCartItemCount() {
        return cartItemCount;
    }

    public void setCartItemCount(Integer cartItemCount) {
        this.cartItemCount = cartItemCount;
    }

    public Integer getFavoriteItemCount() {
        return favoriteItemCount;
    }

    public void setFavoriteItemCount(Integer favoriteItemCount) {
        this.favoriteItemCount = favoriteItemCount;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Integer getOfflineActivityAttendence() {
        return offlineActivityAttendence;
    }

    public void setOfflineActivityAttendence(Integer offlineActivityAttendence) {
        this.offlineActivityAttendence = offlineActivityAttendence;
    }

    @Override
    public String toString() {
        return "Party [id=" + id + ", accountName=" + accountName + ", name=" + name + ", gender="
                        + gender + ", age=" + age + ", homeAddress=" + homeAddress
                        + ", workAddress=" + workAddress + ", homeStatus=" + homeStatus
                        + ", workStatus=" + workStatus + ", memberLevel=" + memberLevel
                        + ", mobile=" + mobile + ", email=" + email + ", wechat=" + wechat + ", qq="
                        + qq + ", weibo=" + weibo + ", orderCount=" + orderCount + ", orderAmount="
                        + orderAmount + ", cartItemCount=" + cartItemCount + ", favoriteItemCount="
                        + favoriteItemCount + ", salary=" + salary + ", offlineActivityAttendence="
                        + offlineActivityAttendence + "]";
    }

}
