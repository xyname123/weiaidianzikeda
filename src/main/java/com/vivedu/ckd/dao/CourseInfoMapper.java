package com.vivedu.ckd.dao;

import com.vivedu.ckd.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseInfoMapper {



    List<CourseInfo> findCourseByUserId(String id);

    List<CourseInfo> findCourseByUserIdPage(@Param(value = "userId")String userId, @Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize);

    List<CourseInfo> findCourseByUserIdIf(@Param(value = "userId")String userId,@Param(value = "sort") String sort,@Param(value = "pageNum")Integer pageNum,@Param(value = "pageSize")Integer pageSize);

    List<CourseInfo> findCourseByKey(@Param(value = "courseName")String courseName, @Param(value = "pageNum")Integer pageNum, @Param(value = "pageSize")Integer pageSize);

    List<CourseInfo> findAllCourse( @Param(value = "pageNum")Integer pageNum, @Param(value = "pageSize")Integer pageSize);

    List<CourseInfo> findCourse(@Param(value = "id")Integer id,@Param(value = "pageNum")Integer pageNum, @Param(value = "pageSize")Integer pageSize);

    List<CourseInfo> findMarked(String courseId);

    List<T_SHARE_CDXT_YJS_JBXX> findT();

    void updateCourse(@Param("ids") List<CourseInfoFilm>  courseInfoFilmlist);

    void InsertCourse(@Param("courseInfoAilist")List<CourseInfoAiVo> courseInfoAilist);

    void InsertCourseMetel(@Param("courseInfoMetelList")List<CourseInfoMetel> courseInfoMetelList);

    TimesStatistics query(@Param("type")int type);

    int update(TimesStatistics timesStatistics);

    int addTimesStatistics(TimesStatistics timesStatistics);

    StatisticsModel getCountList();

    int updateStatisticsCount();

    List<CourseInfo> findCourseByfilmTop( @Param(value = "film")String film, @Param(value = "page")Integer page, @Param(value = "size")Integer size);

    List<CourseInfo> findRec(@Param(value = "pageNum")Integer pageNum,@Param(value = "pageSize")Integer pageSize);

    List<CourseInfo> recIndex(@Param(value = "pageNum")Integer pageNum,@Param(value = "pageSize")Integer pageSize);

    List<CourseInfo> findrquick(@Param(value = "pageNum")Integer pageNum, @Param(value = "pageSize")Integer pageSize);

    List<CourseInfo> findquickRec(@Param(value = "pageNum")Integer pageNum, @Param(value = "pageSize")Integer pageSize);

    List<CourseInfo> findquickZiyuan(@Param(value = "pageNum")Integer pageNum,@Param(value = "pageSize") Integer pageSize);

    List<CourseInfo> findCourseDatal(@Param(value = "id")Integer id,  @Param(value = "pageNum")Integer pageNum, @Param(value = "pageSize")Integer pageSize);

    List<CourseInfoFilm> findFilm(@Param("ghs")List<CourseInfoFilm> courseInfoFilmlist);

    void updateFilm(@Param("courseInfoFilmlistBen")List<CourseInfoFilm> courseInfoFilmlistBen);

    List<CourseInfoAiVo> findAi(@Param("findAiSql")List<CourseInfoAiVo> courseInfoAilist);

    void updateAi(@Param("updateAiSql")List<CourseInfoAiVo> courseInfoAilistBen);

    int findMete(@Param("courseInfoMetel")String courseInfoMetel);

    void updateMete(@Param("updateMeteSql")List<CourseInfoMetel> courseInfoMetelListBean);

    int findAiVo(@Param("id")String id);

    int findFilmT(@Param("id")String courseid);



    int getRecommendCourseListSize(@Param("isRec") Integer isRec, @Param("source") String source, @Param("courseName") String courseName);

    List<CourseInfo> getRecommendCourseList(@Param("isRec") Integer isRec, @Param("firstIndex") Integer firstIndex, @Param("pageSize") Integer pageSize, @Param("source") String source,@Param("courseName") String courseName);

    int updateRecommendCourse(@Param("isRec")Integer isRec, @Param("id")Integer id);

    List<CourseInfo> getRecommendCourseListL(@Param("firstIndex")Integer firstIndex, @Param("pageSize")Integer pageSize);

    int getRecommendCourseListK();

    int findCourseByUserIdNum(String userId);

    int findCourseByKeyNum(String courseName);

    int findAllCourseNum();
}
