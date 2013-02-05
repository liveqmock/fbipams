package pams.repository.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SvpsarchivectlExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    public SvpsarchivectlExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andArchivemonthIsNull() {
            addCriterion("ARCHIVEMONTH is null");
            return (Criteria) this;
        }

        public Criteria andArchivemonthIsNotNull() {
            addCriterion("ARCHIVEMONTH is not null");
            return (Criteria) this;
        }

        public Criteria andArchivemonthEqualTo(String value) {
            addCriterion("ARCHIVEMONTH =", value, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchivemonthNotEqualTo(String value) {
            addCriterion("ARCHIVEMONTH <>", value, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchivemonthGreaterThan(String value) {
            addCriterion("ARCHIVEMONTH >", value, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchivemonthGreaterThanOrEqualTo(String value) {
            addCriterion("ARCHIVEMONTH >=", value, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchivemonthLessThan(String value) {
            addCriterion("ARCHIVEMONTH <", value, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchivemonthLessThanOrEqualTo(String value) {
            addCriterion("ARCHIVEMONTH <=", value, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchivemonthLike(String value) {
            addCriterion("ARCHIVEMONTH like", value, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchivemonthNotLike(String value) {
            addCriterion("ARCHIVEMONTH not like", value, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchivemonthIn(List<String> values) {
            addCriterion("ARCHIVEMONTH in", values, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchivemonthNotIn(List<String> values) {
            addCriterion("ARCHIVEMONTH not in", values, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchivemonthBetween(String value1, String value2) {
            addCriterion("ARCHIVEMONTH between", value1, value2, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchivemonthNotBetween(String value1, String value2) {
            addCriterion("ARCHIVEMONTH not between", value1, value2, "archivemonth");
            return (Criteria) this;
        }

        public Criteria andArchiveyearIsNull() {
            addCriterion("ARCHIVEYEAR is null");
            return (Criteria) this;
        }

        public Criteria andArchiveyearIsNotNull() {
            addCriterion("ARCHIVEYEAR is not null");
            return (Criteria) this;
        }

        public Criteria andArchiveyearEqualTo(String value) {
            addCriterion("ARCHIVEYEAR =", value, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andArchiveyearNotEqualTo(String value) {
            addCriterion("ARCHIVEYEAR <>", value, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andArchiveyearGreaterThan(String value) {
            addCriterion("ARCHIVEYEAR >", value, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andArchiveyearGreaterThanOrEqualTo(String value) {
            addCriterion("ARCHIVEYEAR >=", value, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andArchiveyearLessThan(String value) {
            addCriterion("ARCHIVEYEAR <", value, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andArchiveyearLessThanOrEqualTo(String value) {
            addCriterion("ARCHIVEYEAR <=", value, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andArchiveyearLike(String value) {
            addCriterion("ARCHIVEYEAR like", value, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andArchiveyearNotLike(String value) {
            addCriterion("ARCHIVEYEAR not like", value, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andArchiveyearIn(List<String> values) {
            addCriterion("ARCHIVEYEAR in", values, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andArchiveyearNotIn(List<String> values) {
            addCriterion("ARCHIVEYEAR not in", values, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andArchiveyearBetween(String value1, String value2) {
            addCriterion("ARCHIVEYEAR between", value1, value2, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andArchiveyearNotBetween(String value1, String value2) {
            addCriterion("ARCHIVEYEAR not between", value1, value2, "archiveyear");
            return (Criteria) this;
        }

        public Criteria andOperidIsNull() {
            addCriterion("OPERID is null");
            return (Criteria) this;
        }

        public Criteria andOperidIsNotNull() {
            addCriterion("OPERID is not null");
            return (Criteria) this;
        }

        public Criteria andOperidEqualTo(String value) {
            addCriterion("OPERID =", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotEqualTo(String value) {
            addCriterion("OPERID <>", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidGreaterThan(String value) {
            addCriterion("OPERID >", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidGreaterThanOrEqualTo(String value) {
            addCriterion("OPERID >=", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidLessThan(String value) {
            addCriterion("OPERID <", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidLessThanOrEqualTo(String value) {
            addCriterion("OPERID <=", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidLike(String value) {
            addCriterion("OPERID like", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotLike(String value) {
            addCriterion("OPERID not like", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidIn(List<String> values) {
            addCriterion("OPERID in", values, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotIn(List<String> values) {
            addCriterion("OPERID not in", values, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidBetween(String value1, String value2) {
            addCriterion("OPERID between", value1, value2, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotBetween(String value1, String value2) {
            addCriterion("OPERID not between", value1, value2, "operid");
            return (Criteria) this;
        }

        public Criteria andOperdateIsNull() {
            addCriterion("OPERDATE is null");
            return (Criteria) this;
        }

        public Criteria andOperdateIsNotNull() {
            addCriterion("OPERDATE is not null");
            return (Criteria) this;
        }

        public Criteria andOperdateEqualTo(Date value) {
            addCriterion("OPERDATE =", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateNotEqualTo(Date value) {
            addCriterion("OPERDATE <>", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateGreaterThan(Date value) {
            addCriterion("OPERDATE >", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateGreaterThanOrEqualTo(Date value) {
            addCriterion("OPERDATE >=", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateLessThan(Date value) {
            addCriterion("OPERDATE <", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateLessThanOrEqualTo(Date value) {
            addCriterion("OPERDATE <=", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateIn(List<Date> values) {
            addCriterion("OPERDATE in", values, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateNotIn(List<Date> values) {
            addCriterion("OPERDATE not in", values, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateBetween(Date value1, Date value2) {
            addCriterion("OPERDATE between", value1, value2, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateNotBetween(Date value1, Date value2) {
            addCriterion("OPERDATE not between", value1, value2, "operdate");
            return (Criteria) this;
        }

        public Criteria andRecversionIsNull() {
            addCriterion("RECVERSION is null");
            return (Criteria) this;
        }

        public Criteria andRecversionIsNotNull() {
            addCriterion("RECVERSION is not null");
            return (Criteria) this;
        }

        public Criteria andRecversionEqualTo(Long value) {
            addCriterion("RECVERSION =", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionNotEqualTo(Long value) {
            addCriterion("RECVERSION <>", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionGreaterThan(Long value) {
            addCriterion("RECVERSION >", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionGreaterThanOrEqualTo(Long value) {
            addCriterion("RECVERSION >=", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionLessThan(Long value) {
            addCriterion("RECVERSION <", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionLessThanOrEqualTo(Long value) {
            addCriterion("RECVERSION <=", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionIn(List<Long> values) {
            addCriterion("RECVERSION in", values, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionNotIn(List<Long> values) {
            addCriterion("RECVERSION not in", values, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionBetween(Long value1, Long value2) {
            addCriterion("RECVERSION between", value1, value2, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionNotBetween(Long value1, Long value2) {
            addCriterion("RECVERSION not between", value1, value2, "recversion");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated do_not_delete_during_merge Wed Jun 01 16:06:26 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SVPSARCHIVECTL
     *
     * @mbggenerated Wed Jun 01 16:06:26 CST 2011
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}