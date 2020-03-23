package com.vivedu.ckd.dao;

import com.vivedu.ckd.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface CourseInfoMapper {



    List<CourseInfo> findCourseByUserId(@Param(value = "userid")String userid, @Param(value = "pageNum")Integer pageNum,@Param(value = "pageSize") Integer pageSize);

    List<CourseInfo> findCourseByUserIdPage(@Param(value = "userId")String userId, @Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize);

    List<CourseInfo> findCourseByUserIdIf(@Param(value = "userId")String userId,@Param(value = "sort") String sort,@Param(value = "pageNum")Integer pageNum,@Param(value = "pageSize")Integer pageSize);

    List<CourseInfo> findCourseByKey(@Param(value = "courseName") String courseName, @Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize, @Param(value = "state") Integer state, @Param(value = "courseTypeCode") Integer courseTypeCode);

    List<CourseInfo> findAllCourse(@Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize, @Param(value = "state") Integer state,@Param(value = "courseTypeCode") Integer courseTypeCode);

    List<CourseInfo> findCourse(@Param(value = "id")Integer id,@Param(value = "pageNum")Integer pageNum, @Param(value = "pageSize")Integer pageSize);

    List<CourseInfo> findMarked(String courseId);

    List<T_SHARE_CDXT_YJS_JBXX> findT();

    void updateCourse(@Param("courseInfoFilmlist") List<CourseInfoFilm>  courseInfoFilmlist);

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

    CourseInfo findCourseDatal(@Param(value = "id")Integer id);

    List<CourseInfoFilm> findFilm(@Param("ghs")List<CourseInfoFilm> courseInfoFilmlist);

    void updateFilm(@Param("courseInfoFilmlistBen")List<CourseInfoFilm> courseInfoFilmlistBen);

    List<CourseInfoAiVo> findAi(@Param("findAiSql")List<CourseInfoAiVo> courseInfoAilist);

    void updateAi(@Param("updateAiSql")List<CourseInfoAiVo> courseInfoAilistBen);

    int findMete(@Param("name")String name);

    void updateMete(@Param("updateMeteSql")List<CourseInfoMetel> courseInfoMetelListBean);

    int findAiVo(@Param("id")String id);

    int findFilmT(@Param("id")String courseid);


    int getRecommendCourseListSize(@Param("isRec") Integer isRec, @Param("source") String source, @Param("courseName") String courseName,@Param("startDate")String startDate,@Param("categoryID")String categoryID);

    List<CourseInfo> getRecommendCourseList(@Param("isRec") Integer isRec, @Param("firstIndex") Integer firstIndex, @Param("pageSize") Integer pageSize, @Param("source") String source,@Param("courseName") String courseName,@Param("startDate")String startDate,@Param("sortId")Integer sortId,@Param("categoryID")String categoryID);

    int getRecommendCourseListSize(@Param("isRec") Integer isRec, @Param("source") String source, @Param("courseName") String courseName);

    List<CourseInfo> getRecommendCourseList(@Param("isRec") Integer isRec, @Param("firstIndex") Integer firstIndex, @Param("pageSize") Integer pageSize, @Param("source") String source,@Param("courseName") String courseName);

    int updateRecommendCourse(@Param("isRec")Integer isRec, @Param("id")Integer id);

    List<CourseInfo> getRecommendCourseListL(@Param("firstIndex")Integer firstIndex, @Param("pageSize")Integer pageSize);

    int getRecommendCourseListK();

    int findCourseByUserIdNum(String userId);

    int findCourseByKeyNum(@Param("courseName")String courseName,@Param("state") Integer state,@Param("courseTypeCode") Integer courseTypeCode);

    int findAllCourseNum(@Param("state")Integer state,@Param("courseTypeCode")Integer courseTypeCode);

    List<CourseInfo> findCoursetopState(@Param("state")Integer state, @Param("page")Integer page, @Param("size")Integer size);


    int findCoursetopStateT(@Param("state")Integer state);

    LessonsLearned queryLessonsLearned(@Param("studentID") BigInteger studentID, @Param("courseId") Integer courseId);

    int updateLessonsLearned(LessonsLearned lessonsLearned);

    int addLessonsLearned(LessonsLearned lessonsLearned);

    StudyDuration queryStudyDuration(@Param("studentID") String studentID);

    int updateStudyDuration(StudyDuration studyDuration);

    int addStudyDuration(StudyDuration studyDuration);

    int queryCourseCount(@Param("userId") String userId,@Param("startDate") String startDate,@Param("endDate")String endDate);

    int queryCourseEnd(@Param("userId") String userId,@Param("startDate") String startDate,@Param("endDate")String endDate);

    List<StudyDuration> queryStudyDurationList(@Param("userId") String userId);

    List<CourseInfo> queryCourseInfoList(@Param("userId") String userId,@Param("startDate") String startDate,@Param("endDate")String endDate);

    Integer queryRank(@Param("userId") String userId,@Param("startDate") String startDate,@Param("endDate")String endDate);

    Integer queryAllCount(@Param("userId") String userId,@Param("startDate") String startDate,@Param("endDate")String endDate);

    List<T_SHARE_CDXT_BKS_KCPK> queryKCPK(@Param("userId") String userId,@Param("week")long week);

    List<T_SHARE_CDXT_BKS_KCPK> queryJSPK(@Param("userId") String userId,@Param("week")long week);


    int findCourseDatalCoutnt(Integer id);

    void updateStudyCount(@Param("id") Integer id, @Param("i") int i);

    void InsertCourseDan(CourseInfoAiVo courseInfoAiVo);

    void InsertCourseDanTeacher(@Param("coursename")String coursename,@Param("teacher")String teacher);

    void InsertCourseOne(CourseInfoAiVo courseInfoAiVo);

    void updateMeteOne(CourseInfoMetel courseInfoMetel);

    void updateAiCourseOneTeacherAndChapList(@Param("teacherData")String teacherData, @Param("chapterListData")String chapterListData, @Param("coursename")String coursename, @Param("source")String source);

    void updateAiCourse(CourseInfoAiVo courseInfoAiVo);

    void InsertCourseOneMe(CourseInfoMetel courseInfoMetel);

    void updateCourseFilm(CourseInfoFilm courseInfoFilm);

    void updateFilmeOne(CourseInfoFilm courseInfoFilm);

    void insertBrowse(@Param("userid")Integer userid, @Param("id")Integer id);

    hotKey findKeyCourseNameAndHotNum(String courseName);

    void updateKeyCourseNameAndHotNum(@Param("courseName")String courseName ,@Param("hot") int hot);

    void addKeyCourseNameAndHotNum(String courseName);

    List<hotKey> getpopularKeyWordInfo(@Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize);

    List<categoryCode> getCourseType(@Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize);

    int getpopularKeyWordInfoNum(@Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize);

    int getCourseTypeNum(@Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize);

    List<categoryThird> getCourseThird(@Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize);

    int getCourseThirdeNum(@Param("pageNum")Integer pageNum,@Param("pageSize") Integer pageSize);

    List<GroupClass> getGroupClassList();

    int updateGroupClassSort(@Param("list")List<GroupClass> list);

    List<GroupMember> getGroupMemberList(@Param("groupId")int groupId);

    int updateGroupMemberSort(@Param("list")List<GroupMember> list);

    int queryMaxSortId();

    int addGroupClass(GroupClass groupClass);

    int deleteGroupClass(@Param("id")int id);

    GroupMember queryGroupMember(@Param("courseId")int courseId,@Param("groupId")int groupId);

    int queryGroupMemberMax(@Param("courseId")int courseId,@Param("groupId")int groupId);

    int addGroupMember(GroupMember groupMember);

    int deleteGroupMember(@Param("id") int id);

    int find();

    int findp(@Param("courseId")Integer courseId,@Param("groupClassId") Integer groupClassId);

    List<GroupClass> getGroupClassListPage(@Param("firstIndex")Integer firstIndex,@Param("pageSize")Integer pageSize);

    List<CourseInfo> getCourseInfoList(@Param("groupClassId")Integer groupClassId);

    int findCourseByUserIdLearnNum(String userid);

    void catTime(@Param("time")Timestamp time, @Param("couseid")String couseid);

    List<BrowseCourse> findtime(String userid);

    int  deleteGroupSort(@Param("groupId")int groupId);

    int updateGroupSort(List<GroupMember> list);

    int updateGroup(List<GroupClass> list);

}
