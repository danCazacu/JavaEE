package hw8;

import entity.CourseEntity;
import entity.OptionalCourseEntity;
import entity.OptionalPackageEntity;
import util.Constants;
import util.JPAUtil;

import javax.faces.bean.ManagedBean;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ManagedBean
public class SearchPage {

    private boolean typeFiler;
    private boolean nameFilter;
    private boolean yearFilter;
    private boolean semesterFilter;
    private boolean creditsFilter;
    private boolean optionalPackageFilter;

    private boolean optionalCourseFilter;
    private boolean compulsoryCourseFilter;

    private boolean semester1Filter;
    private boolean semester2Filter;

    private boolean year1Filter;
    private boolean year2Filter;
    private boolean year3Filter;

    private int minCreditsFilter = 1;
    private int maxCreditsFilter = 1;

    private String containsNameFilter ;

    private HashSet<OptionalPackageEntity> lstOptionalPackagesFilter;

    private List<CourseEntity> lstCompulsoryCourses = new ArrayList<>();
    private List<OptionalCourseEntity> lstOptionalCourses = new ArrayList<>();

    private List<SearchPageBean> lstSearcheResult = new ArrayList<>();

    public void doFilter(SearchPage searchPage ){

        try {
            if(!isTypeFiler()){

                compulsoryCourseFilter = true;
                optionalCourseFilter = true;
            }

            if (compulsoryCourseFilter == true) {

                CriteriaBuilder builder = JPAUtil.getEntityManager().getCriteriaBuilder();
                CriteriaQuery<CourseEntity> query = builder.createQuery(CourseEntity.class);
                Root<CourseEntity> courseRoot = query.from(CourseEntity.class);

                List<Predicate> andPredicates = new ArrayList<Predicate>();

                Metamodel m = JPAUtil.getEntityManager().getMetamodel();
                EntityType<CourseEntity> Course_ = m.entity(CourseEntity.class);

                Subquery<OptionalCourseEntity> subquery = query.subquery(OptionalCourseEntity.class);
                Root<OptionalCourseEntity> optionalCourseRoot = subquery.from(OptionalCourseEntity.class);
                subquery.select(optionalCourseRoot);

               // andPredicates.add(builder.exists(subquery).not());

                if (isNameFilter()) {
                    //criteria.add(builder.like(courseRoot.get(Course_.getName()), "%" + containsNameFilter.trim() + "%"));
                    andPredicates.add(builder.like(courseRoot.<String>get("name"), "%" + containsNameFilter.trim() + "%"));

                }

                List<Predicate> orPredicates = new ArrayList<Predicate>();

                if(isYearFilter()){

                    if(isYear1Filter() ){

                        andPredicates.add(builder.equal(courseRoot.<Integer>get("year"), 1));
                    }

                    if(isYear2Filter() ){

                        andPredicates.add(builder.equal(courseRoot.<Integer>get("year"), 2));
                    }

                    if(isYear3Filter() ){

                        andPredicates.add(builder.equal(courseRoot.<Integer>get("year"), 3));
                    }
                }

                if(isSemesterFilter()){

                    if(isSemester1Filter() ){

                        andPredicates.add(builder.equal(courseRoot.<Integer>get("semester"), 1));
                    }

                    if(isSemester2Filter() ){

                        andPredicates.add(builder.equal(courseRoot.<Integer>get("semester"), 2));
                    }
                }

                if(isCreditsFilter()){


                    andPredicates.add(builder.greaterThanOrEqualTo(courseRoot.<Integer>get("credits"), minCreditsFilter));

                    andPredicates.add(builder.lessThanOrEqualTo(courseRoot.<Integer>get("credits"), maxCreditsFilter));

                }


                //query.where(builder.or(orPredicates.toArray(new Predicate[orPredicates.size()])));

                query.where(builder.and(andPredicates.toArray(new Predicate[andPredicates.size()])));

                lstCompulsoryCourses = JPAUtil.getEntityManager().createQuery(query).getResultList();
            }

            if ( optionalCourseFilter == true) {

                CriteriaBuilder builder = JPAUtil.getEntityManager().getCriteriaBuilder();
                CriteriaQuery<OptionalCourseEntity> query = builder.createQuery(OptionalCourseEntity.class);
                Root<OptionalCourseEntity> optionalCourseRoot = query.from(OptionalCourseEntity.class);

                Metamodel m = JPAUtil.getEntityManager().getMetamodel();
                EntityType<OptionalCourseEntity> OptionalCourse_ = m.entity(OptionalCourseEntity.class);

                List<Predicate> andPredicates = new ArrayList<Predicate>();

                if (isNameFilter()) {

                    andPredicates.add(builder.like(optionalCourseRoot.<String>get("name"), "%" + containsNameFilter.trim() + "%"));
                }

                if(isYearFilter()){

                    if(isYear1Filter() ){

                        andPredicates.add(builder.equal(optionalCourseRoot.<Integer>get("year"), 1));
                    }

                    if(isYear2Filter() ){

                        andPredicates.add(builder.equal(optionalCourseRoot.<Integer>get("year"), 2));
                    }

                    if(isYear3Filter() ){

                        andPredicates.add(builder.equal(optionalCourseRoot.<Integer>get("year"), 3));
                    }
                }

                if(isSemesterFilter()){

                    if(isSemester1Filter() ){

                        andPredicates.add(builder.equal(optionalCourseRoot.<Integer>get("semester"), 1));
                    }

                    if(isSemester2Filter() ){

                        andPredicates.add(builder.equal(optionalCourseRoot.<Integer>get("semester"), 2));
                    }
                }

                if(isCreditsFilter()){


                    andPredicates.add(builder.greaterThanOrEqualTo(optionalCourseRoot.<Integer>get("credits"), minCreditsFilter));

                    andPredicates.add(builder.lessThanOrEqualTo(optionalCourseRoot.<Integer>get("credits"), maxCreditsFilter));

                }


                query.where(builder.and(andPredicates.toArray(new Predicate[andPredicates.size()])));

                TypedQuery<OptionalCourseEntity> q = JPAUtil.getEntityManager().createQuery(query);
                lstOptionalCourses = q.getResultList();

            }

        }catch(Exception e ){

            e.printStackTrace();
        }

        for (int i = 0; i < lstCompulsoryCourses.size(); i++) {

            SearchPageBean search = new SearchPageBean();
            search.setCourse(lstCompulsoryCourses.get(i));
            search.setOptionalPackageCode("");
            lstSearcheResult.add(search);
        }

        for(int i = 0 ; i < lstOptionalCourses.size(); i++){

            SearchPageBean search = new SearchPageBean();
            search.setCourse(lstOptionalCourses.get(i).getCourse());
            search.setOptionalPackageCode(lstOptionalCourses.get(i).getOptionalPackage().getCode());
            lstSearcheResult.add(search);
        }

        System.out.println(searchPage.nameFilter + "\t" + searchPage.containsNameFilter);
        System.out.println(searchPage.yearFilter);
        System.out.println(searchPage.semesterFilter);
        System.out.println(searchPage.creditsFilter);
        System.out.println(searchPage.optionalPackageFilter);
    }

    public void onTypeFilterChange() {
        if (!typeFiler){

            compulsoryCourseFilter = false;
            optionalCourseFilter = false;
        }
    }


    public SearchPage(){}

    public boolean isTypeFiler() {
        return typeFiler;
    }

    public void setTypeFiler(boolean typeFiler) {
        this.typeFiler = typeFiler;
    }

    public boolean isNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(boolean nameFilter) {
        this.nameFilter = nameFilter;
    }

    public boolean isYearFilter() {
        return yearFilter;
    }

    public void setYearFilter(boolean yearFilter) {
        this.yearFilter = yearFilter;
    }

    public boolean isSemesterFilter() {
        return semesterFilter;
    }

    public void setSemesterFilter(boolean semesterFilter) {
        this.semesterFilter = semesterFilter;
    }

    public boolean isCreditsFilter() {
        return creditsFilter;
    }

    public void setCreditsFilter(boolean creditsFilter) {
        this.creditsFilter = creditsFilter;
    }

    public boolean isOptionalPackageFilter() {
        return optionalPackageFilter;
    }

    public void setOptionalPackageFilter(boolean optionalPackageFilter) {
        this.optionalPackageFilter = optionalPackageFilter;
    }

    public boolean isOptionalCourseFilter() {

        return optionalCourseFilter;
    }

    public void setOptionalCourseFilter(boolean optionalCourseFilter) {

        if(typeFiler == false){

            this.optionalCourseFilter = false;
        }
        this.optionalCourseFilter = optionalCourseFilter;
    }

    public boolean isCompulsoryCourseFilter() {

        return compulsoryCourseFilter;
    }

    public void setCompulsoryCourseFilter(boolean compulsoryCourseFilter) {
        this.compulsoryCourseFilter = compulsoryCourseFilter;
    }

    public boolean isSemester1Filter() {
        return semester1Filter;
    }

    public void setSemester1Filter(boolean semester1Filter) {
        this.semester1Filter = semester1Filter;
    }

    public boolean isSemester2Filter() {
        return semester2Filter;
    }

    public void setSemester2Filter(boolean semester2Filter) {
        this.semester2Filter = semester2Filter;
    }

    public HashSet<OptionalPackageEntity> getLstOptionalPackagesFilter() {
        return lstOptionalPackagesFilter;
    }

    public void setLstOptionalPackagesFilter(HashSet<OptionalPackageEntity> lstOptionalPackagesFilter) {
        this.lstOptionalPackagesFilter = lstOptionalPackagesFilter;
    }

    public boolean isYear1Filter() {
        return year1Filter;
    }

    public void setYear1Filter(boolean year1Filter) {
        this.year1Filter = year1Filter;
    }

    public boolean isYear2Filter() {
        return year2Filter;
    }

    public void setYear2Filter(boolean year2Filter) {
        this.year2Filter = year2Filter;
    }

    public boolean isYear3Filter() {
        return year3Filter;
    }

    public void setYear3Filter(boolean year3Filter) {
        this.year3Filter = year3Filter;
    }

    public int getMinCreditsFilter() {
        return minCreditsFilter;
    }

    public void setMinCreditsFilter(int minCreditsFilter) {
        this.minCreditsFilter = minCreditsFilter;
    }

    public int getMaxCreditsFilter() {
        return maxCreditsFilter;
    }

    public void setMaxCreditsFilter(int maxCreditsFilter) {
        this.maxCreditsFilter = maxCreditsFilter;
    }

    public String getContainsNameFilter() {
        return containsNameFilter;
    }

    public void setContainsNameFilter(String containsNameFilter) {
        this.containsNameFilter = containsNameFilter;
    }

    public List<CourseEntity> getLstCompulsoryCourses() {
        return lstCompulsoryCourses;
    }

    public void setLstCompulsoryCourses(List<CourseEntity> lstCompulsoryCourses) {
        this.lstCompulsoryCourses = lstCompulsoryCourses;
    }

    public List<OptionalCourseEntity> getLstOptionalCourses() {
        return lstOptionalCourses;
    }

    public void setLstOptionalCourses(ArrayList<OptionalCourseEntity> lstOptionalCourses) {
        this.lstOptionalCourses = lstOptionalCourses;
    }

    public List<SearchPageBean> getLstSearcheResult() {
        return lstSearcheResult;
    }

    public void setLstSearcheResult(List<SearchPageBean> lstSearcheResult) {
        this.lstSearcheResult = lstSearcheResult;
    }
}
